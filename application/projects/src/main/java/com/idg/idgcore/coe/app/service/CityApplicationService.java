package com.idg.idgcore.coe.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.dto.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.CityDTO;
import com.idg.idgcore.coe.dto.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.CityAssembler;
import com.idg.idgcore.coe.domain.entity.MutationEntity;
import com.idg.idgcore.coe.domain.entity.CityEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.ICityDomainService;
import com.idg.idgcore.coe.domain.service.IMutationsDomainService;
import com.idg.idgcore.enumerations.core.ResponseCodeType;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.DELETED;

@Slf4j
@Service ("cityApplicationService")
public class CityApplicationService extends AbstractApplicationService
        implements ICityApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ICityDomainService cityDomainService;
    @Autowired
    private CityAssembler cityAssembler;

    public CityDTO getCityByCode (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException {
        log.info("In getCityByCode with parameters sessionContext {}, cityDTO {}",
                sessionContext, cityDTO);
        CityDTO result;
        fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        if (isAuthorized(cityDTO.getAuthorized())) {
            CityEntity cityEntity = cityDomainService.getCityByCode(
                    cityDTO.getCityCode());
            result = cityAssembler.convertEntityToDto(cityEntity);
        }
        else {
            MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                    cityDTO.getTaskIdentifier());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CityDTO.class);
            }
            catch (JsonProcessingException jpe) {
                throw new FatalException(ResponseCodeType.FATAL_ERROR,
                        "Error while processing the Json", jpe);
            }
            finally {
                Interaction.close();
            }
        }
        return result;
    }

    public List<CityDTO> getCities (SessionContext sessionContext) throws FatalException {
        log.info("In getCities with parameters sessionContext {}", sessionContext);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CityDTO> cityDTOList = new ArrayList<>();
        try {
            cityDTOList.addAll(cityDomainService.getCities().stream()
                    .map(entity -> cityAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode());
            cityDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CityEntity cityEntity = null;
                try {
                    cityEntity = objectMapper.readValue(data, CityEntity.class);
                }
                catch (JsonProcessingException e) {
                    throw new BusinessException("BE001", "Error while parsing the record", e);
                }
                return cityAssembler.convertEntityToDto(cityEntity);
            }).collect(Collectors.toList()));
            cityDTOList = cityDTOList.stream().collect(
                    Collectors.groupingBy(CityDTO::getCityCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CityDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            cityDTOList = cityDTOList.stream().filter(dto -> !dto.getStatus().equals(DELETED))
                    .collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return cityDTOList;
    }

    public TransactionStatus processCity (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException {
        log.info("In processCity with parameters sessionContext {}, cityDTO {}",
                sessionContext, cityDTO);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(cityDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return transactionStatus;
    }

    @Override
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        CityDTO cityDTO = objMapper.readValue(data, CityDTO.class);
        save(cityDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return null;
    }

    @Override
    public void save (CityDTO cityDTO) {
        cityDomainService.save(cityDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return CityDTO.builder().build().getTaskCode();
    }

}

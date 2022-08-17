package com.idg.idgcore.coe.app.service.city;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.city.CityDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.city.CityAssembler;
import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.city.ICityDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Arrays;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.CHECKER;
import static com.idg.idgcore.coe.common.Constants.DRAFT;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

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
    @Autowired
    private MutationAssembler mutationAssembler;

    public CityDTO getCityByCode (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCityByCode with parameters sessionContext {}, cityDTO {}",
                    sessionContext, cityDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CityDTO result = null;
        try {
            if (isAuthorized(cityDTO.getAuthorized())) {
                CityEntity cityEntity = cityDomainService.getCityByCode(
                        cityDTO.getCityCode());
                result = cityAssembler.convertEntityToDto(cityEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        cityDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CityDTO.class);
                result = cityAssembler.setAuditFields(mutationEntity,result);
                fillTransactionStatus(transactionStatus);
            }
        }
        catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return result;
    }

    public List<CityDTO> getCities (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCities with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CityDTO> cityDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            cityDTOList.addAll(cityDomainService.getCities().stream()
                    .map(entity -> cityAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            cityDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CityDTO cityDTO = null;
                try {
                    cityDTO = objectMapper.readValue(data, CityDTO.class);
                    cityDTO = cityAssembler.setAuditFields(entity,cityDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return cityDTO;
            }).collect(Collectors.toList()));
            cityDTOList = cityDTOList.stream().collect(
                    Collectors.groupingBy(CityDTO::getCityCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CityDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
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
        if (log.isInfoEnabled()) {
            log.info("In processCity with parameters sessionContext {}, cityDTO {}",
                    sessionContext, cityDTO);
        }
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
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
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
        return cityAssembler.convertEntityToDto(cityDomainService.getCityByCode(code));
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
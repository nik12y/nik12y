package com.idg.idgcore.coe.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.dto.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.CountryDTO;
import com.idg.idgcore.coe.dto.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.CountryAssembler;
import com.idg.idgcore.coe.domain.entity.CountryEntity;
import com.idg.idgcore.coe.domain.entity.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.ICountryDomainService;
import com.idg.idgcore.coe.domain.service.IMutationsDomainService;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.ResponseCodeType;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.DELETED;

@Slf4j
@Service ("countryApplicationService")
public class CountryApplicationService extends AbstractApplicationService
        implements ICountryApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ICountryDomainService countryDomainService;
    @Autowired
    private CountryAssembler countryAssembler;

    public CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException {
        log.info("In getCountryByCode with parameters sessionContext {}, countryDTO {}",
                sessionContext, countryDTO);
        CountryDTO result;
        fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        if (isAuthorized(countryDTO.getAuthorized())) {
            CountryEntity countryEntity = countryDomainService.getCountryByCode(
                    countryDTO.getCountryCode());
            result = countryAssembler.convertEntityToDto(countryEntity);
        }
        else {
            MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                    countryDTO.getTaskIdentifier());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CountryDTO.class);
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

    public List<CountryDTO> getCountries (SessionContext sessionContext) throws FatalException {
        log.info("In getCountries with parameters sessionContext {}", sessionContext);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CountryDTO> countryDTOList = new ArrayList<>();
        try {
            countryDTOList.addAll(countryDomainService.getCountries().stream()
                    .map(entity -> countryAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode());
            countryDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CountryEntity countryEntity = null;
                try {
                    countryEntity = objectMapper.readValue(data, CountryEntity.class);
                }
                catch (JsonProcessingException e) {
                    throw new BusinessException("BE001", "Operation failed, error while parsing the record.", e);
                }
                return countryAssembler.convertEntityToDto(countryEntity);
            }).collect(Collectors.toList()));
            countryDTOList = countryDTOList.stream().collect(
                    Collectors.groupingBy(CountryDTO::getCountryCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CountryDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            countryDTOList = countryDTOList.stream().filter(dto -> !dto.getStatus().equals(DELETED))
                    .collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return countryDTOList;
    }

    @Transactional
    public TransactionStatus processCountry (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException {
        log.info("In processCountry with parameters sessionContext {}, countryDTO {}",
                sessionContext, countryDTO);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(countryDTO);
            fillTransactionStatus(transactionStatus);
            registerInteractionForClosure();
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

    private void registerInteractionForClosure () {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            public void beforeCommit () {
                try {
                    Interaction.close();
                }
                catch (FatalException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        CountryDTO countryDTO = objMapper.readValue(data, CountryDTO.class);
        save(countryDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return countryAssembler.convertEntityToDto(countryDomainService.getCountryByCode(code));
    }

    @Override
    public void save (CountryDTO countryDTO) {
        countryDomainService.save(countryDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return CountryDTO.builder().build().getTaskCode();
    }

}

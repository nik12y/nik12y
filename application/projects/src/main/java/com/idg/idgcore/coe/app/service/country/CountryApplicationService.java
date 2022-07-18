package com.idg.idgcore.coe.app.service.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.country.CountryAssembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.country.ICountryDomainService;
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
    @Autowired
    private MutationAssembler mutationAssembler;

    public CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountryByCode with parameters sessionContext {}, countryDTO {}",
                    sessionContext, countryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CountryDTO result = null;
        try {
            if (isAuthorized(countryDTO.getAuthorized())) {
                CountryEntity countryEntity = countryDomainService.getCountryByCode(
                        countryDTO.getCountryCode());
                result = countryAssembler.convertEntityToDto(countryEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        countryDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CountryDTO.class);
                result = countryAssembler.setAuditFields(mutationEntity,result);
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

    public List<CountryDTO> getCountries (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CountryDTO> countryDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            unauthorizedEntities = unauthorizedEntities.stream()
                    .filter(dto -> !dto.getStatus().equals(DRAFT)).collect(Collectors.toList());
            countryDTOList.addAll(countryDomainService.getCountries().stream()
                    .map(entity -> countryAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            countryDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CountryDTO countryDTO = null;
                try {
                    countryDTO = objectMapper.readValue(data, CountryDTO.class);
                    countryAssembler.setAuditFields(entity,countryDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return countryDTO;
            }).collect(Collectors.toList()));
            countryDTOList = countryDTOList.stream().collect(
                    Collectors.groupingBy(CountryDTO::getCountryCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CountryDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
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
        if (log.isInfoEnabled()) {
            log.info("In processCountry with parameters sessionContext {}, countryDTO {}",
                    sessionContext, countryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(countryDTO);
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
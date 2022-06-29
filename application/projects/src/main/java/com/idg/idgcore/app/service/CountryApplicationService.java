package com.idg.idgcore.app.service;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.app.config.*;
import com.idg.idgcore.app.dto.*;
import com.idg.idgcore.domain.entity.*;
import com.idg.idgcore.domain.process.*;
import com.idg.idgcore.domain.service.*;
import com.idg.idgcore.dto.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.*;
import com.idg.idgcore.infra.exception.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.idg.idgcore.common.Constants.Status.DELETED;

@Slf4j
@Service ("countryApplicationService")
public class CountryApplicationService
        extends AbstractApplicationService
        implements ICountryApplicationService
{

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private ProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ICountryDomainService countryDomainService;

    public CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException {
        log.info("In getCountryByCode with parameters sessionContext {}, countryDTO {}", sessionContext, countryDTO);
        CountryDTO result;
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        if (isAuthorized(countryDTO.getAuthorized())) {
            CountryEntity countryEntity = countryDomainService.getCountryByCode(
                    countryDTO.getCountryCode());
//            transactionStatus.setReplyMessage(mapper.map(countryEntity, CountryDTO.class).toString());
            result = mapper.map(countryEntity, CountryDTO.class);
        }
        else
        {
            MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                    countryDTO.getTaskIdentifier());
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
//                transactionStatus.setReplyMessage(objectMapper.readValue(payload.getData(), CountryDTO.class).toString());
                result = objectMapper.readValue(payload.getData(), CountryDTO.class);
            }
            catch (JsonProcessingException jpe) {
                throw new FatalException(ResponseCodeType.FATAL_ERROR,"Error while processing the Json",jpe);
            }
            finally {
                Interaction.close();
            }
        }

        return result;
    }

    public List<CountryDTO> getCountries (SessionContext sessionContext)
            throws FatalException {
        log.info("In getCountries with parameters sessionContext {}", sessionContext);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        ObjectMapper objectMapper = new ObjectMapper();
        List<CountryDTO> countryDTOList = new ArrayList<>();

        try {
            countryDTOList.addAll(countryDomainService.getCountries().stream().map(entity ->
            {
                return mapper.map(entity, CountryDTO.class);
            }).collect(Collectors.toList()));
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode());
            countryDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CountryEntity countryEntity = null;
                try {
                    countryEntity = objectMapper.readValue(data,
                            CountryEntity.class);
                }
                catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                return mapper.map(countryEntity, CountryDTO.class);
            }).collect(Collectors.toList()));
            countryDTOList.stream().collect(Collectors.groupingBy(CountryDTO::getCountryCode,
                    Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CountryDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(
                    Collectors.toList());
            countryDTOList.stream().filter(dto -> !dto.getStatus().equals(DELETED)).collect(
                    Collectors.toList());

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

    public TransactionStatus processCountry (SessionContext sessionContext, CountryDTO countryDTO) throws FatalException {
        log.info("In processCountry with parameters sessionContext {}, countryDTO {}", sessionContext, countryDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        try
        {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(countryDTO);

            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException){
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception){
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
            CountryDTO countryDTO = objMapper.readValue(data, CountryDTO.class);
            save(countryDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return null;
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

    private String writeObjectToString (Object data)
            throws FatalException {
        log.info("In generateResponse with parameters data {}", data);

        String result = null;
        try {
            ObjectMapper objMapper = new ObjectMapper();
            result = objMapper.writeValueAsString(data);
        }
        catch (JsonProcessingException jpe)
        {
            throw new FatalException(ResponseCodeType.FATAL_ERROR,"Error while parsing the transaction status.", jpe);
        }
        return result;
    }

    private void closeInteraction (Interaction interaction) {
        try {
            interaction.close();
        }
        catch (FatalException e) {
            throw new RuntimeException(e);
        }
    }
}

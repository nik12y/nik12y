package com.idg.idgcore.coe.app.service.language;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.language.ILanguageDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
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

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("languageApplicationService")
public class LanguageApplicationService extends AbstractApplicationService
        implements ILanguageApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ILanguageDomainService languageDomainService;
    @Autowired
    private LanguageAssembler languageAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public LanguageDTO getLanguageByCode (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getLanguageByCode with parameters sessionContext {}, languageDTO {}",
                    sessionContext, languageDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        LanguageDTO result = null;
        try {
            if (isAuthorized(languageDTO.getAuthorized())) {
                LanguageEntity languageEntity = languageDomainService.getLanguageByCode(
                        languageDTO.getLanguageCode());
                result = languageAssembler.convertEntityToDto(languageEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        languageDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), LanguageDTO.class);
                result = languageAssembler.setAuditFields(mutationEntity,result);
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

    public List<LanguageDTO> getLanguages (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getLanguages with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<LanguageDTO> languageDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            languageDTOList.addAll(languageDomainService.getLanguages().stream()
                    .map(entity -> languageAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            languageDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                LanguageDTO languageDTO = null;
                try {
                    languageDTO = objectMapper.readValue(data, LanguageDTO.class);
                    languageDTO = languageAssembler.setAuditFields(entity,languageDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return languageDTO;
            }).collect(Collectors.toList()));
            languageDTOList = languageDTOList.stream().collect(
                    Collectors.groupingBy(LanguageDTO::getLanguageCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(LanguageDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return languageDTOList;
    }

    @Transactional
    public TransactionStatus processLanguage (SessionContext sessionContext, LanguageDTO languageDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processLanguage with parameters sessionContext {}, languageDTO {}",
                    sessionContext, languageDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(languageDTO);
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
        LanguageDTO languageDTO = objMapper.readValue(data, LanguageDTO.class);
        save(languageDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return languageAssembler.convertEntityToDto(languageDomainService.getLanguageByCode(code));
    }

    @Override
    public void save (LanguageDTO languageDTO) {
        languageDomainService.save(languageDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return LanguageDTO.builder().build().getTaskCode();
    }

}
package com.idg.idgcore.coe.app.service.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.transaction.TransactionAssembler;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.transaction.ITransactionDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service ("transactionApplicationService")
public class TransactionApplicationService extends AbstractApplicationService
        implements ITransactionApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ITransactionDomainService transactionDomainService;
    @Autowired
    private TransactionAssembler transactionAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public TransactionDTO getTransactionByCode (SessionContext sessionContext, TransactionDTO transactionDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getTransactionByCode with parameters sessionContext {}, transactionDTO {}",
                    sessionContext, transactionDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        TransactionDTO result = null;
        try {
            if (isAuthorized(transactionDTO.getAuthorized())) {
                TransactionEntity transactionEntity = transactionDomainService.getTransactionByCode(
                        transactionDTO.getTransactionCode());
                result = transactionAssembler.convertEntityToDto(transactionEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        transactionDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), TransactionDTO.class);
                result = transactionAssembler.setAuditFields(mutationEntity,result);
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

    public List<TransactionDTO> getTransactions (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<TransactionDTO> transactionDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            transactionDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                TransactionDTO transactionDTO = null;
                try {
                    transactionDTO = objectMapper.readValue(data, TransactionDTO.class);
                    transactionDTO = transactionAssembler.setAuditFields(entity,transactionDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return transactionDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return transactionDTOList;
    }

    public TransactionStatus processTransaction (SessionContext sessionContext, TransactionDTO transactionDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processTransaction with parameters sessionContext {}, transactionDTO {}",
                    sessionContext, transactionDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(transactionDTO);
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
        TransactionDTO transactionDTO = objMapper.readValue(data, TransactionDTO.class);
        save(transactionDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return transactionAssembler.convertEntityToDto(transactionDomainService.getTransactionByCode(code));
    }

    @Override
    public void save (TransactionDTO transactionDTO) {
        transactionDomainService.save(transactionDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return TransactionDTO.builder().build().getTaskCode();
    }

}
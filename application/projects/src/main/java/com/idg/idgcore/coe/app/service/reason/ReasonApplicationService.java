package com.idg.idgcore.coe.app.service.reason;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.domain.assembler.reason.ReasonAssembler;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.reason.IReasonDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
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
@Service ("reasonApplicationService")
public class ReasonApplicationService extends AbstractApplicationService
        implements IReasonApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IReasonDomainService reasonDomainService;
    @Autowired
    private ReasonAssembler reasonAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public ReasonDTO getReasonByCode (SessionContext sessionContext, ReasonDTO reasonDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getReasonByCode with parameters sessionContext {}, reasonDTO {}",
                    sessionContext, reasonDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ReasonDTO result = null;
        try {
            if (isAuthorized(reasonDTO.getAuthorized())) {
                ReasonEntity reasonEntity = reasonDomainService.getReasonByCode(
                        reasonDTO.getPrimaryReasonCode());
                result = reasonAssembler.convertEntityToDto(reasonEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        reasonDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), ReasonDTO.class);
                result = reasonAssembler.setAuditFields(mutationEntity,result);
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

    public List<ReasonDTO> getReasons (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getReasons with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ReasonDTO> reasonDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            reasonDTOList.addAll(reasonDomainService.getReasons().stream()
                    .map(entity -> reasonAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            reasonDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                ReasonDTO reasonDTO = null;
                try {
                    reasonDTO = objectMapper.readValue(data, ReasonDTO.class);
                    reasonDTO = reasonAssembler.setAuditFields(entity,reasonDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return reasonDTO;
            }).collect(Collectors.toList()));
            reasonDTOList = reasonDTOList.stream().collect(
                    Collectors.groupingBy(ReasonDTO::getPrimaryReasonCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(ReasonDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return reasonDTOList;
    }

    @Transactional
    public TransactionStatus processReason (SessionContext sessionContext, ReasonDTO reasonDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processReason with parameters sessionContext {}, reasonDTO {}",
                    sessionContext, reasonDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(reasonDTO);
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
        ReasonDTO reasonDTO = objMapper.readValue(data, ReasonDTO.class);
        save(reasonDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return reasonAssembler.convertEntityToDto(reasonDomainService.getReasonByCode(code));
    }

    @Override
    public void save (ReasonDTO reasonDTO) {
        reasonDomainService.save(reasonDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return ReasonDTO.builder().build().getTaskCode();
    }

}
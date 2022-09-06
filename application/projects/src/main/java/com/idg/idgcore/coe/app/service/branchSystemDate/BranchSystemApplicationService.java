package com.idg.idgcore.coe.app.service.branchSystemDate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.branchSystemDate.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchSystemDate.IBranchSystemDateDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("branchSystemApplicationService")
public class BranchSystemApplicationService extends AbstractApplicationService
        implements IBranchSystemApplicationService{

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBranchSystemDateDomainService branchSystemDomainService;
    @Autowired
    private BranchSystemDateAssembler branchSystemAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public BranchSystemDateDTO getBranchSystemDateByCode (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getBranchSystemByCode with parameters sessionContext {}, branchSystemDateDTO {}",
                    sessionContext, branchSystemDateDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BranchSystemDateDTO result = null;
        try {
            if (isAuthorized(branchSystemDateDTO.getAuthorized())) {
                BranchSystemDateEntity branchSystemDateEntity = branchSystemDomainService.getBranchSystemDateByCode(
                        branchSystemDateDTO.getBranchCode());
                result = branchSystemAssembler.convertEntityToDto(branchSystemDateEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        branchSystemDateDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BranchSystemDateDTO.class);
                result = branchSystemAssembler.setAuditFields(mutationEntity,result);
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

    public List<BranchSystemDateDTO> getBranchSystemDateAll (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getBranchSystemDateAll with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BranchSystemDateDTO> branchSystemDateDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            branchSystemDateDTOList.addAll(branchSystemDomainService.getBranchSystemDateAll().stream()
                    .map(entity -> branchSystemAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            branchSystemDateDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BranchSystemDateDTO branchSystemDateDTO = null;
                try {
                    branchSystemDateDTO = objectMapper.readValue(data, BranchSystemDateDTO.class);
                    branchSystemDateDTO = branchSystemAssembler.setAuditFields(entity,branchSystemDateDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return branchSystemDateDTO;
            }).collect(Collectors.toList()));
            branchSystemDateDTOList = branchSystemDateDTOList.stream().collect(
                    Collectors.groupingBy(BranchSystemDateDTO::getBranchCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(BranchSystemDateDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return branchSystemDateDTOList;
    }

    public TransactionStatus processBranchSystemDate (SessionContext sessionContext, BranchSystemDateDTO branchSystemDateDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processBranchSystemDate with parameters sessionContext {}, branchSystemDateDTO {}",
                    sessionContext, branchSystemDateDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(branchSystemDateDTO);
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
        BranchSystemDateDTO branchSystemDateDTO = objMapper.readValue(data, BranchSystemDateDTO.class);
        save(branchSystemDateDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return branchSystemAssembler.convertEntityToDto(branchSystemDomainService.getBranchSystemDateByCode(code));
    }

    @Override
    public void save (BranchSystemDateDTO branchSystemDateDTO) {
        branchSystemDomainService.save(branchSystemDateDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return BranchSystemDateDTO.builder().build().getTaskCode();
    }


}

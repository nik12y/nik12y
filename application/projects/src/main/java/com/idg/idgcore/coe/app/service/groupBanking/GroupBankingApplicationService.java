package com.idg.idgcore.coe.app.service.groupBanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.groupBanking.IGroupBankingDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
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

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("groupBankingApplicationService")
public class GroupBankingApplicationService extends AbstractApplicationService
        implements IGroupBankingApplicationService {

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IGroupBankingDomainService iGroupBankingDomainService;
    @Autowired
    private GroupBankingAssembler groupBankingAssembler;

    public GroupBankingDTO getGroupBankByCode(SessionContext sessionContext, GroupBankingDTO groupBankingDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getGroupBankByCode with parameters sessionContext {}, groupBankingDTO {}",
                    sessionContext, groupBankingDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        GroupBankingDTO result = null;
        try {
            if (isAuthorized(groupBankingDTO.getAuthorized())) {
                GroupBankingEntity groupBankingEntity = iGroupBankingDomainService.getGroupBankByCode(
                        groupBankingDTO.getGroupBankingCode());
                result = groupBankingAssembler.convertEntityToDto(groupBankingEntity);
            } else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        groupBankingDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), GroupBankingDTO.class);
                result = groupBankingAssembler.setAuditFields(mutationEntity, result);
                fillTransactionStatus(transactionStatus);
            }
        } catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return result;
    }

    public List<GroupBankingDTO> getGroupBanks(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getGroupBanks with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<GroupBankingDTO> groupBankingDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            groupBankingDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                GroupBankingDTO groupBankingDTO = null;
                try {
                    groupBankingDTO = objectMapper.readValue(data, GroupBankingDTO.class);
                    groupBankingDTO = groupBankingAssembler.setAuditFields(entity, groupBankingDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return groupBankingDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return groupBankingDTOList;
    }


    public TransactionStatus processGroupBanking(SessionContext sessionContext, GroupBankingDTO groupBankingDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processGroupBanking with parameters sessionContext {}, groupBankingDTO {}",
                    sessionContext, groupBankingDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(groupBankingDTO);
            fillTransactionStatus(transactionStatus);
        } catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        GroupBankingDTO groupBankingDTO = objMapper.readValue(data, GroupBankingDTO.class);
        save(groupBankingDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return groupBankingAssembler.convertEntityToDto(iGroupBankingDomainService.getGroupBankByCode(code));
    }

    @Override
    public void save(GroupBankingDTO groupBankingDTO) {
        iGroupBankingDomainService.save(groupBankingDTO);
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return GroupBankingDTO.builder().build().getTaskCode();
    }


}




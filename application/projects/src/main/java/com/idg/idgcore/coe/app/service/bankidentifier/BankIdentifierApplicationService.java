package com.idg.idgcore.coe.app.service.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bankidentifier.IBankIdentifierDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
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
@Service("bankIdentifierApplicationService")
public class BankIdentifierApplicationService  extends AbstractApplicationService implements IBankIdentifierApplicationService{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBankIdentifierDomainService bankIdentifierDomainService;
    @Autowired
    BankIdentifierAssembler bankIdentifierAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public BankIdentifierDTO getBankIdentifierByCode(SessionContext sessionContext, BankIdentifierDTO bankIdentifierDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In getBankIdentifierByCode with parameters sessionContext {}, bankIdentifierDTO {}",
                    sessionContext, bankIdentifierDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BankIdentifierDTO result = null;
        try {
            if (isAuthorized(bankIdentifierDTO.getAuthorized())) {
                BankIdentifierEntity bankIdentifierEntity = bankIdentifierDomainService.getBankIdentifierByCode(
                        bankIdentifierDTO.getBankIdentifierCode());
                result = bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        bankIdentifierDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BankIdentifierDTO.class);
                result = bankIdentifierAssembler.setAuditFields(mutationEntity,result);
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

    public List<BankIdentifierDTO> getBankIdentifiers(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getBankIdentifiers with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BankIdentifierDTO> bankIdentifierDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            bankIdentifierDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BankIdentifierDTO bankIdentifierDTO = null;
                try {
                    bankIdentifierDTO = objectMapper.readValue(data, BankIdentifierDTO.class);
                    bankIdentifierDTO = bankIdentifierAssembler.setAuditFields(entity,bankIdentifierDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return bankIdentifierDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return bankIdentifierDTOList;
    }

    public TransactionStatus processBankIdentifier(SessionContext sessionContext, BankIdentifierDTO bankIdentifierDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processBankIdentifier with parameters sessionContext {}, bankIdentifierDTO {}",
                    sessionContext, bankIdentifierDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(bankIdentifierDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if (!Interaction.isLastInteraction())
            {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        BankIdentifierDTO bankIdentifierDTO = objMapper.readValue(data, BankIdentifierDTO.class);
        save(bankIdentifierDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return bankIdentifierAssembler.convertEntityToDto(bankIdentifierDomainService.getBankIdentifierByCode(code));
    }

    @Override
    public void save(BankIdentifierDTO bankIdentifierDTO) {
        bankIdentifierDomainService.save(bankIdentifierDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return BankIdentifierDTO.builder().build().getTaskCode();
    }
}

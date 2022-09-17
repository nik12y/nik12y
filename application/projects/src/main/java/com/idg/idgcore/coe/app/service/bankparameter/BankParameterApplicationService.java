package com.idg.idgcore.coe.app.service.bankparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.bankparameter.BankParameterAssembler;
import com.idg.idgcore.coe.domain.entity.bankparameter.BankParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bankparameter.IBankParameterDomainService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;



import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("bankParameterApplicationService")
public class BankParameterApplicationService extends AbstractApplicationService
        implements IBankParameterApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBankParameterDomainService bankParameterDomainService;
    @Autowired
    private BankParameterAssembler bankParameterAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public List<BankParameterDTO> searchBankParameter (SessionContext sessionContext,
            BankParameterDTO bankParameterDTO)
            throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In searchBankParameter with parameters sessionContext {}, bankParameterDTO {}",
                    sessionContext, bankParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        List<BankParameterDTO> result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(
                    bankParameterDTO.getTaskCode(), bankParameterDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BankParameterDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, BankParameterDTO.class);
                    dto = bankParameterAssembler.setAuditFields(entity, dto);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList();
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        if (log.isInfoEnabled()) {
            log.info("RETURNING searchBankParameter with {}", result);
        }
        return result;
    }

    public BankParameterDTO getBankParameterByBankCode (SessionContext sessionContext,
            BankParameterDTO bankParameterDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getBankParameterByCode with parameters sessionContext {}, bankParameterDTO {}",
                    sessionContext, bankParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BankParameterDTO result = null;
        try {
            if (isAuthorized(bankParameterDTO.getAuthorized())) {
                BankParameterEntity bankParameterEntity = bankParameterDomainService.getBankParameterByBankCode(
                        bankParameterDTO.getBankCode());
                result = bankParameterAssembler.convertEntityToDto(bankParameterEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        bankParameterDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BankParameterDTO.class);
                result = bankParameterAssembler.setAuditFields(mutationEntity, result);
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

    public List<BankParameterDTO> getBankParameters (SessionContext sessionContext)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BankParameterDTO> bankParameterDTOList = new ArrayList<>();
        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getMutations(
                    getTaskCode());
            bankParameterDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BankParameterDTO bankParameterDTO = null;
                try {
                    bankParameterDTO = objectMapper.readValue(data, BankParameterDTO.class);
                    bankParameterDTO = bankParameterAssembler.setAuditFields(entity,
                            bankParameterDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return bankParameterDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return bankParameterDTOList;
    }

    public TransactionStatus processBankParameter (SessionContext sessionContext,
            BankParameterDTO bankParameterDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In processBankParameter with parameters sessionContext {}, bankParameterDTO {}",
                    sessionContext, bankParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(bankParameterDTO);
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
        BankParameterDTO bankParameterDTO = objMapper.readValue(data, BankParameterDTO.class);
        save(bankParameterDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return bankParameterAssembler.convertEntityToDto(
                bankParameterDomainService.getBankParameterByBankCode(code));
    }

    @Override
    public void save (BankParameterDTO bankParameterDTO) {
        bankParameterDomainService.save(bankParameterDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return BankParameterDTO.builder().build().getTaskCode();
    }

}
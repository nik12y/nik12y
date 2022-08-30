package com.idg.idgcore.coe.app.service.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.bank.BankAssembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bank.IBankDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
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
@Service("bankApplicationService")
public class BankApplicationService extends AbstractApplicationService implements IBankApplicationService{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBankDomainService bankDomainService;
    @Autowired
    private BankAssembler bankAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;


    public BankDTO getBankByCode(SessionContext sessionContext, BankDTO bankDTO) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getBankByCode with parameters sessionContext {}, bankDTO {}",
                    sessionContext, bankDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BankDTO result = null;
        try {
            if (isAuthorized(bankDTO.getAuthorized())) {
                BankEntity bankEntity = bankDomainService.getBankByCode(
                        bankDTO.getBankCode());
                result = bankAssembler.convertEntityToDto(bankEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        bankDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BankDTO.class);
                result = bankAssembler.setAuditFields(mutationEntity,result);
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


    public List<BankDTO> getBanks(SessionContext sessionContext) throws FatalException {

        if (log.isInfoEnabled()) {
            log.info("In getBanks with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BankDTO> bankDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            bankDTOList.addAll(bankDomainService.getBanks().stream()
                    .map(entity -> bankAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            bankDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BankDTO bankDTO = null;
                try {
                    bankDTO = objectMapper.readValue(data, BankDTO.class);
                    bankDTO = bankAssembler.setAuditFields(entity,bankDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return bankDTO;
            }).collect(Collectors.toList()));
            bankDTOList = bankDTOList.stream().collect(
                    Collectors.groupingBy(BankDTO::getBankCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(BankDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return bankDTOList;
    }


    public TransactionStatus processBank(SessionContext sessionContext, BankDTO bankDTO) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processBank with parameters sessionContext {}, bankDTO {}",
                    sessionContext, bankDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(bankDTO);
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
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        BankDTO bankDTO = objMapper.readValue(data, BankDTO.class);
        save(bankDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return bankAssembler.convertEntityToDto(bankDomainService.getBankByCode(code));
    }

    @Override
    public void save(BankDTO bankDTO) {
        bankDomainService.save(bankDTO);
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return BankDTO.builder().build().getTaskCode();
    }







}

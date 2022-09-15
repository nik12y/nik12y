package com.idg.idgcore.coe.app.service.currencypair;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.currencypair.CurrencyPairAssembler;
import com.idg.idgcore.coe.domain.entity.currencypair.CurrencyPairEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.currencypair.ICurrencyPairDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.currencypair.CurrencyPairDTO;
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

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("currencyPairApplicationService")
public class CurrencyPairApplicationService extends AbstractApplicationService implements ICurrencyPairApplicationService {

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ICurrencyPairDomainService currencyPairDomainService;
    @Autowired
    private CurrencyPairAssembler currencyPairAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public CurrencyPairDTO getCurrencyPairById(SessionContext sessionContext, CurrencyPairDTO currencyPairDTO) throws FatalException{
        if (log.isInfoEnabled()) {
            log.info("In getCurrencyPairById with parameters sessionContext{}, currencyPairDTO {}",
                    sessionContext, currencyPairDTO);
        }

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyPairDTO result = null;
        try{
            if (isAuthorized(currencyPairDTO.getAuthorized())) {
                CurrencyPairEntity currencyPairEntity = currencyPairDomainService.getCurrencyPairById(
                        currencyPairDTO.getPairId());
                result = currencyPairAssembler.convertEntityToDto(currencyPairEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyPairDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CurrencyPairDTO.class);
                result = currencyPairAssembler.setAuditFields(mutationEntity,result);
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

    @Transactional(Transactional.TxType.NOT_SUPPORTED)
    public List<CurrencyPairDTO> getCurrencyPairs (SessionContext sessionContext) throws FatalException {

        if (log.isInfoEnabled()) {
            log.info("In getCurrencyPairs with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyPairDTO> currencyPairDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            currencyPairDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CurrencyPairDTO currencyPairDTO = null;
                try {
                    currencyPairDTO = objectMapper.readValue(data, CurrencyPairDTO.class);
                    currencyPairDTO = currencyPairAssembler.setAuditFields(entity,currencyPairDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return currencyPairDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return currencyPairDTOList;
    }

    public TransactionStatus processCurrencyPair (SessionContext sessionContext, CurrencyPairDTO currencyPairDTO)
            throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processCurrencyPair with parameters sessionContext {}, currencyPairDTO {}",
                    sessionContext, currencyPairDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(currencyPairDTO);
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

    public CurrencyPairDTO getByCurrency1AndCurrency2AndEntityTypeAndEntityCode (SessionContext sessionContext, CurrencyPairDTO currencyPairDTO) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getByCurrency1AndCurrency2AndEntityCodeType with parameters sessionContext {}, currencyPairDTO {}",
                    sessionContext, currencyPairDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyPairDTO result = null;
        try {
            if (isAuthorized(currencyPairDTO.getAuthorized())) {
                CurrencyPairEntity currencyPairEntity = currencyPairDomainService.getByCurrency1AndCurrency2AndEntityTypeAndEntityCode(
                        currencyPairDTO.getCurrency1(), currencyPairDTO.getCurrency2(),
                        currencyPairDTO.getEntityType(), currencyPairDTO.getEntityCode());
                result = currencyPairAssembler.convertEntityToDto(currencyPairEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyPairDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(),
                        CurrencyPairDTO.class);
                result = currencyPairAssembler.setAuditFields(mutationEntity, result);
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

    @Override
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        CurrencyPairDTO currencyPairDTO = objMapper.readValue(data, CurrencyPairDTO.class);
        save(currencyPairDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return currencyPairAssembler.convertEntityToDto(currencyPairDomainService.getCurrencyPairById(Integer.parseInt(code)));
    }

    @Override
    public void save(CurrencyPairDTO currencyPairDTO){
        currencyPairDomainService.save(currencyPairDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return CurrencyPairDTO.builder().build().getTaskCode();
    }
}

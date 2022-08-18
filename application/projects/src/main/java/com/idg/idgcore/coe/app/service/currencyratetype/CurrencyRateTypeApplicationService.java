package com.idg.idgcore.coe.app.service.currencyratetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.currencyratetype.ICurrencyRateTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
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
import java.util.List;


@Slf4j
@Service("currencyRateTypeApplicationService")
public class CurrencyRateTypeApplicationService extends AbstractApplicationService implements ICurrencyRateTypeApplicationService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private ICurrencyRateTypeDomainService currencyRateTypeDomainService;
    @Autowired
    private CurrencyRateTypeAssembler currencyRateTypeAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public CurrencyRateTypeDTO getCurrencyRateTypeByType(SessionContext sessionContext, CurrencyRateTypeDTO currencyRateTypeDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In getCurrencyRateTypeByType with parameters sessionContext {}, currencyRateTypeDTO {}",
                    sessionContext, currencyRateTypeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyRateTypeDTO result = null;
        try {
            if (isAuthorized(currencyRateTypeDTO.getAuthorized())) {
                CurrencyRateTypeEntity currencyRateTypeEntity = currencyRateTypeDomainService.getCurrencyRateTypeByType(currencyRateTypeDTO.getCurrencyRateType());
                result =currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity);
            } else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyRateTypeDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), CurrencyRateTypeDTO.class);
                result = currencyRateTypeAssembler.setAuditFields(mutationEntity, result);
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

    public List<CurrencyRateTypeDTO> getCurrencyRateTypes(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCurrencyRateType with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyRateTypeDTO> currencyRateTypeDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(), AUTHORIZED_N);
            currencyRateTypeDTOList.addAll(currencyRateTypeDomainService.getCurrencyRateTypes().stream()
                    .map(entity -> currencyRateTypeAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            currencyRateTypeDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CurrencyRateTypeDTO currencyRateTypeDTO = null;
                try {
                    currencyRateTypeDTO = objectMapper.readValue(data, CurrencyRateTypeDTO.class);
                    currencyRateTypeDTO = currencyRateTypeAssembler.setAuditFields(entity, currencyRateTypeDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return currencyRateTypeDTO;
            }).collect(Collectors.toList()));
            currencyRateTypeDTOList = currencyRateTypeDTOList.stream().collect(
                    Collectors.groupingBy(CurrencyRateTypeDTO::getCurrencyRateType, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(CurrencyRateTypeDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return currencyRateTypeDTOList;
    }

    public TransactionStatus processCurrencyRateType(SessionContext sessionContext, CurrencyRateTypeDTO currencyRateTypeDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processCurrencyRateType with parameters sessionContext {}, currencyratetypeDTO {}",
                    sessionContext, currencyRateTypeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(currencyRateTypeDTO);
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

    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        CurrencyRateTypeDTO currencyRateTypeDTO = objMapper.readValue(data, CurrencyRateTypeDTO.class);
        save(currencyRateTypeDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeDomainService.getCurrencyRateTypeByType(code));
    }

    @Override
    public void save(CurrencyRateTypeDTO currencyRateTypeDTO) {
        currencyRateTypeDomainService.save(currencyRateTypeDTO);
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return CurrencyRateTypeDTO.builder().build().getTaskCode();
    }
}

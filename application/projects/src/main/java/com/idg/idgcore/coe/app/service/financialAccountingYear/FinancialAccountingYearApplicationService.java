package com.idg.idgcore.coe.app.service.financialAccountingYear;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.app.config.*;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.domain.assembler.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.base.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.mapping.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import com.idg.idgcore.infra.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service ("financialAccountingYearService")
public class FinancialAccountingYearApplicationService extends AbstractApplicationService
        implements IFinancialAccountingYearApplicationService
{
    private final ModelMapper modelMapper = new ModelMapper();
    @Autowired private IProcessConfiguration process;
    @Autowired private MappingConfig mappingConfig;
    @Autowired private IMutationsDomainService mutationsDomainService;
    @Autowired private IFinancialAccountingYearDomainService domainService;
    @Autowired private FinancialAccountingYearAssembler assembler;
    @Autowired private MutationAssembler mutationAssembler;


    public FinancialAccountingYearDTO getFinancialAccountingYearByCode (
            SessionContext sessionContext, FinancialAccountingYearDTO dto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getFinancialAccountingYearByCode with parameters sessionContext {}, countryDTO {}",
                    sessionContext, dto);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        FinancialAccountingYearDTO result = null;
        try {
            if (isAuthorized(dto.getAuthorized())) {
                FinancialAccountingYearEntity entity = domainService.getFinancialAccountingYearByCode(
                        dto.getBankCode());
                result = assembler.convertEntityToDto(entity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        dto.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = modelMapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(),
                        FinancialAccountingYearDTO.class);
                result = assembler.setAuditFields(mutationEntity, result);
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

    public FinancialAccountingYearDTO getByBankCodeAndBranchCodeAndFinancialAccountingYearCode (
            SessionContext sessionContext, FinancialAccountingYearDTO dto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getFinancialAccountingYearByBankCodeBranchCode with parameters sessionContext {}, countryDTO {}",
                    sessionContext, dto);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        FinancialAccountingYearDTO result = null;
        try {
            if (isAuthorized(dto.getAuthorized())) {
                FinancialAccountingYearEntity entity = domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                        dto.getBankCode(), dto.getBranchCode(),
                        dto.getFinancialAccountingYearCode());
                result = assembler.convertEntityToDto(entity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        dto.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = modelMapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(),
                        FinancialAccountingYearDTO.class);
                result = assembler.setAuditFields(mutationEntity, result);
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

    public List<FinancialAccountingYearDTO> getFinancialAccountingYears (
            SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getFinancialAccountingYears with parameters sessionContext {}",
                    sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<FinancialAccountingYearDTO> dtoList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            dtoList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                FinancialAccountingYearDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, FinancialAccountingYearDTO.class);
                    dto = assembler.setAuditFields(entity, dto);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return dtoList;
    }

    public TransactionStatus processFinancialAccountingYear (SessionContext sessionContext,
            FinancialAccountingYearDTO dto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In processFinancialAccountingYear with parameters sessionContext {}, countryDTO {}",
                    sessionContext, dto);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(dto);
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
        FinancialAccountingYearDTO dto = objMapper.readValue(data,
                FinancialAccountingYearDTO.class);
        dto.getFinancialAccountingYearPeriodicCode();

        save(dto);
    }


    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        String[] fields = code.split(FIELD_SEPARATOR);
        if (fields.length == 3) {
            return assembler.convertEntityToDto(
                    domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                            fields[0], fields[1], fields[2]));
        }
        else
            return null;
    }


    @Override
    public void save (FinancialAccountingYearDTO dto) {
        domainService.save(dto);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return FinancialAccountingYearDTO.builder().build().getTaskCode();
    }

}
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
import com.idg.idgcore.coe.domain.util.*;
import com.idg.idgcore.coe.dto.base.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;
import java.util.function.*;

import static com.idg.idgcore.coe.common.Constants.*;
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


    public List<FinancialAccountingYearDTO> searchFinancialAccountingYear(SessionContext sessionContext, FinancialAccountingForSearchYearDTO financialAccountingYearDTO)
            throws FatalException, JsonProcessingException {

        if (log.isInfoEnabled()) {
            log.info("In searchFinancialAccountingYear with parameters sessionContext {}, financialAccountingYearDTO {}",
                    sessionContext, financialAccountingYearDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        List<FinancialAccountingYearDTO> result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            if (log.isInfoEnabled()) {
                log.info(" TO Call findByTaskCodeAndTaskIdentifierStartsWith({},{})",
                        financialAccountingYearDTO.getTaskCode(), financialAccountingYearDTO.getTaskIdentifier());
            }

            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(financialAccountingYearDTO.getTaskCode(), financialAccountingYearDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                FinancialAccountingYearDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, FinancialAccountingYearDTO.class);
                    dto = assembler.setAuditFields(entity, dto);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList();
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        if (log.isInfoEnabled()) {
            log.info("RETURNING searchFinancialAccountingYear with {}", result);
        }
        return result;
    }


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
    //-------SERVICES PROCESSING

    /**
     * service for the given system dates according to the bank & branch dates,
     * the financial cycle & period code will be returned to the branches
     */
    public FinancialAccountingYearProcessDTO getFinancialAccountingYearDateAndPeriodCode (
            String branchCode, Date inputDate) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(" IN getFinancialAccountingYearDateAndPeriodCode[",
                    branchCode + ", " + inputDate + "] ");
        }
        FinancialAccountingYearProcessDTO result = null;
        FinancialAccountingYearEntity entity = domainService.getFinancialAccountingYearForProcessCall(
                branchCode, inputDate);
        return assembler.getDateAndPeriodCode(entity, inputDate);
    }

    /**
     * service for retrieval of Start Date & End Date of financial cycle and each of the period code defined in the system in Financial Cycle Configuration screen.
     */
    public FinancialAccountingYearDTO getFinancialAccountingYearDateAndAllPeriodCode (
            String branchCode, Date inputDate) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(" IN getFinancialAccountingYearDateAndPeriodCode[",
                    branchCode + ", " + inputDate + "] ");
        }
        FinancialAccountingYearProcessDTO result = null;
        FinancialAccountingYearEntity entity = domainService.getFinancialAccountingYearForProcessCall(
                branchCode, inputDate);
        return assembler.getDtoFromEntity(entity, inputDate);
    }

    /**
     * service for retrieval of Start Date & End Date of financial cycle and each of the period code defined in the system in Financial Cycle Configuration screen.
     */
    public FinancialAccountingYearDTO getPeriodCodeDetails (SessionContext sessionContext,
            FinancialAccountingYearDTO dto) {
        if (log.isInfoEnabled()) {
            log.info(" IN getPeriodCodeDetails with {}  ", dto);
        }
        FinancialAccountingYearUtil financialAccountingYearUtil = new FinancialAccountingYearUtil();
        FinancialAccountingYearDTO financialAccountingYearDTO = financialAccountingYearUtil.getPeriodCodeDetails(
                dto);
        return financialAccountingYearDTO;
    }

}
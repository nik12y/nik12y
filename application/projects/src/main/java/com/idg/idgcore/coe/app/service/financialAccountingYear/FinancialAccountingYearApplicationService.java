package com.idg.idgcore.coe.app.service.financialAccountingYear;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.financialAccountingYear.FinancialAccountingYearAssembler;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.service.financialAccountingYear.FinancialAccountingYearDomainService;
import com.idg.idgcore.coe.domain.util.FinancialAccountingYearUtil;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingForSearchYearDTO;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingYearDTO;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingYearProcessDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.common.Constants.FINANCIAL_ACCOUNTING_YEAR;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("financialAccountingYearService")
public class FinancialAccountingYearApplicationService extends GenericApplicationService<FinancialAccountingYearDTO,
        FinancialAccountingYearEntity,
        FinancialAccountingYearDomainService,
        FinancialAccountingYearAssembler> {

    protected FinancialAccountingYearApplicationService() {
        super(FINANCIAL_ACCOUNTING_YEAR);
    }

    private final ModelMapper modelMapper = new ModelMapper();

    public List<FinancialAccountingYearDTO> searchFinancialAccountingYear(
            SessionContext sessionContext,
            FinancialAccountingForSearchYearDTO financialAccountingYearDTO)
            throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In searchFinancialAccountingYear with parameters sessionContext {}, financialAccountingYearDTO {}",
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
                        financialAccountingYearDTO.getTaskCode(),
                        financialAccountingYearDTO.getTaskIdentifier());
            }
            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(
                    financialAccountingYearDTO.getTaskCode(),
                    financialAccountingYearDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                FinancialAccountingYearDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, FinancialAccountingYearDTO.class);
                    assembler.setAuditFields(entity, dto);
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

    public FinancialAccountingYearDTO getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
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
                result = assembler.toDTO(entity);
            } else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        dto.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = modelMapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(),
                        FinancialAccountingYearDTO.class);
                assembler.setAuditFields(mutationEntity, result);
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

    public List<FinancialAccountingYearDTO> getFinancialAccountingYears(
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
                    assembler.setAuditFields(entity, dto);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return dtoList;
    }

    public TransactionStatus processFinancialAccountingYear(SessionContext sessionContext,
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
        FinancialAccountingYearDTO dto = objMapper.readValue(data,
                FinancialAccountingYearDTO.class);
        dto.getFinancialAccountingYearPeriodicCode();
        save(dto);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        String[] fields = code.split(FIELD_SEPARATOR);
        if (fields.length == 3) {
            return assembler.toDTO(
                    domainService.getByBankCodeAndBranchCodeAndFinancialAccountingYearCode(
                            fields[0], fields[1], fields[2]));
        } else
            return null;
    }

    @Override
    public void save(FinancialAccountingYearDTO dto) {
        domainService.save(dto);
    }

    public String getTaskCode() {
        return FinancialAccountingYearDTO.builder().build().getTaskCode();
    }
    //-------SERVICES PROCESSING

    /**
     * service for the given system dates according to the bank & branch dates,
     * the financial cycle & period code will be returned to the branches
     */
    public FinancialAccountingYearProcessDTO getFinancialAccountingYearDateAndPeriodCode(
            SessionContext sessionContext,
            FinancialAccountingYearProcessDTO dto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(" IN getFinancialAccountingYearDateAndPeriodCode[",
                    sessionContext + ", " + dto + "] ");
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        FinancialAccountingYearProcessDTO result = null;
        FinancialAccountingYearEntity entity = null;
        try {
            String branchCode = dto.getBranchCode();
            Date inputDate = formatter.parse(dto.getBranchDate());
            if (log.isInfoEnabled()) {
                log.info(" IN getFinancialAccountingYearDateAndPeriodCode[",
                        branchCode + ", " + inputDate + "] ");
            }
            entity = domainService.getFinancialAccountingYearForProcessCall(
                    branchCode, inputDate);
            return assembler.getDateAndPeriodCode(entity, inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * service for retrieval of Start Date & End Date of financial cycle and each of the period code defined in the system in Financial Cycle Configuration screen.
     */
    public FinancialAccountingYearDTO getFinancialAccountingYearDateAndAllPeriodCode(
            SessionContext sessionContext,
            FinancialAccountingYearProcessDTO dto) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(" IN getFinancialAccountingYearDateAndAllPeriodCode[",
                    sessionContext + ", " + dto + "] ");
        }
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        FinancialAccountingYearProcessDTO result = null;
        FinancialAccountingYearEntity entity = null;
        try {
            String branchCode = dto.getBranchCode();
            Date inputDate = formatter.parse(dto.getBranchDate());
            entity = domainService.getFinancialAccountingYearForProcessCall(
                    branchCode, inputDate);
            return assembler.getDtoFromEntity(entity, inputDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * service for retrieval of Start Date & End Date of financial cycle and each of the period code defined in the system in Financial Cycle Configuration screen.
     */
    public FinancialAccountingYearDTO getPeriodCodeDetails(SessionContext sessionContext,
                                                           FinancialAccountingYearDTO dto) {
        if (log.isInfoEnabled()) {
            log.info(" IN getPeriodCodeDetails with {}  ", dto);
        }
        FinancialAccountingYearUtil financialAccountingYearUtil = new FinancialAccountingYearUtil();
        FinancialAccountingYearDTO financialAccountingYearDTO = financialAccountingYearUtil.getPeriodCodeDetails(
                dto);
        return financialAccountingYearDTO;
    }

    public List<FinancialAccountingYearDTO> getFinancialAccountingYearsFromMain(
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
                    assembler.setAuditFields(entity, dto);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return dtoList;
    }
}
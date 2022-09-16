package com.idg.idgcore.coe.app.service.mulbranchparameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mulbranchparameter.IMulBranchParameterDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingYearDTO;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
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

import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("mulBranchparamApplicationService")
public class MulBranchParameterApplicationService extends AbstractApplicationService
        implements IMulBranchParameterApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IMulBranchParameterDomainService imulBranchParameterDomainService;
    @Autowired
    private MulBranchParameterAssembler mulBranchParameterAssembler;

    public List<MulBranchParameterDTO> getMulBranchParameters(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getMulBranchParameters with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<MulBranchParameterDTO> mulBranchParameterDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            mulBranchParameterDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                MulBranchParameterDTO mulBranchParameterDTO = null;
                try {
                    mulBranchParameterDTO = objectMapper.readValue(data, MulBranchParameterDTO.class);
                    mulBranchParameterDTO = mulBranchParameterAssembler.setAuditFields(entity, mulBranchParameterDTO);
                }
                catch (JsonProcessingException e) {
                    log.error("Exception in getMulBranchParameters",e);
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return mulBranchParameterDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            log.error("Exception in getMulBranchParameters",exception);
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return mulBranchParameterDTOList;
    }

    public TransactionStatus processMulBranchParameter(SessionContext sessionContext, MulBranchParameterDTO mulBranchParameterDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processBranchParameter with parameters sessionContext {}, mulBranchParameterDTO {}",
                    sessionContext, mulBranchParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(mulBranchParameterDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            log.error("Exception in processMulBranchParameter",fatalException);
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            log.error("Exception in processMulBranchParameter",exception);
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
        MulBranchParameterDTO mulBranchParameterDTO = objMapper.readValue(data, MulBranchParameterDTO.class);
        save(mulBranchParameterDTO);
    }
    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {

        String[] fields = code.split(FIELD_SEPARATOR);
        if (fields.length == 2) {
            return mulBranchParameterAssembler.convertEntityToDto(
                    imulBranchParameterDomainService.getByCurrencyCodeAndEntityCode(
                            fields[0], fields[1]));
        }
        else
            return null;
    }

    @Override
    public void save(MulBranchParameterDTO mulBranchParameterDTO) {
        imulBranchParameterDomainService.save(mulBranchParameterDTO);
    }

    @Override
    public MulBranchParameterDTO getByCurrencyCodeAndEntityCode(SessionContext sessionContext, MulBranchParameterDTO mulBranchParameterDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In  getByCurrencyCodeAndEntityCode with parameters sessionContext {}, countryDTO {}",
                    sessionContext, mulBranchParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        MulBranchParameterDTO result = null;
        try {
            if (isAuthorized(mulBranchParameterDTO.getAuthorized())) {
                MulBranchParameterEntity mulBranchParameterEntity  = imulBranchParameterDomainService.getByCurrencyCodeAndEntityCode(
                        mulBranchParameterDTO.getCurrencyCode(), mulBranchParameterDTO.getEntityCode());
                result = mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        mulBranchParameterDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(),
                        MulBranchParameterDTO.class);
                result = mulBranchParameterAssembler.setAuditFields(mutationEntity, result);
                fillTransactionStatus(transactionStatus);
            }
        }
        catch (JsonProcessingException jpe)  {
            log.error("Exception in getByCurrencyCodeAndEntityCode",jpe);
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        catch (Exception exception) {
            log.error("Exception in getByCurrencyCodeAndEntityCode",exception);
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        }
        finally {
            Interaction.close();
        }
        return result;
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return MulBranchParameterDTO.builder().build().getTaskCode();
    }


}

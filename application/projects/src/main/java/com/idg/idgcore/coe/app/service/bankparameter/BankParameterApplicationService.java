package com.idg.idgcore.coe.app.service.bankparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.bankparameter.BankParameterAssembler;
import com.idg.idgcore.coe.domain.entity.bankparameter.BankParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.service.bankparameter.BankParameterDomainService;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.BANKPARAMETER;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("bankParameterApplicationService")
public class BankParameterApplicationService extends GenericApplicationService<BankParameterDTO,
        BankParameterEntity, BankParameterDomainService, BankParameterAssembler> {

    public BankParameterApplicationService() {
        super(BANKPARAMETER);
    }

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
                    dto = assembler.setAuditFields(entity, dto);
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

    public String getTaskCode () {
        return BankParameterDTO.builder().build().getTaskCode();
    }

}
package com.idg.idgcore.coe.app.service.branchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.branchparameter.BranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.branchparameter.BranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.service.branchparameter.BranchParameterDomainService;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.BRANCH_PARAMETER;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("branchParameterApplicationService")
public class BranchParameterApplicationService extends GenericApplicationService<BranchParameterDTO, BranchParameterEntity,
        BranchParameterDomainService, BranchParameterAssembler> {

    protected BranchParameterApplicationService() {
        super(BRANCH_PARAMETER);
    }

    public List<BranchParameterDTO> searchBranchParameter(SessionContext sessionContext, BranchParameterDTO branchParameterDTO)
            throws FatalException, JsonProcessingException {

        if (log.isInfoEnabled()) {
            log.info("In searchBranchParameter with parameters sessionContext {}, branchParameterDTO {}",
                    sessionContext, branchParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        List<BranchParameterDTO> result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(branchParameterDTO.getTaskCode(), branchParameterDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BranchParameterDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, BranchParameterDTO.class);
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
            log.info("RETURNING searchBranchParameter with {}", result);
        }
        return result;
    }

    public String getTaskCode () {
        return BranchParameterDTO.builder().build().getTaskCode();
    }

}
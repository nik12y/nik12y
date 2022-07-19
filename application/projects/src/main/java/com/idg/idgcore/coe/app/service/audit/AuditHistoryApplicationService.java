package com.idg.idgcore.coe.app.service.audit;

import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.domain.assembler.city.*;
import com.idg.idgcore.coe.domain.assembler.country.*;
import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.service.audit.IAuditHistoryDomainService;
import com.idg.idgcore.coe.dto.audit.AuditHistoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.idg.idgcore.datatypes.core.TransactionStatus;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.INACTIVE;
import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_Y;

@Slf4j
@Service ("auditHistoryApplicationService")
public class AuditHistoryApplicationService extends AbstractApplicationService
        implements IAuditHistoryApplicationService
{
    @Autowired
    private IAuditHistoryDomainService auditHistoryDomainService;
    @Autowired
    private AuditHistoryAssembler auditHistoryAssembler;
    @Override
    public AuditHistoryDTO getAuditRecordByVersion (SessionContext sessionContext,
                                               AuditHistoryDTO auditHistoryDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getAuditRecordByVersion with parameters sessionContext {}, auditHistoryDTO {}",
                    sessionContext, auditHistoryDTO);
        }
        AuditHistoryDTO dto = AuditHistoryDTO.builder().build();
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        try {
            AuditHistoryEntity auditHistoryEntity = auditHistoryDomainService.getAuditHistoryByRecordVersion(
                    auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(),
                    auditHistoryDTO.getRecordVersion(), AUTHORIZED_Y, INACTIVE);
            dto = auditHistoryAssembler.setAuditFields(auditHistoryEntity);
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return dto;
    }

    @Override
    public List<AuditHistoryDTO> getAuditHistory (SessionContext sessionContext,
                                             AuditHistoryDTO auditHistoryDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getAuditHistory with parameters sessionContext {}, auditHistoryDTO {}",
                    sessionContext, auditHistoryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        List<AuditHistoryEntity> auditHistoryEntity = new ArrayList<>();
        List<AuditHistoryDTO> dtoList =  new ArrayList<>();
        try {
            auditHistoryEntity = auditHistoryDomainService.getAuditHistory(
                    auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(), AUTHORIZED_Y, INACTIVE);

            dtoList = auditHistoryEntity.stream().map(ah -> auditHistoryAssembler.setAuditFields(ah))
                    .collect(Collectors.toList());
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return dtoList;
    }

}
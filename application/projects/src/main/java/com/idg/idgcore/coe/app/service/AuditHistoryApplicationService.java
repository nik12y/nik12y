package com.idg.idgcore.coe.app.service;

import com.idg.idgcore.coe.domain.entity.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.service.IAuditHistoryDomainService;
import com.idg.idgcore.coe.dto.PayloadDTO;
import com.idg.idgcore.coe.dto.AuditHistoryDTO;
import com.idg.idgcore.dto.context.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.*;

@Slf4j
@Service ("auditHistoryApplicationService")
public class AuditHistoryApplicationService implements IAuditHistoryApplicationService {
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private IAuditHistoryDomainService auditHistoryDomainService;

    @Override
    public PayloadDTO getAuditRecordByVersion (SessionContext sessionContext,
                                               AuditHistoryDTO auditHistoryDTO) {
        log.info("In getCountryByCode with parameters sessionContext {}, auditHistoryDTO {}",
                sessionContext, auditHistoryDTO);
        AuditHistoryEntity auditHistoryEntity = auditHistoryDomainService.getAuditHistoryByRecordVersion(
                auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(),
                auditHistoryDTO.getRecordVersion(), INACTIVE);
        return mapper.map(auditHistoryEntity.getPayload(), PayloadDTO.class);
    }

    @Override
    public List<PayloadDTO> getAuditHistory (SessionContext sessionContext,
                                             AuditHistoryDTO auditHistoryDTO) {
        List<AuditHistoryEntity> auditHistoryEntity = auditHistoryDomainService.getAuditHistory(
                auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(), INACTIVE);
        return auditHistoryEntity.stream().map(ah -> mapper.map(ah.getPayload(), PayloadDTO.class))
                .collect(Collectors.toList());
    }

}

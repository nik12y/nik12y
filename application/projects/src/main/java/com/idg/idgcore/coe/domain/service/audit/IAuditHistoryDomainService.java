package com.idg.idgcore.coe.domain.service.audit;

import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;

import java.util.List;

public interface IAuditHistoryDomainService {
    public AuditHistoryEntity getAuditHistoryByRecordVersion (String taskCode,
                                                              String taskIdentifier,
                                                              Integer recordVersion,
                                                              String status);
    List<AuditHistoryEntity> getAuditHistory (String taskCode, String taskIdentifier, String status);

}
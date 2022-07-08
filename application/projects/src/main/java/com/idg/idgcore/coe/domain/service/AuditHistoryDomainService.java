package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.domain.entity.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.repository.IAuditHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class AuditHistoryDomainService implements IAuditHistoryDomainService {
    @Autowired
    private IAuditHistoryRepository auditHistoryRepository;

    @Override
    public AuditHistoryEntity getAuditHistoryByRecordVersion (String taskCode,
                                                              String taskIdentifier,
                                                              Integer recordVersion,
                                                              String status) {
        return this.auditHistoryRepository.findByTaskCodeAndTaskIdentifierAndRecordVersionAndStatus(
                taskCode, taskIdentifier, recordVersion, status);
    }

    @Override
    public List<AuditHistoryEntity> getAuditHistory (String taskCode, String taskIdentifier,
                                                     String status) {
        return this.auditHistoryRepository.findByTaskCodeAndTaskIdentifierAndStatus(taskCode,
                taskIdentifier, status);
    }

}

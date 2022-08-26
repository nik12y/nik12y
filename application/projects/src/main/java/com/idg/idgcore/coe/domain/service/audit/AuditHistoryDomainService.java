package com.idg.idgcore.coe.domain.service.audit;

import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.repository.audit.IAuditHistoryRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

import static com.idg.idgcore.coe.common.Constants.INACTIVE;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AuditHistoryDomainService implements IAuditHistoryDomainService {
    @Autowired
    private IAuditHistoryRepository auditHistoryRepository;

    @Override
    public AuditHistoryEntity getAuditHistoryByRecordVersion (String taskCode,
                                                              String taskIdentifier,
                                                              Integer recordVersion,
                                                              String authorized, String status)
            throws BusinessException {
        AuditHistoryEntity auditHistoryEntity = null;
        try {

            if(INACTIVE.equals(status))
            {
                auditHistoryEntity = this.auditHistoryRepository.findByTaskCodeAndTaskIdentifierAndRecordVersionAndAuthorizedAndStatusNot(
                        taskCode, taskIdentifier, recordVersion, authorized, status);
            }
            else
            {
                auditHistoryEntity = this.auditHistoryRepository.findByTaskCodeAndTaskIdentifierAndRecordVersionAndAuthorizedAndStatus(
                        taskCode, taskIdentifier, recordVersion, authorized, status);
            }
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return auditHistoryEntity;
    }

    @Override
    public List<AuditHistoryEntity> getAuditHistory (String taskCode, String taskIdentifier,
                                                     String authorized, String status) {
        List<AuditHistoryEntity> auditHistoryEntityList = new ArrayList<>();
        try {
            auditHistoryEntityList = this.auditHistoryRepository.findByTaskCodeAndTaskIdentifierAndAuthorizedAndStatusNot(
                    taskCode, taskIdentifier, authorized, status);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return auditHistoryEntityList;
    }

}

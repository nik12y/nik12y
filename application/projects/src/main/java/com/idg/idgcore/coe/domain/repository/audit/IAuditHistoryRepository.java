package com.idg.idgcore.coe.domain.repository.audit;

import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuditHistoryRepository extends JpaRepository<AuditHistoryEntity, Integer> {
    List<AuditHistoryEntity> findByTaskCodeAndTaskIdentifierAndAuthorizedAndStatusNot(
                        String taskCode, String taskIdentifier, String authorized, String status);
    AuditHistoryEntity findByTaskCodeAndTaskIdentifierAndRecordVersionAndAuthorizedAndStatus (
                        String taskCode, String taskIdentifier, Integer recordVersion,
                        String authorized, String status);
    AuditHistoryEntity findByTaskCodeAndTaskIdentifierAndRecordVersionAndAuthorizedAndStatusNot (
            String taskCode, String taskIdentifier, Integer recordVersion,
            String authorized, String status);

}

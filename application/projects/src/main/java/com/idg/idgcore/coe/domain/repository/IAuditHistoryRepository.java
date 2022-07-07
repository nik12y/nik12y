package com.idg.idgcore.coe.domain.repository;

import com.idg.idgcore.coe.domain.entity.AuditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAuditHistoryRepository extends JpaRepository<AuditHistoryEntity, Integer> {
    List<AuditHistoryEntity> findByTaskCodeAndTaskIdentifierAndStatus (String taskCode,
                                                                       String taskIdentifier,
                                                                       String status);
    AuditHistoryEntity findByTaskCodeAndTaskIdentifierAndRecordVersionAndStatus (String taskCode,
                                                                                 String taskIdentifier,
                                                                                 Integer recordVersion,
                                                                                 String status);

}

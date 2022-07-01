package com.idg.idgcore.domain.repository;

import com.idg.idgcore.domain.entity.AuditHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public interface IAuditHistoryRepository
        extends JpaRepository<AuditHistoryEntity, Integer>
{
    List<AuditHistoryEntity> findAllByTaskIdentifier (String taskIdentifier);
    List<AuditHistoryEntity> findAllByTaskIdentifierAndRecordVersion (String taskIdentifier,
                                                                      String recordVersion);
}

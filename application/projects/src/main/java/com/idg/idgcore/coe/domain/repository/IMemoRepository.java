package com.idg.idgcore.coe.domain.repository;

import com.idg.idgcore.coe.domain.entity.MemoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMemoRepository extends JpaRepository<MemoEntity, Integer> {
    MemoEntity findByReferenceNoAndRecordVersion (String referenceNo, Integer recordVersion);
    MemoEntity findByModuleIdAndTaskCodeAndTaskIdentifierAndRecordVersion (String moduleId,
                                                                           String taskCode,
                                                                           String taskIdentifier,
                                                                           Integer recordVersion);

}

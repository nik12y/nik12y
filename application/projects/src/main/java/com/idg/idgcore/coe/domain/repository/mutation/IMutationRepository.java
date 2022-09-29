package com.idg.idgcore.coe.domain.repository.mutation;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMutationRepository extends JpaRepository<MutationEntity, Integer> {
    List<MutationEntity> findByTaskCodeAndAuthorized(String taskCode,String authorized);
    MutationEntity findByTaskIdentifier (String taskIdentifier);
    MutationEntity findByTaskCodeAndTaskIdentifier (String taskCode, String taskIdentifier);
    List<MutationEntity> findByTaskCode (String taskCode);
    List<MutationEntity> findByTaskCodeAndTaskIdentifierContaining(String taskCode, String taskIdentifier);
}

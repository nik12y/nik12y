package com.idg.idgcore.domain.repository;

import com.idg.idgcore.domain.entity.MutationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMutationRepository extends JpaRepository<MutationEntity, Integer> {
    MutationEntity findByTaskCode (String taskCode);
    List<MutationEntity> findByTaskCodeAndAuthorizedAndStatusNot (String taskCode,
                                                                  String authorized, String status);
    List<MutationEntity> findByTaskIdentifierAndAuthorized (String taskIdentifier,
                                                            String authorized);
    MutationEntity findByTaskIdentifier (String taskIdentifier);

}

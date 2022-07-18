package com.idg.idgcore.coe.domain.repository.mutation;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IMutationRepository extends JpaRepository<MutationEntity, Integer> {
    MutationEntity findByTaskCode (String taskCode);
    List<MutationEntity> findByTaskCodeAndAuthorized(String taskCode,
                                                                  String authorized);
    List<MutationEntity> findByTaskIdentifierAndAuthorized (String taskIdentifier,
                                                            String authorized);
    MutationEntity findByTaskIdentifier (String taskIdentifier);
    Integer countByTaskCodeAndTaskIdentifierAndStatusAndAuthorized (String taskCode,
                                                                    String taskIdentifier,
                                                                    String status,
                                                                    String authorized);
    MutationEntity findByTaskCodeAndTaskIdentifier (String taskCode, String taskIdentifier);

}

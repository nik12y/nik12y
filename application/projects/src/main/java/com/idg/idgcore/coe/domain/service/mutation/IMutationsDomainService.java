package com.idg.idgcore.coe.domain.service.mutation;

import com.idg.idgcore.coe.dto.mutation.MutationDTO;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;

import java.util.List;

public interface IMutationsDomainService {
    MutationEntity getConfigurationByCode (final String taskIdentifier);
    MutationEntity  addUpdate (final MutationDTO mutationDTO);

    MutationEntity  save (final MutationDTO mutationDTO);
    void insertIntoAuditHistory (final MutationDTO mutationDTO);
    List<MutationEntity> getUnauthorizedMutation(final String taskCode,final String authorized);
}

package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.dto.MutationDTO;
import com.idg.idgcore.coe.domain.entity.MutationEntity;

import java.util.List;

public interface IMutationsDomainService {
    MutationEntity getConfigurationByCode (final String taskIdentifier);
    MutationEntity addUpdate (final MutationDTO mutationDTO);
    void insertIntoAuditHistory (final MutationDTO mutationDTO);
    List<MutationEntity> getUnauthorizedMutation(final String taskCode);
}

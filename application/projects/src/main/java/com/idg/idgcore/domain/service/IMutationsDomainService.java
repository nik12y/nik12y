package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.MutationDTO;
import com.idg.idgcore.domain.entity.MutationEntity;

import java.util.List;

public interface IMutationsDomainService {
    MutationEntity getConfigurationByCode (final String taskIdentifier);
    MutationEntity addUpdate (final MutationDTO mutationDTO);
    void insertIntoAuditHistory (final MutationDTO mutationDTO);
    List<MutationEntity> getUnauthorizedMutation(final String taskCode);
}

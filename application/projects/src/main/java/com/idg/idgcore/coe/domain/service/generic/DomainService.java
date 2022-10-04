package com.idg.idgcore.coe.domain.service.generic;

import java.util.List;

import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;

public abstract class DomainService<
        T_DTO extends CoreEngineBaseDTO,
        T_ENTITY extends AbstractAuditableDomainEntity> {

    public abstract T_ENTITY getEntityByIdentifier(String identifier);

    public abstract List<T_ENTITY> getAllEntities();

    public abstract void save(T_DTO valDTO);
}
package com.idg.idgcore.coe.domain.service.mitigant;

import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;

import java.util.List;

public interface IMitigantDomainService {

    MitigantEntity getConfigurationByCode(MitigantDTO mitigantDTO);

    List<MitigantEntity> getMitigantAll();

    MitigantEntity getMitigantByCode(String mitigantCode);

    void save(MitigantDTO mitigantDTO);
}

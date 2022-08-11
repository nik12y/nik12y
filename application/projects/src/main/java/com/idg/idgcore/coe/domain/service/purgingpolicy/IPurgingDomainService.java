package com.idg.idgcore.coe.domain.service.purgingpolicy;

import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;

import java.util.List;

public interface IPurgingDomainService {

    PurgingEntity getConfigurationByCode (PurgingDTO purgingDTO);
    List<PurgingEntity> getPurgingAll();
    PurgingEntity getPurgingByCode (String moduleCode);
    void save (PurgingDTO purgingDTO);

}

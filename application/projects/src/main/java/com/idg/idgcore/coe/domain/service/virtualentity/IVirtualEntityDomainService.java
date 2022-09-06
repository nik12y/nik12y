package com.idg.idgcore.coe.domain.service.virtualentity;

import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import java.util.List;


public interface IVirtualEntityDomainService {

    VirtualEntity getConfigurationByCode(VirtualEntityDTO virtualEntityDTO);
    List<VirtualEntity> getVirtualEntityAll ();
    VirtualEntity getByVirtualEntityCode(String entityCode);
    void save(VirtualEntityDTO virtualEntityDTO);
}

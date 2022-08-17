package com.idg.idgcore.coe.domain.service.errorOverride;

import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;

import java.util.List;

public interface IErrorOverrideDomainService {
    ErrorOverrideEntity getConfigurationByCode (ErrorOverrideDTO errorOverrideDTO);
    List<ErrorOverrideEntity> getErrorCodes ();
    ErrorOverrideEntity getErrorOverrideByCode (String errorOverrideCode);
    void save (ErrorOverrideDTO errorOverrideDTO);

}

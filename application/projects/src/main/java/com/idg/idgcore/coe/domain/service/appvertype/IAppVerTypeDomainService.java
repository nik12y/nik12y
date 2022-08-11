package com.idg.idgcore.coe.domain.service.appvertype;

import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;

import java.util.List;

public interface IAppVerTypeDomainService {

    AppVerTypeEntity getConfigurationByCode (AppVerTypeDTO appVerTypeDTO);
    List<AppVerTypeEntity> getAppVerTypes();
    AppVerTypeEntity getAppVerTypeByID (String verificationTypeId);
    void save (AppVerTypeDTO appVerTypeDTO);
}

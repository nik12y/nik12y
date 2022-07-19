package com.idg.idgcore.coe.domain.service.purpose;

import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;

import java.util.List;

public interface IPurposeDomainService {

    PurposeEntity getConfigurationByCode(PurposeDTO purposeDTO);

    List<PurposeEntity> getPurposes();

    PurposeEntity getPurposeByCode(String purposeCode);

    void save(PurposeDTO purposeDTO);
}

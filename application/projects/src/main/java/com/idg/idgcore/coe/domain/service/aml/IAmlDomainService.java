package com.idg.idgcore.coe.domain.service.aml;


import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.dto.aml.AmlDTO;

import java.util.List;

public interface IAmlDomainService {
    AmlEntity getConfigurationByCode (AmlDTO amlDTO);
    List<AmlEntity> getAmls ();
    AmlEntity getAmlByCode (String productCategory);
    void save (AmlDTO productCategory);

}
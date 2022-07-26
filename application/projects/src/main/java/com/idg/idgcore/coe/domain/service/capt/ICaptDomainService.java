package com.idg.idgcore.coe.domain.service.capt;

import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.dto.capt.CaptDTO;

import java.util.List;

public interface ICaptDomainService {
    CaptEntity getConfigurationByCode (CaptDTO captDTO);
    List<CaptEntity> getCaptAll();
    CaptEntity getCaptByCode (String clearingPaymentTypeCode);
    void save (CaptDTO captDTO);

}

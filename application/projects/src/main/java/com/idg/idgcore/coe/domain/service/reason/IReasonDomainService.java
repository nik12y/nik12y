package com.idg.idgcore.coe.domain.service.reason;

import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;

import java.util.List;

public interface IReasonDomainService {
    ReasonEntity getConfigurationByCode (ReasonDTO reasonDTO);
    List<ReasonEntity> getReasons ();
    ReasonEntity getReasonByCode (String primaryReasonCode);
    void save (ReasonDTO reasonDTO);

}

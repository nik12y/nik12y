package com.idg.idgcore.coe.app.service.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;

public interface IBaseApplicationService {
    void addUpdateRecord (String data) throws JsonProcessingException;
    CoreEngineBaseDTO getConfigurationByCode (String code);

}

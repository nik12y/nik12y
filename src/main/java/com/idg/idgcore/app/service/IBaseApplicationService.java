package com.idg.idgcore.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.app.dto.CoreEngineBaseDTO;

public interface IBaseApplicationService {
    void addUpdateRecord (String data) throws JsonProcessingException;
    CoreEngineBaseDTO getConfigurationByCode (String code);

}

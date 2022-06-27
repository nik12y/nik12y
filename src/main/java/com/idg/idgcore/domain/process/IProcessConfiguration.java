package com.idg.idgcore.domain.process;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.app.dto.CoreEngineBaseDTO;
import com.idg.idgcore.app.dto.MutationDTO;

public interface IProcessConfiguration {
    void process (CoreEngineBaseDTO baseDTO) throws JsonProcessingException;
    void addUpdateRecord (MutationDTO dto);
    void insertIntoAuditHistory (MutationDTO dto);
    void insertIntoBaseTable (MutationDTO dto) throws JsonProcessingException;

}

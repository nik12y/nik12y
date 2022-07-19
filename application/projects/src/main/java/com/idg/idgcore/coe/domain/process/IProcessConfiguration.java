package com.idg.idgcore.coe.domain.process;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.MutationDTO;

public interface IProcessConfiguration {
    void process (CoreEngineBaseDTO baseDTO) throws JsonProcessingException;
    void addUpdateRecord (MutationDTO dto);
    void insertIntoAuditHistory (MutationDTO dto);
    void insertIntoBaseTable (MutationDTO dto) throws JsonProcessingException;

}
package com.idg.idgcore.coe.app.service.purpose;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IPurposeApplicationService extends IBaseApplicationService {

    TransactionStatus processPurpose(SessionContext sessionContext, PurposeDTO dto) throws FatalException, JsonProcessingException;

    void addUpdateRecord(String data) throws JsonProcessingException;

    CoreEngineBaseDTO getConfigurationByCode(String code);

    void save(PurposeDTO purposeDTO);

    PurposeDTO getPurposeByCode(SessionContext sessionContext, PurposeDTO purposeDTO) throws FatalException, JsonProcessingException;

    List<PurposeDTO> getPurposes(SessionContext sessionContext) throws FatalException;

}

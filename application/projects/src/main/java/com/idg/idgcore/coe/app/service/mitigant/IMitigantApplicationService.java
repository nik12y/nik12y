package com.idg.idgcore.coe.app.service.mitigant;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IMitigantApplicationService extends IBaseApplicationService {

    TransactionStatus processMitigant(SessionContext sessionContext, MitigantDTO dto) throws FatalException, JsonProcessingException;

    void addUpdateRecord(String data) throws JsonProcessingException;

    CoreEngineBaseDTO getConfigurationByCode(String code);

    void save(MitigantDTO mitigantDTO);

    MitigantDTO getMitigantByCode(SessionContext sessionContext, MitigantDTO mitigantDTO) throws FatalException, JsonProcessingException;

    List<MitigantDTO> getMitigantAll(SessionContext sessionContext) throws FatalException;
}

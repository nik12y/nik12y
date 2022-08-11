package com.idg.idgcore.coe.app.service.aml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IAmlApplicationService extends IBaseApplicationService {

    TransactionStatus processAml (SessionContext sessionContext, AmlDTO dto)
            throws FatalException, JsonProcessingException;

    void save (AmlDTO amlDTO);

    AmlDTO getAmlByCode (SessionContext sessionContext, AmlDTO amlDTO)
            throws FatalException, JsonProcessingException;

    List<AmlDTO> getAmls (SessionContext sessionContext) throws FatalException;

}
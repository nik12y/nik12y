package com.idg.idgcore.coe.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.PayloadDTO;
import com.idg.idgcore.coe.dto.AuditHistoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IAuditHistoryApplicationService {
    PayloadDTO getAuditRecordByVersion (SessionContext sessionContext,
                                        AuditHistoryDTO auditHistoryDTO)
            throws FatalException, JsonProcessingException;
    List<PayloadDTO> getAuditHistory (SessionContext sessionContext,
                                      AuditHistoryDTO auditHistoryDTO) throws FatalException;

}

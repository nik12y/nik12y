package com.idg.idgcore.coe.endpoint.graphql.resolver.audit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.audit.IAuditHistoryApplicationService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.audit.AuditHistoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Slf4j
@Component
public class AuditHistoryQueryResolver implements GraphQLQueryResolver {
    @Autowired
    private IAuditHistoryApplicationService auditHistoryApplicationService;

    public PayloadDTO getAuditRecordByVersion (SessionContext sessionContext,
                                               AuditHistoryDTO auditHistoryDTO)
            throws FatalException, JsonProcessingException {
        return this.auditHistoryApplicationService.getAuditRecordByVersion(sessionContext,
                auditHistoryDTO);
    }

    public List<PayloadDTO> getAuditRecords (SessionContext sessionContext,
                                             AuditHistoryDTO auditHistoryDTO)
            throws FatalException {
        return this.auditHistoryApplicationService.getAuditHistory(sessionContext, auditHistoryDTO);
    }

}

package com.idg.idgcore.coe.domain.assembler.audit;

import com.idg.idgcore.coe.domain.entity.audit.*;
import com.idg.idgcore.coe.dto.audit.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.stereotype.*;

import javax.annotation.*;

@Component
public class AuditHistoryAssembler {
    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AuditHistoryDTO setAuditFields (AuditHistoryEntity auditHistoryEntity)
    {
        AuditHistoryDTO auditHistoryDTO = new AuditHistoryDTO();
        auditHistoryDTO.setAction(auditHistoryEntity.getAction());
        auditHistoryDTO.setAuthorized(auditHistoryEntity.getAuthorized());
        auditHistoryDTO.setData(auditHistoryEntity.getPayload().getData());
        auditHistoryDTO.setRecordVersion(auditHistoryEntity.getRecordVersion());
        auditHistoryDTO.setStatus(auditHistoryEntity.getStatus());
        auditHistoryDTO.setLastConfigurationAction(auditHistoryEntity.getLastConfigurationAction());
        auditHistoryDTO.setCreatedBy(auditHistoryEntity.getCreatedBy());
        auditHistoryDTO.setCreationTime(auditHistoryEntity.getCreationTime());
        auditHistoryDTO.setLastUpdatedBy(auditHistoryEntity.getLastUpdatedBy());
        auditHistoryDTO.setLastUpdatedTime(auditHistoryEntity.getLastUpdatedTime());
        auditHistoryDTO.setTaskCode(auditHistoryEntity.getTaskCode());
        auditHistoryDTO.setTaskIdentifier(auditHistoryEntity.getTaskIdentifier());
        return auditHistoryDTO;
    }
}

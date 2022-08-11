package com.idg.idgcore.coe.dto.audit;

import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuditHistoryDTO extends CoreEngineBaseDTO
{
    private String data;
    private String referenceNo;
    private String traceInfo;

//    private String action;
//    private String status;
//    private Integer recordVersion;
//    private String authorized;
//    private String lastConfigurationAction;
//    private String taskCode;
//    private String taskIdentifier;
}

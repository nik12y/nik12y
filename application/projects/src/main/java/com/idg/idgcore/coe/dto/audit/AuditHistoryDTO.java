package com.idg.idgcore.coe.dto.audit;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class AuditHistoryDTO
{
    private String taskCode;
    private String taskIdentifier;
    private String data;
    private String status;
    private String authorized;
    private Integer recordVersion;
    private String referenceNo;
    private String traceInfo;
    private String action;
    private String lastConfigurationAction;
    private String createdBy;
    private String lastUpdatedBy;
    private Date creationTime;
    private Date lastUpdatedTime;
}

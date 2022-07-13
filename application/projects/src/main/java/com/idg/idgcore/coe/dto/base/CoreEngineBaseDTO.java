package com.idg.idgcore.coe.dto.base;

import com.idg.idgcore.coe.dto.audit.*;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CoreEngineBaseDTO extends AuditableDTO {
    private String action;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
    private String taskCode;
    private String taskIdentifier;
}

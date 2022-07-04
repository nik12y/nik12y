package com.idg.idgcore.coe.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.EMPTY_STRING;

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

    public String getTaskCode () {
        return EMPTY_STRING;
    }

    public String getTaskIdentifier () {
        return EMPTY_STRING;
    }
}

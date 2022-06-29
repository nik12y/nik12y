package com.idg.idgcore.app.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;

//@Data
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
        return "";
    }

    public String getTaskIdentifier () {
        return "";
    }
}

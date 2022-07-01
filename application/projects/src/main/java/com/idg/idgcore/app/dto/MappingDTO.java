package com.idg.idgcore.app.dto;

import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MappingDTO {
    private String action;
    private String role;
    private String authorized;
    private String status;
    private String updateRecordVersion;
    private String inactivePreviousRecord;
    private String lastConfigurationAction;
    private String insertRecordIntoAudit;
    private String insertRecordIntoBasetable;
    private String mappingStatus;
}

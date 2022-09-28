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
public abstract class CoreEngineBaseDTO extends AuditableDTO {
    private String action;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
    private String taskIdentifier;

    public void setTaskCode(String s) {}
    public abstract String getTaskCode();

    public static Class<? extends CoreEngineBaseDTO> getSpecificType() {
        return CoreEngineBaseDTO.class;
    }
}

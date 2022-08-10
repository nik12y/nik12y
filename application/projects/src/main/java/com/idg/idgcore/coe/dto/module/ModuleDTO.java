package com.idg.idgcore.coe.dto.module;

import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import static com.idg.idgcore.coe.common.Constants.MODULE;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonIgnoreProperties (ignoreUnknown = true)
public class ModuleDTO extends CoreEngineBaseDTO {
    private String moduleCode;
    private String moduleName;
    private String bankCode;
    private Integer moduleUsers;
    private Integer moduleCurrentUsers;
    private boolean licensed;
    private boolean purgeAvailable;
    private boolean userDefinedFields;
    private boolean installed;

    @Override
    public String getTaskCode () {
        return MODULE;
    }

    @Override
    public String getTaskIdentifier () {
        return this.getModuleCode();
    }

}

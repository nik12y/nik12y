package com.idg.idgcore.coe.dto.virtualentity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;


import static com.idg.idgcore.coe.common.Constants.VIRTUAL_ENTITY;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VirtualEntityDTO extends CoreEngineBaseDTO {

    private String entityType;
    private String entityCode;
    private String entityName;
    private String parentEntityType;
    private String parentEntityCode;
    private Boolean isDefault;
    private String effectiveDate;

    public Boolean getIsDefault() { return isDefault; }

    @Override
    public String getTaskCode() { return VIRTUAL_ENTITY; }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(VIRTUAL_ENTITY);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getEntityCode();
    }
}

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

    @Override
    public String getTaskCode() {
        return "AuditHistoryDTO";
    }
}

package com.idg.idgcore.coe.dto.zakat;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.ToString;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.Date;
import static com.idg.idgcore.coe.common.Constants.ZAKAT;

@ToString(callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ZakatDTO extends CoreEngineBaseDTO {
    private Integer zakatYear;
    private String startDateOfRamadan;

    @Override
    public String getTaskCode () {
        return ZAKAT;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(ZAKAT);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getZakatYear().toString();
    }


}

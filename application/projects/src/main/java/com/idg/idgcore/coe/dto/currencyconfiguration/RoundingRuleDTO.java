package com.idg.idgcore.coe.dto.currencyconfiguration;

import com.idg.idgcore.coe.domain.entity.currencyconfiguration.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class RoundingRuleDTO {

    private String roundingRule;

    private String roundingRuleInstructionClass;

    public RoundingRuleDTO(RoundingRulesDetailsEntity roundingRulesEntity){
        this.roundingRule = roundingRulesEntity.getRoundingRule();
        this.roundingRuleInstructionClass = roundingRulesEntity.getRoundingRuleInstructionClass();
    }

}

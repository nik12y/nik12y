package com.idg.idgcore.coe.domain.entity.currencyconfiguration;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "rounding_rules_meta_info")
public class RoundingRulesDetailsEntity {

    @Id
    @Column(name="rounding_rule")
    private String roundingRule;

    @Column(name="instruction_class")
    private String roundingRuleInstructionClass;
}

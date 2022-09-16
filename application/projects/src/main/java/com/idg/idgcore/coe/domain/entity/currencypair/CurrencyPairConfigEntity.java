package com.idg.idgcore.coe.domain.entity.currencypair;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_MCY_CNFG_CURRENCY_PAIR_CONFIG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class CurrencyPairConfigEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="pair_config_id")
    private Integer pairConfigId;

    @Column(name="pair_id")
    private Integer pairId;

    @Column(name="entity_code")
    private String entityCode;

}

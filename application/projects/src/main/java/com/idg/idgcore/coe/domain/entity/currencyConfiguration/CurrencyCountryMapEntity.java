package com.idg.idgcore.coe.domain.entity.currencyConfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;




@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_country_map")
@Inheritance(strategy = InheritanceType.JOINED)
public class CurrencyCountryMapEntity extends AbstractAuditableDomainEntity
        implements Serializable
{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="currency_con_id")
    private Integer currencyConId;

    @Setter
    @Getter
    @Column(name = "currency_code")
    private String countryCurrencyCode;

    @Setter
    @Getter
    @Column(name = "currency_country_code")
    private String currencyCountryCode;

    @Setter
    @Getter
    @Column(name = "currency_country_name")
    private String currencyCountryName;

    @Setter
    @Column (name="record_status")
    private String status;

    @Setter
    @Column (name="record_version")
    private Integer recordVersion;

    @Setter
    @Column (name="is_authorized")
    private String authorized;

    @Setter
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;
}

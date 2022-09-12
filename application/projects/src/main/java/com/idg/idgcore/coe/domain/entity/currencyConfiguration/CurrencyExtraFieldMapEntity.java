package com.idg.idgcore.coe.domain.entity.currencyConfiguration;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;



@NoArgsConstructor
@Entity
@Table(name = "idgc_mcy_cnfg_extra_field_map")
@Inheritance(strategy = InheritanceType.JOINED)
public class CurrencyExtraFieldMapEntity extends AbstractAuditableDomainEntity
        implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="currency_ext_field_id")
    private Integer currencyExtFieldId;

    @Setter
    @Getter
    @Column(name = "currency_code")
    private String currencyCode;

    @Setter
    @Getter
    @Column(name = "extra_field_name")
    private String extraFieldName;

    @Setter
    @Getter
    @Column(name = "extra_field_value")
    private String extraFieldValue;

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

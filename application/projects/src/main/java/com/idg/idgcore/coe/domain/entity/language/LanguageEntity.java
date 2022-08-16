package com.idg.idgcore.coe.domain.entity.language;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "idgc_coe_language_code_cnfg")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(LanguageEntityKey.class)
public class LanguageEntity extends AbstractAuditableDomainEntity implements Serializable{
    @Id
    @Column(name = "language_code")
    private String languageCode;

    @Column(name = "language_code_alternate")
    private String languageCodeAlternate;

    @Column(name = "language_name")
    private String languageName;

    @Column(name = "locale_code")
    private String localeCode;

    @Column(name = "locale_name")
    private String localeName;

    @Column(name = "life_cycle_id")
    private String lifeCycleId;

    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "record_version")
    private Integer recordVersion;

    @Column(name = "record_status")
    private String status;

    @Column(name = "is_authorized")
    private String authorized;

    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;

}
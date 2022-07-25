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
@Table(name = "IDGC_COE_LANGUAGE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(LanguageEntityKey.class)
public class LanguageEntity extends AbstractAuditableDomainEntity implements Serializable{
    @Id
    private String languageCode;
    private String languageCodeAlternate;
    private String languageName;
    private String localeCode;
    private String localeName;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}
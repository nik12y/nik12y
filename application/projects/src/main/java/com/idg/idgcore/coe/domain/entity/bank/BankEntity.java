package com.idg.idgcore.coe.domain.entity.bank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.IdClass;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "IDGC_COE_BANK_CODES_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BankEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BankEntity extends AbstractAuditableDomainEntity
        implements Serializable {

    @Id
    private String bankCode;
    private String bankCodeRegulatory;
    private String bankName;


    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;


}

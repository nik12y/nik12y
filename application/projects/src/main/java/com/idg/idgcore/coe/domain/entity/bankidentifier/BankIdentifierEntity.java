package com.idg.idgcore.coe.domain.entity.bankidentifier;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;
import javax.persistence.Id;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_BANK_IDENTIFIER_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BankIdentifierEntityKey.class)
public class BankIdentifierEntity  extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    private String bankIdentifierCode;
    private String bankIdentifierCodeName;
    private String bankAddress1;
    private String bankAddress2;
    private String bankAddress3;
    private String bankAddress4;
    private String internalAddress;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
}

package com.idg.idgcore.coe.domain.entity.iban;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import javax.persistence.Embedded;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_IBAN_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (IbanEntityKey.class)
public class IbanEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    private String ibanCountryCode;
    private Integer ibanCountryPosition;
    private Integer ibanCountryCodeLength;
    private Integer ibanCheckDigitPosition;
    private Integer ibanCheckDigitLength;
    private String ibanNationalIdLength;
    private Integer ibanTotalLength;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

    @Embedded
    private IbanBbanEntity ibanBbanEntity;


}

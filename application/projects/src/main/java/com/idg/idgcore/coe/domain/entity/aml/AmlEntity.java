package com.idg.idgcore.coe.domain.entity.aml;

import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntityKey;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_AML_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(AmlEntityKey.class)
public class AmlEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    private String productCategory;
    private String productDescription;
    private char isProductType;
    private String productLimitCurrency;
    private String exchangeRateCode;
    private String exchangeRateType;
    private String debitCreditIndicator;

    @Embedded
    private LimitEntity limitEntity;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}

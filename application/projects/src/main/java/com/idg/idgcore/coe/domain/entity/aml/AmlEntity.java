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
    @Column(name = "product_category")
    private String productCategory;
    @Column(name = "product_description")
    private String productDescription;
    @Column(name = "is_product_type")
    private char isProductType;
    @Column(name = "product_limit_currency")
    private String productLimitCurrency;
    @Column(name = "exchange_rate_code")
    private String exchangeRateCode;
    @Column(name = "exchange_rate_type")
    private String exchangeRateType;
    @Column(name = "debit_credit_indicator")
    private String debitCreditIndicator;

    @Embedded
    private LimitEntity limitEntity;

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

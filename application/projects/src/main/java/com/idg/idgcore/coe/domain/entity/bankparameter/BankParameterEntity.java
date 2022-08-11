package com.idg.idgcore.coe.domain.entity.bankparameter;

import com.fasterxml.jackson.annotation.*;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

//@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_BANK_PARAMETERS_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (BankParameterEntityKey.class)
@JsonIgnoreProperties (ignoreUnknown = true)
public class BankParameterEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="bank_code")
    private String bankCode;
    @Column (name="bank_name")
    private String bankName;
    @Column (name="bank_code_regulatory")
    private String bankCodeRegulatory;
    @Column (name="bank_concise_name")
    private String bankConciseName;
    @Column (name="bank_group_code")
    private String bankGroupCode;
    @Column (name="life_cycle_id")
    private String lifeCycleId;
    @Column (name="reference_no")
    private String referenceNo;
    @Column (name="record_status")
    private String status;
    @Column (name="record_version")
    private Integer recordVersion;
    @Column (name="is_authorized")
    private String authorized;
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;

    @Embedded
    private BankParameterAddressEntity bankParameterAddressEntity;
    @Embedded
    private BankParameterContactInfoEntity bankParameterContactInfoEntity;
    @Embedded
    private BankParameterCurrencyEntity bankParameterCurrencyEntity;
    @Embedded
    private BankParameterPreferencesEntity bankParameterPreferencesEntity;
    @Embedded
    private BankParameterForOdLoanEntity bankParameterForOdLoanEntity;



}

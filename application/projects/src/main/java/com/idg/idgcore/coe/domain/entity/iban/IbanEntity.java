package com.idg.idgcore.coe.domain.entity.iban;

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
@Table (name = "IDGC_COE_IBAN_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (IbanEntityKey.class)
public class IbanEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="iban_country_code")
    private String ibanCountryCode;
    @Column (name="iban_country_position")
    private Integer ibanCountryPosition;
    @Column (name="iban_country_code_length")
    private Integer ibanCountryCodeLength;
    @Column (name="iban_check_digit_position")
    private Integer ibanCheckDigitPosition;
    @Column (name="iban_check_digit_length")
    private Integer ibanCheckDigitLength;
    @Column (name="iban_national_id_length")
    private Integer ibanNationalIdLength;
    @Column (name="iban_total_length")
    private Integer ibanTotalLength;
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
    private IbanBbanEntity ibanBbanEntity;


}

package com.idg.idgcore.coe.domain.entity.capt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table (name = "IDGC_COE_CLEARING_PAYMENT_TYPE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (CaptEntityKey.class)
@JsonIgnoreProperties (ignoreUnknown = true)
public class CaptEntity extends AbstractAuditableDomainEntity implements Serializable
{
    @Id
    @Column(name="clearing_payment_type_code")
    private String clearingPaymentTypeCode;
    @Column(name="clearing_payment_type_name")
    private String clearingPaymentTypeName;
    @Column(name="network_type")
    private String networkType;
    @Column(name="is_clearing_payment_calender")
    private char isClearingPaymentCalender;
    @Column(name="weekly_off1")
    private String weeklyOff1;
    @Column(name="weekly_off2")
    private String weeklyOff2;
    @Column(name="weekly_off3")
    private String weeklyOff3;
    @Column(name="life_cycle_id")
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

}

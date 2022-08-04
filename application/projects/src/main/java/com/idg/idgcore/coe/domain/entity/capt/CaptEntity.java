package com.idg.idgcore.coe.domain.entity.capt;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
    private String clearingPaymentTypeCode;
    private String clearingPaymentTypeName;
    private String networkType;
    private char isClearingPaymentCalender;
    private String weeklyOff1;
    private String weeklyOff2;
    private String weeklyOff3;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}

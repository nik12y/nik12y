package com.idg.idgcore.coe.domain.entity.state;


import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.NoArgsConstructor;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.InheritanceType;
import javax.persistence.Column;
import javax.persistence.Inheritance;
import javax.persistence.IdClass;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_STATE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(StateEntityKey.class)
public class StateEntity extends AbstractAuditableDomainEntity
        implements Serializable {
    @Id
    private String stateCode;
    private String stateName;

    @Column(name = "country_code", updatable = false)
    private String countryCode;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
}
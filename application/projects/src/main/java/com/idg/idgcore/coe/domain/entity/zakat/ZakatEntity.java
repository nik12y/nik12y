package com.idg.idgcore.coe.domain.entity.zakat;

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
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_ZAKAT_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(ZakatEntityKey.class)
public class ZakatEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    private Integer zakatYear;
    private Date startDateOfRamadan;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
}

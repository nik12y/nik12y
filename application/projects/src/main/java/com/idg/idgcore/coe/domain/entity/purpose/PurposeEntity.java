package com.idg.idgcore.coe.domain.entity.purpose;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_PURPOSE_CODE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(PurposeEntityKey.class)
public class PurposeEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    private String purposeCode;
    private String purposeName;
    private String purposeDescription;
    private String purposeType;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}

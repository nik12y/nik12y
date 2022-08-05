package com.idg.idgcore.coe.domain.entity.appvertype;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_APP_VER_TYPE_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(AppVerTypeEntityKey.class)
public class AppVerTypeEntity extends AbstractAuditableDomainEntity
        implements Serializable {

    @Id
    private String verificationTypeId;
    private String verificationTypeName;
    private String verificationTypeDesc;
    private char isViewableToCustomer;
    private char isAlertToBeSentOnCompl;
    private char isExternal;
    private char isDocumentRequired;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "verification_type_id")
    private List<DocumentsEntity> documents;

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
}

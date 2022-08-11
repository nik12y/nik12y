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
    @Column(name = "verification_type_id")
    private String verificationTypeId;
    @Column(name = "verification_type_name")
    private String verificationTypeName;
    @Column(name = "verification_type_desc")
    private String verificationTypeDesc;
    @Column(name = "is_viewable_to_customer")
    private char isViewableToCustomer;
    @Column(name = "is_alert_to_be_sent_on_compl")
    private char isAlertToBeSentOnCompl;
    @Column(name = "is_external")
    private char isExternal;
    @Column(name = "is_document_required")
    private char isDocumentRequired;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "verification_type_id")
    private List<DocumentsEntity> documents;

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

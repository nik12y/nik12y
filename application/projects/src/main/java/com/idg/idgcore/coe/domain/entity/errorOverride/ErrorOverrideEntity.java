package com.idg.idgcore.coe.domain.entity.errorOverride;

import com.idg.idgcore.domain.*;
import lombok.*;

import javax.persistence.*;
import java.io.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_ERROR_OVERRIDE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (ErrorOverrideEntityKey.class)
public class ErrorOverrideEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    private String errorCode;
    private String errorMessage;
    private String typeOfMessage;
    private char isConfirmationRequired;
    private String functionCode;
    private String batchType;

    private String lifeCycleId;
    private String referenceNo;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    private String lastConfigurationAction;

    @Embedded
    private ErrorOverrideLanguageDetailsEntity errorOverrideLanguageDetailsEntity;

    @Embedded
    private ErrorOverrideConversionsEntity errorOverrideConversionsEntity;


}

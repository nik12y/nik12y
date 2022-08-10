package com.idg.idgcore.coe.domain.entity.verificationcategory;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;

import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_APP_CATEGORY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(AppVerCategoryConfigEntityKey.class)

public class AppVerCategoryConfigEntity extends AbstractAuditableDomainEntity
        implements Serializable {

        @Id
        @Column(name="app_verification_category_id")
        private String appVerificationCategoryId;

        @Column(name="verification_category_desc")
        private String verificationCategoryDesc;

        @Column(name="is_external")
        private char isExternal;


        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "app_verification_category_id")
        private List<AppVerTypeConfigEntity> appVerTypeConfigEntity;

        @Column(name="life_cycle_id")
        private String lifeCycleId;

        @Column(name="reference_no")
        private String referenceNo;

        @Column(name="record_status")
        private String status;

        @Column(name="record_version")
        private Integer recordVersion;

        @Column(name="is_authorized")
        private String authorized;

        @Column(name="last_configuration_action")
        private String lastConfigurationAction;


}

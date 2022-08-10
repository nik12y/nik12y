package com.idg.idgcore.coe.domain.entity.categorychecklist;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_APP_VER_CHECKLIST_POLICY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(AppVerCatChecklistPolicyEntityKey.class)
public class AppVerCatChecklistPolicyEntity extends AbstractAuditableDomainEntity
        implements Serializable {

        @Id
        @Column(name="app_ver_checklist_policy_id")
        private String appVerChecklistPolicyId;

        @Column(name="app_ver_checklist_policy_desc")
        private String appVerChecklistPolicyDesc;

        @Column(name="domain_id")
        private String domainId;

        @Column(name="domain_category_id")
        private String domainCategoryId;

        @Column(name="event_id")
        private String eventId;

        @Column(name="effective_date")
        private Date effectiveDate;

        @Column(name="entity")
        private String entity;

        @Column(name="rule_id")
        private String ruleId;

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


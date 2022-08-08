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
        private String appVerChecklistPolicyId;
        private String appVerChecklistPolicyDesc;
        private String domainId;
        private String domainCategoryId;
        private String eventId;
        private Date effectiveDate;
        private String entity;
        private String ruleId;


//        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
//        @JoinColumn(name = "app_ver_checklist_policy_id")
//        private List<AppVerChecklistPolicyCategoryEntity> appVerChecklistPolicyCategoryEntity;
//


        private String status;
        private Integer recordVersion;
        private String authorized;
        private String lastConfigurationAction;
}


package com.idg.idgcore.coe.domain.entity.riskcategory;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@Builder
@Entity
@Table(name = "IDGC_COE_RISK_CATEGORY_CNFG")

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(RiskCategoryEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskCategoryEntity extends AbstractAuditableDomainEntity
        implements Serializable {
        @Id
        @Column(name = "risk_category_code")
        private String riskCategoryCode;
        @Column(name = "risk_category_name")
        private String riskCategoryName;
        @Column(name = "risk_category_description")
        private String riskCategoryDescription;
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






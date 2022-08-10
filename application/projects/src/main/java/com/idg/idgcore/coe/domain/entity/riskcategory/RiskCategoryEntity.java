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

        private String status;
        private Integer recordVersion;
        private String authorized;
        private String lastConfigurationAction;

    }






package com.idg.idgcore.coe.domain.entity.riskcode;

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
@Table(name = "IDGC_COE_RISK_CODE_CNFG")

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(RiskCodeEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiskCodeEntity extends AbstractAuditableDomainEntity
        implements Serializable {
        @Id
     //   @Column(name = "risk_code")
        private String riskCode;
      //  @Column(name = "risk_code_name")
        private String riskCodeName;
     //   @Column(name = "risk_code_description")
        private String riskCodeDescription;
        private String riskCategoryCode;

        private Character isAllowDetailsModified;
        private String riskMode;

        private String status;
        private Integer recordVersion;
        private String authorized;
        private String lastConfigurationAction;

    }






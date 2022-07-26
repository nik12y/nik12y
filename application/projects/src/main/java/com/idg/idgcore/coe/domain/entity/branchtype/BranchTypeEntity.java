package com.idg.idgcore.coe.domain.entity.branchtype;

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
@Table(name = "IDGC_COE_BRANCH_TYPE_CNFG")

@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(BranchTypeEntityKey.class)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BranchTypeEntity extends AbstractAuditableDomainEntity
        implements Serializable {
        @Id
        private String branchTypeCode;
       // @Column(name="branch_type_code", updatable = false)
        private String branchTypeName;

        private String status;
        private Integer recordVersion;
        private String authorized;
        private String lastConfigurationAction;

    }






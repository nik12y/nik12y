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
        private String appVerificationCategoryId;
        private String verificationCategoryDesc;
        private char isExternal;



//        @Embedded
        @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
        @JoinColumn(name = "app_verification_category_id")
        private List<AppVerTypeConfigEntity> appVerTypeConfigEntity;

        private String status;
        private Integer recordVersion;
        private String authorized;
        private String lastConfigurationAction;

}

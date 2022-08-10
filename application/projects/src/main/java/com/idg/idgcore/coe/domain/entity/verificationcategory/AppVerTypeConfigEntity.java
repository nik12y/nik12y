package com.idg.idgcore.coe.domain.entity.verificationcategory;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.ToString;
import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_APP_CATEGORY_TYPE_CNFG")
@ToString
public class AppVerTypeConfigEntity extends AbstractAuditableDomainEntity
        implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="app_category_type_id")
    private Integer appCategoryTypeId;

    @Column(name="app_verification_type_id")
    private String appVerificationTypeId;

    @Column(name="is_view_to_customer")
    private char isViewToCustomer;

    @Column(name="nature")
    private String nature;

}

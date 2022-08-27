package com.idg.idgcore.coe.domain.entity.regulatoryRegion;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_VE_REG_REGION_MAPPING_B")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class RegulatoryRegionMappingEntity  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reg_region_mapping_id")
    private Integer regRegionMappingId;
    @Column(name = "demographic_mapping_code")
    private String demographicMappingCode;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;
//    @Column(name = "demographic_mapping_name")
//    private String demographicMappingName;
}

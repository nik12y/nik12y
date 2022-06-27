package com.idg.idgcore.domain.entity;

import lombok.*;

import javax.persistence.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table (name = "IDGC_COE_MAPPING_CONFIG")
@ToString
public class MappingEntity {
    @Id
    private Long id;
    private String action;
    private String role;
    private String authorized;
    private String status;
    private String updateRecordVersion;
    private String inactivePreviousRecord;
    private String lastConfigurationAction;
    private String insertRecordIntoAudit;
    private String insertRecordIntoBasetable;
    private String mappingStatus;

}

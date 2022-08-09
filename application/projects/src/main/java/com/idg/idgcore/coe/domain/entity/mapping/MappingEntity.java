package com.idg.idgcore.coe.domain.entity.mapping;

import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.ToString;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;

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
    private String copyRecordFromBaseTable;
    private String mappingStatus;

}

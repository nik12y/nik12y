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
import javax.persistence.Column;

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
    @Column (name = "mapping_id")
    private Long id;
    @Column (name = "record_action")
    private String action;
    @Column (name = "use_role")
    private String role;
    @Column (name = "is_authorized")
    private String authorized;
    @Column (name = "record_status")
    private String status;
    @Column (name = "update_record_version")
    private String updateRecordVersion;
    @Column (name = "inactive_previous_record")
    private String inactivePreviousRecord;
    @Column (name = "last_configuration_action")
    private String lastConfigurationAction;
    @Column (name = "insert_record_into_audit")
    private String insertRecordIntoAudit;
    @Column (name = "insert_record_into_basetable")
    private String insertRecordIntoBasetable;
    @Column (name = "copy_record_from_base_table")
    private String copyRecordFromBaseTable;
    @Column (name = "mapping_status")
    private String mappingStatus;

}

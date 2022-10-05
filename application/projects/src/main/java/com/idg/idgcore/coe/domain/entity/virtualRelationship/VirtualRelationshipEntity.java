package com.idg.idgcore.coe.domain.entity.virtualRelationship;

import com.vladmihalcea.hibernate.type.json.JsonType;
import lombok.*;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_VE_RELATIONSHIP_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@TypeDef(name = "json", typeClass = JsonType.class)
@ToString

public class VirtualRelationshipEntity implements Serializable {

    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "json_tree", columnDefinition = "clob")
    private String jsonData;
}

package com.idg.idgcore.coe.domain.entity.appvertype;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_APP_VER_TYPE_DOC_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class DocumentsEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name="idgc_coe_app_ver_type_doc_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer idgcCoeAppVerTypeDocId;
    @Column(name = "document_name")
    private String documentName;
    @Column(name = "nature")
    private String nature;
}

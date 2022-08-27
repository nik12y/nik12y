package com.idg.idgcore.coe.domain.entity.questionCategory;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_QUE_CAT_DETAIL_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
//@IdClass(QuestionCatDetailsKey.class)
public class QuestionCatDetailsEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer queCatDetailId;
    private String questionId;
    private String questionNature;
    private String parentQuestionId;
    private String displayCondition;
    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;


}

package com.idg.idgcore.coe.domain.entity.question;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_QUESTION_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(QuestionEntityKey.class)
public class QuestionEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "question_id")
    private String questionId;
    @Column(name = "question_name")
    private String questionName;
    @Column(name = "question_text")
    private String questionText;
    @Column(name = "question_description")
    private String questionDescription;
    @Column(name = "answer_display_type")
    private String answerDisplayType;
    @Column(name = "manual_fact_based")
    private String manualFactBased;
    @Column(name = "answer_values")
    private String answerValue;
    @Column(name = "life_cycle_id")
    private String lifeCycleId;
    @Column(name = "reference_no")
    private String referenceNo;

    @Column(name = "record_status")
    private String status;
    @Column(name = "record_version")
    private Integer recordVersion;
    @Column(name = "is_authorized")
    private String authorized;
    @Column(name = "last_configuration_action")
    private String lastConfigurationAction;
}

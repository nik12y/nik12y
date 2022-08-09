package com.idg.idgcore.coe.domain.entity.questionCategory;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "IDGC_COE_QUESTION_CATEGORY_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
@IdClass(QuestionCategoryEntityKey.class)
public class QuestionCategoryEntity extends AbstractAuditableDomainEntity implements Serializable {

    @Id
    @Column(name = "question_category_id")
    private String questionCategoryId;
    @Column(name = "question_category_name")
    private String questionCategoryName;
    @Column(name = "question_display")
    private String questionDisplay;
    @Column(name = "is_show_que_category_name")
    private char isShowQuestionCategoryName;
    @Column(name = "is_enable_document_upload")
    private char isEnableDocumentUpload;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "question_category_id")
    private List<QuestionCatDetailsEntity> questionCatDetails = new ArrayList<>();

    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;
}

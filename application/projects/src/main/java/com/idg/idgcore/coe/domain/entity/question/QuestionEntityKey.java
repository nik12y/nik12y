package com.idg.idgcore.coe.domain.entity.question;

import com.idg.idgcore.domain.AbstractDomainKey;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "IDGC_COE_QUESTION_CNFG")
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class QuestionEntityKey  extends AbstractDomainKey
        implements Serializable {

    @Id
    private String questionId;

    @Override
    public String keyAsString() {
        return questionId;
    }
}

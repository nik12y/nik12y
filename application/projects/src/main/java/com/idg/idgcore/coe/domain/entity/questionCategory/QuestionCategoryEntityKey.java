package com.idg.idgcore.coe.domain.entity.questionCategory;

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
@Inheritance(strategy = InheritanceType.JOINED)
@ToString
public class QuestionCategoryEntityKey extends AbstractDomainKey
        implements Serializable {
    @Id
    private String questionCategoryId;

    @Override
    public String keyAsString() {
        return questionCategoryId;
    }
}

package com.idg.idgcore.coe.app.service.question;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.question.QuestionAssembler;
import com.idg.idgcore.coe.domain.entity.question.QuestionEntity;
import com.idg.idgcore.coe.domain.service.question.QuestionDomainService;
import com.idg.idgcore.coe.dto.question.QuestionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.QUESTION;

@Slf4j
@Service ("questionApplicationService")
public class QuestionApplicationService extends GenericApplicationService<QuestionDTO,
        QuestionEntity, QuestionDomainService, QuestionAssembler> {
    protected QuestionApplicationService() {
        super(QUESTION);
    }

    public String getTaskCode () {
        return QuestionDTO.builder().build().getTaskCode();
    }

}
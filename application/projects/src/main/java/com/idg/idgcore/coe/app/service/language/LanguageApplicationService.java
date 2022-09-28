package com.idg.idgcore.coe.app.service.language;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.common.Constants;
import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.service.language.LanguageDomainService;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service ("languageApplicationService")
public class LanguageApplicationService
        extends GenericApplicationService
            <LanguageDTO, LanguageEntity,
                    LanguageDomainService, LanguageAssembler> {
    LanguageApplicationService() {
        super(Constants.LANGUAGE);
    }
    public String getTaskCode() {
        return LanguageDTO.builder().build().getTaskCode();
    }
}

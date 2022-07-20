package com.idg.idgcore.coe.domain.service.language;

import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.dto.language.LanguageDTO;

import java.util.List;

public interface ILanguageDomainService {

    LanguageEntity getConfigurationByCode (LanguageDTO languageDTO);
    List<LanguageEntity> getLanguages ();
    LanguageEntity getLanguageByCode (String languageCode);
    void save (LanguageDTO languageDTO);
}

package com.idg.idgcore.coe.domain.service.language;

import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.repository.language.ILanguageRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class LanguageDomainService extends DomainService<LanguageDTO, LanguageEntity> {
    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private LanguageAssembler languageAssembler;

    public LanguageEntity getEntityByIdentifier(String languageCode) {
        return this.languageRepository.findByLanguageCode(languageCode);
    }

    public List<LanguageEntity> getLanguages () {
        return this.languageRepository.findAll();
    }

    @Override
    public List<LanguageEntity> getAllEntities() { return getLanguages(); }

    public void save (LanguageDTO languageDTO) {
        LanguageEntity entity = languageAssembler.toEntity(languageDTO);
        this.languageRepository.save(entity);
    }
}
package com.idg.idgcore.coe.domain.service.language;

import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.repository.language.ILanguageRepository;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class LanguageDomainService implements ILanguageDomainService {
    @Autowired
    private ILanguageRepository languageRepository;

    @Autowired
    private LanguageAssembler languageAssembler;

    public LanguageEntity getConfigurationByCode (LanguageDTO languageDTO) {
        LanguageEntity languageEntity = null;
        try {
            languageEntity = this.languageRepository.findByLanguageCode(languageDTO.getLanguageCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return languageEntity;
    }

    public List<LanguageEntity> getLanguages () {
        return this.languageRepository.findAll();
    }

    public LanguageEntity getLanguageByCode (String languageCode) {
        LanguageEntity languageEntity = null;
        try {
            languageEntity = this.languageRepository.findByLanguageCode(languageCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return languageEntity;
    }

    public void save (LanguageDTO languageDTO) {
        try{
            LanguageEntity languageEntity = languageAssembler.convertDtoToEntity(languageDTO);
            this.languageRepository.save(languageEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}
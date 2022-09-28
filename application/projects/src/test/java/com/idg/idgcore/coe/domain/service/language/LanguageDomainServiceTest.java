package com.idg.idgcore.coe.domain.service.language;

import com.idg.idgcore.coe.domain.assembler.language.LanguageAssembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.repository.language.ILanguageRepository;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageDomainServiceTest {

    @Mock
    private ILanguageRepository languageRepository;

    @InjectMocks
    private LanguageDomainService languageDomainService;

    private LanguageEntity languageEntity;
    private LanguageDTO languageDTO;
    private LanguageAssembler languageAssembler;


    @BeforeEach
    void setUp() {
        languageDTO=getLanguageDTO();
        languageEntity=getLanguageEntity();
    }

    private LanguageEntity getLanguageEntity() {
        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageCode("en");
        languageEntity.setLanguageCodeAlternate("eng");
        languageEntity.setLanguageName("english");
        languageEntity.setLocaleCode("en");
        languageEntity.setLocaleName("english(I=UK)");
        languageEntity.setStatus("draft");
        languageEntity.setRecordVersion(0);
        return languageEntity;
    }

    private LanguageDTO getLanguageDTO() {
        LanguageDTO languageDTO = new LanguageDTO();
        languageDTO.setLanguageCode("en");
        languageDTO.setLanguageCodeAlternate("eng");
        languageDTO.setLanguageName("english");
        languageDTO.setLocaleCode("en");
        languageDTO.setLocaleName("english(UK)");
        languageDTO.setStatus("DELETED");
        languageDTO.setRecordVersion(1);
        return languageDTO;
    }

    @Test
    @DisplayName("Junit test for language method ")
    void getLanguageReturnlanguagesList() {
        given(languageRepository.findAll()).willReturn(List.of(languageEntity));
        List<LanguageEntity> languageEntityList = languageDomainService.getLanguages();
        assertThat(languageEntityList).isNotNull();
        assertThat(languageEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit for getConfigurationByCode for return the language when the language code is valid")
    void getConfigurationByCodeWhenLanguageCodeIsValid() {
        LanguageDTO languageDTO = LanguageDTO.builder().languageCode("EN").build();

        LanguageEntity languageEntity = new LanguageEntity();
        languageEntity.setLanguageCode("EN");
        languageEntity.setLanguageCodeAlternate("ENG");
        languageEntity.setLanguageName("English");
        languageEntity.setLocaleCode("EN");
        languageEntity.setLocaleName("English(UK)");

        when(languageRepository.findByLanguageCode(languageDTO.getLanguageCode()))
                .thenReturn(languageEntity);
        LanguageEntity result = languageDomainService.getEntityByIdentifier(languageDTO.getLanguageCode());
        assertEquals(languageEntity, result);
    }

    @Test
    @DisplayName("JUnit for getConfigurationByCode for exception when the language code is invalid")
    void getConfigurationByCodeWhenLanguageCodeIsInvalidThenThrowException() {
        assertThrows(
                BusinessException.class, () -> {
                    languageDomainService.getEntityByIdentifier(null);
                });
    }
}
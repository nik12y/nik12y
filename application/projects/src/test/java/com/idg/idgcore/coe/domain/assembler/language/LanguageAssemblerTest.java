package com.idg.idgcore.coe.domain.assembler.language;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class LanguageAssemblerTest {

    @InjectMocks
    private LanguageAssembler languageAssembler;

    @Test
    @DisplayName("JUnit for setAuditFields where set the authorized field in languageDTO")
    void setAuditFieldsShouldSetAuthorized() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        LanguageDTO languageDTO = new LanguageDTO();

        languageDTO = languageAssembler.setAuditFields(mutationEntity, languageDTO);
        assertEquals("Y", languageDTO.getAuthorized());
    }
}
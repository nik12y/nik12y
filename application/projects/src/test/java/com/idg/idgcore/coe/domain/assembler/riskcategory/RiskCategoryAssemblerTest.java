package com.idg.idgcore.coe.domain.assembler.riskcategory;


import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RiskCategoryAssemblerTest {

    @InjectMocks
    private RiskCategoryAssembler riskCategoryAssembler;

    @Test
    @DisplayName("JUnit for setAuditFields where set the authorized field in riskCategoryDTO")
    void setAuditFieldsShouldSetAuthorized() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        RiskCategoryDTO riskCategoryDTO = new RiskCategoryDTO();
        riskCategoryAssembler.setAuditFields(mutationEntity, riskCategoryDTO);
        assertEquals("Y", riskCategoryDTO.getAuthorized());
    }
}
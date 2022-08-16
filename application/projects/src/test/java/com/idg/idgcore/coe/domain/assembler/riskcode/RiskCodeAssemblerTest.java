package com.idg.idgcore.coe.domain.assembler.riskcode;


import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class RiskCodeAssemblerTest {

    @InjectMocks
    private RiskCodeAssembler riskCodeAssembler;
    @Test
    @DisplayName("JUnit for setAuditFields where set the authorized field in riskCodeDTO")
    void setAuditFieldsShouldSetAuthorized() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        RiskCodeDTO riskCodeDTO = new RiskCodeDTO();
        riskCodeDTO = riskCodeAssembler.setAuditFields(mutationEntity, riskCodeDTO);
        assertEquals("Y", riskCodeDTO.getAuthorized());
    }

}
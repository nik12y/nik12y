package com.idg.idgcore.coe.domain.assembler.branchtype;


import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class BranchTypeAssemblerTest {

    @InjectMocks
    private BranchTypeAssembler branchTypeAssembler;
    @Test
    @DisplayName("JUnit for setAuditFields where set the authorized field in branchTypeDTO")
    void setAuditFieldsShouldSetAuthorized() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        BranchTypeDTO branchTypeDTO = new BranchTypeDTO();
        branchTypeDTO = branchTypeAssembler.setAuditFields(mutationEntity, branchTypeDTO);
        assertEquals("Y", branchTypeDTO.getAuthorized());
    }

}
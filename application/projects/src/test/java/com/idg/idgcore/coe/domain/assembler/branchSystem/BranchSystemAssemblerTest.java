package com.idg.idgcore.coe.domain.assembler.branchSystem;

import com.idg.idgcore.coe.domain.assembler.branchSystem.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystem.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.branchSystem.BranchSystemDateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class BranchSystemAssemblerTest {

    @InjectMocks
    private BranchSystemDateAssembler branchSystemDateAssembler;

    @Test
    @DisplayName("Should set the authorized field in branchSystemDateAssembler")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        BranchSystemDateDTO branchSystemDateDTO = BranchSystemDateDTO.builder().build();
        branchSystemDateDTO = branchSystemDateAssembler.setAuditFields(mutationEntity, branchSystemDateDTO);
        assertEquals("Y", branchSystemDateDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in purgingDTO")
    void convertEntityToDTO(){
        BranchSystemDateEntity branchSystemDateEntity=new BranchSystemDateEntity();
        BranchSystemDateDTO branchSystemDateDTO=branchSystemDateAssembler.convertEntityToDto(branchSystemDateEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in purgingDTO")
    void convertDTOToEntity(){
        BranchSystemDateDTO branchSystemDateDTO=new BranchSystemDateDTO();
        BranchSystemDateEntity branchSystemDateEntity=branchSystemDateAssembler.convertDtoToEntity(branchSystemDateDTO);
    }
}

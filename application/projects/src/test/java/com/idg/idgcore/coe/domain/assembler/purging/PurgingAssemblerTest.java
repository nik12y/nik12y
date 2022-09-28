package com.idg.idgcore.coe.domain.assembler.purging;

import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PurgingAssemblerTest {

    @InjectMocks
    private PurgingAssembler purgingAssembler;

    @Test
    @DisplayName("Should set the authorized field in purgingDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        PurgingDTO purgingDTO = PurgingDTO.builder().build();
        purgingAssembler.setAuditFields(mutationEntity, purgingDTO);
        assertEquals("Y", purgingDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in purgingDTO")
    void convertEntityToDTO(){
        PurgingEntity purgingEntity=new PurgingEntity();
        PurgingDTO purgingDTO=purgingAssembler.toDTO(purgingEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in purgingDTO")
    void convertDTOToEntity(){
        PurgingDTO purgingDTO=new PurgingDTO();
        PurgingEntity purgingEntity=purgingAssembler.toEntity(purgingDTO);
    }
}

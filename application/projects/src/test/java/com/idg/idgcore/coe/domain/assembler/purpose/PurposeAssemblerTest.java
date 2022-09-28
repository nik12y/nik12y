package com.idg.idgcore.coe.domain.assembler.purpose;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PurposeAssemblerTest {

    @InjectMocks
    private PurposeAssembler purposeAssembler;
    @Test
    @DisplayName("Should set the authorized field in purposeDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        PurposeDTO purposeDTO = PurposeDTO.builder().build();
        purposeAssembler.setAuditFields(mutationEntity, purposeDTO);
        assertEquals("Y", purposeDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in purposeDTO")
    void convertEntityToDTO(){
        PurposeEntity purposeEntity=new PurposeEntity();
        PurposeDTO purposeDTO=purposeAssembler.toDTO(purposeEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in purposeDTO")
    void convertDTOToEntity(){
        PurposeDTO purposeDTO=new PurposeDTO();
        PurposeEntity purposeEntity=purposeAssembler.toEntity(purposeDTO);
    }

}
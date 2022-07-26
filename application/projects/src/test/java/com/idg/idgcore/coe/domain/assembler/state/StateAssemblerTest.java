package com.idg.idgcore.coe.domain.assembler.state;


import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;

import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.dto.state.StateDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class StateAssemblerTest {

        @InjectMocks
        private StateAssembler stateAssembler;


        @Test
        @DisplayName("Should set the authorized field in stateDTO")
        void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
            MutationEntity mutationEntity = new MutationEntity();
            mutationEntity.setAuthorized("Y");
            StateDTO stateDTO = StateDTO.builder().build();
            stateDTO = stateAssembler.setAuditFields(mutationEntity, stateDTO);
            assertEquals("Y", stateDTO.getAuthorized());
        }

         @Test
         @DisplayName("Should set the authorized field in stateDTO")
        void convertEntityToDTO(){
             StateEntity stateEntity=new StateEntity();
            StateDTO stateDTO=stateAssembler.convertEntityToDto(stateEntity);
         }

    }


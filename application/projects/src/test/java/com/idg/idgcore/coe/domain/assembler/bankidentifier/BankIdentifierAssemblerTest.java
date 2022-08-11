package com.idg.idgcore.coe.domain.assembler.bankidentifier;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class BankIdentifierAssemblerTest {

    @InjectMocks
    private BankIdentifierAssembler bankIdentifierAssembler;


    @Test
    @DisplayName("JUnit Test for setAuditFields where set the authorized field in bankIdentifierDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInBankIdentifierDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        BankIdentifierDTO bankIdentifierDTO = BankIdentifierDTO.builder().build();

        bankIdentifierDTO =
                bankIdentifierAssembler.setAuditFields(mutationEntity, bankIdentifierDTO);

        assertEquals("Y", bankIdentifierDTO.getAuthorized());
    }
}
package com.idg.idgcore.coe.domain.assembler.zakat;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.*;

class ZakatAssemblerTest {

    @Mock
    private ZakatAssembler zakatAssembler = new ZakatAssembler();

    @Test
    @DisplayName("JUnit Test for setAuditFields where set the authorized field in zakatDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInZakatDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        ZakatDTO zakatDTO = new ZakatDTO();
        zakatDTO.setZakatYear(2021);
        zakatDTO.setStartDateOfRamadan("2021-04-13");
        zakatDTO.setAuthorized("Y");
        zakatDTO = zakatAssembler.setAuditFields(mutationEntity, zakatDTO);
        assertEquals("Y", zakatDTO.getAuthorized());
    }
}
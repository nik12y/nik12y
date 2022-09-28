package com.idg.idgcore.coe.domain.assembler.bankidentifier;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import org.springframework.stereotype.Component;

@Component
public class BankIdentifierAssembler extends Assembler<BankIdentifierDTO, BankIdentifierEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return BankIdentifierDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BankIdentifierEntity.class;
    }
}

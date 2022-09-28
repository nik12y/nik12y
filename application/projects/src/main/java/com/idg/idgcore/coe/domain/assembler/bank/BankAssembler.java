package com.idg.idgcore.coe.domain.assembler.bank;


import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BankAssembler extends Assembler<BankDTO, BankEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return BankDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BankEntity.class;
    }
}

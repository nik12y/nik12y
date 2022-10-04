package com.idg.idgcore.coe.domain.assembler.language;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class LanguageAssembler extends Assembler<LanguageDTO, LanguageEntity> {

    @Override
    public Class getSpecificDTOClass() { return LanguageDTO.class; }
    @Override
    public Class getSpecificEntityClass() { return LanguageEntity.class; }

}

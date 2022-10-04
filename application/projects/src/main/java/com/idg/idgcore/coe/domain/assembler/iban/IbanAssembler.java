package com.idg.idgcore.coe.domain.assembler.iban;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.iban.IbanBbanEntity;
import com.idg.idgcore.coe.domain.entity.iban.IbanEntity;
import com.idg.idgcore.coe.dto.iban.IbanBbanDTO;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import org.springframework.stereotype.Component;

@Component
public class IbanAssembler extends Assembler<IbanDTO, IbanEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return IbanDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return IbanEntity.class;
    }

    @Override
    public IbanEntity toEntity(IbanDTO ibanDTO) {
        /**
         * For Iban Bban
         */
        IbanBbanDTO ibanBbanDTO= ibanDTO.getIbanBban();
        IbanBbanEntity ibanBbanEntity = modelMapper.map(ibanBbanDTO, IbanBbanEntity.class);
        /**
         * Set Entity values
         */
        IbanEntity ibanEntity = modelMapper.map(ibanDTO, IbanEntity.class);
        /**
         * Set Iban
         */
        ibanEntity.setIbanBbanEntity(ibanBbanEntity);
        return ibanEntity;
    }

    @Override
    public IbanDTO toDTO(IbanEntity ibanEntity) {
        /**
         * For Iban Bban
         */
        IbanBbanEntity ibanBbanEntity= ibanEntity.getIbanBbanEntity();
        IbanBbanDTO ibanBbanDTO = modelMapper.map(ibanBbanEntity, IbanBbanDTO.class);
        /**
         * Set Entity values
         */
        IbanDTO ibanDTO = modelMapper.map(ibanEntity, IbanDTO.class);
        ibanDTO.setIbanBban(ibanBbanDTO);
        /**
         * Set Iban Entity values
         */
        return ibanDTO;
    }
}

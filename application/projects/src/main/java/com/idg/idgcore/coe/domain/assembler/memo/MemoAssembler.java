package com.idg.idgcore.coe.domain.assembler.memo;

import com.idg.idgcore.coe.domain.entity.memo.MemoEntity;
import com.idg.idgcore.coe.dto.memo.MemoDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


@Component
public class MemoAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public MemoEntity convertDtoToEntity (MemoDTO memoDTO) {
        return modelMapper.map(memoDTO, MemoEntity.class);
    }

    public MemoDTO convertEntityToDto (MemoEntity memoEntity) {
        return modelMapper.map(memoEntity, MemoDTO.class);
    }
}

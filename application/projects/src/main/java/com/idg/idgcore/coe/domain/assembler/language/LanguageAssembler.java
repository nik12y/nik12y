package com.idg.idgcore.coe.domain.assembler.language;

import com.idg.idgcore.coe.domain.entity.language.LanguageEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class LanguageAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public LanguageEntity convertDtoToEntity (LanguageDTO languageDTO) {
        LanguageEntity languageEntity = modelMapper.map(languageDTO, LanguageEntity.class);
        return languageEntity;
    }

    public LanguageDTO convertEntityToDto (LanguageEntity languageEntity) {
        LanguageDTO languageDTO = modelMapper.map(languageEntity, LanguageDTO.class);
        return languageDTO;
    }

    public LanguageDTO setAuditFields (MutationEntity  mutationEntity, LanguageDTO languageDTO) {
        languageDTO.setAction(mutationEntity.getAction());
        languageDTO.setAuthorized(mutationEntity.getAuthorized());
        languageDTO.setRecordVersion(mutationEntity.getRecordVersion());
        languageDTO.setStatus(mutationEntity.getStatus());
        languageDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        languageDTO.setCreatedBy(mutationEntity.getCreatedBy());
        languageDTO.setCreationTime(mutationEntity.getCreationTime());
        languageDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        languageDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        languageDTO.setTaskCode(mutationEntity.getTaskCode());
        languageDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return languageDTO;
    }

//    public char getCharValueFromBoolean (boolean value) {
//        return value ? CHAR_Y : CHAR_N;
//    }
//
//    public boolean getBooleanValueFromChar (Character value) {
//        return value.equals(CHAR_Y);
//    }
}

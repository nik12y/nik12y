package com.idg.idgcore.coe.domain.assembler.mitigant;

import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantRiskCodeEntity;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantRiskCodeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;
import org.modelmapper.TypeToken;
import java.lang.reflect.Type;



import javax.annotation.PostConstruct;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class MitigantAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public MitigantEntity convertDtoToEntity(MitigantDTO mitigantDTO) {

        List<MitigantRiskCodeDTO> mitigantRiskCodeDTOList = mitigantDTO.getMitigantRiskCode();
        Type listType = new TypeToken<List<MitigantRiskCodeEntity>>(){}.getType();
        List<MitigantRiskCodeEntity> mitigantRiskCodeEntityList = modelMapper.map(mitigantRiskCodeDTOList,listType);
        MitigantEntity mitigantEntity = modelMapper.map(mitigantDTO, MitigantEntity.class);
        mitigantEntity.setMitigantRiskCode(mitigantRiskCodeEntityList);
        mitigantEntity.setIsAllowModification(getCharValueFromBoolean(mitigantDTO.getIsAllowModification()));
        mitigantEntity.setIsActionable(getCharValueFromBoolean(mitigantDTO.getIsActionable()));
        return mitigantEntity;
    }

    public MitigantDTO convertEntityToDto(MitigantEntity mitigantEntity) {

        List<MitigantRiskCodeEntity> mitigantRiskCodeEntityList = mitigantEntity.getMitigantRiskCode();
        Type listType = new TypeToken<List<MitigantRiskCodeDTO>>(){}.getType();
        List<MitigantRiskCodeDTO> mitigantRiskCodeDTOList = modelMapper.map(mitigantRiskCodeEntityList,listType);
        MitigantDTO mitigantDTO = modelMapper.map(mitigantEntity, MitigantDTO.class);
        mitigantDTO.setMitigantRiskCode(mitigantRiskCodeDTOList);
        mitigantDTO.setIsAllowModification(getBooleanValueFromChar(mitigantEntity.getIsAllowModification()));
        mitigantDTO.setIsActionable(getBooleanValueFromChar(mitigantEntity.getIsActionable()));
        return mitigantDTO;
    }

    public MitigantDTO setAuditFields (MutationEntity mutationEntity, MitigantDTO mitigantDTO) {
        mitigantDTO.setAction(mutationEntity.getAction());
        mitigantDTO.setAuthorized(mutationEntity.getAuthorized());
        mitigantDTO.setRecordVersion(mutationEntity.getRecordVersion());
        mitigantDTO.setStatus(mutationEntity.getStatus());
        mitigantDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        mitigantDTO.setCreatedBy(mutationEntity.getCreatedBy());
        mitigantDTO.setCreationTime(mutationEntity.getCreationTime());
        mitigantDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        mitigantDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        mitigantDTO.setTaskCode(mutationEntity.getTaskCode());
        mitigantDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return mitigantDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}

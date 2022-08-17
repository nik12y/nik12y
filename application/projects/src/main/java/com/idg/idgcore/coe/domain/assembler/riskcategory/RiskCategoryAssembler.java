package com.idg.idgcore.coe.domain.assembler.riskcategory;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class RiskCategoryAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public RiskCategoryEntity convertDtoToEntity(RiskCategoryDTO riskCategoryDTO) {
        RiskCategoryEntity riskCategoryEntity = modelMapper.map(riskCategoryDTO, RiskCategoryEntity.class);

        return riskCategoryEntity;
    }


    public RiskCategoryDTO convertEntityToDto(RiskCategoryEntity riskCategoryEntity) {
        RiskCategoryDTO riskCategoryDTO = modelMapper.map(riskCategoryEntity, RiskCategoryDTO.class);

        return riskCategoryDTO;
    }

    public RiskCategoryDTO setAuditFields (MutationEntity mutationEntity, RiskCategoryDTO riskCategoryDTO) {
        riskCategoryDTO.setAction(mutationEntity.getAction());
        riskCategoryDTO.setAuthorized(mutationEntity.getAuthorized());
        riskCategoryDTO.setRecordVersion(mutationEntity.getRecordVersion());
        riskCategoryDTO.setStatus(mutationEntity.getStatus());
        riskCategoryDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        riskCategoryDTO.setCreatedBy(mutationEntity.getCreatedBy());
        riskCategoryDTO.setCreationTime(mutationEntity.getCreationTime());
        riskCategoryDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        riskCategoryDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        riskCategoryDTO.setTaskCode(mutationEntity.getTaskCode());
        riskCategoryDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return riskCategoryDTO;
    }
    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }

}

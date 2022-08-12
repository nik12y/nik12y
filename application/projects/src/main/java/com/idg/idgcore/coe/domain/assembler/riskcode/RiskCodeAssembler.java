package com.idg.idgcore.coe.domain.assembler.riskcode;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class RiskCodeAssembler {

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public RiskCodeEntity convertDtoToEntity(RiskCodeDTO riskCodeDTO) {
        RiskCodeEntity riskCodeEntity = modelMapper.map(riskCodeDTO, RiskCodeEntity.class);
//        riskCodeEntity.setRiskCode(riskCodeDTO.getRiskCode());
//        riskCodeEntity.setRiskCodeName(riskCodeDTO.getRiskCodeName());
//        riskCodeEntity.setRiskCodeDescription(riskCodeDTO.getRiskCodeDescription());
        riskCodeEntity.setIsAllowDetailsModified(getCharValueFromBoolean(riskCodeDTO.getIsAllowDetailsModified()));
//        riskCodeEntity.setRiskCategoryCode(riskCodeDTO.getRiskCategoryCode());



        return riskCodeEntity;
    }


    public RiskCodeDTO convertEntityToDto(RiskCodeEntity riskCodeEntity) {
        RiskCodeDTO riskCodeDTO = modelMapper.map(riskCodeEntity, RiskCodeDTO.class);
        riskCodeDTO.setIsAllowDetailsModified(getBooleanValueFromChar(riskCodeEntity.getIsAllowDetailsModified()));
//        riskCodeDTO.setRiskCode(riskCodeEntity.getRiskCode());
//        riskCodeDTO.setRiskCodeName(riskCodeEntity.getRiskCodeName());
//        riskCodeDTO.setRiskCategoryCode(riskCodeEntity.getRiskCategoryCode());
//        riskCodeDTO.setRiskCodeDescription(riskCodeEntity.getRiskCodeDescription());
//        riskCodeDTO.setRiskMode(riskCodeEntity.getRisk_Mode());

        return riskCodeDTO;
    }

    public RiskCodeDTO setAuditFields (MutationEntity mutationEntity, RiskCodeDTO riskCodeDTO) {
        riskCodeDTO.setAction(mutationEntity.getAction());
        riskCodeDTO.setAuthorized(mutationEntity.getAuthorized());
        riskCodeDTO.setRecordVersion(mutationEntity.getRecordVersion());
        riskCodeDTO.setStatus(mutationEntity.getStatus());
        riskCodeDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        riskCodeDTO.setCreatedBy(mutationEntity.getCreatedBy());
        riskCodeDTO.setCreationTime(mutationEntity.getCreationTime());
        riskCodeDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        riskCodeDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        riskCodeDTO.setTaskCode(mutationEntity.getTaskCode());
        riskCodeDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return riskCodeDTO;
    }
    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }

}

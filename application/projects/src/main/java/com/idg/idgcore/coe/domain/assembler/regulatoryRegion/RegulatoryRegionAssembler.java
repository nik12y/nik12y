package com.idg.idgcore.coe.domain.assembler.regulatoryRegion;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionMappingEntity;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionMappingDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

@Component
public class RegulatoryRegionAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public RegulatoryRegionConfigDTO convertEntityToDto(RegulatoryRegionConfigEntity regulatoryRegionConfigEntity) {
        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntity = regulatoryRegionConfigEntity.getRegulatoryRegionMappingEntity();
        Type listType = new TypeToken<List<RegulatoryRegionMappingDTO>>() {
        }.getType();
        List<RegulatoryRegionMappingDTO> regulatoryRegionMappingDTOList = modelMapper.map(regulatoryRegionMappingEntity, listType);

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = modelMapper.map(regulatoryRegionConfigEntity, RegulatoryRegionConfigDTO.class);
        regulatoryRegionConfigDTO.setRegRegionCode(regulatoryRegionConfigEntity.getRegRegionCode());
        regulatoryRegionConfigDTO.setRegionName(regulatoryRegionConfigEntity.getRegionName());
        regulatoryRegionConfigDTO.setRegionDescription(regulatoryRegionConfigEntity.getRegionDescription());
        regulatoryRegionConfigDTO.setRegionEffectiveDate(formatter.format(regulatoryRegionConfigEntity.getRegionEffectiveDate()));
        regulatoryRegionConfigDTO.setRegionGroupCode(regulatoryRegionConfigEntity.getRegionGroupCode());
        regulatoryRegionConfigDTO.setPurpose(regulatoryRegionConfigEntity.getPurpose());
        regulatoryRegionConfigDTO.setRegulatoryRegionMapping(regulatoryRegionMappingDTOList);
        return regulatoryRegionConfigDTO;
    }

    public RegulatoryRegionConfigEntity convertDtoToEntity(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) throws ParseException {

        List<RegulatoryRegionMappingDTO> regulatoryRegionMappingDTOList = regulatoryRegionConfigDTO.getRegulatoryRegionMapping();

        Type listType = new TypeToken<List<RegulatoryRegionMappingEntity>>() {
        }.getType();
        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntities = modelMapper.map(regulatoryRegionMappingDTOList, listType);

        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = modelMapper.map(regulatoryRegionConfigDTO, RegulatoryRegionConfigEntity.class);
        regulatoryRegionConfigEntity.setRegRegionCode(regulatoryRegionConfigDTO.getRegRegionCode());
        regulatoryRegionConfigEntity.setRegionName(regulatoryRegionConfigDTO.getRegionName());
        regulatoryRegionConfigEntity.setRegionDescription(regulatoryRegionConfigEntity.getRegionDescription());
        regulatoryRegionConfigEntity.setRegionEffectiveDate(formatter.parse(regulatoryRegionConfigDTO.getRegionEffectiveDate()));
        regulatoryRegionConfigEntity.setRegionGroupCode(regulatoryRegionConfigDTO.getRegionGroupCode());
        regulatoryRegionConfigEntity.setPurpose(regulatoryRegionConfigDTO.getPurpose());
        regulatoryRegionConfigEntity.setRegulatoryRegionMappingEntity(regulatoryRegionMappingEntities);
        return regulatoryRegionConfigEntity;
    }

    public RegulatoryRegionConfigDTO setAuditFields(MutationEntity mutationEntity,
                                                    RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) {
        regulatoryRegionConfigDTO.setAction(mutationEntity.getAction());
        regulatoryRegionConfigDTO.setAuthorized(mutationEntity.getAuthorized());
        regulatoryRegionConfigDTO.setRecordVersion(mutationEntity.getRecordVersion());
        regulatoryRegionConfigDTO.setStatus(mutationEntity.getStatus());
        regulatoryRegionConfigDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        regulatoryRegionConfigDTO.setCreatedBy(mutationEntity.getCreatedBy());
        regulatoryRegionConfigDTO.setCreationTime(mutationEntity.getCreationTime());
        regulatoryRegionConfigDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        regulatoryRegionConfigDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        regulatoryRegionConfigDTO.setTaskCode(mutationEntity.getTaskCode());
        regulatoryRegionConfigDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return regulatoryRegionConfigDTO;
    }
}

package com.idg.idgcore.coe.domain.assembler.regulatoryRegion;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionMappingEntity;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionMappingDTO;
import com.idg.idgcore.coe.exception.Error;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
public class RegulatoryRegionAssembler extends Assembler<RegulatoryRegionConfigDTO, RegulatoryRegionConfigEntity> {

    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public Class getSpecificDTOClass() {
        return RegulatoryRegionConfigDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return RegulatoryRegionConfigEntity.class;
    }

    @Override
    public RegulatoryRegionConfigDTO toDTO(RegulatoryRegionConfigEntity regulatoryRegionConfigEntity) {
        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntity = regulatoryRegionConfigEntity.getRegulatoryRegionMappingEntity();
        List<RegulatoryRegionMappingDTO> regulatoryRegionMappingDTOList=new ArrayList<>();
        regulatoryRegionMappingDTOList.addAll(regulatoryRegionMappingEntity.stream().map(entity -> {
            RegulatoryRegionMappingDTO regulatoryRegionMappingDTO=new RegulatoryRegionMappingDTO();
            regulatoryRegionMappingDTO.setDemographicMappingCode(entity.getDemographicMappingCode());
            regulatoryRegionMappingDTO.setStatus(entity.getStatus());
            regulatoryRegionMappingDTO.setAuthorized(entity.getAuthorized());
            regulatoryRegionMappingDTO.setRecordVersion(entity.getRecordVersion());
            regulatoryRegionMappingDTO.setLastConfigurationAction(entity.getLastConfigurationAction());
            return regulatoryRegionMappingDTO;
        }).collect(Collectors.toList()));

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = modelMapper.map(regulatoryRegionConfigEntity, RegulatoryRegionConfigDTO.class);
        regulatoryRegionConfigDTO.setRegulatoryRegionCode(regulatoryRegionConfigEntity.getRegRegionCode());
        regulatoryRegionConfigDTO.setRegulatoryRegionName(regulatoryRegionConfigEntity.getRegionName());
        regulatoryRegionConfigDTO.setRegulatoryRegionDescription(regulatoryRegionConfigEntity.getRegionDescription());
        regulatoryRegionConfigDTO.setRegionEffectiveDate(formatter.format(regulatoryRegionConfigEntity.getRegionEffectiveDate()));
        regulatoryRegionConfigDTO.setRegionGroupCode(regulatoryRegionConfigEntity.getRegionGroupCode());
        regulatoryRegionConfigDTO.setPurpose(regulatoryRegionConfigEntity.getPurpose());
        regulatoryRegionConfigDTO.setRegulatoryRegionMapping(regulatoryRegionMappingDTOList);
        return regulatoryRegionConfigDTO;
    }

    @Override
    public RegulatoryRegionConfigEntity toEntity(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) {

        List<RegulatoryRegionMappingDTO> regulatoryRegionMappingDTOList = regulatoryRegionConfigDTO.getRegulatoryRegionMapping();

        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntityList=new ArrayList<>();
        regulatoryRegionMappingEntityList.addAll(regulatoryRegionMappingDTOList.stream().map(dto->{
            RegulatoryRegionMappingEntity regulatoryRegionMappingEntity=new RegulatoryRegionMappingEntity();
            regulatoryRegionMappingEntity.setDemographicMappingCode(dto.getDemographicMappingCode());
            regulatoryRegionMappingEntity.setStatus(dto.getStatus());
            regulatoryRegionMappingEntity.setAuthorized(dto.getAuthorized());
            regulatoryRegionMappingEntity.setRecordVersion(dto.getRecordVersion());
            regulatoryRegionMappingEntity.setLastConfigurationAction(dto.getLastConfigurationAction());
            return regulatoryRegionMappingEntity;
        }).collect(Collectors.toList()));

        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = modelMapper.map(regulatoryRegionConfigDTO, RegulatoryRegionConfigEntity.class);
        regulatoryRegionConfigEntity.setRegRegionCode(regulatoryRegionConfigDTO.getRegulatoryRegionCode());
        regulatoryRegionConfigEntity.setRegionName(regulatoryRegionConfigDTO.getRegulatoryRegionName());
        regulatoryRegionConfigEntity.setRegionDescription(regulatoryRegionConfigEntity.getRegionDescription());
        try {
            regulatoryRegionConfigEntity.setRegionEffectiveDate(formatter.parse(regulatoryRegionConfigDTO.getRegionEffectiveDate()));
        } catch (ParseException e) {
            log.error("ParseException in parsing date [{}].", regulatoryRegionConfigDTO.getRegionEffectiveDate(), e);
            ExceptionUtil.handleException(Error.JSON_PARSING_ERROR);
        }
        regulatoryRegionConfigEntity.setRegionGroupCode(regulatoryRegionConfigDTO.getRegionGroupCode());
        regulatoryRegionConfigEntity.setPurpose(regulatoryRegionConfigDTO.getPurpose());
        regulatoryRegionConfigEntity.setRegulatoryRegionMappingEntity(regulatoryRegionMappingEntityList);
        return regulatoryRegionConfigEntity;
    }
}

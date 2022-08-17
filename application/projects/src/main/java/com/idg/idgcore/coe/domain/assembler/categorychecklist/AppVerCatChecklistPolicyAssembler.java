package com.idg.idgcore.coe.domain.assembler.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.*;

@Component
public class AppVerCatChecklistPolicyAssembler {

    private ModelMapper modelMapper = new ModelMapper();
    DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AppVerCatChecklistPolicyEntity convertDtoToEntity(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = modelMapper.map(appVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity.class);
        return appVerCatChecklistPolicyEntity;
    }

    public AppVerCatChecklistPolicyDTO convertEntityToDto(AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity){
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = modelMapper.map(appVerCatChecklistPolicyEntity, AppVerCatChecklistPolicyDTO.class);
        appVerCatChecklistPolicyDTO.setEffectiveDate(formatter.format(appVerCatChecklistPolicyEntity.getEffectiveDate()));
        return appVerCatChecklistPolicyDTO;
    }

    public AppVerCatChecklistPolicyDTO setAuditFields (MutationEntity mutationEntity, AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
        appVerCatChecklistPolicyDTO.setAction(mutationEntity.getAction());
        appVerCatChecklistPolicyDTO.setAuthorized(mutationEntity.getAuthorized());
        appVerCatChecklistPolicyDTO.setRecordVersion(mutationEntity.getRecordVersion());
        appVerCatChecklistPolicyDTO.setStatus(mutationEntity.getStatus());
        appVerCatChecklistPolicyDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        appVerCatChecklistPolicyDTO.setCreatedBy(mutationEntity.getCreatedBy());
        appVerCatChecklistPolicyDTO.setCreationTime(mutationEntity.getCreationTime());
        appVerCatChecklistPolicyDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        appVerCatChecklistPolicyDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        appVerCatChecklistPolicyDTO.setTaskCode(mutationEntity.getTaskCode());
        appVerCatChecklistPolicyDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return appVerCatChecklistPolicyDTO;
    }

}

package com.idg.idgcore.coe.domain.assembler.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AppVerCatChecklistPolicyAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AppVerCatChecklistPolicyEntity convertDtoToEntity(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = modelMapper.map(appVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity.class);
        //Check why audit values are not updating
        return appVerCatChecklistPolicyEntity;

//        List<AppVerChecklistPolicyCategoryDTO> appVerChecklistPolicyCategoryDTO = appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyCategory();
//        List<AppVerChecklistPolicyCategoryEntity> appVerChecklistPolicyCategoryEntityList = new ArrayList<>();
//        appVerChecklistPolicyCategoryEntityList.addAll(appVerChecklistPolicyCategoryDTO.stream().map(dto->{
//            AppVerChecklistPolicyCategoryEntity appVerChecklistPolicyCategoryEntity1=null;
//            appVerChecklistPolicyCategoryEntity1 = modelMapper.map(dto, AppVerChecklistPolicyCategoryEntity.class);
//            return appVerChecklistPolicyCategoryEntity1;
//        }).collect(Collectors.toList()));
//
//        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = modelMapper.map(appVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity.class);
//        appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyCategoryEntity(appVerChecklistPolicyCategoryEntityList);
//        return appVerCatChecklistPolicyEntity;
    }

    public AppVerCatChecklistPolicyDTO convertEntityToDto(AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity){
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = modelMapper.map(appVerCatChecklistPolicyEntity, AppVerCatChecklistPolicyDTO.class);
        return appVerCatChecklistPolicyDTO;
    }


//       List<AppVerChecklistPolicyCategoryDTO> appVerChecklistPolicyCategoryDTOList = new ArrayList<>();
//        List<AppVerChecklistPolicyCategoryEntity> appVerChecklistPolicyCategoryEntity = appVerCatChecklistPolicyEntity.getAppVerChecklistPolicyCategoryEntity();
//        appVerChecklistPolicyCategoryDTOList.addAll(appVerChecklistPolicyCategoryEntity.stream().map(entity->{
//            AppVerChecklistPolicyCategoryDTO appVerChecklistPolicyCategoryDTO1=null;
//            appVerChecklistPolicyCategoryDTO1 = modelMapper.map(entity, AppVerChecklistPolicyCategoryDTO.class);
//
////            AppVerChecklistPolicyCategoryDTO appVerChecklistPolicyCategoryDTO=new AppVerChecklistPolicyCategoryDTO();
////            appVerChecklistPolicyCategoryDTO.setAppVerCatId(entity.getAppVerChklstPolicyCatId());
//            return appVerChecklistPolicyCategoryDTO1;
//        }).collect(Collectors.toList()));
//
//        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = modelMapper.map(appVerCatChecklistPolicyEntity, AppVerCatChecklistPolicyDTO.class);
//        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyCategory(appVerChecklistPolicyCategoryDTOList);
//        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId(appVerCatChecklistPolicyEntity.getAppVerChecklistPolicyId());
//        appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc(appVerCatChecklistPolicyEntity.getAppVerChecklistPolicyDesc());
//        appVerCatChecklistPolicyDTO.setDomainId(appVerCatChecklistPolicyEntity.getDomainId());
//        appVerCatChecklistPolicyDTO.setDomainCategoryId(appVerCatChecklistPolicyEntity.getDomainCategoryId());
//        appVerCatChecklistPolicyDTO.setEventId(appVerCatChecklistPolicyEntity.getEventId());
////        appVerCatChecklistPolicyDTO.setEffectiveDate(appVerCatChecklistPolicyEntity.getEffectiveDate());
//        appVerCatChecklistPolicyDTO.setEntity(appVerCatChecklistPolicyEntity.getEntity());
//        appVerCatChecklistPolicyDTO.setAuthoringMode(appVerCatChecklistPolicyEntity.getAuthoringMode());
//        return appVerCatChecklistPolicyDTO;
//    }


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

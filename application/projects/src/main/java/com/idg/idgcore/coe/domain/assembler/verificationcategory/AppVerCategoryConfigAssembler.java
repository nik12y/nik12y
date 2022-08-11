package com.idg.idgcore.coe.domain.assembler.verificationcategory;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerTypeConfigEntity;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerTypeConfigDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class AppVerCategoryConfigAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AppVerCategoryConfigEntity convertDtoToEntity(AppVerCategoryConfigDTO appVerCategoryConfigDTO) {
       List<AppVerTypeConfigDTO> appVerTypeConfigDTO = appVerCategoryConfigDTO.getAppVerTypeConfig();
       List<AppVerTypeConfigEntity> appVerTypeConfigEntityList = new ArrayList<>();
        appVerTypeConfigEntityList.addAll(appVerTypeConfigDTO.stream().map(entity->{
                    AppVerTypeConfigEntity appVerTypeConfigEntity1=null;
                    appVerTypeConfigEntity1 = modelMapper.map(entity, AppVerTypeConfigEntity.class);
                    appVerTypeConfigEntity1.setIsViewToCustomer(getCharValueFromBoolean(entity.getIsViewToCustomer()));
            return appVerTypeConfigEntity1;
        }).collect(Collectors.toList()));

        AppVerCategoryConfigEntity appVerCategoryConfigEntity = modelMapper.map(appVerCategoryConfigDTO, AppVerCategoryConfigEntity.class);
        appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntityList);
        appVerCategoryConfigEntity.setIsExternal(getCharValueFromBoolean(appVerCategoryConfigDTO.getIsExternal()));
        return appVerCategoryConfigEntity;
    }

    public AppVerCategoryConfigDTO convertEntityToDto(AppVerCategoryConfigEntity appVerCategoryConfigEntity){
        List<AppVerTypeConfigDTO> appVerTypeConfigDTOList = new ArrayList<>();
        List<AppVerTypeConfigEntity> appVerTypeConfigEntity = appVerCategoryConfigEntity.getAppVerTypeConfigEntity();
        appVerTypeConfigDTOList.addAll(appVerTypeConfigEntity.stream().map(dto->{
            AppVerTypeConfigDTO appVerTypeConfigDTO=new AppVerTypeConfigDTO();
            appVerTypeConfigDTO.setAppVerificationTypeId(dto.getAppVerificationTypeId());
            appVerTypeConfigDTO.setViewToCustomer(getBooleanValueFromChar(dto.getIsViewToCustomer()));
            appVerTypeConfigDTO.setNature(dto.getNature());
            return appVerTypeConfigDTO;
        }).collect(Collectors.toList()));

        AppVerCategoryConfigDTO appVerCategoryConfigDTO = modelMapper.map(appVerCategoryConfigEntity, AppVerCategoryConfigDTO.class);
        appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTOList);
        appVerCategoryConfigDTO.setAppVerificationCategoryId(appVerCategoryConfigEntity.getAppVerificationCategoryId());
        appVerCategoryConfigDTO.setVerificationCategoryDesc(appVerCategoryConfigEntity.getVerificationCategoryDesc());
        appVerCategoryConfigDTO.setExternal(getBooleanValueFromChar(appVerCategoryConfigEntity.getIsExternal()));
        return appVerCategoryConfigDTO;
    }




    public AppVerCategoryConfigDTO setAuditFields (MutationEntity mutationEntity, AppVerCategoryConfigDTO appVerCategoryConfigDTO) {
        appVerCategoryConfigDTO.setAction(mutationEntity.getAction());
        appVerCategoryConfigDTO.setAuthorized(mutationEntity.getAuthorized());
        appVerCategoryConfigDTO.setRecordVersion(mutationEntity.getRecordVersion());
        appVerCategoryConfigDTO.setStatus(mutationEntity.getStatus());
        appVerCategoryConfigDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        appVerCategoryConfigDTO.setCreatedBy(mutationEntity.getCreatedBy());
        appVerCategoryConfigDTO.setCreationTime(mutationEntity.getCreationTime());
        appVerCategoryConfigDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        appVerCategoryConfigDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        appVerCategoryConfigDTO.setTaskCode(mutationEntity.getTaskCode());
        appVerCategoryConfigDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return appVerCategoryConfigDTO;
    }


    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }


}

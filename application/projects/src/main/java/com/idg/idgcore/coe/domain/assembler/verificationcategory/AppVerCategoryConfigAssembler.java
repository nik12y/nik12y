package com.idg.idgcore.coe.domain.assembler.verificationcategory;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerTypeConfigEntity;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerTypeConfigDTO;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AppVerCategoryConfigAssembler extends Assembler<AppVerCategoryConfigDTO, AppVerCategoryConfigEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return AppVerCategoryConfigDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return AppVerCategoryConfigEntity.class;
    }

    @Override
    public AppVerCategoryConfigEntity toEntity(AppVerCategoryConfigDTO appVerCategoryConfigDTO) {
        AppVerCategoryConfigEntity appVerCategoryConfigEntity = super.toEntity(appVerCategoryConfigDTO);

        List<AppVerTypeConfigDTO> appVerTypeConfigDTO = appVerCategoryConfigDTO.getAppVerTypeConfig();
        List<AppVerTypeConfigEntity> appVerTypeConfigEntityList = new ArrayList<>();
        appVerTypeConfigEntityList.addAll(appVerTypeConfigDTO.stream().map(entity -> {
            AppVerTypeConfigEntity appVerTypeConfigEntity1 = null;
            appVerTypeConfigEntity1 = modelMapper.map(entity, AppVerTypeConfigEntity.class);
            appVerTypeConfigEntity1.setIsViewToCustomer(getCharValueFromBoolean(entity.getIsViewToCustomer()));
            return appVerTypeConfigEntity1;
        }).toList());
        appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntityList);
        appVerCategoryConfigEntity.setIsExternal(getCharValueFromBoolean(appVerCategoryConfigDTO.getIsExternal()));
        return appVerCategoryConfigEntity;
    }

    @Override
    public AppVerCategoryConfigDTO toDTO(AppVerCategoryConfigEntity appVerCategoryConfigEntity) {
        AppVerCategoryConfigDTO appVerCategoryConfigDTO = super.toDTO(appVerCategoryConfigEntity);

        List<AppVerTypeConfigDTO> appVerTypeConfigDTOList = new ArrayList<>();
        List<AppVerTypeConfigEntity> appVerTypeConfigEntity = appVerCategoryConfigEntity.getAppVerTypeConfigEntity();
        appVerTypeConfigDTOList.addAll(appVerTypeConfigEntity.stream().map(dto ->
                modelMapper.map(dto, AppVerTypeConfigDTO.class)).toList());

        appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTOList);
        appVerCategoryConfigDTO.setExternal(getBooleanValueFromChar(appVerCategoryConfigEntity.getIsExternal()));

        return appVerCategoryConfigDTO;
    }

}

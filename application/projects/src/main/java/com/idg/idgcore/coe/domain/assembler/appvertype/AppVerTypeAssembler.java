package com.idg.idgcore.coe.domain.assembler.appvertype;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.appvertype.DocumentsEntity;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.appvertype.DocumentsDTO;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.List;

@Component
public class AppVerTypeAssembler extends Assembler<AppVerTypeDTO, AppVerTypeEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return AppVerTypeDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return AppVerTypeEntity.class;
    }

    @Override
    public AppVerTypeEntity toEntity(AppVerTypeDTO appVerTypeDTO) {
        AppVerTypeEntity appVerTypeEntity = super.toEntity(appVerTypeDTO);
        if(appVerTypeDTO.getIsDocumentRequired()) {
            List<DocumentsDTO> documentsDTOList = appVerTypeDTO.getDocuments();
            Type listType = new TypeToken<List<DocumentsEntity>>() {
            }.getType();
            List<DocumentsEntity> documentsEntityList = modelMapper.map(documentsDTOList, listType);
            appVerTypeEntity.setDocuments(documentsEntityList);
        }
        appVerTypeEntity.setIsViewableToCustomer(getCharValueFromBoolean(appVerTypeDTO.getIsViewableToCustomer()));
        appVerTypeEntity.setIsAlertToBeSentOnCompl(getCharValueFromBoolean(appVerTypeDTO.getIsAlertToBeSentOnCompl()));
        appVerTypeEntity.setIsExternal(getCharValueFromBoolean(appVerTypeDTO.getIsExternal()));
        appVerTypeEntity.setIsDocumentRequired(getCharValueFromBoolean(appVerTypeDTO.getIsDocumentRequired()));
        return appVerTypeEntity;
    }

    @Override
    public AppVerTypeDTO toDTO(AppVerTypeEntity appVerTypeEntity) {
        AppVerTypeDTO appVerTypeDTO = super.toDTO(appVerTypeEntity);
        if(appVerTypeEntity.getIsDocumentRequired() == 'Y') {
            List<DocumentsEntity> appVerTypeEntityList = appVerTypeEntity.getDocuments();
            Type listType = new TypeToken<List<DocumentsDTO>>() {
            }.getType();
            List<DocumentsDTO> documentsDTOList = modelMapper.map(appVerTypeEntityList, listType);
            appVerTypeDTO.setDocuments(documentsDTOList);
        }
        appVerTypeDTO.setViewableToCustomer(getBooleanValueFromChar(appVerTypeEntity.getIsViewableToCustomer()));
        appVerTypeDTO.setAlertToBeSentOnCompl(getBooleanValueFromChar(appVerTypeEntity.getIsAlertToBeSentOnCompl()));
        appVerTypeDTO.setExternal(getBooleanValueFromChar(appVerTypeEntity.getIsExternal()));
        appVerTypeDTO.setDocumentRequired(getBooleanValueFromChar(appVerTypeEntity.getIsDocumentRequired()));
        return appVerTypeDTO;
    }
}

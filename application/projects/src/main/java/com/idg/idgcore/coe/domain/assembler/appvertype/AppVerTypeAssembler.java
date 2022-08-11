package com.idg.idgcore.coe.domain.assembler.appvertype;

import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.appvertype.DocumentsEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.appvertype.DocumentsDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class AppVerTypeAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public AppVerTypeEntity convertDtoToEntity(AppVerTypeDTO appVerTypeDTO) {

        AppVerTypeEntity appVerTypeEntity = modelMapper.map(appVerTypeDTO, AppVerTypeEntity.class);

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

    public AppVerTypeDTO convertEntityToDto(AppVerTypeEntity appVerTypeEntity) {

        AppVerTypeDTO appVerTypeDTO = modelMapper.map(appVerTypeEntity, AppVerTypeDTO.class);

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

    public AppVerTypeDTO setAuditFields (MutationEntity mutationEntity, AppVerTypeDTO appVerTypeDTO) {
        appVerTypeDTO.setAction(mutationEntity.getAction());
        appVerTypeDTO.setAuthorized(mutationEntity.getAuthorized());
        appVerTypeDTO.setRecordVersion(mutationEntity.getRecordVersion());
        appVerTypeDTO.setStatus(mutationEntity.getStatus());
        appVerTypeDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        appVerTypeDTO.setCreatedBy(mutationEntity.getCreatedBy());
        appVerTypeDTO.setCreationTime(mutationEntity.getCreationTime());
        appVerTypeDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        appVerTypeDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        appVerTypeDTO.setTaskCode(mutationEntity.getTaskCode());
        appVerTypeDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return appVerTypeDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }
}

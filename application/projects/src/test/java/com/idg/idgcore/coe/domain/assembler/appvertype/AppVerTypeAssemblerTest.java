package com.idg.idgcore.coe.domain.assembler.appvertype;

import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.entity.appvertype.DocumentsEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.appvertype.DocumentsDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
class AppVerTypeAssemblerTest {

    @InjectMocks
    private AppVerTypeAssembler appVerTypeAssembler;

    @Test
    @DisplayName("Should convert the entity to dto when the isDocumentRequired is true")
    void convertEntityToDtoWhenIsDocumentRequiredIsTrue() {

        AppVerTypeEntity appVerTypeEntity = new AppVerTypeEntity();
        appVerTypeEntity.setVerificationTypeId("VT001");
        appVerTypeEntity.setVerificationTypeName("Identification Proof");
        appVerTypeEntity.setVerificationTypeDesc("Customer Identification");
        appVerTypeEntity.setIsViewableToCustomer('Y');
        appVerTypeEntity.setIsAlertToBeSentOnCompl('Y');
        appVerTypeEntity.setIsExternal('Y');
        appVerTypeEntity.setIsDocumentRequired('Y');

        DocumentsEntity documentsEntity = new DocumentsEntity();
        documentsEntity.setDocumentName("PAN Card");
        documentsEntity.setNature("Mandatory");
        appVerTypeEntity.setDocuments(List.of(documentsEntity));

        AppVerTypeDTO appVerTypeDTO = appVerTypeAssembler.convertEntityToDto(appVerTypeEntity);
        assertEquals(appVerTypeEntity.getVerificationTypeId(), appVerTypeDTO.getVerificationTypeId());
        assertEquals(appVerTypeEntity.getVerificationTypeName(), appVerTypeDTO.getVerificationTypeName());
        assertEquals(appVerTypeEntity.getVerificationTypeDesc(), appVerTypeDTO.getVerificationTypeDesc());
        assertEquals(appVerTypeEntity.getIsViewableToCustomer(), appVerTypeAssembler.getCharValueFromBoolean(
                        appVerTypeDTO.getIsViewableToCustomer()));
        assertEquals(appVerTypeEntity.getIsAlertToBeSentOnCompl(), appVerTypeAssembler.getCharValueFromBoolean(
                        appVerTypeDTO.getIsAlertToBeSentOnCompl()));
        assertEquals(appVerTypeEntity.getIsExternal(), appVerTypeAssembler.getCharValueFromBoolean(appVerTypeDTO.getIsExternal()));
        assertEquals(appVerTypeEntity.getIsDocumentRequired(), appVerTypeAssembler.getCharValueFromBoolean(appVerTypeDTO.getIsDocumentRequired()));

        List<DocumentsDTO> documentsDTOList = appVerTypeDTO.getDocuments();

        assertEquals(1, documentsDTOList.size());

        DocumentsDTO documentsDTO = documentsDTOList.get(0);

        assertEquals(documentsEntity.getDocumentName(), documentsDTO.getDocumentName());
        assertEquals(documentsEntity.getNature(), documentsDTO.getNature());
    }

    @Test
    @DisplayName("Should convert the entity to dto when the isDocumentRequired is false")
    void convertEntityToDtoWhenIsDocumentRequiredIsFalse() {

        AppVerTypeEntity appVerTypeEntity = new AppVerTypeEntity();

        appVerTypeEntity.setVerificationTypeId("VT001");
        appVerTypeEntity.setVerificationTypeName("Identification Proof");
        appVerTypeEntity.setVerificationTypeDesc("Customer Identification");
        appVerTypeEntity.setIsViewableToCustomer('Y');
        appVerTypeEntity.setIsAlertToBeSentOnCompl('Y');
        appVerTypeEntity.setIsExternal('Y');
        appVerTypeEntity.setIsDocumentRequired('N');

        AppVerTypeDTO appVerTypeDTO = appVerTypeAssembler.convertEntityToDto(appVerTypeEntity);

        assertEquals(appVerTypeEntity.getVerificationTypeId(), appVerTypeDTO.getVerificationTypeId());
        assertEquals(appVerTypeEntity.getVerificationTypeName(), appVerTypeDTO.getVerificationTypeName());
        assertEquals(appVerTypeEntity.getVerificationTypeDesc(), appVerTypeDTO.getVerificationTypeDesc());

        assertTrue(appVerTypeDTO.getIsViewableToCustomer());
        assertTrue(appVerTypeDTO.getIsAlertToBeSentOnCompl());
        assertTrue(appVerTypeDTO.getIsExternal());
        assertFalse(appVerTypeDTO.getIsDocumentRequired());
    }

    @Test
    @DisplayName(
            "Should set the authorized field in amlDTO when the authorized field in mutationEntity is not null")
    void setAuditFieldsWhenAuthorizedIsNotNull() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        AppVerTypeDTO appVerTypeDTO = AppVerTypeDTO.builder().build();

        AppVerTypeAssembler appVerTypeAssembler = new AppVerTypeAssembler();
        appVerTypeDTO = appVerTypeAssembler.setAuditFields(mutationEntity, appVerTypeDTO);

        assertEquals("Y", appVerTypeDTO.getAuthorized());
    }
}
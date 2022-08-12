package com.idg.idgcore.coe.domain.assembler.verificationcategory;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerTypeConfigEntity;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerTypeConfigDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class AppVerCategoryConfigAssemblerTest {

        @InjectMocks
        private AppVerCategoryConfigAssembler appVerCategoryConfigAssembler;


        @Test
        @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
        void setAuditFieldsShouldSetAuthorizedFieldInAppVerCategoryConfigDTO() {
            MutationEntity mutationEntity = new MutationEntity();
            mutationEntity.setAuthorized("Y");
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = AppVerCategoryConfigDTO.builder().build();
            appVerCategoryConfigDTO = appVerCategoryConfigAssembler.setAuditFields(mutationEntity, appVerCategoryConfigDTO);
            assertEquals("Y", appVerCategoryConfigDTO.getAuthorized());
        }

        @Test
        @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
        void convertEntityToDTO(){
            AppVerCategoryConfigEntity appVerCategoryConfigEntity = new AppVerCategoryConfigEntity();
            appVerCategoryConfigEntity.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigEntity.setVerificationCategoryDesc("Address");
            appVerCategoryConfigEntity.setIsExternal('Y');

            List<AppVerTypeConfigEntity> appVerTypeConfigEntity=new ArrayList<>();
            appVerTypeConfigEntity.add(new AppVerTypeConfigEntity(1,"VC001",'Y',"mutation"));
            appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntity);
            appVerCategoryConfigEntity.setStatus("draft");
            appVerCategoryConfigEntity.setRecordVersion(0);

            AppVerCategoryConfigDTO appVerCategoryConfigDTO=appVerCategoryConfigAssembler.convertEntityToDto(appVerCategoryConfigEntity);
        }
    @Test
    @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
    void convertDtoToEntity(){
        AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
        appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
        appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
        appVerCategoryConfigDTO.setExternal(true);

        List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
        appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
        appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);
        AppVerCategoryConfigEntity appVerCategoryConfigEntity=appVerCategoryConfigAssembler.convertDtoToEntity(appVerCategoryConfigDTO);
    }
}
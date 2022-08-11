package com.idg.idgcore.coe.domain.service.verificationcategory;

import com.idg.idgcore.coe.domain.assembler.verificationcategory.AppVerCategoryConfigAssembler;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerTypeConfigEntity;
import com.idg.idgcore.coe.domain.repository.verificationcategory.IAppVerCategoryConfigRepository;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerTypeConfigDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

    @ExtendWith(MockitoExtension.class)
    class AppVerCategoryDomainServiceTest {

        @Mock
        private IAppVerCategoryConfigRepository appVerCategoryConfigRepository;

        @InjectMocks
        private AppVerCategoryDomainService appVerCategoryDomainService;

        private AppVerCategoryConfigEntity appVerCategoryConfigEntity;
        private AppVerCategoryConfigDTO appVerCategoryConfigDTO;
        private AppVerCategoryConfigAssembler appVerCategoryConfigAssembler;


        @BeforeEach
        void setUp() {
            appVerCategoryConfigDTO=appVerCategoryConfigDTO();
            appVerCategoryConfigEntity=getappVerCategoryConfigEntity();
        }

        @Test
        @DisplayName("Junit test for AppVerCategoryConfigs method ")
        void getAppVerCategoryConfigsReturnStatesList() {
            given(appVerCategoryConfigRepository.findAll()).willReturn(List.of(appVerCategoryConfigEntity));
            List<AppVerCategoryConfigEntity> appVerCategoryConfigEntityList = appVerCategoryDomainService.getAppVerCategoryConfigs();
            assertThat(appVerCategoryConfigEntityList).isNotNull();
            assertThat(appVerCategoryConfigEntityList.size()).isEqualTo(1);
        }


        @Test
        @DisplayName("JUnit test for AppVerCategoryConfigs method for negative scenario")
        void getAppVerCategoryConfigsEmptyStateEntityList()
        {
            given(appVerCategoryConfigRepository.findAll()).willReturn(Collections.emptyList());
            List<AppVerCategoryConfigEntity> appVerCategoryConfigEntityList = appVerCategoryDomainService.getAppVerCategoryConfigs();
            assertThat(appVerCategoryConfigEntityList).isEmpty();
            assertThat(appVerCategoryConfigEntityList.size()).isEqualTo(0);

        }


        @Test
        @DisplayName("JUnit test for getAppVerCategoryConfigById method")
        void getgetAppVerCategoryConfigByIdReturnStateEntityObject() {
            given(appVerCategoryConfigRepository.findByAppVerificationCategoryId("VC0001")).willReturn(appVerCategoryConfigEntity);
            AppVerCategoryConfigEntity apVerCategoryConfigEntity1 =appVerCategoryDomainService.getAppVerCategoryConfigByID(appVerCategoryConfigEntity.getAppVerificationCategoryId());
            assertThat(apVerCategoryConfigEntity1).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getAppVerCategoryConfigById catch block method")
        void getgetAppVerCategoryConfigByIdReturnCatchBolock() {
            AppVerCategoryConfigEntity appVerCategoryConfigEntity1=null;

            assertThrows(Exception.class,()-> {
                AppVerCategoryConfigEntity appVerCategoryConfigEntity2 = appVerCategoryDomainService.getAppVerCategoryConfigByID(appVerCategoryConfigEntity1.getAppVerificationCategoryId());
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode try block method")
        void getConfigurationByCodeTryBlock() {
            given(appVerCategoryConfigRepository.findByAppVerificationCategoryId("VC0001")).willReturn(appVerCategoryConfigEntity);
            AppVerCategoryConfigEntity AppVerCategoryByCode = appVerCategoryDomainService.getConfigurationByCode(appVerCategoryConfigDTO);
            assertThat(AppVerCategoryByCode).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getConfigurationByCodeCatchBlock() {
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = null;
            assertThrows(BusinessException.class,()-> {
                AppVerCategoryConfigEntity appVerCategoryByCode = appVerCategoryDomainService.getConfigurationByCode(appVerCategoryConfigDTO);
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getSaveCodeCatchBlock() {
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = null;
            assertThrows(Exception.class,()-> {
                appVerCategoryDomainService.save(appVerCategoryConfigDTO);
            });
        }


        private AppVerCategoryConfigEntity getappVerCategoryConfigEntity()
        {
            AppVerCategoryConfigEntity appVerCategoryConfigEntity = new AppVerCategoryConfigEntity();
            appVerCategoryConfigEntity.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigEntity.setVerificationCategoryDesc("Address");
            appVerCategoryConfigEntity.setIsExternal('Y');

            List<AppVerTypeConfigEntity> appVerTypeConfigEntity=new ArrayList<>();
            appVerTypeConfigEntity.add(new AppVerTypeConfigEntity(1,"VC001",'Y',"mutation"));
            appVerCategoryConfigEntity.setAppVerTypeConfigEntity(appVerTypeConfigEntity);
            appVerCategoryConfigEntity.setStatus("draft");
            appVerCategoryConfigEntity.setRecordVersion(0);
            return appVerCategoryConfigEntity;
        }

        private AppVerCategoryConfigDTO appVerCategoryConfigDTO()
        {
            AppVerCategoryConfigDTO appVerCategoryConfigDTO = new AppVerCategoryConfigDTO();
            appVerCategoryConfigDTO.setAppVerificationCategoryId("VC0001");
            appVerCategoryConfigDTO.setVerificationCategoryDesc("Address");
            appVerCategoryConfigDTO.setExternal(true);
            List<AppVerTypeConfigDTO> appVerTypeConfigDTO=new ArrayList<>();
            appVerTypeConfigDTO.add(new AppVerTypeConfigDTO("VC001",true,"mutation"));
            appVerCategoryConfigDTO.setAppVerTypeConfig(appVerTypeConfigDTO);
            appVerCategoryConfigDTO.setStatus("DELETED");
            appVerCategoryConfigDTO.setRecordVersion(1);
            return appVerCategoryConfigDTO;
        }

    }

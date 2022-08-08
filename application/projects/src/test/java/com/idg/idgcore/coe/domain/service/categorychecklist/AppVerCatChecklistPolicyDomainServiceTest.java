package com.idg.idgcore.coe.domain.service.categorychecklist;

import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.repository.categorychecklist.IAppVerCatChecklistPolicyRepository;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class AppVerCatChecklistPolicyDomainServiceTest {

        @Mock
        private IAppVerCatChecklistPolicyRepository appVerCatChecklistPolicyRepository;

        @InjectMocks
        private AppVerCatChecklistPolicyDomainService appVerCatChecklistPolicyDomainService;

        private AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity;
        private AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO;
        private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;


        @BeforeEach
        void setUp() {
            appVerCatChecklistPolicyDTO=getAppVerCatChecklistPolicyDTO();
            appVerCatChecklistPolicyEntity=getAppVerCatChecklistPolicyEntity();
        }

        @Test
        @DisplayName("Junit test for AppVerCategoryConfigs method ")
        void getAppVerCategoryConfigsReturnStatesList() {
            given(appVerCatChecklistPolicyRepository.findAll()).willReturn(List.of(appVerCatChecklistPolicyEntity));
            List<AppVerCatChecklistPolicyEntity> appVerCatChecklistPolicyEntityList = appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicys();
            assertThat(appVerCatChecklistPolicyEntityList).isNotNull();
            assertThat(appVerCatChecklistPolicyEntityList.size()).isEqualTo(1);
        }


        @Test
        @DisplayName("JUnit test for AppVerCategoryConfigs method for negative scenario")
        void getAppVerCategoryConfigsEmptyStateEntityList()
        {
            given(appVerCatChecklistPolicyRepository.findAll()).willReturn(Collections.emptyList());
            List<AppVerCatChecklistPolicyEntity> appVerCatChecklistPolicyEntityList = appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicys();
            assertThat(appVerCatChecklistPolicyEntityList).isEmpty();
            assertThat(appVerCatChecklistPolicyEntityList.size()).isEqualTo(0);

        }


        @Test
        @DisplayName("JUnit test for getAppVerCategoryConfigById method")
        void getgetAppVerCategoryConfigByIdReturnStateEntityObject() {
            given(appVerCatChecklistPolicyRepository.findByAppVerChecklistPolicyId("VCK0001")).willReturn(appVerCatChecklistPolicyEntity);
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity1 =appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(appVerCatChecklistPolicyEntity.getAppVerChecklistPolicyId());
            assertThat(appVerCatChecklistPolicyEntity1).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getAppVerCategoryConfigById catch block method")
        void getgetAppVerCategoryConfigByIdReturnCatchBolock() {
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity1=null;

            assertThrows(Exception.class,()-> {
                AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity2 = appVerCatChecklistPolicyDomainService.getAppVerChecklistPolicyById(appVerCatChecklistPolicyEntity1.getAppVerChecklistPolicyId());
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode try block method")
        void getConfigurationByCodeTryBlock() {
            given(appVerCatChecklistPolicyRepository.findByAppVerChecklistPolicyId("VCK0001")).willReturn(appVerCatChecklistPolicyEntity);
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntitybycode = appVerCatChecklistPolicyDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTO);
            assertThat(appVerCatChecklistPolicyEntitybycode).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getConfigurationByCodeCatchBlock() {
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = null;
            assertThrows(BusinessException.class,()-> {
                AppVerCatChecklistPolicyEntity appVerCatchecklistPolicyByCode = appVerCatChecklistPolicyDomainService.getConfigurationByCode(appVerCatChecklistPolicyDTO);
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getSaveCodeCatchBlock() {
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO1 = null;
            assertThrows(Exception.class,()-> {
                appVerCatChecklistPolicyDomainService.save(appVerCatChecklistPolicyDTO1);
            });
        }


        private AppVerCatChecklistPolicyEntity getAppVerCatChecklistPolicyEntity()
        {
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = new AppVerCatChecklistPolicyEntity();
            appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyId("VCK0001");
            appVerCatChecklistPolicyEntity.setAppVerChecklistPolicyDesc("Address");
            appVerCatChecklistPolicyEntity.setDomainId("DM0001");
            appVerCatChecklistPolicyEntity.setDomainCategoryId("DC0001");
//        appVerCatChecklistPolicyEntity.setEffectiveDate(2022-07-20);
            appVerCatChecklistPolicyEntity.setEventId("EV0001");
            appVerCatChecklistPolicyEntity.setEntity("Customer");
            appVerCatChecklistPolicyEntity.setRuleId("RL0001");
            appVerCatChecklistPolicyEntity.setStatus("draft");
            appVerCatChecklistPolicyEntity.setRecordVersion(0);
            return appVerCatChecklistPolicyEntity;
        }

        private AppVerCatChecklistPolicyDTO getAppVerCatChecklistPolicyDTO()
        {
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
            appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
            appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
            appVerCatChecklistPolicyDTO.setDomainId("DM0001");
            appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
            appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
            appVerCatChecklistPolicyDTO.setEventId("EV0001");
            appVerCatChecklistPolicyDTO.setEntity("Customer");
            appVerCatChecklistPolicyDTO.setRuleId("RL0001");
            appVerCatChecklistPolicyDTO.setStatus("DELETED");
            appVerCatChecklistPolicyDTO.setRecordVersion(1);
            return appVerCatChecklistPolicyDTO;
        }

    }


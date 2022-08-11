package com.idg.idgcore.coe.domain.assembler.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(MockitoExtension.class)
class AppVerCatChecklistPolicyAssemblerTest {

        @InjectMocks
        private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;


        @Test
        @DisplayName("Should set the authorized field in appVerCatChecklistPolicyDTO")
        void setAuditFieldsShouldSetAuthorizedFieldInAppVerCatChecklistPolicyDTO() {
            MutationEntity mutationEntity = new MutationEntity();
            mutationEntity.setAuthorized("Y");
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = AppVerCatChecklistPolicyDTO.builder().build();
            appVerCatChecklistPolicyDTO = appVerCatChecklistPolicyAssembler.setAuditFields(mutationEntity, appVerCatChecklistPolicyDTO);
            assertEquals("Y", appVerCatChecklistPolicyDTO.getAuthorized());
        }

        @Test
        @DisplayName("Should set the authorized field in appVerCatChecklistPolicyDTO")
        void convertEntityToDTO(){
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
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO=appVerCatChecklistPolicyAssembler.convertEntityToDto(appVerCatChecklistPolicyEntity);
            assertThat(appVerCatChecklistPolicyDTO).descriptionText();
        }
        @Test
        @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
        void convertDtoToEntity(){
            AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO = new AppVerCatChecklistPolicyDTO();
            appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyId("VCK0001");
            appVerCatChecklistPolicyDTO.setAppVerChecklistPolicyDesc("Address");
            appVerCatChecklistPolicyDTO.setDomainId("DM0001");
            appVerCatChecklistPolicyDTO.setDomainCategoryId("DC0001");
//        appVerCatChecklistPolicyEntity.setEffectiveDate(2022-07-20);
            appVerCatChecklistPolicyDTO.setEventId("EV0001");
            appVerCatChecklistPolicyDTO.setEntity("Customer");
            appVerCatChecklistPolicyDTO.setRuleId("RL0001");
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity=appVerCatChecklistPolicyAssembler.convertDtoToEntity(appVerCatChecklistPolicyDTO);
            assertThat(appVerCatChecklistPolicyEntity).descriptionText();
        }
    }

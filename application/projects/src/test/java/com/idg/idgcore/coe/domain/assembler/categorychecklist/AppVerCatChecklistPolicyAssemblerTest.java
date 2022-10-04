package com.idg.idgcore.coe.domain.assembler.categorychecklist;

import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;


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
        appVerCatChecklistPolicyAssembler.setAuditFields(mutationEntity, appVerCatChecklistPolicyDTO);
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
        appVerCatChecklistPolicyEntity.setEffectiveDate(getDate("2022-07-20"));
        appVerCatChecklistPolicyEntity.setEventId("EV0001");
        appVerCatChecklistPolicyEntity.setEntity("Customer");
        appVerCatChecklistPolicyEntity.setRuleId("RL0001");
        appVerCatChecklistPolicyEntity.setStatus("draft");
        appVerCatChecklistPolicyEntity.setRecordVersion(0);
        AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO=appVerCatChecklistPolicyAssembler.toDTO(appVerCatChecklistPolicyEntity);
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
        appVerCatChecklistPolicyDTO.setEffectiveDate("2022-07-20");
        appVerCatChecklistPolicyDTO.setEventId("EV0001");
        appVerCatChecklistPolicyDTO.setEntity("Customer");
        appVerCatChecklistPolicyDTO.setRuleId("RL0001");
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity=appVerCatChecklistPolicyAssembler.toEntity(appVerCatChecklistPolicyDTO);
        assertThat(appVerCatChecklistPolicyEntity).descriptionText();
    }

    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

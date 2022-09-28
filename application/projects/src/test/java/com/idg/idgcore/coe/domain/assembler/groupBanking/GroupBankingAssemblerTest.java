package com.idg.idgcore.coe.domain.assembler.groupBanking;

import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.idg.idgcore.coe.common.Constants.GROUP_BANKING;
import static org.junit.jupiter.api.Assertions.assertEquals;
class GroupBankingAssemblerTest {

    @Mock
    private GroupBankingAssembler groupBankingAssembler=new GroupBankingAssembler();


    @Test
    void setAuditFields() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Crime Bank Of India");
        groupBankingDTO.setAuthorized("Y");
        groupBankingAssembler.setAuditFields(mutationEntity, groupBankingDTO);
        assertEquals("Y", groupBankingDTO.getAuthorized());
    }

    @Test
    @DisplayName("Junit test for convertDTO to Entity")
    void convertDtoToEntity() {
        GroupBankingEntity groupBankingEntity = new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");
        groupBankingEntity.setLifeCycleId(null);
        groupBankingEntity.setReferenceNo(null);
        groupBankingEntity.setStatus("new");
        groupBankingEntity.setAuthorized("N");
        groupBankingEntity.setRecordVersion(0);
        groupBankingEntity.setLastConfigurationAction("unauthorized");

        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");

        groupBankingDTO.setStatus("new");
        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setRecordVersion(0);
        groupBankingDTO.setTaskCode(GROUP_BANKING);
        groupBankingDTO.setLastConfigurationAction("unauthorized");

        assertEquals(groupBankingEntity,groupBankingAssembler.toEntity(groupBankingDTO));
    }
}
package com.idg.idgcore.coe.domain.assembler.groupBanking;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import org.springframework.stereotype.Component;

@Component
public class GroupBankingAssembler extends Assembler<GroupBankingDTO, GroupBankingEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return GroupBankingDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return GroupBankingEntity.class;
    }

    @Override
    public GroupBankingEntity toEntity(GroupBankingDTO groupBankingDTO) {
        GroupBankingEntity groupBankingEntity = super.toEntity(groupBankingDTO);
        groupBankingEntity.setBankGroupName(groupBankingDTO.getGroupBankingName());
        groupBankingEntity.setBankGroupCode(groupBankingDTO.getGroupBankingCode());
        return groupBankingEntity;
    }

    @Override
    public GroupBankingDTO toDTO(GroupBankingEntity groupBankingEntity) {
        GroupBankingDTO groupBankingDTO = super.toDTO(groupBankingEntity);
        groupBankingDTO.setGroupBankingCode(groupBankingEntity.getBankGroupCode());
        groupBankingDTO.setGroupBankingName(groupBankingEntity.getBankGroupName());
        return groupBankingDTO;
    }
}


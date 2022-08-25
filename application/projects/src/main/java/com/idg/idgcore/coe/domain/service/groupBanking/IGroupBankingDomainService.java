package com.idg.idgcore.coe.domain.service.groupBanking;

import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;

import java.util.List;

public interface IGroupBankingDomainService {

    GroupBankingEntity getConfigurationByCode (GroupBankingDTO groupBankingDTO);
    List<GroupBankingEntity> getGroupBanks ();
    GroupBankingEntity getGroupBankByCode (String bankGroupCode);
    void save (GroupBankingDTO groupBankingDTO);
}




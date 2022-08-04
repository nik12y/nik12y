package com.idg.idgcore.coe.domain.service.bankgroup;

import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntity;
import com.idg.idgcore.coe.dto.bankgroup.GroupBankingDTO;

import java.util.List;

public interface IGroupBankingDomainService {

    GroupBankingEntity getConfigurationByCode (GroupBankingDTO groupBankingDTO);
    List<GroupBankingEntity> getGroupBanks ();
    GroupBankingEntity getGroupBankByCode (String bankGroupCode);
    void save (GroupBankingDTO groupBankingDTO);
}




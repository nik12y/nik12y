package com.idg.idgcore.coe.domain.repository.groupBanking;

import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupBankingRepository extends JpaRepository<GroupBankingEntity, GroupBankingEntityKey> {

    GroupBankingEntity findByBankGroupCode(String bankGroupCode);
}

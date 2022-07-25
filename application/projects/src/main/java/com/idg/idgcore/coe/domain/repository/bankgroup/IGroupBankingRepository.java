package com.idg.idgcore.coe.domain.repository.bankgroup;

import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IGroupBankingRepository extends JpaRepository<GroupBankingEntity, GroupBankingEntityKey> {

    GroupBankingEntity findByBankGroupCode(String bankGroupCode);
}

package com.idg.idgcore.coe.domain.repository.bankgroup;

import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntity;
import com.idg.idgcore.coe.domain.repository.bank.IBankRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class IGroupBankingRepositoryTest {


    @Autowired
    IGroupBankingRepository iGroupBankingRepository;

    @DisplayName("jUnit test to get all bankGroups ")
    @Test
    void findAllBanks() {
        GroupBankingEntity groupBankingEntity = new GroupBankingEntity("CBI", "Crime Bank of India",null,null
                , "draft", 0, "N", "draft");
        iGroupBankingRepository.save(groupBankingEntity);

        GroupBankingEntity groupBanking1 = new GroupBankingEntity("SBI",
                "State Bank of India",null,null, "draft", 0, "N", "draft");
        iGroupBankingRepository.save(groupBanking1);

        //when - condition
        List<GroupBankingEntity> groupBankingEntities = iGroupBankingRepository.findAll();

        //then - output
        assertThat(groupBankingEntities).isNotNull();
   assertThat(groupBankingEntities.size()).isEqualTo(2);

    }

    @DisplayName("jUnit test to get bank by code ")
    @Test
    void findByBankCode() {
        GroupBankingEntity groupBankingEntity = new GroupBankingEntity("PNB",
                "Punjab national bank",null,null, "draft", 0, "Y", "draft");
        iGroupBankingRepository.save(groupBankingEntity);

        GroupBankingEntity groupBankingEntity1 = iGroupBankingRepository.findByBankGroupCode("PNB");

        assertThat(groupBankingEntity1.getBankGroupName().equals("Punjab national bank"));

    }

    @Test
    void saveBankTest() {
        GroupBankingEntity groupBanking = new GroupBankingEntity("PNB",
                "Punjab national bank",null,null, "draft", 0, "Y", "draft");

        GroupBankingEntity groupBankingEntity = iGroupBankingRepository.save(groupBanking);

        assertThat(groupBankingEntity).isNotNull();
        assertThat(groupBankingEntity.getBankGroupCode()).isEqualTo("PNB");


    }

}
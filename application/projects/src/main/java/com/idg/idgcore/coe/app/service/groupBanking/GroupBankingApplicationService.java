package com.idg.idgcore.coe.app.service.groupBanking;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.service.groupBanking.GroupBankingDomainService;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.GROUP_BANKING;

@Slf4j
@Service("groupBankingApplicationService")
public class GroupBankingApplicationService extends GenericApplicationService<GroupBankingDTO,
        GroupBankingEntity,
        GroupBankingDomainService,
        GroupBankingAssembler> {

    protected GroupBankingApplicationService() {
        super(GROUP_BANKING);
    }

    public String getTaskCode() {
        return GroupBankingDTO.builder().build().getTaskCode();
    }
}




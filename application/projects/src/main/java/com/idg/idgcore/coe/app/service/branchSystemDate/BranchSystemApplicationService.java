package com.idg.idgcore.coe.app.service.branchSystemDate;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.branchSystemDate.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.service.branchSystemDate.BranchSystemDateDomainService;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.BRANCH_SYSTEM_DATE;

@Slf4j
@Service("branchSystemApplicationService")
public class BranchSystemApplicationService extends GenericApplicationService<
        BranchSystemDateDTO, BranchSystemDateEntity, BranchSystemDateDomainService, BranchSystemDateAssembler> {

    protected BranchSystemApplicationService() {
        super(BRANCH_SYSTEM_DATE);
    }

    public String getTaskCode () {
        return BranchSystemDateDTO.builder().build().getTaskCode();
    }
}

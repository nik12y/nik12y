package com.idg.idgcore.coe.app.service.branchtype;


import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.service.branchtype.BranchTypeDomainService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.BRANCHTYPE;

@Slf4j
@Service("branchTypeApplicationService")
public class BranchTypeApplicationService extends GenericApplicationService<BranchTypeDTO,
        BranchTypeEntity, BranchTypeDomainService, BranchTypeAssembler> {

    protected BranchTypeApplicationService() {
        super(BRANCHTYPE);
    }

    public String getTaskCode () {
        return BranchTypeDTO.builder().build().getTaskCode();
    }
}





package com.idg.idgcore.coe.app.service.mulbranchparameter;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.common.Constants;
import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.service.mulbranchparameter.MulBranchParameterDomainService;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service("mulBranchparamApplicationService")
public class MulBranchParameterApplicationService extends
        GenericApplicationService<MulBranchParameterDTO, MulBranchParameterEntity, MulBranchParameterDomainService, MulBranchParameterAssembler>
  {
    protected  MulBranchParameterApplicationService() {
        super(Constants.CURR_BRANCH_PARAM);
    }

    public String getTaskCode () {
        return MulBranchParameterDTO.builder().build().getTaskCode();
    }
}
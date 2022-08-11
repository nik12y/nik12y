package com.idg.idgcore.coe.domain.service.branchparameter;

import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.*;

import java.util.*;

public interface IBranchParameterDomainService {
    BranchParameterEntity getConfigurationByCode (BranchParameterDTO branchParameterDTO);
    List<BranchParameterEntity> getBranchParameters ();
    BranchParameterEntity getBranchParameterByBranchCode (String bankCode);
    void save (BranchParameterDTO branchParameterDTO);

}

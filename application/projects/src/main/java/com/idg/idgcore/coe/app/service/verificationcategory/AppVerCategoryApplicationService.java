package com.idg.idgcore.coe.app.service.verificationcategory;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.verificationcategory.AppVerCategoryConfigAssembler;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.service.verificationcategory.AppVerCategoryDomainService;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.CATEGORY;


@Slf4j
@Service("verificationCategoryApplicationService")
public class AppVerCategoryApplicationService extends GenericApplicationService<AppVerCategoryConfigDTO,
        AppVerCategoryConfigEntity, AppVerCategoryDomainService, AppVerCategoryConfigAssembler> {


    protected AppVerCategoryApplicationService() {
        super(CATEGORY);
    }

    public String getTaskCode () {
        return AppVerCategoryConfigDTO.builder().build().getTaskCode();
    }
}

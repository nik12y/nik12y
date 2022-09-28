package com.idg.idgcore.coe.app.service.categorychecklist;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.service.categorychecklist.AppVerCatChecklistPolicyDomainService;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.CHECKLIST;


@Slf4j
@Service("checklistApplicationService")
public class AppVerCatChecklistPolicyApplicationService extends GenericApplicationService<
        AppVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity, AppVerCatChecklistPolicyDomainService,
        AppVerCatChecklistPolicyAssembler> {

    protected AppVerCatChecklistPolicyApplicationService() {
        super(CHECKLIST);
    }

    public String getTaskCode () {
        return AppVerCatChecklistPolicyDTO.builder().build().getTaskCode();
    }

}



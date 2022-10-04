package com.idg.idgcore.coe.app.service.appvertype;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.appvertype.AppVerTypeAssembler;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.service.appvertype.AppVerTypeDomainService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.APP_VERIFICATION_TYPE;

@Slf4j
@Service("appVerTypeApplicationService")
public class AppVerTypeApplicationService extends GenericApplicationService<AppVerTypeDTO,
        AppVerTypeEntity, AppVerTypeDomainService, AppVerTypeAssembler> {

    protected AppVerTypeApplicationService() {
        super(APP_VERIFICATION_TYPE);
    }

    public String getTaskCode() {
        return AppVerTypeDTO.builder().build().getTaskCode();
    }
}

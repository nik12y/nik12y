package com.idg.idgcore.coe.app.service.errorOverride;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.errorOverride.ErrorOverrideAssembler;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.service.errorOverride.ErrorOverrideDomainService;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.ERROR_OVERRIDE;

@Slf4j
@Service ("errorOverrideService")
public class ErrorOverrideApplicationService extends GenericApplicationService<ErrorOverrideDTO,
        ErrorOverrideEntity, ErrorOverrideDomainService, ErrorOverrideAssembler> {

    protected ErrorOverrideApplicationService() {
        super(ERROR_OVERRIDE);
    }

    public String getTaskCode () {
        return ErrorOverrideDTO.builder().build().getTaskCode();
    }

}
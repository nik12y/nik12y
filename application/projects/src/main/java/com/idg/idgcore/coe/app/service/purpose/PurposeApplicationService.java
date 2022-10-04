package com.idg.idgcore.coe.app.service.purpose;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.service.purpose.PurposeDomainService;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.PURPOSE;

@Slf4j
@Service("purposeApplicationService")
public class PurposeApplicationService extends GenericApplicationService<PurposeDTO, PurposeEntity,
        PurposeDomainService, PurposeAssembler> {

    protected PurposeApplicationService() {
        super(PURPOSE);
    }

    public String getTaskCode() {
        return PurposeDTO.builder().build().getTaskCode();
    }
}




package com.idg.idgcore.coe.app.service.aml;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.aml.AmlAssembler;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.service.aml.AmlDomainService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.AML;

@Slf4j
@Service("amlApplicationService")
public class AmlApplicationService extends GenericApplicationService<AmlDTO, AmlEntity, AmlDomainService, AmlAssembler> {

    protected AmlApplicationService() {
        super(AML);
    }

    public String getTaskCode () {
        return AmlDTO.builder().build().getTaskCode();
    }

}

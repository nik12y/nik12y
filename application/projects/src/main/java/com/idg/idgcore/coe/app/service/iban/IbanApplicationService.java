package com.idg.idgcore.coe.app.service.iban;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.iban.IbanAssembler;
import com.idg.idgcore.coe.domain.entity.iban.IbanEntity;
import com.idg.idgcore.coe.domain.service.iban.IbanDomainService;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.IBAN;

@Slf4j
@Service ("ibanApplicationService")
public class IbanApplicationService extends GenericApplicationService<IbanDTO, IbanEntity, IbanDomainService,
        IbanAssembler> {

    protected IbanApplicationService() {
        super(IBAN);
    }

    public String getTaskCode () {
        return IbanDTO.builder().build().getTaskCode();
    }

}
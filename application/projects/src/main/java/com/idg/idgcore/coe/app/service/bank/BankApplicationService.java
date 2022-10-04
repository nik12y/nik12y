package com.idg.idgcore.coe.app.service.bank;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.bank.BankAssembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.service.bank.BankDomainService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.BANK;

@Slf4j
@Service("bankApplicationService")
public class BankApplicationService extends GenericApplicationService<BankDTO, BankEntity,
        BankDomainService, BankAssembler> {

    protected BankApplicationService() {
        super(BANK);
    }

    public String getTaskCode() {
        return BankDTO.builder().build().getTaskCode();
    }
}

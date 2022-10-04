package com.idg.idgcore.coe.app.service.bankidentifier;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.service.bankidentifier.BankIdentifierDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.BANK_IDENTIFIER;

@Slf4j
@Service("bankIdentifierApplicationService")
public class BankIdentifierApplicationService  extends GenericApplicationService<BankIdentifierDTO, BankIdentifierEntity,
        BankIdentifierDomainService, BankIdentifierAssembler> {

    protected BankIdentifierApplicationService() {
        super(BANK_IDENTIFIER);
    }

    public String getTaskCode () {
        return BankIdentifierDTO.builder().build().getTaskCode();
    }
}

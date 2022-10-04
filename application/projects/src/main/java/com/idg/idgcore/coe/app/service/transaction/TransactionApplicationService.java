package com.idg.idgcore.coe.app.service.transaction;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.transaction.TransactionAssembler;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.service.transaction.TransactionDomainService;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.TRANSACTION;

@Slf4j
@Service ("transactionApplicationService")
public class TransactionApplicationService extends GenericApplicationService<TransactionDTO,
        TransactionEntity,
        TransactionDomainService,
        TransactionAssembler> {

    protected TransactionApplicationService() {
        super(TRANSACTION);
    }

    public String getTaskCode () {
        return TransactionDTO.builder().build().getTaskCode();
    }

}
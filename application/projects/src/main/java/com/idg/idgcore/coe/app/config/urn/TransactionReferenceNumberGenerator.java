package com.idg.idgcore.coe.app.config.urn;

import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.app.service.urn.*;
import com.idg.idgcore.dto.context.*;

public class TransactionReferenceNumberGenerator implements ITransactionNumberGenerator {

    public String generateTransactionReferenceId (SessionContext sessionContext) {
        UrnGeneratorService urnGeneratorService = new UrnGeneratorService();
        return urnGeneratorService.generateTransactionUrn(sessionContext);
    }


}

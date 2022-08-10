package com.idg.idgcore.coe.app.service.urn;

import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.app.service.urn.service.*;
import com.idg.idgcore.coe.common.*;


public class TransactionReferenceNumberGenerator implements ITransactionNumberGenerator {

    public String generateTransactionReferenceId (SessionContext sessionContext) {
        UrnGeneratorService urnGeneratorService = new UrnGeneratorService();
        return urnGeneratorService.generateTransactionUrn(sessionContext);
    }

	@Override
	public String generateTransactionReferenceId(com.idg.idgcore.dto.context.SessionContext arg0) {

		return null;
	}


}

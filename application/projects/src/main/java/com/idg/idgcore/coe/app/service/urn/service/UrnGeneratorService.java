package com.idg.idgcore.coe.app.service.urn.service;

import com.idg.idgcore.coe.common.*;
import com.idg.idgcore.coe.exception.*;


public interface UrnGeneratorService {

	public String generateTransactionUrn(SessionContext sessionContext) throws
			UrnGeneratorException;

}

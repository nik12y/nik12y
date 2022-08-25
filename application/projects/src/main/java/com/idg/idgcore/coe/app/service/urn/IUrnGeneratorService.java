package com.idg.idgcore.coe.app.service.urn;

import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.dto.context.*;

public interface IUrnGeneratorService {

	public String generateTransactionUrn(SessionContext sessionContext) throws
			UrnGeneratorException;

}

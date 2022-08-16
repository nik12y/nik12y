package com.idg.idgcore.coe.app.service.errorOverride;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.coe.dto.errorOverride.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;

import java.util.*;

public interface IErrorOverrideApplicationService extends IBaseApplicationService {
    TransactionStatus processErrorOverride (SessionContext sessionContext, ErrorOverrideDTO dto)
            throws FatalException, JsonProcessingException;
    void save (ErrorOverrideDTO errorOverrideDTO);
    ErrorOverrideDTO getErrorOverrideByCode (SessionContext sessionContext, ErrorOverrideDTO errorOverrideDTO)
            throws FatalException, JsonProcessingException;
    List<ErrorOverrideDTO> getErrorCodes (SessionContext sessionContext) throws FatalException;

}

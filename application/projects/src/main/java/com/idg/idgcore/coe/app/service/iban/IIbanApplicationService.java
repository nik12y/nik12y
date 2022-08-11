package com.idg.idgcore.coe.app.service.iban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.coe.dto.iban.IbanDTO;

import java.util.*;

public interface IIbanApplicationService extends IBaseApplicationService {
    TransactionStatus processIban(SessionContext sessionContext, IbanDTO dto) throws FatalException,
            JsonProcessingException;
    void save (IbanDTO ibanDTO);
    IbanDTO getIbanByIbanCountryCode (SessionContext sessionContext, IbanDTO ibanDTO)
            throws FatalException, JsonProcessingException;
    List<IbanDTO> getIbans(SessionContext sessionContext)
            throws FatalException;

}

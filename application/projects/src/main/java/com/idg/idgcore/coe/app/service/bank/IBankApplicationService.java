package com.idg.idgcore.coe.app.service.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IBankApplicationService extends IBaseApplicationService {
    TransactionStatus processBank (SessionContext sessionContext, BankDTO dto)
            throws FatalException, JsonProcessingException;
    void save (BankDTO bankDTO);
    BankDTO getBankByCode (SessionContext sessionContext, BankDTO bankDTO)
            throws FatalException, JsonProcessingException;
    List<BankDTO> getBanks(SessionContext sessionContext)
        throws FatalException;

}

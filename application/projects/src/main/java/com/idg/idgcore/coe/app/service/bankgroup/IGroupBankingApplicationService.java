package com.idg.idgcore.coe.app.service.bankgroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.bankgroup.GroupBankingDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;


public interface IGroupBankingApplicationService  extends IBaseApplicationService {

    TransactionStatus processGroupBanking (SessionContext sessionContext, GroupBankingDTO groupBankingDTO) throws FatalException, JsonProcessingException;

    void save (GroupBankingDTO groupBankingDTO);

    GroupBankingDTO getGroupBankByCode (SessionContext sessionContext, GroupBankingDTO groupBankingDTO) throws FatalException, JsonProcessingException;

    List<GroupBankingDTO> getGroupBanks (SessionContext sessionContext) throws FatalException;

}

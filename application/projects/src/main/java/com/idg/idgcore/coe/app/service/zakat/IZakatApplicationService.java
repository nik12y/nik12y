package com.idg.idgcore.coe.app.service.zakat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import java.util.List;

public interface IZakatApplicationService extends IBaseApplicationService {
    TransactionStatus processZakat (SessionContext sessionContext, ZakatDTO dto)
            throws FatalException, JsonProcessingException;
    void save (ZakatDTO zakatDTO);
    ZakatDTO getZakatByYear (SessionContext sessionContext, ZakatDTO zakatDTO)
            throws FatalException, JsonProcessingException;
    List<ZakatDTO> getZakats (SessionContext sessionContext) throws FatalException;

}

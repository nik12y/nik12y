package com.idg.idgcore.coe.app.service.city;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.coe.dto.city.CityDTO;

import java.util.List;

public interface ICityApplicationService extends IBaseApplicationService {
    TransactionStatus processCity (SessionContext sessionContext, CityDTO dto)
            throws FatalException, JsonProcessingException;
    void save (CityDTO cityDTO);
    CityDTO getCityByCode (SessionContext sessionContext, CityDTO cityDTO)
            throws FatalException, JsonProcessingException;
    List<CityDTO> getCities (SessionContext sessionContext) throws FatalException;

}

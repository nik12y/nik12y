package com.idg.idgcore.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.app.dto.CountryDTO;
import java.util.List;

public interface ICountryApplicationService extends IBaseApplicationService {
    TransactionStatus processCountry (SessionContext sessionContext, CountryDTO dto) throws FatalException,
            JsonProcessingException;
    void save (CountryDTO countryDTO);
    CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException;
    List<CountryDTO> getCountries (SessionContext sessionContext)
            throws FatalException;

}

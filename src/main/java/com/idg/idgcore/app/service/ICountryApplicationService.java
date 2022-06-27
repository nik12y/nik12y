package com.idg.idgcore.app.service;

import com.fasterxml.jackson.core.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.dto.core.*;
import com.idg.idgcore.infra.exception.FatalException;
import com.idg.idgcore.app.dto.CountryDTO;

import java.util.*;

public interface ICountryApplicationService extends IBaseApplicationService {
    TransactionStatus processCountry (SessionContext sessionContext, CountryDTO dto) throws FatalException,
            JsonProcessingException;
    void save (CountryDTO countryDTO);
    CountryDTO getCountryByCode (SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException;
    List<CountryDTO> getCountries (SessionContext sessionContext)
            throws FatalException;

}

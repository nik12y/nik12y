package com.idg.idgcore.coe.app.service.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.coe.dto.country.CountryDTO;

import java.util.List;

public interface ICountryApplicationService extends IBaseApplicationService {
    TransactionStatus processCountry(SessionContext sessionContext, CountryDTO dto)
            throws FatalException, JsonProcessingException;

    void save(CountryDTO countryDTO);

    CountryDTO getCountryByCode(SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException;

    List<CountryDTO> getCountries(SessionContext sessionContext) throws FatalException;

    List<CountryDTO> searchCountry(SessionContext sessionContext, CountryDTO countryDTO) throws FatalException, JsonProcessingException;

}

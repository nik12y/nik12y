package com.idg.idgcore.coe.domain.service.country;

import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;

import java.util.List;

public interface ICountryDomainService {
    CountryEntity getConfigurationByCode (CountryDTO countryDTO);
    List<CountryEntity> getCountries ();
    CountryEntity getCountryByCode (String countryCode);
    void save (CountryDTO countryDTO);

}

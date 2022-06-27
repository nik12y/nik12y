package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.CountryDTO;
import com.idg.idgcore.domain.entity.CountryEntity;

import java.util.List;

public interface ICountryDomainService {
    CountryEntity getConfigurationByCode (CountryDTO countryDTO);
    List<CountryEntity> getCountries ();
    CountryEntity getCountryByCode (String countryCode);
    void save (CountryDTO countryDTO);

}

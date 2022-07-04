package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.dto.CountryDTO;
import com.idg.idgcore.coe.domain.entity.CountryEntity;

import java.util.List;

public interface ICountryDomainService {
    CountryEntity getConfigurationByCode (CountryDTO countryDTO);
    List<CountryEntity> getCountries ();
    CountryEntity getCountryByCode (String countryCode);
    void save (CountryDTO countryDTO);

}

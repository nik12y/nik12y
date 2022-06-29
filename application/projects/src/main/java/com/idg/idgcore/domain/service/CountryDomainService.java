package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.CountryDTO;
import com.idg.idgcore.domain.entity.CountryEntity;
import com.idg.idgcore.domain.repository.ICountryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryDomainService implements ICountryDomainService {
    @Autowired
    private ICountryRepository countryRepository;

    public CountryEntity getConfigurationByCode (CountryDTO countryDTO) {
        return this.countryRepository.findByCountryCode(countryDTO.getCountryCode());
    }

    public List<CountryEntity> getCountries () {
        return this.countryRepository.findAll();
    }

    public CountryEntity getCountryByCode (String countryCode) {
        return this.countryRepository.findByCountryCode(countryCode);
    }

    public void save (CountryDTO countryDTO) {
        ModelMapper mapper = new ModelMapper();
        CountryEntity countryEntity = mapper.map(countryDTO, CountryEntity.class);
        this.countryRepository.save(countryEntity);
    }
}

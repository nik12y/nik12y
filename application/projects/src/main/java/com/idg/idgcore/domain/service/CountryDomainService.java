package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.CountryDTO;
import com.idg.idgcore.domain.assembler.CountryAssembler;
import com.idg.idgcore.domain.entity.CountryEntity;
import com.idg.idgcore.domain.repository.ICountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CountryDomainService implements ICountryDomainService {
    @Autowired
    private ICountryRepository countryRepository;

    @Autowired
    private CountryAssembler countryAssembler;

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
        CountryEntity countryEntity =countryAssembler.convertDtoToEntity(countryDTO);
        this.countryRepository.save(countryEntity);
    }
}

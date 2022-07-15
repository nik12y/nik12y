package com.idg.idgcore.coe.domain.service.country;

import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.domain.assembler.country.CountryAssembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.repository.country.ICountryRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CountryDomainService implements ICountryDomainService {
    @Autowired
    private ICountryRepository countryRepository;
    @Autowired
    private CountryAssembler countryAssembler;

    public CountryEntity getConfigurationByCode (CountryDTO countryDTO) {
        CountryEntity countryEntity = null;
        try {
            countryEntity = this.countryRepository.findByCountryCode(countryDTO.getCountryCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return countryEntity;
    }

    public List<CountryEntity> getCountries () {
        return this.countryRepository.findAll();
    }

    public CountryEntity getCountryByCode (String countryCode) {
        CountryEntity countryEntity = null;
        try {
            countryEntity = this.countryRepository.findByCountryCode(countryCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return countryEntity;
    }

    public void save (CountryDTO countryDTO) {
        try {
            CountryEntity countryEntity = countryAssembler.convertDtoToEntity(countryDTO);
            this.countryRepository.save(countryEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}

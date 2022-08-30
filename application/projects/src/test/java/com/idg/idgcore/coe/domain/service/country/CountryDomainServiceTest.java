package com.idg.idgcore.coe.domain.service.country;

import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.repository.country.ICountryRepository;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CountryDomainServiceTest {

    @Mock
    private ICountryRepository countryRepository;

    @InjectMocks
    private CountryDomainService countryDomainService;

    private CountryEntity countryEntity;
    private CountryDTO countryDTO;


    @BeforeEach
    void setUp() {
        countryDTO=getCountryDTO ();
        countryEntity=getCountryEntity();
    }

    @Test
    @DisplayName("Junit test for getCountries method ")
    void getCountriesReturnStatesList() {
        given(countryRepository.findAll()).willReturn(List.of(countryEntity));
        List<CountryEntity> countryEntityList = countryDomainService.getCountries();
        assertThat(countryEntityList).isNotNull();
        assertThat(countryEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getCountries method for negative scenario")
    void getCountriesEmptyStateEntityList()
    {
        given(countryRepository.findAll()).willReturn(Collections.emptyList());
        List<CountryEntity> countryEntityList = countryDomainService.getCountries();
        assertThat(countryEntityList).isEmpty();
        assertThat(countryEntityList.size()).isZero();

    }


    @Test
    @DisplayName("JUnit test for getCountryById method")
    void getCountryByCodeReturnCountryEntityObject() {
        given(countryRepository.findByCountryCode("IN")).willReturn(countryEntity);
        CountryEntity countryEntity1 =countryDomainService.getCountryByCode(countryEntity.getCountryCode());
        assertThat(countryEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getCountryById catch block method")
    void getCountryByIdReturnCatchBolock() {
        CountryEntity countryEntity1=null;

        assertThrows(Exception.class,()-> {
            CountryEntity countryEntity2 = countryDomainService.getCountryByCode(countryEntity1.getCountryCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(countryRepository.findByCountryCode("IN")).willReturn(countryEntity);
        CountryEntity countryByCode = countryDomainService.getConfigurationByCode(countryDTO);
        assertThat(countryByCode).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        CountryDTO countryDTO = null;
        assertThrows(BusinessException.class,()-> {
            CountryEntity countryByCode = countryDomainService.getConfigurationByCode(countryDTO);
        });
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        CountryDTO countryDTO = null;
        assertThrows(Exception.class,()-> {
            countryDomainService.save(countryDTO);
        });
    }


    private CountryEntity getCountryEntity()
    {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCountryCode("IN");
        countryEntity.setCountryName("INDIA");
        countryEntity.setCountryCodeAlternate("IN");
        countryEntity.setCountryCodeMachine("345");
        countryEntity.setRegionCode("ASIA");
        countryEntity.setCurrencyLimit("INR");
        countryEntity.setOverallLimit(100000f);
        countryEntity.setIsIban('N');
        countryEntity.setIsEuMember('N');
        countryEntity.setIsClearingNetwork('N');
        countryEntity.setIsMtGenerate('N');
        countryEntity.setIsClearingNetwork('N');
        return countryEntity;
    }

    private CountryDTO getCountryDTO()
    {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryCode("IN");
        countryDTO.setCountryName("INDIA");
        countryDTO.setAlternateCountryCode("IN");
        countryDTO.setNumericCountryCode("345");
        countryDTO.setRegionCode("ASIA");
        countryDTO.setLimitCurrency("INR");
        countryDTO.setOverallLimit(100000f);
        countryDTO.setIbanRequired(false);
        countryDTO.setEuMember(false);
        countryDTO.isClearingCodeBicPlus();
        countryDTO.setGenerateMt205(false);
        countryDTO.setDefaultClearingNetwork(false);
        countryDTO.setAuthorized("Y");
        return countryDTO;
    }

}
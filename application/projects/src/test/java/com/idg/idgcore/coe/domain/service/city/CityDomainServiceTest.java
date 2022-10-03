package com.idg.idgcore.coe.domain.service.city;


import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.repository.city.ICityRepository;
import com.idg.idgcore.coe.dto.city.CityDTO;
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
class CityDomainServiceTest {
    @Mock
    private ICityRepository cityRepository;

    @InjectMocks
    private CityDomainService cityDomainService;

    private CityEntity cityEntity;
    private CityDTO cityDTO;


    @BeforeEach
    void setUp() {
        cityDTO=getCityDTO ();
        cityEntity=getCityEntity();
    }

    @Test
    @DisplayName("Junit test for getCities method ")
    void getCitiesReturnCitiesList() {
        given(cityRepository.findAll()).willReturn(List.of(cityEntity));
        List<CityEntity> cityEntityList = cityDomainService.getAllEntities();
        assertThat(cityEntityList).isNotNull();
        assertThat(cityEntityList).hasSize(1);
    }

    @Test
    @DisplayName("JUnit test for getCities method for negative scenario")
    void getCitiesEmptyCityEntityList()
    {
        given(cityRepository.findAll()).willReturn(Collections.emptyList());
        List<CityEntity> cityEntityList = cityDomainService.getAllEntities();
        assertThat(cityEntityList).isEmpty();
        assertThat(cityEntityList).hasSize(0);

    }

    @Test
    @DisplayName("JUnit test for getCityById method")
    void getCityByCodeReturnCityEntityObject() {
        given(cityRepository.findByCityCode("MU")).willReturn(cityEntity);
        CityEntity cityEntity1 =cityDomainService.getCityByCode(cityEntity.getCityCode());
        assertThat(cityEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getCityById catch block method")
    void getCityByCodeReturnCatchBlock() {
        CityEntity cityEntity1=null;

        assertThrows(Exception.class,()-> {
            CityEntity cityEntity2 = cityDomainService.getCityByCode(cityEntity1.getCityCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(cityRepository.findByCityCode("MU")).willReturn(cityEntity);
        CityEntity cityByCode = cityDomainService.getEntityByIdentifier(cityDTO.getTaskIdentifier());
        assertThat(cityByCode).isNotNull();
    }

/*
    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        CityDTO cityDTO = null;
        assertThrows(BusinessException.class,()-> {
            CityEntity cityByCode = cityDomainService.getConfigurationByCode(cityDTO);
        });
    }*/


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        CityDTO cityDTO = null;
        assertThrows(Exception.class,()-> {
            cityDomainService.save(cityDTO);
        });
    }


    private CityEntity getCityEntity()
    {
        CityEntity cityEntity=new CityEntity();
        cityEntity.setCityCode("MU");
        cityEntity.setCityName("MUMBAI");
        cityEntity.setCountryCode("IN");

        return cityEntity;
    }

    private CityDTO getCityDTO()
    {
        CityDTO cityDTO=new CityDTO();
        cityDTO.setCityCode("MU");
        cityDTO.setCityName("MUMBAI");
        cityDTO.setCountryCode("IN");
        return cityDTO;
    }

}
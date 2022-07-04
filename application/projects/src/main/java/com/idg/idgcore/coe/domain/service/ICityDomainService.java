package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.dto.CityDTO;
import com.idg.idgcore.coe.domain.entity.CityEntity;

import java.util.List;

public interface ICityDomainService {
    CityEntity getConfigurationByCode (CityDTO cityDTO);
    List<CityEntity> getCities ();
    CityEntity getCityByCode (String cityCode);
    void save (CityDTO cityDTO);

}

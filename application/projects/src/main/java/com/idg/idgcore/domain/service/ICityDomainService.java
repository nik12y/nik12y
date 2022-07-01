package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.CityDTO;
import com.idg.idgcore.domain.entity.CityEntity;

import java.util.List;

public interface ICityDomainService {
    CityEntity getConfigurationByCode (CityDTO cityDTO);
    List<CityEntity> getCities ();
    CityEntity getCityByCode (String cityCode);
    void save (CityDTO cityDTO);

}

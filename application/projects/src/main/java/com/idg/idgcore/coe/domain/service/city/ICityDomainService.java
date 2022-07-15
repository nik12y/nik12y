package com.idg.idgcore.coe.domain.service.city;

import com.idg.idgcore.coe.dto.city.CityDTO;
import com.idg.idgcore.coe.domain.entity.city.CityEntity;

import java.util.List;

public interface ICityDomainService {
    CityEntity getConfigurationByCode (CityDTO cityDTO);
    List<CityEntity> getCities ();
    CityEntity getCityByCode (String cityCode);
    void save (CityDTO cityDTO);

}

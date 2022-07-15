package com.idg.idgcore.coe.domain.service.city;

import com.idg.idgcore.coe.dto.city.CityDTO;
import com.idg.idgcore.coe.domain.assembler.city.CityAssembler;
import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.repository.city.ICityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityDomainService implements ICityDomainService {
    @Autowired
    private ICityRepository cityRepository;

    @Autowired
    private CityAssembler cityAssembler;

    public CityEntity getConfigurationByCode (CityDTO cityDTO) {
        return this.cityRepository.findByCityCode(cityDTO.getCityCode());
    }

    public List<CityEntity> getCities () {
        return this.cityRepository.findAll();
    }

    public CityEntity getCityByCode (String cityCode) {
        return this.cityRepository.findByCityCode(cityCode);
    }

    public void save (CityDTO cityDTO) {
        CityEntity cityEntity =cityAssembler.convertDtoToEntity(cityDTO);
        this.cityRepository.save(cityEntity);
    }
}

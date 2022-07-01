package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.CityDTO;
import com.idg.idgcore.domain.assembler.CityAssembler;
import com.idg.idgcore.domain.entity.CityEntity;
import com.idg.idgcore.domain.repository.ICityRepository;
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

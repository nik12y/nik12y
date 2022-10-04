package com.idg.idgcore.coe.app.service.city;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.common.Constants;
import com.idg.idgcore.coe.domain.assembler.city.CityAssembler;
import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.service.city.CityDomainService;
import com.idg.idgcore.coe.dto.city.CityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service ("cityApplicationService")
public class CityApplicationService extends GenericApplicationService<CityDTO, CityEntity, CityDomainService, CityAssembler> {
    CityApplicationService() {
        super(Constants.CITY);
    }

    public String getTaskCode () {
        return CityDTO.builder().build().getTaskCode();
    }
}
package com.idg.idgcore.coe.domain.service.zakat;

import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;

import java.util.List;

public interface IZakatDomainService {
    ZakatEntity getConfigurationByYear (ZakatDTO zakatDTO);
    List<ZakatEntity> getZakats ();
    ZakatEntity getZakatByYear (Integer zakatYear);
    void save (ZakatDTO zakatDTO);
}

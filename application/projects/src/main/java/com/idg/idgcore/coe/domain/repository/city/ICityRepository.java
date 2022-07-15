package com.idg.idgcore.coe.domain.repository.city;

import com.idg.idgcore.coe.domain.entity.city.CityEntity;
import com.idg.idgcore.coe.domain.entity.city.CityEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<CityEntity, CityEntityKey> {
    CityEntity findByCityCode (String cityCode);
}

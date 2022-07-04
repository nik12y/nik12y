package com.idg.idgcore.coe.domain.repository;

import com.idg.idgcore.coe.domain.entity.CityEntity;
import com.idg.idgcore.coe.domain.entity.CityEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<CityEntity, CityEntityKey> {
    CityEntity findByCityCode (String cityCode);
}

package com.idg.idgcore.domain.repository;

import com.idg.idgcore.domain.entity.CityEntity;
import com.idg.idgcore.domain.entity.CityEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICityRepository extends JpaRepository<CityEntity, CityEntityKey> {
    CityEntity findByCityCode (String cityCode);
}

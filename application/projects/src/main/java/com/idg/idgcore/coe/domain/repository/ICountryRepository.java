package com.idg.idgcore.coe.domain.repository;

import com.idg.idgcore.coe.domain.entity.CountryEntity;
import com.idg.idgcore.coe.domain.entity.CountryEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<CountryEntity,CountryEntityKey> {
    CountryEntity findByCountryCode (String countryCode);
}

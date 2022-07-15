package com.idg.idgcore.coe.domain.repository.country;

import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.country.CountryEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<CountryEntity,CountryEntityKey> {
    CountryEntity findByCountryCode (String countryCode);
}

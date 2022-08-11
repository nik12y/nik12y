package com.idg.idgcore.coe.domain.repository.zakat;

import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IZakatRepository extends JpaRepository<ZakatEntity, ZakatEntityKey> {
    ZakatEntity findByZakatYear (Integer zakatYear);
}

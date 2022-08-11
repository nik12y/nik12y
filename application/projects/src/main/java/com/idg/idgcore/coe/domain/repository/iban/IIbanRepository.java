package com.idg.idgcore.coe.domain.repository.iban;

import com.idg.idgcore.coe.domain.entity.iban.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface IIbanRepository extends JpaRepository<IbanEntity,IbanEntityKey> {
    IbanEntity findByIbanCountryCode (String ibanCountryCode);
}

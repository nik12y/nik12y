package com.idg.idgcore.coe.domain.service.iban;

import com.idg.idgcore.coe.domain.entity.iban.*;
import com.idg.idgcore.coe.dto.iban.*;

import java.util.*;

public interface IIbanDomainService {
    IbanEntity getConfigurationByCode (IbanDTO ibanDTO);
    List<IbanEntity> getIbans();
    IbanEntity getIbanByIbanCountryCode (String ibanCountryCode);
    void save (IbanDTO ibanDTO);

}

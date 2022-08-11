package com.idg.idgcore.coe.domain.service.bankparameter;

import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import com.idg.idgcore.coe.dto.bankparameter.*;

import java.util.*;

public interface IBankParameterDomainService {
    BankParameterEntity getConfigurationByCode (BankParameterDTO bankParameterDTO);
    List<BankParameterEntity> getBankParameters ();
    BankParameterEntity getBankParameterByBankCode (String bankCode);
    void save (BankParameterDTO bankParameterDTO);

}

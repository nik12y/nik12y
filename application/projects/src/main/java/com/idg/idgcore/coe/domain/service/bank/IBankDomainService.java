package com.idg.idgcore.coe.domain.service.bank;

import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.dto.bank.BankDTO;

import java.util.List;

public interface IBankDomainService {
    BankEntity getConfigurationByCode (BankDTO bankDTO);
    List<BankEntity> getBanks ();
    BankEntity getBankByCode (String bankCode);
    void save(BankDTO bankDTO);
}

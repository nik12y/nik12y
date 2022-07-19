package com.idg.idgcore.coe.domain.service.bankidentifier;

import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;

import java.util.List;

public interface IBankIdentifierDomainService {
    BankIdentifierEntity getConfigurationByCode(BankIdentifierDTO bankIdentifierDTO);
    List<BankIdentifierEntity> getBankIdentifiers();
    BankIdentifierEntity getBankIdentifierByCode(String bankIdentifierCode);
    void save(BankIdentifierDTO bankIdentifierDTO);
}

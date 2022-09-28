package com.idg.idgcore.coe.domain.service.bank;

import com.idg.idgcore.coe.domain.assembler.bank.BankAssembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.repository.bank.IBankRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BankDomainService extends DomainService<BankDTO, BankEntity> {

    @Autowired
    private IBankRepository bankRepository;

    @Autowired
    private BankAssembler bankAssembler;

    @Override
    public BankEntity getEntityByIdentifier(String bankCode) {
        return this.bankRepository.findByBankCode(bankCode);
    }

    @Override
    public List<BankEntity> getAllEntities() {
        return this.bankRepository.findAll();
    }

    public void save(BankDTO bankDTO) {
        BankEntity bankEntity = bankAssembler.toEntity(bankDTO);
        this.bankRepository.save(bankEntity);
    }
}

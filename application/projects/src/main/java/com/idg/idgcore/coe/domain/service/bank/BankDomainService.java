package com.idg.idgcore.coe.domain.service.bank;

import com.idg.idgcore.coe.domain.assembler.bank.BankAssembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.repository.bank.IBankRepository;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BankDomainService implements IBankDomainService{

    @Autowired
    private IBankRepository bankRepository;

    @Autowired
    private BankAssembler bankAssembler;

    public BankEntity getConfigurationByCode(BankDTO bankDTO) {
        BankEntity bankEntity = null;
        try {
            bankEntity = this.bankRepository.findByBankCode(bankDTO.getBankCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankEntity;
    }

    public List<BankEntity> getBanks() {
        return this.bankRepository.findAll();
    }

    public BankEntity getBankByCode(String bankCode) {
        return this.bankRepository.findByBankCode(bankCode);
    }

    public void save(BankDTO bankDTO) {
        BankEntity bankEntity = bankAssembler.convertDtoToEntity(bankDTO);
        this.bankRepository.save(bankEntity);
    }
}

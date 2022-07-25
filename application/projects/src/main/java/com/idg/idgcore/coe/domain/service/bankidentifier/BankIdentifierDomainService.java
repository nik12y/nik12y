package com.idg.idgcore.coe.domain.service.bankidentifier;

import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.repository.bankidentifier.IBankIdentifierRepository;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BankIdentifierDomainService implements IBankIdentifierDomainService {

    @Autowired
    private IBankIdentifierRepository bankIdentifierRepository;

    @Autowired
    private BankIdentifierAssembler bankIdentifierAssembler;

    public BankIdentifierEntity getConfigurationByCode(BankIdentifierDTO bankIdentifierDTO) {
        BankIdentifierEntity bankIdentifierEntity = null;
        try {
            bankIdentifierEntity = this.bankIdentifierRepository.findByBankIdentifierCode(bankIdentifierDTO.getBankIdentifierCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankIdentifierEntity;
    }

    public List<BankIdentifierEntity> getBankIdentifiers() {
        return this.bankIdentifierRepository.findAll();
    }

    public BankIdentifierEntity getBankIdentifierByCode(String bankIdentifierCode) {
        BankIdentifierEntity bankIdentifierEntity = null;
        try {
            bankIdentifierEntity = this.bankIdentifierRepository.findByBankIdentifierCode(bankIdentifierCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankIdentifierEntity;
    }

    public void save(BankIdentifierDTO bankIdentifierDTO) {
        try {
            BankIdentifierEntity bankIdentifierEntity = bankIdentifierAssembler.convertDtoToEntity(bankIdentifierDTO);
            this.bankIdentifierRepository.save(bankIdentifierEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

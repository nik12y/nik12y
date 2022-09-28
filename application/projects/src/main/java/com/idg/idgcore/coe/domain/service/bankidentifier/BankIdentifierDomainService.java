package com.idg.idgcore.coe.domain.service.bankidentifier;

import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.repository.bankidentifier.IBankIdentifierRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BankIdentifierDomainService extends DomainService<BankIdentifierDTO, BankIdentifierEntity> {

    @Autowired
    private IBankIdentifierRepository bankIdentifierRepository;

    @Autowired
    private BankIdentifierAssembler bankIdentifierAssembler;

    @Override
    public BankIdentifierEntity getEntityByIdentifier(String bankIdentifierCode) {
        BankIdentifierEntity bankIdentifierEntity = null;
        try {
            bankIdentifierEntity = this.bankIdentifierRepository.findByBankIdentifierCode(bankIdentifierCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankIdentifierEntity;
    }

    @Override
    public List<BankIdentifierEntity> getAllEntities() {
        return this.bankIdentifierRepository.findAll();
    }

    public void save(BankIdentifierDTO bankIdentifierDTO) {
        try {
            BankIdentifierEntity bankIdentifierEntity = bankIdentifierAssembler.toEntity(bankIdentifierDTO);
            this.bankIdentifierRepository.save(bankIdentifierEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

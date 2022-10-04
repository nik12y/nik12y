package com.idg.idgcore.coe.domain.service.bankparameter;

import com.idg.idgcore.coe.domain.assembler.bankparameter.BankParameterAssembler;
import com.idg.idgcore.coe.domain.entity.bankparameter.BankParameterEntity;
import com.idg.idgcore.coe.domain.repository.bankparameter.IBankParameterRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.bankparameter.BankParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BankParameterDomainService extends DomainService<BankParameterDTO, BankParameterEntity> {
    @Autowired
    private IBankParameterRepository bankParameterRepository;

    @Autowired
    private BankParameterAssembler bankParameterAssembler;

    @Override
    public BankParameterEntity getEntityByIdentifier(String bankCode) {
        BankParameterEntity bankParameterEntity = null;
        try {
            bankParameterEntity = this.bankParameterRepository.findByBankCode(bankCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankParameterEntity;
    }

    @Override
    public List<BankParameterEntity> getAllEntities() {
        return this.bankParameterRepository.findAll();
    }

    public void save (BankParameterDTO bankParameterDTO) {
        try {
            BankParameterEntity bankParameterEntity = bankParameterAssembler.toEntity(
                    bankParameterDTO);
            this.bankParameterRepository.save(bankParameterEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

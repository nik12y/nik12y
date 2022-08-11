package com.idg.idgcore.coe.domain.service.bankparameter;

import com.idg.idgcore.coe.domain.assembler.bankparameter.BankParameterAssembler;
import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import com.idg.idgcore.coe.domain.repository.bankparameter.IBankParameterRepository;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BankParameterDomainService implements IBankParameterDomainService {
    @Autowired
    private IBankParameterRepository bankParameterRepository;

    @Autowired
    private BankParameterAssembler bankParameterAssembler;

    public BankParameterEntity getConfigurationByCode (BankParameterDTO bankParameterDTO) {
        BankParameterEntity bankParameterEntity = null;
        try {
            bankParameterEntity= this.bankParameterRepository.findByBankCode(bankParameterDTO.getBankCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankParameterEntity;
    }

    public List<BankParameterEntity> getBankParameters () {
        return this.bankParameterRepository.findAll();
    }

    public BankParameterEntity getBankParameterByBankCode (String bankCode) {
        BankParameterEntity bankParameterEntity = null;
        try {
            bankParameterEntity = this.bankParameterRepository.findByBankCode(bankCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return bankParameterEntity;
    }

    public void save (BankParameterDTO bankParameterDTO) {
        try {
            BankParameterEntity bankParameterEntity = bankParameterAssembler.convertDtoToEntity(
                    bankParameterDTO);
            this.bankParameterRepository.save(bankParameterEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

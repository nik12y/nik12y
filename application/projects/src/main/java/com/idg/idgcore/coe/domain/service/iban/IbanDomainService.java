package com.idg.idgcore.coe.domain.service.iban;

import com.idg.idgcore.coe.domain.assembler.iban.IbanAssembler;
import com.idg.idgcore.coe.domain.entity.iban.*;
import com.idg.idgcore.coe.domain.repository.iban.IIbanRepository;
import com.idg.idgcore.coe.dto.iban.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class IbanDomainService implements IIbanDomainService {
    @Autowired
    private IIbanRepository ibanRepository;

    @Autowired
    private IbanAssembler ibanAssembler;

    public IbanEntity getConfigurationByCode (IbanDTO ibanDTO) {
        IbanEntity ibanEntity = null;
        try {
            ibanEntity= this.ibanRepository.findByIbanCountryCode(ibanDTO.getIbanCountryCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return ibanEntity;
    }

    public List<IbanEntity> getIbans () {
        return this.ibanRepository.findAll();
    }

    public IbanEntity getIbanByIbanCountryCode (String ibanCountryCode) {
        IbanEntity ibanEntity = null;
        try {
            ibanEntity = this.ibanRepository.findByIbanCountryCode(ibanCountryCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return ibanEntity;
    }

    public void save (IbanDTO ibanDTO) {
        try {
            IbanEntity ibanEntity = ibanAssembler.convertDtoToEntity(
                    ibanDTO);
            this.ibanRepository.save(ibanEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

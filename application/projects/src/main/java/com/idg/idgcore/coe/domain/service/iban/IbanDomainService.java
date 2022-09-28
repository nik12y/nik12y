package com.idg.idgcore.coe.domain.service.iban;

import com.idg.idgcore.coe.domain.assembler.iban.IbanAssembler;
import com.idg.idgcore.coe.domain.entity.iban.IbanEntity;
import com.idg.idgcore.coe.domain.repository.iban.IIbanRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class IbanDomainService extends DomainService<IbanDTO, IbanEntity> {
    @Autowired
    private IIbanRepository ibanRepository;

    @Autowired
    private IbanAssembler ibanAssembler;

    @Override
    public IbanEntity getEntityByIdentifier(String ibanCountryCode) {
        IbanEntity ibanEntity = null;
        try {
            ibanEntity = this.ibanRepository.findByIbanCountryCode(ibanCountryCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return ibanEntity;
    }

    @Override
    public List<IbanEntity> getAllEntities() {
        return this.ibanRepository.findAll();
    }

    public void save (IbanDTO ibanDTO) {
        try {
            IbanEntity ibanEntity = ibanAssembler.toEntity(ibanDTO);
            this.ibanRepository.save(ibanEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

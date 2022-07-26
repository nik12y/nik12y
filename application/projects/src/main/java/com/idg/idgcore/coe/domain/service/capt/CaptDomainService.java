package com.idg.idgcore.coe.domain.service.capt;

import com.idg.idgcore.coe.domain.assembler.capt.CaptAssembler;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.repository.capt.ICaptRepository;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CaptDomainService implements ICaptDomainService {
    @Autowired
    private ICaptRepository captRepository;

    @Autowired
    private CaptAssembler captAssembler;

    public CaptEntity getConfigurationByCode (CaptDTO captDTO) {
        return this.captRepository.findByClearingPaymentTypeCode(captDTO.getClearingPaymentTypeCode());
    }

    public List<CaptEntity> getCaptAll() {
        return this.captRepository.findAll();
    }

    public CaptEntity getCaptByCode (String clearingPaymentTypeCode) {
        return this.captRepository.findByClearingPaymentTypeCode(clearingPaymentTypeCode);
    }

    public void save (CaptDTO captDTO) {
        CaptEntity captEntity =captAssembler.convertDtoToEntity(captDTO);
        this.captRepository.save(captEntity);
    }
}

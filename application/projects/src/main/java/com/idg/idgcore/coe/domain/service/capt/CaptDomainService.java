package com.idg.idgcore.coe.domain.service.capt;

import com.idg.idgcore.coe.domain.assembler.capt.CaptAssembler;
import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.repository.capt.ICaptRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class CaptDomainService extends DomainService<CaptDTO, CaptEntity> {

    @Autowired
    private ICaptRepository captRepository;
    @Autowired
    private CaptAssembler captAssembler;

    @Override
    public CaptEntity getEntityByIdentifier(String captCode) {
        CaptEntity captEntity = null;
        try {
            captEntity = this.captRepository.findByClearingPaymentTypeCode(captCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return captEntity;
    }

    @Override
    public List<CaptEntity> getAllEntities() {
        return this.captRepository.findAll();
    }

    public void save (CaptDTO captDTO) {
        try {
            CaptEntity captEntity = captAssembler.toEntity(captDTO);
            this.captRepository.save(captEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}

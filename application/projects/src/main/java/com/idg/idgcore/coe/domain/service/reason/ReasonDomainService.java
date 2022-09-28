package com.idg.idgcore.coe.domain.service.reason;

import com.idg.idgcore.coe.domain.assembler.reason.ReasonAssembler;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.repository.reason.IReasonRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ReasonDomainService extends DomainService<ReasonDTO, ReasonEntity> {
    @Autowired
    private IReasonRepository reasonRepository;
    @Autowired
    private ReasonAssembler reasonAssembler;

    @Override
    public ReasonEntity getEntityByIdentifier(String identifier) {
        ReasonEntity reasonEntity = null;
        try {
            reasonEntity = this.reasonRepository.findByPrimaryReasonCode(identifier);
        }
        catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return reasonEntity;
    }

    @Override
    public List<ReasonEntity> getAllEntities() {
        return this.reasonRepository.findAll();
    }

    public void save (ReasonDTO reasonDTO) {
        try {
            ReasonEntity reasonEntity = reasonAssembler.toEntity(reasonDTO);
            this.reasonRepository.save(reasonEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

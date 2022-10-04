package com.idg.idgcore.coe.domain.service.purgingpolicy;

import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.repository.purgingpolicy.IPurgingRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class PurgingDomainService extends DomainService<PurgingDTO, PurgingEntity> {

    @Autowired
    private IPurgingRepository purgingRepository;
    @Autowired
    private PurgingAssembler purgingAssembler;

    @Override
    public PurgingEntity getEntityByIdentifier(String moduleCode) {
        PurgingEntity purgingEntity = null;
        try {
            purgingEntity = this.purgingRepository.findByModuleCode(moduleCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purgingEntity;
    }

    @Override
    public List<PurgingEntity> getAllEntities() {
        return this.purgingRepository.findAll();
    }

    public void save (PurgingDTO purgingDTO) {
        try {
            PurgingEntity purgingEntity = purgingAssembler.toEntity(purgingDTO);
            this.purgingRepository.save(purgingEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

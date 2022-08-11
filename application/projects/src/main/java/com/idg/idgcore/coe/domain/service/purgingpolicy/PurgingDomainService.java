package com.idg.idgcore.coe.domain.service.purgingpolicy;

import com.idg.idgcore.coe.domain.assembler.purgingpolicy.PurgingAssembler;
import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.repository.purgingpolicy.IPurgingRepository;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class PurgingDomainService implements IPurgingDomainService{

    @Autowired
    private IPurgingRepository purgingRepository;
    @Autowired
    private PurgingAssembler purgingAssembler;

    public PurgingEntity getConfigurationByCode (PurgingDTO purgingDTO) {
        PurgingEntity purgingEntity = null;
        try {
            purgingEntity = this.purgingRepository.findByModuleCode(purgingDTO.getModuleCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purgingEntity;
    }

    public List<PurgingEntity> getPurgingAll () {
        return this.purgingRepository.findAll();
    }

    public PurgingEntity getPurgingByCode (String moduleCode) {
        PurgingEntity purgingEntity = null;
        try {
            purgingEntity = this.purgingRepository.findByModuleCode(moduleCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purgingEntity;
    }

    public void save (PurgingDTO purgingDTO) {
        try {
            PurgingEntity purgingEntity = purgingAssembler.convertDtoToEntity(purgingDTO);
            this.purgingRepository.save(purgingEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

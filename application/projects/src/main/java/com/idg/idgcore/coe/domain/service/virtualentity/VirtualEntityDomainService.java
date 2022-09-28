package com.idg.idgcore.coe.domain.service.virtualentity;

import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.repository.virtualentity.IVirtualEntityRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class VirtualEntityDomainService extends DomainService<VirtualEntityDTO, VirtualEntity> {

    @Autowired
    private IVirtualEntityRepository virtualEntityRepository;

    @Autowired
    private VirtualEntityAssembler virtualEntityAssembler;

    @Override
    public VirtualEntity getEntityByIdentifier(String entityCode) {
        VirtualEntity virtualEntity = null;
        try {
            virtualEntity = this.virtualEntityRepository.findByEntityCode(entityCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return virtualEntity;
    }

    @Override
    public List<VirtualEntity> getAllEntities() {
        return this.virtualEntityRepository.findAll();
    }

    @Override
    public void save(VirtualEntityDTO virtualEntityDTO) {
        try {
            VirtualEntity virtualEntity = virtualEntityAssembler.toEntity(virtualEntityDTO);
            this.virtualEntityRepository.save(virtualEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

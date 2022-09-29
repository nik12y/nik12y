package com.idg.idgcore.coe.domain.service.virtualentity;

import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.repository.virtualentity.IVirtualEntityRepository;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class VirtualEntityDomainService implements IVirtualEntityDomainService{

    @Autowired
    private IVirtualEntityRepository virtualEntityRepository;

    @Autowired
    private VirtualEntityAssembler virtualEntityAssembler;

    @Override
    public VirtualEntity getConfigurationByCode(VirtualEntityDTO virtualEntityDTO)
    {
        VirtualEntity virtualEntity = null;
        try {
            virtualEntity = this.virtualEntityRepository.findByEntityCode(virtualEntity.getEntityCode());
        } catch(Exception e) {
            if(log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return virtualEntity;
    }

    @Override
    public List<VirtualEntity> getVirtualEntityAll() {
        return this.virtualEntityRepository.findAll();
    }

    @Override
    public VirtualEntity getByVirtualEntityCode(String entityCode) {
        VirtualEntity virtualEntity = null;
        try {
            virtualEntity = this.virtualEntityRepository.findByEntityCode(entityCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return virtualEntity;
    }

    @Override
    public void save(VirtualEntityDTO virtualEntityDTO) {
        try {
            VirtualEntity virtualEntity = virtualEntityAssembler.convertDtoToEntity(virtualEntityDTO);
            this.virtualEntityRepository.save(virtualEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

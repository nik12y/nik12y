package com.idg.idgcore.coe.domain.service.aml;

import com.idg.idgcore.coe.domain.assembler.aml.AmlAssembler;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.repository.aml.IAmlRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AmlDomainService extends DomainService<AmlDTO, AmlEntity> {

    @Autowired
    private IAmlRepository amlRepository;

    @Autowired
    private AmlAssembler amlAssembler;

    @Override
    public AmlEntity getEntityByIdentifier(String productCategory) {
        AmlEntity amlEntity = null;
        try {
            amlEntity = this.amlRepository.findByProductCategory(productCategory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return amlEntity;
    }

    @Override
    public List<AmlEntity> getAllEntities() {
        return this.amlRepository.findAll();
    }

    public void save (AmlDTO amlDTO) {
        try {
            AmlEntity amlEntity = amlAssembler.toEntity(amlDTO);
            this.amlRepository.save(amlEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


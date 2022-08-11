package com.idg.idgcore.coe.domain.service.aml;

import com.idg.idgcore.coe.domain.assembler.aml.AmlAssembler;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.repository.aml.IAmlRepository;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AmlDomainService implements IAmlDomainService {
    @Autowired
    private IAmlRepository amlRepository;
    @Autowired
    private AmlAssembler amlAssembler;

    public AmlEntity getConfigurationByCode (AmlDTO amlDTO) {
        AmlEntity amlEntity = null;
        try {
            amlEntity = this.amlRepository.findByProductCategory(amlDTO.getProductCategory());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return amlEntity;
    }

    public List<AmlEntity> getAmls () {
        return this.amlRepository.findAll();
    }

    public AmlEntity getAmlByCode (String productCategory) {
        AmlEntity amlEntity = null;
        try {
            amlEntity = this.amlRepository.findByProductCategory(productCategory);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return amlEntity;
    }

    public void save (AmlDTO amlDTO) {
        try {
            AmlEntity amlEntity = amlAssembler.convertDtoToEntity(amlDTO);
            this.amlRepository.save(amlEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


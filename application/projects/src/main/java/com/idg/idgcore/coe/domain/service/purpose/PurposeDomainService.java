package com.idg.idgcore.coe.domain.service.purpose;

import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.repository.purpose.IPurposeRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class PurposeDomainService extends DomainService<PurposeDTO, PurposeEntity> {

    @Autowired
    private IPurposeRepository purposeRepository;

    @Autowired
    private PurposeAssembler purposeAssembler;

    @Override
    public PurposeEntity getEntityByIdentifier(String purposeCode) {
        PurposeEntity purposeEntity = null;
        try {
            purposeEntity = this.purposeRepository.findByPurposeCode(purposeCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purposeEntity;
    }

    @Override
    public List<PurposeEntity> getAllEntities() {
        return this.purposeRepository.findAll();
    }

    @Override
    public void save(PurposeDTO purposeDTO) {
        try {
            PurposeEntity purposeEntity = purposeAssembler.toEntity(purposeDTO);
            this.purposeRepository.save(purposeEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

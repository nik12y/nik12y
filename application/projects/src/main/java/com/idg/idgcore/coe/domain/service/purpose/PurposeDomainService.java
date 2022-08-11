package com.idg.idgcore.coe.domain.service.purpose;

import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.repository.purpose.IPurposeRepository;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class PurposeDomainService implements IPurposeDomainService {

    @Autowired
    private IPurposeRepository purposeRepository;

    @Autowired
    private PurposeAssembler purposeAssembler;


    @Override
    public PurposeEntity getConfigurationByCode(PurposeDTO purposeDTO) {
        PurposeEntity purposeEntity = null;
        try {
            purposeEntity = this.purposeRepository.findByPurposeCode(purposeDTO.getPurposeCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purposeEntity;
    }

    @Override
    public List<PurposeEntity> getPurposes() {
        return this.purposeRepository.findAll();
    }

    @Override
    public PurposeEntity getPurposeByCode(String purposeCode) {
        PurposeEntity purposeEntity = null;
        try {
            purposeEntity = this.purposeRepository.findByPurposeCode(purposeCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return purposeEntity;
    }

    @Override
    public void save(PurposeDTO purposeDTO) {
        try {
            PurposeEntity purposeEntity = purposeAssembler.convertDtoToEntity(purposeDTO);
            this.purposeRepository.save(purposeEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

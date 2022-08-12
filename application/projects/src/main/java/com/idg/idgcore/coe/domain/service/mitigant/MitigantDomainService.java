package com.idg.idgcore.coe.domain.service.mitigant;

import com.idg.idgcore.coe.domain.assembler.mitigant.MitigantAssembler;
import com.idg.idgcore.coe.domain.assembler.purpose.PurposeAssembler;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.repository.mitigant.IMitigantRepository;
import com.idg.idgcore.coe.domain.repository.purpose.IPurposeRepository;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class MitigantDomainService implements IMitigantDomainService{

    @Autowired
    private IMitigantRepository mitigantRepository;

    @Autowired
    private MitigantAssembler mitigantAssembler;

    @Override
    public MitigantEntity getConfigurationByCode(MitigantDTO mitigantDTO) {
        MitigantEntity mitigantEntity = null;
        try {
            mitigantEntity = this.mitigantRepository.findByMitigantCode(mitigantDTO.getMitigantCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mitigantEntity;
    }

    @Override
    public List<MitigantEntity> getMitigantAll() {
        return this.mitigantRepository.findAll();
    }

    @Override
    public MitigantEntity getMitigantByCode(String mitigantCode) {
        MitigantEntity mitigantEntity = null;
        try {
            mitigantEntity = this.mitigantRepository.findByMitigantCode(mitigantCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mitigantEntity;
    }

    @Override
    public void save(MitigantDTO mitigantDTO) {
        try {
            MitigantEntity mitigantEntity = mitigantAssembler.convertDtoToEntity(mitigantDTO);
            this.mitigantRepository.save(mitigantEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

package com.idg.idgcore.coe.domain.service.appvertype;

import com.idg.idgcore.coe.domain.assembler.appvertype.AppVerTypeAssembler;
import com.idg.idgcore.coe.domain.entity.appvertype.AppVerTypeEntity;
import com.idg.idgcore.coe.domain.repository.appvertype.IAppVerTypeRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AppVerTypeDomainService extends DomainService<AppVerTypeDTO, AppVerTypeEntity> {

    @Autowired
    private IAppVerTypeRepository appVerTypeRepository;

    @Autowired
    private AppVerTypeAssembler appVerTypeAssembler;

    @Override
    public AppVerTypeEntity getEntityByIdentifier(String verificationTypeId) {
        AppVerTypeEntity appVerTypeEntity = null;
        try {
            appVerTypeEntity = this.appVerTypeRepository.findByVerificationTypeId(verificationTypeId);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerTypeEntity;
    }

    @Override
    public List<AppVerTypeEntity> getAllEntities() {
        return this.appVerTypeRepository.findAll();
    }

    public void save(AppVerTypeDTO appVerTypeDTO) {
        try {
            AppVerTypeEntity appVerTypeEntity = appVerTypeAssembler.toEntity(appVerTypeDTO);
            this.appVerTypeRepository.save(appVerTypeEntity);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

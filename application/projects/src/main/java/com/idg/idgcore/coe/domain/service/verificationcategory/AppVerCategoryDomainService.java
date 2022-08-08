package com.idg.idgcore.coe.domain.service.verificationcategory;


import com.idg.idgcore.coe.domain.assembler.verificationcategory.AppVerCategoryConfigAssembler;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.repository.verificationcategory.IAppVerCategoryConfigRepository;
import com.idg.idgcore.coe.dto.verificationcategory.AppVerCategoryConfigDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AppVerCategoryDomainService implements IAppVerCategoryConfigDomainService {
    @Autowired
    private IAppVerCategoryConfigRepository appVerCategoryConfigRepository;

    @Autowired
    private AppVerCategoryConfigAssembler appVerCategoryConfigAssembler;

    public AppVerCategoryConfigEntity getConfigurationByCode (AppVerCategoryConfigDTO appVercategoryConfigDTO) {
        AppVerCategoryConfigEntity appVerCategoryConfigEntity = null;
        try {
            appVerCategoryConfigEntity = this.appVerCategoryConfigRepository.findByAppVerificationCategoryId(appVercategoryConfigDTO.getAppVerificationCategoryId());
        }
        catch (Exception e){
            if (log.isErrorEnabled())
            {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerCategoryConfigEntity;
    }

    public List<AppVerCategoryConfigEntity> getAppVerCategoryConfigs() {
        return this.appVerCategoryConfigRepository.findAll();
    }

    public AppVerCategoryConfigEntity getAppVerCategoryConfigByID (String appVerAppVerificationCategoryId) {
        AppVerCategoryConfigEntity appVerCategoryConfigEntity = null;
        try {
            appVerCategoryConfigEntity = this.appVerCategoryConfigRepository.findByAppVerificationCategoryId(appVerAppVerificationCategoryId);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerCategoryConfigEntity;
    }

    public void save (AppVerCategoryConfigDTO appVercategoryConfigDTO) {
        try {
            AppVerCategoryConfigEntity appVerCategoryConfigEntity = appVerCategoryConfigAssembler.convertDtoToEntity(appVercategoryConfigDTO);
            this.appVerCategoryConfigRepository.save(appVerCategoryConfigEntity);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

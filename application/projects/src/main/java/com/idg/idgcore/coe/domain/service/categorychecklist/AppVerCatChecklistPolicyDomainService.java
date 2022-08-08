package com.idg.idgcore.coe.domain.service.categorychecklist;


import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.repository.categorychecklist.IAppVerCatChecklistPolicyRepository;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AppVerCatChecklistPolicyDomainService implements IAppVerCatChecklistPolicyDomainService{

    @Autowired
    private IAppVerCatChecklistPolicyRepository appVerCatChecklistPolicyRepository;

    @Autowired
    private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;

    @Override
    public AppVerCatChecklistPolicyEntity getConfigurationByCode(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = null;
        try {
            appVerCatChecklistPolicyEntity = this.appVerCatChecklistPolicyRepository.findByAppVerChecklistPolicyId(appVerCatChecklistPolicyDTO.getAppVerChecklistPolicyId());
        }
        catch (Exception e){
            if (log.isErrorEnabled())
            {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerCatChecklistPolicyEntity;

    }

    @Override
    public List<AppVerCatChecklistPolicyEntity> getAppVerChecklistPolicys() {
        return this.appVerCatChecklistPolicyRepository.findAll();
    }

    @Override
    public AppVerCatChecklistPolicyEntity getAppVerChecklistPolicyById(String appVerChecklistPolicyId) {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = null;
        try {
            appVerCatChecklistPolicyEntity = this.appVerCatChecklistPolicyRepository.findByAppVerChecklistPolicyId(appVerChecklistPolicyId);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerCatChecklistPolicyEntity;
    }

    @Override
    public void save(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
            try {
                AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = appVerCatChecklistPolicyAssembler.convertDtoToEntity(appVerCatChecklistPolicyDTO);
                this.appVerCatChecklistPolicyRepository.save(appVerCatChecklistPolicyEntity);
            }
            catch (Exception e){
                if (log.isErrorEnabled()){
                    log.error(e.getMessage());
                }
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
            }
        }

    }


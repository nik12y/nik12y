package com.idg.idgcore.coe.domain.service.categorychecklist;


import com.idg.idgcore.coe.domain.assembler.categorychecklist.AppVerCatChecklistPolicyAssembler;
import com.idg.idgcore.coe.domain.entity.categorychecklist.AppVerCatChecklistPolicyEntity;
import com.idg.idgcore.coe.domain.repository.categorychecklist.IAppVerCatChecklistPolicyRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.categorychecklist.AppVerCatChecklistPolicyDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class AppVerCatChecklistPolicyDomainService extends DomainService<AppVerCatChecklistPolicyDTO, AppVerCatChecklistPolicyEntity> {

    @Autowired
    private IAppVerCatChecklistPolicyRepository appVerCatChecklistPolicyRepository;

    @Autowired
    private AppVerCatChecklistPolicyAssembler appVerCatChecklistPolicyAssembler;

    @Override
    public AppVerCatChecklistPolicyEntity getEntityByIdentifier(String appVerChecklistPolicyId) {
        AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = null;
        try {
            appVerCatChecklistPolicyEntity = this.appVerCatChecklistPolicyRepository.
                    findByAppVerChecklistPolicyId(appVerChecklistPolicyId);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return appVerCatChecklistPolicyEntity;
    }

    @Override
    public List<AppVerCatChecklistPolicyEntity> getAllEntities() {
        return this.appVerCatChecklistPolicyRepository.findAll();
    }

    @Override
    public void save(AppVerCatChecklistPolicyDTO appVerCatChecklistPolicyDTO) {
        try {
            AppVerCatChecklistPolicyEntity appVerCatChecklistPolicyEntity = appVerCatChecklistPolicyAssembler.
                    toEntity(appVerCatChecklistPolicyDTO);
            this.appVerCatChecklistPolicyRepository.save(appVerCatChecklistPolicyEntity);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

}


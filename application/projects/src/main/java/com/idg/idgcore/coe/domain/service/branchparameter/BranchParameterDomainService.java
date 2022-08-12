package com.idg.idgcore.coe.domain.service.branchparameter;

import com.idg.idgcore.coe.domain.assembler.branchparameter.BranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import com.idg.idgcore.coe.domain.repository.branchparameter.IBranchParameterRepository;
import com.idg.idgcore.coe.dto.branchparameter.*;
import com.idg.idgcore.coe.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchParameterDomainService implements IBranchParameterDomainService {
    @Autowired
    private IBranchParameterRepository branchParameterRepository;

    @Autowired
    private BranchParameterAssembler branchParameterAssembler;

    public BranchParameterEntity getConfigurationByCode (BranchParameterDTO branchParameterDTO) {
        BranchParameterEntity branchParameterEntity = null;
        try {
            branchParameterEntity= this.branchParameterRepository.findByBranchCode(branchParameterDTO.getBranchCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchParameterEntity;
    }

    public List<BranchParameterEntity> getBranchParameters () {
        return this.branchParameterRepository.findAll();
    }

    public BranchParameterEntity getBranchParameterByBranchCode (String branchCode) {
        BranchParameterEntity branchParameterEntity = null;
        try {
            branchParameterEntity = this.branchParameterRepository.findByBranchCode(branchCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchParameterEntity;
    }

    public void save (BranchParameterDTO branchParameterDTO) {
        try {
            BranchParameterEntity branchParameterEntity = branchParameterAssembler.convertDtoToEntity(
                    branchParameterDTO);
            this.branchParameterRepository.save(branchParameterEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

package com.idg.idgcore.coe.domain.service.branchSystem;

import com.idg.idgcore.coe.domain.assembler.branchSystem.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystem.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.repository.branchSystem.IBranchSystemDateRepository;
import com.idg.idgcore.coe.dto.branchSystem.BranchSystemDateDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchSystemDateDomainService implements IBranchSystemDateDomainService{

    @Autowired
    private IBranchSystemDateRepository branchSystemRepository;
    @Autowired
    private BranchSystemDateAssembler branchSystemAssembler;

    public BranchSystemDateEntity getConfigurationByCode (BranchSystemDateDTO branchSystemDateDTO) {
        BranchSystemDateEntity branchSystemDateEntity = null;
        try {
            branchSystemDateEntity = this.branchSystemRepository.findByBranchCode(branchSystemDateDTO.getBranchCode());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchSystemDateEntity;
    }

    public List<BranchSystemDateEntity> getBranchSystemDateAll () {
        return this.branchSystemRepository.findAll();
    }

    public BranchSystemDateEntity getBranchSystemDateByCode (String branchCode) {
        BranchSystemDateEntity branchSystemDateEntity = null;
        try {
            branchSystemDateEntity = this.branchSystemRepository.findByBranchCode(branchCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchSystemDateEntity;
    }

    public void save (BranchSystemDateDTO branchSystemDateDTO) {
        try {
            BranchSystemDateEntity branchSystemDateEntity = branchSystemAssembler.convertDtoToEntity(branchSystemDateDTO);
            this.branchSystemRepository.save(branchSystemDateEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

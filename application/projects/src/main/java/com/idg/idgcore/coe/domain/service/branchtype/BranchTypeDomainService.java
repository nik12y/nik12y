package com.idg.idgcore.coe.domain.service.branchtype;

import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.repository.branchtype.IBranchTypeRepository;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchTypeDomainService implements IBranchTypeDomainService {


    @Autowired
    private IBranchTypeRepository branchTypeRepository;

    @Autowired
    private BranchTypeAssembler branchtypeAssembler;


    @Override
    public BranchTypeEntity getConfigurationByBranchTypeCode(BranchTypeDTO branchtypeDTO) {
        BranchTypeEntity branchTypeEntity = null;
        try {
            branchTypeEntity = this.branchTypeRepository.findByBranchTypeCode(branchtypeDTO.getBranchTypeCode());
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }

        return branchTypeEntity;
    }

    public List<BranchTypeEntity> getBranches() {
        return this.branchTypeRepository.findAll();
    }

    @Override
    public BranchTypeEntity getBranchTypeByCode(String BranchTypeCode) {
        BranchTypeEntity branchTypeEntity = null;
        try {
            branchTypeEntity = this.branchTypeRepository.findByBranchTypeCode(BranchTypeCode);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchTypeEntity;


    }


    public void save(BranchTypeDTO branchTypeDTO) {
        try {
            BranchTypeEntity branchTypeEntity = branchtypeAssembler.convertDtoToEntity(branchTypeDTO);
            this.branchTypeRepository.save(branchTypeEntity);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


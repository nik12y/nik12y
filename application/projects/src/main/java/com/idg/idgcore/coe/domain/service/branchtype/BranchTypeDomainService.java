package com.idg.idgcore.coe.domain.service.branchtype;

import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.repository.branchtype.IBranchTypeRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchTypeDomainService extends DomainService<BranchTypeDTO, BranchTypeEntity> {

    @Autowired
    private IBranchTypeRepository branchTypeRepository;

    @Autowired
    private BranchTypeAssembler branchtypeAssembler;

    @Override
    public BranchTypeEntity getEntityByIdentifier(String BranchTypeCode) {
        BranchTypeEntity branchTypeEntity = null;
        try {
            branchTypeEntity = this.branchTypeRepository.findByBranchTypeCode(BranchTypeCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchTypeEntity;
    }

    @Override
    public List<BranchTypeEntity> getAllEntities() {
        return this.branchTypeRepository.findAll();
    }

    public void save(BranchTypeDTO branchTypeDTO) {
        try {
            BranchTypeEntity branchTypeEntity = branchtypeAssembler.toEntity(branchTypeDTO);
            this.branchTypeRepository.save(branchTypeEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


package com.idg.idgcore.coe.domain.service.branchSystemDate;

import com.idg.idgcore.coe.domain.assembler.branchSystemDate.BranchSystemDateAssembler;
import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.repository.branchSystemDate.IBranchSystemDateRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchSystemDateDomainService extends DomainService<BranchSystemDateDTO, BranchSystemDateEntity> {

    @Autowired
    private IBranchSystemDateRepository branchSystemRepository;
    @Autowired
    private BranchSystemDateAssembler branchSystemAssembler;

    @Override
    public BranchSystemDateEntity getEntityByIdentifier(String branchCode) {
        BranchSystemDateEntity branchSystemDateEntity = null;
        try {
            branchSystemDateEntity = this.branchSystemRepository.findByBranchCode(branchCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchSystemDateEntity;
    }

    @Override
    public List<BranchSystemDateEntity> getAllEntities() {
        return this.branchSystemRepository.findAll();
    }

    public void save (BranchSystemDateDTO branchSystemDateDTO) {
        try {
            BranchSystemDateEntity branchSystemDateEntity = branchSystemAssembler.toEntity(branchSystemDateDTO);
            this.branchSystemRepository.save(branchSystemDateEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

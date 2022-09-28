package com.idg.idgcore.coe.domain.service.branchparameter;

import com.idg.idgcore.coe.domain.assembler.branchparameter.BranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.branchparameter.BranchParameterEntity;
import com.idg.idgcore.coe.domain.repository.branchparameter.IBranchParameterRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class BranchParameterDomainService extends DomainService<BranchParameterDTO, BranchParameterEntity> {

    @Autowired
    private IBranchParameterRepository branchParameterRepository;

    @Autowired
    private BranchParameterAssembler branchParameterAssembler;

    @Override
    public BranchParameterEntity getEntityByIdentifier(String branchCode) {
        BranchParameterEntity branchParameterEntity = null;
        try {
            branchParameterEntity = this.branchParameterRepository.findByBranchCode(branchCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return branchParameterEntity;
    }

    @Override
    public List<BranchParameterEntity> getAllEntities() {
        return this.branchParameterRepository.findAll();
    }

    public void save (BranchParameterDTO branchParameterDTO) {
        try {
            BranchParameterEntity branchParameterEntity = branchParameterAssembler.toEntity(
                    branchParameterDTO);
            this.branchParameterRepository.save(branchParameterEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

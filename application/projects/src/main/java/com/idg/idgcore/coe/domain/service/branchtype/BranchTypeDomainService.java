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

//        public BranchTypeEntity getConfigurationByBranchType (BranchTypeDTO branchTypeDTO) {
//            return this.branchTypeRepository.findByBranchTypeCode(branchTypeDTO.getBranchTypeCode());
//        }
//
//        public List<BranchTypeEntity> getBranches() {
//            return this.branchTypeRepository.findAll();
//        }
//
//        public BranchTypeEntity getbranchTypeByCode (String branchtypeCode) {
//            return this.branchTypeRepository.findByBranchTypeCode(branchtypeCode);
//        }
//
//    @Override
//    public BranchTypeEntity getConfigurationByBranchTypeCode(BranchTypeDTO branchtypeDTO) {
//        return null;
//    }
//
//
//    @Override
//    public BranchTypeEntity getBranchTypeByCode(String BranchTypeCode) {
//        return this.branchTypeRepository.findByBranchTypeCode(BranchTypeCode);
//    }
//
//    public void save (BranchTypeDTO branchTypeDTO) {
//            BranchTypeEntity branchtypeEntity =branchtypeAssembler.convertDtoToEntity(branchTypeDTO);
//            this.branchTypeRepository.save(branchtypeEntity);
//        }
//    }

//    public BranchTypeEntity getConfigurationByCode(BranchTypeDTO branchTypeDTO) {
//        BranchTypeEntity branchTypeEntity = null;
//        try {
//            branchTypeEntity = this.branchTypeRepository.findByBranchTypeCode(branchTypeDTO.getBranchTypeCode());
//        } catch (Exception e) {
//            if (log.isErrorEnabled()) {
//                log.error(e.getMessage());
//            }
//            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
//        }
//        return branchTypeEntity;
//    }

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

//    public CountryEntity getCountryByCode(String countryCode) {
//        CountryEntity countryEntity = null;
//        try {
//            countryEntity = this.branchTypeRepository.findByBranchTypeCode(branchTypeCode);
//        } catch (Exception e) {
//            if (log.isErrorEnabled()) {
//                log.error(e.getMessage());
//            }
//            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
//        }
//        return countryEntity;
//    }

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


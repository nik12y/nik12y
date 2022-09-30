package com.idg.idgcore.coe.domain.service.mulbranchparameter;


import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.mulbranchparameter.IMulBranchParameterRepository;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.FIELD_SEPARATOR;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service
public class MulBranchParameterDomainService extends DomainService<MulBranchParameterDTO, MulBranchParameterEntity> {
    @Autowired
    private IMulBranchParameterRepository mulBranchParameterRepository;

    @Autowired
    private MulBranchParameterAssembler mulBranchParameterAssembler;

    @Override
    public MulBranchParameterEntity getEntityByIdentifier(String identifier) {
        MulBranchParameterEntity mulBranchParameterEntity=null;
        try {
            String[] fields = identifier.split(FIELD_SEPARATOR);
                    if (fields.length == 3)
                    {
                        mulBranchParameterEntity=mulBranchParameterRepository.getByCurrencyCodeAndEntityCodeAndEntityType(
                                fields[0], fields[1], fields[2]);
                    }

             } catch (Exception e) {
            log.error("Exception in getEntityByIdentifier",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
           return mulBranchParameterEntity;
    }


        @Override
    public List<MulBranchParameterEntity> getAllEntities() {
            return this.mulBranchParameterRepository.findAll();
    }

    public void save (MulBranchParameterDTO mulBranchParameterDTO) {
        try{
        MulBranchParameterEntity mulBranchParameterEntity= mulBranchParameterAssembler.toEntity(mulBranchParameterDTO);
        this.mulBranchParameterRepository.save(mulBranchParameterEntity);
        } catch (Exception e) {
            log.error("Exception in Save",e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}



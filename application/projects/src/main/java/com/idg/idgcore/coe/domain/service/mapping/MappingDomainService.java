package com.idg.idgcore.coe.domain.service.mapping;

import com.idg.idgcore.coe.domain.entity.mapping.MappingEntity;
import com.idg.idgcore.coe.domain.repository.mapping.IMappingRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class MappingDomainService implements IMappingDomainService {
    @Autowired
    private IMappingRepository mappingRepository;

    @Override
    public List<MappingEntity> getAllMappings () {
        return mappingRepository.findAll();
    }

    @Override
    public MappingEntity getMappingByCrietria (String action, String role, String status) {
        MappingEntity mappingEntity = null;
        try {
            mappingEntity = mappingRepository.findByActionAndRoleAndStatus(action, role, status);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mappingEntity;
    }

}


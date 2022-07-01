package com.idg.idgcore.domain.service;

import com.idg.idgcore.app.dto.MutationDTO;
import com.idg.idgcore.domain.entity.MutationEntity;
import com.idg.idgcore.domain.entity.AuditHistoryEntity;
import com.idg.idgcore.domain.repository.IMutationRepository;
import com.idg.idgcore.domain.repository.IAuditHistoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;

@Slf4j
@Service
public class MutationsDomainService implements IMutationsDomainService {
    @Autowired
    private IMutationRepository mutationRepository;
    @Autowired
    private IAuditHistoryRepository auditHistoryRepository;

    public MutationEntity getConfigurationByCode (final String taskIdentifier) {
        return mutationRepository.findByTaskIdentifier(taskIdentifier);
    }

    public MutationEntity findByTaskCode (final String taskCode) {
        try {
            log.info("MutationsDomainService create : taskCode {}", taskCode);
            return this.mutationRepository.findByTaskCode(taskCode);
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public MutationEntity  addUpdate (MutationDTO mutationDTO) {
        try {
            log.info("MutationsDomainService create : mutationDTO {}", mutationDTO);
            ModelMapper mapper = new ModelMapper();
            MutationEntity mutationEntity = mapper.map(mutationDTO, MutationEntity.class);
            mutationEntity = this.mutationRepository.save(mutationEntity);
            return mutationEntity;
        }
        catch (Exception e) {
            log.info(e.getMessage());
            return null;
        }
    }

    public void insertIntoAuditHistory (final MutationDTO mutationDTO) {
        try {
            log.info(" In insertIntoAuditHistory ");
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            AuditHistoryEntity auditHistoryEntity = mapper.map(mutationDTO,
                    AuditHistoryEntity.class);
            this.auditHistoryRepository.save(auditHistoryEntity);
        }
        catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public List<MutationEntity> getUnauthorizedMutation (final String taskCode) {
        log.info("MutationsDomainService create : getConfigurationByAuthorized {}", taskCode);
        String authorized = "N";
        String status = "DELETED";
        return this.mutationRepository.findByTaskCodeAndAuthorizedAndStatusNot(taskCode, authorized,
                status);
    }

}

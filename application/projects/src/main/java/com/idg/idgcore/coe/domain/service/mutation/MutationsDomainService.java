package com.idg.idgcore.coe.domain.service.mutation;

import com.idg.idgcore.coe.dto.mutation.MutationDTO;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.repository.mutation.IMutationRepository;
import com.idg.idgcore.coe.domain.repository.audit.IAuditHistoryRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import static com.idg.idgcore.coe.common.Constants.ADD;
import static com.idg.idgcore.coe.common.Constants.AUTHORIZE;
import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.DRAFT;
import static com.idg.idgcore.coe.common.Constants.MODIFY;
import static com.idg.idgcore.coe.common.Constants.NEW;
import static com.idg.idgcore.coe.common.Constants.UPDATED;
import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;
import static com.idg.idgcore.coe.exception.Error.DUPLICATE_RECORD;
import static com.idg.idgcore.coe.exception.Error.UNAUTHORIZED_RECORD_ALREADY_EXISTS;
import static com.idg.idgcore.coe.exception.Error.VALIDATION_FAILED;

@Slf4j
@Service
public class MutationsDomainService implements IMutationsDomainService {
    @Autowired
    private IMutationRepository mutationRepository;
    @Autowired
    private IAuditHistoryRepository auditHistoryRepository;

    public MutationEntity getConfigurationByCode (final String taskIdentifier) {
        if (log.isInfoEnabled()) {
            log.info("In getConfigurationByCode with parameters taskIdentifier {}", taskIdentifier);
        }
        MutationEntity mutationEntity = null;
        try {
            mutationEntity = mutationRepository.findByTaskIdentifier(taskIdentifier);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mutationEntity;
    }

    public MutationEntity findByTaskCode (final String taskCode) {
        if (log.isInfoEnabled()) {
            log.info("In findByTaskCode with parameters taskCode {}", taskCode);
        }
        MutationEntity mutationEntity = null;
        try {
            mutationEntity = this.mutationRepository.findByTaskCode(taskCode);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return mutationEntity;
    }

    public MutationEntity addUpdate (MutationDTO mutationDTO) {
        if (log.isInfoEnabled()) {
            log.info("In addUpdate with parameters  mutationDTO {}", mutationDTO);
        }
        MutationEntity mutationEntity = null;
        try {
            if (validateMutation(mutationDTO)) {
                ModelMapper mapper = new ModelMapper();
                mutationEntity = mapper.map(mutationDTO, MutationEntity.class);
                return this.mutationRepository.save(mutationEntity);
            }
            else {
                ExceptionUtil.handleException(VALIDATION_FAILED);
            }
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            if (e instanceof BusinessException) {
                throw e;
            }
            else {
                ExceptionUtil.handleException(DATA_ACCESS_ERROR);
            }
        }
        return mutationEntity;
    }

    public MutationEntity fetchRecordIfExists (MutationDTO mutationDTO) throws BusinessException {
        if (log.isInfoEnabled()) {
            log.info("In recordExists with parameters  mutationDTO {}", mutationDTO);
        }
        return mutationRepository.findByTaskCodeAndTaskIdentifier(mutationDTO.getTaskCode(),
                mutationDTO.getTaskIdentifier());
    }

    public void insertIntoAuditHistory (final MutationDTO mutationDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("In insertIntoAuditHistory with parameters mutationDTO {}", mutationDTO);
            }
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
            AuditHistoryEntity auditHistoryEntity = mapper.map(mutationDTO,
                    AuditHistoryEntity.class);
            this.auditHistoryRepository.save(auditHistoryEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }

    public List<MutationEntity> getUnauthorizedMutation (final String taskCode,final String authorized) {
        if (log.isInfoEnabled()) {
            log.info("In getUnauthorizedMutation with parameters taskCode{}", taskCode);
        }
        return this.mutationRepository.findByTaskCodeAndAuthorized(taskCode,authorized);
    }

    public boolean validateMutation (MutationDTO dto) throws BusinessException {
        MutationEntity entity = fetchRecordIfExists(dto);
        if (Objects.nonNull(entity) && !validateDraft(entity, dto)) {
            recordExistsFilter(entity, dto);
            validateUnauthorizedRecords(entity, dto);
            validateDuplicateRecords(entity, dto);
        }
        return Boolean.TRUE;
    }

    private Predicate<MutationDTO> isAddDraft () {
        return d -> (d.getAction().equals(DRAFT) && d.getStatus().equals(DRAFT));
    }

    private Predicate<MutationDTO> isModifyDraft () {
        return d -> (d.getAction().equals(DRAFT) && d.getStatus().equals(UPDATED));
    }

    private Predicate<MutationDTO> isModify () {
        return d -> (d.getAction().equals(MODIFY) && d.getStatus().equals(UPDATED));
    }

    private Predicate<MutationDTO> isNew () {
        return d -> (d.getAction().equals(ADD) && d.getStatus().equals(NEW));
    }

    private void recordExistsFilter (MutationEntity mutationEntity, MutationDTO mutationDTO)
            throws BusinessException {
        if (mutationDTO.getTaskCode().equals(mutationEntity.getTaskCode())
                && mutationDTO.getTaskIdentifier().equals(mutationEntity.getTaskIdentifier())
                && mutationDTO.getAction().equals(mutationEntity.getAction())
                && mutationDTO.getStatus().equals(mutationEntity.getStatus())) {
            ExceptionUtil.handleException(UNAUTHORIZED_RECORD_ALREADY_EXISTS);
        }
    }

    private boolean validateDraft (MutationEntity entity, MutationDTO dto)
            throws BusinessException {
        return (validateAddActionDraft(entity, dto) || validateModifyActionDraft(entity, dto));
    }

    private void validateUnauthorizedRecords (MutationEntity entity, MutationDTO dto)
            throws BusinessException {
        if (AUTHORIZED_N.equals(entity.getAuthorized()) && !AUTHORIZE.equals(dto.getAction())) {
            ExceptionUtil.handleException(UNAUTHORIZED_RECORD_ALREADY_EXISTS);
        }
    }

    private void validateDuplicateRecords (MutationEntity entity, MutationDTO dto)
            throws BusinessException {
        if ((isAddDraft().test(dto) && !DRAFT.equals(entity.getStatus())) || (isNew().test(dto)
                && !DRAFT.equals(entity.getStatus())) || (isModifyDraft().test(dto) && (
                MODIFY.equals(entity.getAction()) && UPDATED.equals(entity.getStatus())))) {
            ExceptionUtil.handleException(DUPLICATE_RECORD);
        }
    }

    private boolean validateAddActionDraft (MutationEntity entity, MutationDTO dto)
            throws BusinessException {
        return (isAddDraft().test(dto) && DRAFT.equals(entity.getAction())) || (isNew().test(dto)
                && DRAFT.equals(entity.getAction()));
    }

    private boolean validateModifyActionDraft (MutationEntity entity, MutationDTO dto)
            throws BusinessException {
        return (isModifyDraft().test(dto) && (DRAFT.equals(entity.getAction()) && UPDATED.equals(
                entity.getAction()))) || (isModify().test(dto) && (DRAFT.equals(entity.getAction())
                && UPDATED.equals(entity.getStatus())));
    }

}

package com.idg.idgcore.coe.domain.service.memo;

import com.idg.idgcore.coe.dto.memo.MemoDTO;
import com.idg.idgcore.coe.domain.assembler.memo.MemoAssembler;
import com.idg.idgcore.coe.domain.entity.memo.MemoEntity;
import com.idg.idgcore.coe.domain.repository.memo.IMemoRepository;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class MemoDomainService implements IMemoDomainService {
    @Autowired
    private IMemoRepository memoRepository;
    @Autowired
    private MemoAssembler memoAssembler;

    @Override
    public MemoEntity getMemoByRefAndVersion (String referenceNo, Integer recordVersion) {
        return this.memoRepository.findByReferenceNoAndRecordVersion(referenceNo, recordVersion);
    }

    @Override
    public MemoEntity getMemoByTaskCodeAndVersion (String moduleId, String taskCode,
                                                   String taskIdentifier, Integer recordVersion) {
        MemoEntity memoEntity = null;
        try {
            memoEntity = this.memoRepository.findByModuleIdAndTaskCodeAndTaskIdentifierAndRecordVersion(
                    moduleId, taskCode, taskIdentifier, recordVersion);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return memoEntity;
    }

    @Override
    public void save (MemoDTO memoDTO) {
        MemoEntity memoEntity = memoAssembler.convertDtoToEntity(memoDTO);
        this.memoRepository.save(memoEntity);
    }

}

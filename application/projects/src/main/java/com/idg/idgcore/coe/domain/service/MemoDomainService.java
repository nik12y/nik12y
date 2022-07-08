package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.dto.MemoDTO;
import com.idg.idgcore.coe.domain.assembler.MemoAssembler;
import com.idg.idgcore.coe.domain.entity.MemoEntity;
import com.idg.idgcore.coe.domain.repository.IMemoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return this.memoRepository.findByModuleIdAndTaskCodeAndTaskIdentifierAndRecordVersion(
                moduleId, taskCode, taskIdentifier, recordVersion);
    }

    @Override
    public void save (MemoDTO memoDTO) {
        MemoEntity memoEntity = memoAssembler.convertDtoToEntity(memoDTO);
        this.memoRepository.save(memoEntity);
    }

}

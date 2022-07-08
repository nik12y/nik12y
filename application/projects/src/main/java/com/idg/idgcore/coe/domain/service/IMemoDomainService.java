package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.domain.entity.MemoEntity;
import com.idg.idgcore.coe.dto.MemoDTO;

public interface IMemoDomainService {
    MemoEntity getMemoByRefAndVersion (String referenceNo, Integer recordVersion);
    MemoEntity getMemoByTaskCodeAndVersion (String moduleId, String taskCode, String taskIdentifier,
                                            Integer recordVersion);
    void save (MemoDTO memoDTO);

}
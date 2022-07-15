package com.idg.idgcore.coe.domain.service.memo;

import com.idg.idgcore.coe.domain.entity.memo.MemoEntity;
import com.idg.idgcore.coe.dto.memo.MemoDTO;

public interface IMemoDomainService {
    MemoEntity getMemoByRefAndVersion (String referenceNo, Integer recordVersion);
    MemoEntity getMemoByTaskCodeAndVersion (String moduleId, String taskCode, String taskIdentifier,
                                            Integer recordVersion);
    void save (MemoDTO memoDTO);

}
package com.idg.idgcore.coe.app.service.memo;

import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.domain.entity.memo.MemoEntity;
import com.idg.idgcore.coe.domain.service.memo.IMemoDomainService;
import com.idg.idgcore.coe.dto.memo.MemoDTO;
import com.idg.idgcore.coe.exception.Error;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service ("memoApplicationService")
public class MemoApplicationService extends AbstractApplicationService
        implements IMemoApplicationService
{
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private IMemoDomainService memoDomainService;

    @Override
    public MemoDTO getMemo (SessionContext sessionContext, MemoDTO memoDTO) throws FatalException {
        log.info("In getMemo with parameters sessionContext {}, memoDTO {}", sessionContext,
                memoDTO);
        MemoEntity memoEntity = null;
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        try {
            if (Objects.nonNull(memoDTO.getReferenceNo()) && !memoDTO.getReferenceNo().isEmpty()) {
                memoEntity = memoDomainService.getMemoByRefAndVersion(memoDTO.getReferenceNo(),
                        memoDTO.getRecordVersion());
            }
            else {
                memoEntity = memoDomainService.getMemoByTaskCodeAndVersion(memoDTO.getModuleId(),
                        memoDTO.getTaskCode(), memoDTO.getTaskIdentifier(),
                        memoDTO.getRecordVersion());
            }
            return mapper.map(memoEntity, MemoDTO.class);
        }
        catch (Exception exception) {
            BusinessException be = new BusinessException(Error.NO_RECORD_FOUND.getCode(),
                    Error.NO_RECORD_FOUND.getMessage());
            fillTransactionStatus(transactionStatus, be);
            throw be;
        }
        finally {
            Interaction.close();
        }
    }

    @Override
    public void save (MemoDTO memoDTO) {
        memoDomainService.save(memoDTO);
    }

    @Transactional
    public TransactionStatus processMemo (SessionContext sessionContext, MemoDTO memoDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processMemo with parameters sessionContext {}, memoDTO {}", sessionContext,
                    memoDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            memoDomainService.save(memoDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

}

package com.idg.idgcore.coe.app.service;

import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.domain.entity.*;
import com.idg.idgcore.coe.domain.service.*;
import com.idg.idgcore.coe.dto.*;
import com.idg.idgcore.domain.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.*;

@Slf4j
@Service ("memoApplicationService")
public class MemoApplicationService  extends AbstractApplicationService implements IMemoApplicationService  {
    private final ModelMapper mapper = new ModelMapper();
    @Autowired
    private IMemoDomainService memoDomainService;

    @Override
    public MemoDTO getMemo(SessionContext sessionContext, MemoDTO memoDTO) {
        log.info("In getMemoByRefAndVersion with parameters sessionContext {}, memoDTO {}",
                sessionContext, memoDTO);

        AbstractDomainEntity memoEntity = MemoEntity.builder().build();

        if(Objects.nonNull(memoDTO.getReferenceNo()) && !memoDTO.getReferenceNo().isEmpty())
        {
             memoEntity = memoDomainService.getMemoByRefAndVersion(memoDTO.getReferenceNo(),
                    memoDTO.getRecordVersion());
        }
        else {
             memoEntity = memoDomainService.getMemoByTaskCodeAndVersion(memoDTO.getModuleId(),
                    memoDTO.getTaskCode(),
                    memoDTO.getTaskIdentifier(),
                    memoDTO.getRecordVersion());
        }
        return mapper.map(memoEntity, MemoDTO.class);
    }

    @Override
    public void save (MemoDTO memoDTO) {
        memoDomainService.save(memoDTO);
    }

    @Transactional
    public TransactionStatus processMemo (SessionContext sessionContext, MemoDTO memoDTO)
            throws FatalException {
        log.info("In processCountry with parameters sessionContext {}, memoDTO {}",
                sessionContext, memoDTO);
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            memoDomainService.save(memoDTO);
            fillTransactionStatus(transactionStatus);
            registerInteractionForClosure();
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

    private void registerInteractionForClosure () {
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            public void beforeCommit () {
                try {
                    Interaction.close();
                }
                catch (FatalException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
    private String generateReferenceNumber () {
        return UUID.randomUUID().toString();
    }

}

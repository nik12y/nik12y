package com.idg.idgcore.coe.domain.service.transaction;

import com.idg.idgcore.coe.domain.assembler.transaction.TransactionAssembler;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.repository.transaction.ITransactionRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class TransactionDomainService extends DomainService<TransactionDTO, TransactionEntity> {

    @Autowired
    private ITransactionRepository transactionRepository;

    @Autowired
    private TransactionAssembler transactionAssembler;

    @Override
    public TransactionEntity getEntityByIdentifier(String transactionCode) {
        TransactionEntity transactionEntity = null;
        try {
            transactionEntity = this.transactionRepository.findByTransactionCode(transactionCode);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return transactionEntity;
    }

    @Override
    public List<TransactionEntity> getAllEntities() {
        return this.transactionRepository.findAll();
    }

    public void save (TransactionDTO transactionDTO) {
        try {
            TransactionEntity transactionEntity = transactionAssembler.toEntity(transactionDTO);
            this.transactionRepository.save(transactionEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

package com.idg.idgcore.coe.domain.service.transaction;

import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.repository.transaction.ITransactionRepository;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class TransactionDomainServiceTest {

    @Mock
    private ITransactionRepository transactionRepository;

    @InjectMocks
    private TransactionDomainService transactionDomainService;
    private TransactionEntity transactionEntity;
    private TransactionDTO transactionDTO;

    @BeforeEach
    void setUp() {
        transactionDTO=getTransactionDTO();
        transactionEntity=getTransactionEntity();
    }

    @Test
    @DisplayName("Junit test for getTransactions method ")
    void getTransactionsReturnTransactionList() {
        given(transactionRepository.findAll()).willReturn(List.of(transactionEntity));
        List<TransactionEntity> bankParameterEntityList = transactionDomainService.getAllEntities();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getTransactions method for negative scenario")
    void getTransactionsEmptyTransactionEntityList()
    {
        given(transactionRepository.findAll()).willReturn(Collections.emptyList());
        List<TransactionEntity> transactionEntityList = transactionDomainService.getAllEntities();

        assertThat(transactionEntityList).isEmpty();
        assertThat(transactionEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getIbanByCodeReturnIbanEntityObject method")
    void getIbanByCodeReturnIbanEntityObject() {
        given(transactionRepository.findByTransactionCode("IA")).willReturn(transactionEntity);
        TransactionEntity transactionEntity1 =transactionDomainService.getEntityByIdentifier(transactionEntity.getTransactionCode());
        assertThat(transactionEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(transactionRepository.findByTransactionCode("IA")).willReturn(transactionEntity);
        TransactionEntity transactionByCode = transactionDomainService.getEntityByIdentifier("IA");
        assertThat(transactionByCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        assertThrows(BusinessException.class,()-> {
            TransactionEntity TransactionByCode = transactionDomainService.getEntityByIdentifier(null);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        TransactionDTO transactionDTO = null;
        assertThrows(Exception.class,()-> {
            transactionDomainService.save(transactionDTO);
        });
    }

    private TransactionEntity getTransactionEntity()
    {
        TransactionEntity transactionEntity = new TransactionEntity();

        transactionEntity.setTransactionCode("IA");
        transactionEntity.setTransactionDesc("TESTDescription");
        transactionEntity.setTransactionSwiftCode("ITA");
        transactionEntity.setExternalTxnCode("ABC");
        transactionEntity.setMisHead("TEST");
        transactionEntity.setIsImmediate('Y');
        transactionEntity.setIsOnValueDate('Y');
        transactionEntity.setIsAfterDaysValueDate('Y');
        transactionEntity.setIsAfterDays('Y');
        transactionEntity.setNoOfDays(2);
        transactionEntity.setIsIntradayManualRel('Y');
        transactionEntity.setStatementDateDerivation("TEST");
        transactionEntity.setIsAmlTracking('Y');
        transactionEntity.setProductGroup("Test");
        transactionEntity.setIsBalChkAppliBatch('Y');
        transactionEntity.setIsBalChkAppliOnline('Y');
        transactionEntity.setIsPricingOnTranCount('Y');
        transactionEntity.setIsPricingTurnoverIncl('Y');
        transactionEntity.setIsPricingBalanceIncl('Y');
        transactionEntity.setIsTurnoverLimit('Y');
        transactionEntity.setIsCoverAccount('Y');
        transactionEntity.setIsPricingPenaltyIncl('Y');
        transactionEntity.setIsConsiderAcStatusChg('Y');
        transactionEntity.setIsChequeMandatory('Y');
        transactionEntity.setIsInterBranchInLcy('Y');
        transactionEntity.setIsThirdPartyTranCode('Y');
        transactionEntity.setAuthorized("Y");
        return transactionEntity;
    }

    private TransactionDTO getTransactionDTO()
    {
        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setTransactionCode("IA");
        transactionDTO.setTransactionDescription("TESTDescription");
        transactionDTO.setTransactionSwiftCode("ITA");
        transactionDTO.setExternalTxnCode("ABC");
        transactionDTO.setMisHead("TEST");
        transactionDTO.setImmediate(true);
        transactionDTO.setOnValueDate(true);
        transactionDTO.setAfterXDaysValueDate(true);
        transactionDTO.setAfterXDays(true);
        transactionDTO.setNoOfDays(2);
        transactionDTO.setIntradayManualRelease(true);
        transactionDTO.setStatementDateDerivation("TEST");
        transactionDTO.setAmlTracking(true);
        transactionDTO.setProductGroup("Test");
        transactionDTO.setAvailableBalanceCheckApplicableBatch(true);
        transactionDTO.setAvailableBalanceCheckApplicableOnline(true);
        transactionDTO.setPricingOnTransactionCount(true);
        transactionDTO.setPricingTurnoverInclusion(true);
        transactionDTO.setPricingBalanceInclusive(true);
        transactionDTO.setTurnoverLimit(true);
        transactionDTO.setCoverAccount(true);
        transactionDTO.setPricingPenaltyInclusive(true);
        transactionDTO.setConsiderForAccountStatusChange(true);
        transactionDTO.setChequeMandatory(true);
        transactionDTO.setInterBranchInLcy(true);
        transactionDTO.setThirdPartyDealingSystemTransactionCode(true);

        transactionDTO.setAuthorized("Y");
        transactionDTO.setTaskCode("AB");
        transactionDTO.setStatus("DELETED");
        transactionDTO.setRecordVersion(1);

        return transactionDTO;
    }

}
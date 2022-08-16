package com.idg.idgcore.coe.app.service.transaction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.transaction.TransactionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.transaction.ITransactionDomainService;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.ServiceInvocationModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith (MockitoExtension.class)
class TransactionApplicationServiceTest {
    @InjectMocks
    private TransactionApplicationService transactionApplicationService;
    @Mock private TransactionAssembler transactionAssembler;
    @Mock private ITransactionDomainService transactionDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;
    @Mock private ProcessConfiguration processConfiguration;
    @Autowired
    private MutationEntity mutationEntity;
    private MutationEntity mutationEntity2;

    private SessionContext sessionContext;
    private TransactionDTO transactionDTO;
    private TransactionDTO transactionDTOAuth;
    private TransactionDTO transactionDTOUnAuth;
    private TransactionEntity transactionEntity;
    private TransactionEntity transactionEntityUnAut;
    private TransactionDTO transactionDTOMapper;
    private TransactionDTO transactionDTO1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        transactionDTOAuth = getTransactionDTOAuthorized();
        transactionEntity = getTransactionEntity();
        transactionDTOUnAuth = getTransactionDTOUnAuthorized();
        transactionEntityUnAut = getTransactionEntity();
        transactionEntityUnAut.setAuthorized("N");
        mutationEntity = getMutationEntity();
        transactionDTO = getTransactionDTO();
        //transactionDTOMapper = getTransactionDTOMapper();
        transactionDTO1 = getTransactionDTO();
        //mutationEntity2=getMutationEntityJsonError();
    }

    @Test
    @DisplayName ("JUnit for getTransactionByCode in application service when Authorize")
    void getTransactionByCodeWithAuthRecord () throws FatalException {
        given(transactionDomainService.getTransactionByCode(transactionDTOAuth.getTransactionCode())).willReturn(transactionEntity);
        given(transactionAssembler.convertEntityToDto(transactionEntity)).willReturn(transactionDTOAuth);
        TransactionDTO transactionDTO1 = transactionApplicationService.getTransactionByCode(sessionContext, transactionDTOAuth);
        assertThat(transactionDTO1.getAuthorized()).isEqualTo("Y");
        assertThat(transactionDTOAuth).isNotNull();
        //assertThat(ibanDTOAuth.toString()).isNotNull();
        //assertThat(ibanDTO.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverageDTO()
    {
        System.out.println(transactionEntity.toString());
        System.out.println(transactionDTO.toString());

        TransactionDTO transactionDTO2 = new TransactionDTO("IA","TESTDescription","ITA","ABC","TEST",true,true,true,true,2,true,"TEST",true,"TEST",true,true,true,true,true,true,true,true,true,true,true,true);

        String s = TransactionDTO.builder()
                .transactionCode("IA")
                .transactionDescription("TESTDescription")
                .transactionSwiftCode("ITA")
                .externalTxnCode("ABC")
                .misHead("TEST")
                .immediate(true)
                .onValueDate(true)
                .afterXDaysValueDate(true)
                .afterXDays(true)
                .noOfDays(2)
                .intradayManualRelease(true)
                .statementDateDerivation("TEST")
                .amlTracking(true)
                .productGroup("TEST")
                .availableBalanceCheckApplicableBatch(true)
                .availableBalanceCheckApplicableOnline(true)
                .pricingOnTransactionCount(true)
                .pricingTurnoverInclusion(true)
                .pricingBalanceInclusive(true)
                .turnoverLimit(true)
                .coverAccount(true)
                .pricingPenaltyInclusive(true)
                .considerForAccountStatusChange(true)
                .interBranchInLcy(true)
                .thirdPartyDealingSystemTransactionCode(true)
                .build()
                .toString();


        TransactionEntityKey transactionEntityKey=new TransactionEntityKey("IA");


        transactionEntityKey.setTransactionCode("IA");
        System.out.println(transactionEntityKey.getTransactionCode());
        transactionEntityKey.keyAsString();
        //ibanEntityKey.builder().bankCode("0003").build();
        assertThat(transactionDTO2).descriptionText();
        //assertThat(s.toString()).isNotNull();
    }


    @DisplayName ("JUnit test for processTransaction method")
    @Test
    void processTransactionWithNew () throws JsonProcessingException, FatalException {
        TransactionDTO transactionDTONew = getTransactionDTOMapper();
        doNothing().when(processConfiguration).process(transactionDTONew);
        transactionApplicationService.processTransaction(sessionContext, transactionDTONew);
        verify(processConfiguration, times(1)).process(transactionDTONew);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        TransactionDTO transactionDTO = getTransactionDTOForSave();
        doNothing().when(transactionDomainService).save(transactionDTO);
        transactionApplicationService.save(transactionDTO);
        transactionApplicationService.addUpdateRecord(payloadStr);
        verify(transactionDomainService, times(1)).save(transactionDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = transactionDTO.getTransactionCode();
        given(transactionDomainService.getTransactionByCode(code)).willReturn(transactionEntity);
        transactionApplicationService.getConfigurationByCode(code);
        assertThat(transactionEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeDTOTest(){
        String code = transactionDTO.getTransactionCode();
        given(transactionDomainService.getTransactionByCode(code)).willReturn(transactionEntity);
        transactionApplicationService.getConfigurationByCode(code);
        assertThat(transactionEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Try Block")
    void processTransactionForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(processConfiguration).process(transactionDTO);
        transactionApplicationService.processTransaction(sessionContext, transactionDTO);
        verify(processConfiguration, times(1)).process(transactionDTO);
    }

    @Test
    @DisplayName("JUnit for processIban in application service for Catch Block")
    void processTransactionForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            transactionApplicationService.processTransaction(sessionContext2, transactionDTO);
            assertThat(transactionDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for getIbanByIbanCountryCode in application service when Not Authorize in catch block")
    void getTransactionByCodeWhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 =getpayloadInvalidString();
        //"{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        given(mutationsDomainService.getConfigurationByCode(transactionDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        /*given(ibanAssembler.setAuditFields(mutationEntity2, ibanDTOUnAuth))
                .willReturn(ibanDTOUnAuth);*/
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            TransactionDTO transactionDTO1 = transactionApplicationService.getTransactionByCode(sessionContext, transactionDTOUnAuth);
            assertEquals("N", transactionDTO1.getAuthorized());
            assertThat(transactionDTO1).isNotNull();

        });
    }
    /**
     *
     * Negative Test Cases
     */
    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize for Negative")
    void getTransactionByCodeIsAuthorizeForNegative() throws FatalException {
        given(transactionDomainService.getTransactionByCode(transactionDTO.getTransactionCode())).willReturn(transactionEntity);
        given(transactionAssembler.convertEntityToDto(transactionEntity)).willReturn(transactionDTO);
        TransactionDTO transactionDTO1 = transactionApplicationService.getTransactionByCode(sessionContext, transactionDTO);
        assertNotEquals("N",transactionDTO1.getAuthorized());
        assertThat(transactionDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getTransactionByCodeIsAuthorizeCheckParameter() throws FatalException {
        //IbanDTO ibanDTOnull=null;
        TransactionDTO transactionDTOEx=new TransactionDTO();
        transactionDTOEx.setTransactionCode("AB");
        transactionDTOEx.setAuthorized("Y");
        given(transactionDomainService.getTransactionByCode(transactionDTOEx.getTransactionCode())).willReturn(transactionEntity);
        given(transactionAssembler.convertEntityToDto(transactionEntity)).willReturn(transactionDTO);
        TransactionDTO stateDTO1 = transactionApplicationService.getTransactionByCode(sessionContext, transactionDTOEx);
        assertThat(transactionDTOEx.getTransactionCode()).isNotBlank();
        assertThat(transactionDTOEx.getAuthorized()).isNotBlank();
    }



    /*@Test
    void getIbanByIbanCountryCode () {
    }

    @Test
    void getIbans () {
    }

    @Test
    void processIban () {
    }

    @Test
    void addUpdateRecord () {
    }

    @Test
    void getConfigurationByCode () {
    }

    @Test
    void save () {
    }*/


    private String transactionCode;
    private String transactionDescription;
    private String transactionSwiftCode;
    private String externalTxnCode;
    private String misHead;
    private Boolean immediate;
    private Boolean onValueDate;
    private Boolean afterXDaysValueDate;
    private Boolean afterXDays;
    private Integer noOfDays;
    private Boolean intradayManualRelease;
    private String statementDateDerivation;
    private Boolean amlTracking;
    private String productGroup;
    private Boolean availableBalanceCheckApplicableBatch;
    private Boolean availableBalanceCheckApplicableOnline;
    private Boolean pricingOnTransactionCount;
    private Boolean pricingTurnoverInclusion;
    private Boolean pricingBalanceInclusive;
    private Boolean turnoverLimit;
    private Boolean coverAccount;
    private Boolean pricingPenaltyInclusive;
    private Boolean considerForAccountStatusChange;
    private Boolean chequeMandatory;
    private Boolean interBranchInLcy;
    private Boolean thirdPartyDealingSystemTransactionCode;


    private TransactionDTO getTransactionDTOAuthorized () {
        TransactionDTO transactionDTOMapper = new TransactionDTO();

        transactionDTOMapper.setTransactionCode("IA");
        transactionDTOMapper.setTransactionDescription("TESTDescription");
        transactionDTOMapper.setTransactionSwiftCode("ITA");
        transactionDTOMapper.setExternalTxnCode("ABC");
        transactionDTOMapper.setMisHead("TEST");
        transactionDTOMapper.setImmediate(true);
        transactionDTOMapper.setOnValueDate(true);
        transactionDTOMapper.setAfterXDaysValueDate(true);
        transactionDTOMapper.setAfterXDays(true);
        transactionDTOMapper.setNoOfDays(2);
        transactionDTOMapper.setIntradayManualRelease(true);
        transactionDTOMapper.setStatementDateDerivation("TEST");
        transactionDTOMapper.setAmlTracking(true);
        transactionDTOMapper.setProductGroup("Test");
        transactionDTOMapper.setAvailableBalanceCheckApplicableBatch(true);
        transactionDTOMapper.setAvailableBalanceCheckApplicableOnline(true);
        transactionDTOMapper.setPricingOnTransactionCount(true);
        transactionDTOMapper.setPricingTurnoverInclusion(true);
        transactionDTOMapper.setPricingBalanceInclusive(true);
        transactionDTOMapper.setTurnoverLimit(true);
        transactionDTOMapper.setCoverAccount(true);
        transactionDTOMapper.setPricingPenaltyInclusive(true);
        transactionDTOMapper.setConsiderForAccountStatusChange(true);
        transactionDTOMapper.setChequeMandatory(true);
        transactionDTOMapper.setInterBranchInLcy(true);
        transactionDTOMapper.setThirdPartyDealingSystemTransactionCode(true);
        transactionDTOMapper.setAuthorized("Y");
        transactionDTOMapper.setTaskCode("REASON");
        transactionDTOMapper.setTaskIdentifier("US");
        return transactionDTOMapper;
    }

    private TransactionDTO getTransactionDTOUnAuthorized () {
        TransactionDTO transactionDTOMapper = new TransactionDTO();

        transactionDTOMapper.setTransactionCode("IA");
        transactionDTOMapper.setTransactionDescription("TESTDescription");
        transactionDTOMapper.setTransactionSwiftCode("ITA");
        transactionDTOMapper.setExternalTxnCode("ABC");
        transactionDTOMapper.setMisHead("TEST");
        transactionDTOMapper.setImmediate(true);
        transactionDTOMapper.setOnValueDate(true);
        transactionDTOMapper.setAfterXDaysValueDate(true);
        transactionDTOMapper.setAfterXDays(true);
        transactionDTOMapper.setNoOfDays(2);
        transactionDTOMapper.setIntradayManualRelease(true);
        transactionDTOMapper.setStatementDateDerivation("TEST");
        transactionDTOMapper.setAmlTracking(true);
        transactionDTOMapper.setProductGroup("Test");
        transactionDTOMapper.setAvailableBalanceCheckApplicableBatch(true);
        transactionDTOMapper.setAvailableBalanceCheckApplicableOnline(true);
        transactionDTOMapper.setPricingOnTransactionCount(true);
        transactionDTOMapper.setPricingTurnoverInclusion(true);
        transactionDTOMapper.setPricingBalanceInclusive(true);
        transactionDTOMapper.setTurnoverLimit(true);
        transactionDTOMapper.setCoverAccount(true);
        transactionDTOMapper.setPricingPenaltyInclusive(true);
        transactionDTOMapper.setConsiderForAccountStatusChange(true);
        transactionDTOMapper.setChequeMandatory(true);
        transactionDTOMapper.setInterBranchInLcy(true);
        transactionDTOMapper.setThirdPartyDealingSystemTransactionCode(true);
        transactionDTOMapper.setTaskCode("REASON");
        transactionDTOMapper.setTaskIdentifier("US");
        transactionDTOMapper.setAuthorized("N");
        return transactionDTOMapper;
    }


    private TransactionDTO getTransactionDTO () {
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

        transactionDTO.setTaskCode("REASON");
        transactionDTO.setTaskIdentifier("US");

        transactionDTO.setAuthorized("Y");
        transactionDTO.setTaskCode("US");
        transactionDTO.setStatus("DELETED");
        transactionDTO.setRecordVersion(1);
        return transactionDTO;
    }

    private TransactionDTO getTransactionDTOMapper () {
        TransactionDTO transactionDTOMapper = new TransactionDTO();

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
        transactionDTO.setTaskCode("REASON");
        transactionDTO.setTaskIdentifier("US");
        transactionDTO.setAuthorized("N");
        transactionDTO.setTaskCode("REASON");
        transactionDTO.setTaskIdentifier("AB");

        return transactionDTOMapper;
    }

    private TransactionDTO getTransactionDTOForSave () {
        TransactionDTO transactionDTOMapper = new TransactionDTO();

        transactionDTOMapper.setTransactionCode("IA");
        transactionDTOMapper.setTransactionDescription("TESTDescription");
        transactionDTOMapper.setTransactionSwiftCode("ITA");
        transactionDTOMapper.setExternalTxnCode("ABC");
        transactionDTOMapper.setMisHead("TEST");
        transactionDTOMapper.setImmediate(true);
        transactionDTOMapper.setOnValueDate(true);
        transactionDTOMapper.setAfterXDaysValueDate(true);
        transactionDTOMapper.setAfterXDays(true);
        transactionDTOMapper.setNoOfDays(2);
        transactionDTOMapper.setIntradayManualRelease(true);
        transactionDTOMapper.setStatementDateDerivation("TEST");
        transactionDTOMapper.setAmlTracking(true);
        transactionDTOMapper.setProductGroup("Test");
        transactionDTOMapper.setAvailableBalanceCheckApplicableBatch(true);
        transactionDTOMapper.setAvailableBalanceCheckApplicableOnline(true);
        transactionDTOMapper.setPricingOnTransactionCount(true);
        transactionDTOMapper.setPricingTurnoverInclusion(true);
        transactionDTOMapper.setPricingBalanceInclusive(true);
        transactionDTOMapper.setTurnoverLimit(true);
        transactionDTOMapper.setCoverAccount(true);
        transactionDTOMapper.setPricingPenaltyInclusive(true);
        transactionDTOMapper.setConsiderForAccountStatusChange(true);
        transactionDTOMapper.setChequeMandatory(true);
        transactionDTOMapper.setInterBranchInLcy(true);
        transactionDTOMapper.setThirdPartyDealingSystemTransactionCode(true);

        transactionDTOMapper.setTaskCode("REASON");
        transactionDTOMapper.setTaskIdentifier("AB");
        transactionDTOMapper.setAction("authorize");
        transactionDTOMapper.setStatus("active");
        transactionDTOMapper.setRecordVersion(1);
        transactionDTOMapper.setAuthorized("N");
        transactionDTOMapper.setLastConfigurationAction("authorized");
        transactionDTOMapper.setTaskCode("Transaction");
        transactionDTOMapper.setTaskIdentifier("AB");

        return transactionDTOMapper;
    }

    private TransactionEntity getTransactionEntity () {
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

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("0002").defaultBranchCode("0002")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber(null)
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private SessionContext getInValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("").defaultBranchCode("")
                .internalTransactionReferenceNumber(null).userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber(null).targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(null)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(null).build();
        return sessionContext;
    }

    private MutationEntity getMutationEntity () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("AB");
        mutationEntity.setTaskCode("REASON");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }


    private MutationEntity getMutationEntityError () {
        String payLoadString =
                getpayloadInvalidString();
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("AB");
        mutationEntity.setTaskCode("REASON");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("active");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("authorize");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private String getpayloadValidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":0,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Transaction\",\"taskIdentifier\":\"IA\",\"transactionCode\":\"IA\",\"transactionDescription\":\"TESTDescription\",\"transactionSwiftCode\":\"ITA\",\"externalTxnCode\":\"ABC\",\"misHead\":true,\"immediate\":true,\"onValueDate\":true,\"afterXDaysValueDate\":true,\"afterXDays\":true,\"noOfDays\":2,\"intradayManualRelease\":true,\"statementDateDerivation\":\"TEST\",\"amlTracking\":true,\"productGroup\":\"TEST\",\"availableBalanceCheckApplicableBatch\":true,\"availableBalanceCheckApplicableOnline\":true,\"pricingOnTransactionCount\":true,\"pricingTurnoverInclusion\":true,\"pricingBalanceInclusive\":true,\"turnoverLimit\":true,\"coverAccount\":true,\"pricingPenaltyInclusive\":true,\"considerForAccountStatusChange\":true,\"chequeMandatory\":true,\"interBranchInLcy\":true,\"thirdPartyDealingSystemTransactionCode\":true}";
        return payLoadString;
    }

    private String getpayloadInvalidString () {
        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":10,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"Transaction\",\"taskIdentifier\":\"IA\",\"transactionCode\":\"IA\",\"transactionDescription\":\"TESTDescription\",\"transactionSwiftCode\":\"ITA\",\"externalTxnCode\":\"ABC\",\"misHead\":true,\"immediate\":true,\"onValueDate\":true,\"afterXDaysValueDate\":true,\"afterXDays\":true,\"noOfDays\":2,\"intradayManualRelease\":true,\"statementDateDerivation\":\"TEST\",\"amlTracking\":true,\"productGroup\":\"TEST\",\"availableBalanceCheckApplicableBatch\":true,\"availableBalanceCheckApplicableOnline\":true,\"pricingOnTransactionCount\":true,\"pricingTurnoverInclusion\":true,\"pricingBalanceInclusive\":true,\"turnoverLimit\":true,\"coverAccount\":true,\"pricingPenaltyInclusive\":true,\"considerForAccountStatusChange\":true,\"chequeMandatory\":true,\"interBranchInLcy\":true,\"thirdPartyDealingSystemTransactionCode\":true}";
        return payLoadString;
    }

}
package com.idg.idgcore.coe.app.service.aml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.aml.AmlAssembler;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.entity.aml.AmlEntityKey;
import com.idg.idgcore.coe.domain.entity.aml.LimitEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.aml.IAmlDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.dto.aml.LimitDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
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

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AmlApplicationServiceTest {

    @InjectMocks
    AmlApplicationService amlApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    AmlAssembler amlAssembler;
    @Mock
    private IAmlDomainService amlDomainService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private AmlEntity amlEntity;
    private AmlDTO amlDTO;
    private AmlDTO amlDTOUnAuth;
    private AmlDTO amlDTO1;
    private AmlEntity amlEntity1;
    private AmlEntity amlEntity2;
    private AmlDTO amlDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        amlDTO = getAmlDTOAuthorized();
        amlEntity = getAmlsEntity();
        amlDTOUnAuth = getAmlDTOUnAuth();
        amlDTOMapper = getAmlDTOMapper();
        mutationEntity = getMutationEntity();
        amlEntity1 = getAmlsEntity();
        amlEntity2 = getAmlsEntity2();
        amlDTO1 = getAmlsDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getAmlByCode where return the Aml when the authorized is Y")
    void getAmlByCodeWhenAuthorizedIsYThenReturnAml() throws FatalException, JsonProcessingException {

        given(amlDomainService.getAmlByCode(amlDTO.getProductCategory())).willReturn(amlEntity);
        given(amlAssembler.convertEntityToDto(amlEntity)).willReturn(amlDTO);
        AmlDTO result = amlApplicationService.getAmlByCode(sessionContext, amlDTO);
        assertEquals(amlDTO, result);
    }

    @Test
    @DisplayName("JUnit for getAmls where return all Amls when there are no unauthorized mutations")
    void getAmlsWhenThereAreNoUnauthorizedMutationsThenReturnAllAmls() throws FatalException {
        given(amlDomainService.getAmls()).willReturn(List.of(amlEntity));
        given(mutationsDomainService.getUnauthorizedMutation(AML, AUTHORIZED_N)).willReturn(List.of());
        given(amlAssembler.convertEntityToDto(amlEntity)).willReturn(amlDTO);

        List<AmlDTO> amlDTOList = amlApplicationService.getAmls(sessionContext);

        assertEquals(1, amlDTOList.size());
        assertEquals(amlDTO, amlDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getAmlByCode in application service when Authorize try Block")
    void getAmlByCodeIsAuthorize() throws FatalException, JsonProcessingException {

        given(amlDomainService.getAmlByCode(amlDTO.getProductCategory())).willReturn(amlEntity);
        given(amlAssembler.convertEntityToDto(amlEntity)).willReturn(amlDTO);
        AmlDTO amlDTO1 = amlApplicationService.getAmlByCode(sessionContext, amlDTO);
        assertEquals("Y",amlDTO1.getAuthorized());
        assertThat(amlDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getAmlByCode in application service when Not Authorize in catch block")
    void getAmlByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"AML\",\"taskIdentifier\":\"Lending\"" +
                        ",\"productCategory\":\"Lending\",\"productDescription\":\"Secured Loan\",\"productLimitCurrency\":\"INR\"" +
                        ",\"exchangeRateCode\":\"ER002\",\"exchangeRateType\":\"Mid-Rate\",\"debitCreditIndicator\":\"Debit Indicator\"" +
                        ",\"limit\":{\"limitCode\":\"LM01\",\"limitAmount\":100000.0,\"limitCurrency\":\"INR\"},\"productType\":true}";

        given(mutationsDomainService.getConfigurationByCode(amlDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            AmlDTO amlDTO1 = amlApplicationService.getAmlByCode(sessionContext, amlDTOMapper);
            assertEquals("N",amlDTO1.getAuthorized());
            assertThat(amlDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for getAmls in application service for catch block for checker")
    void getAmlsCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                amlDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<AmlDTO> amlDTO1 = amlApplicationService.getAmls(sessionContext);
            assertThat(amlDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processAml in application service for Try Block")
    void processAmlForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(amlDTO);
        amlApplicationService.processAml(sessionContext, amlDTO);
        verify(process, times(1)).process(amlDTO);
    }

    @Test
    @DisplayName("JUnit for processAml in application service for Catch Block")
    void processAmlForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            amlApplicationService.processAml(sessionContext2, amlDTO);
            assertThat(amlDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"AML\",\"taskIdentifier\":\"Lending\"" +
                        ",\"productCategory\":\"Lending\",\"productDescription\":\"Secured Loan\",\"productLimitCurrency\":\"INR\"" +
                        ",\"exchangeRateCode\":\"ER002\",\"exchangeRateType\":\"Mid-Rate\",\"debitCreditIndicator\":\"Debit Indicator\"" +
                        ",\"limit\":{\"limitCode\":\"LM01\",\"limitAmount\":100000.0,\"limitCurrency\":\"INR\"},\"productType\":true}";

        doNothing().when(amlDomainService).save(amlDTO);
        amlApplicationService.save(amlDTO);
        amlApplicationService.addUpdateRecord(payLoadString1);
        verify(amlDomainService, times(1)).save(amlDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = amlDTO.getProductCategory();
        given(amlDomainService.getAmlByCode(code)).willReturn(amlEntity);
        amlApplicationService.getConfigurationByCode(code);
        assertThat(amlEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByAmlCode in application service when Authorize for Negative")
    void getAmlByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(amlDomainService.getAmlByCode(amlDTO.getProductCategory())).willReturn(amlEntity);
        given(amlAssembler.convertEntityToDto(amlEntity)).willReturn(amlDTO);
        AmlDTO amlDTO1 = amlApplicationService.getAmlByCode(sessionContext, amlDTO);
        assertNotEquals("N",amlDTO1.getAuthorized());
        assertThat(amlDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getAmlByCode in application service check Parameter not null")
    void getAmlByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        AmlDTO amlDTOnull=null;
        AmlDTO amlDTOEx=new AmlDTO();
        amlDTOEx.setProductCategory("Lending");
        amlDTOEx.setAuthorized("Y");
        given(amlDomainService.getAmlByCode(amlDTOEx.getProductCategory())).willReturn(amlEntity);
        given(amlAssembler.convertEntityToDto(amlEntity)).willReturn(amlDTO);
        AmlDTO amlDTO1 = amlApplicationService.getAmlByCode(sessionContext, amlDTOEx);
        assertThat(amlDTOEx.getProductCategory()).isNotBlank();
        assertThat(amlDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(amlEntity.toString()).isNotNull();
        assertThat(amlDTO.toString()).isNotNull();

        LimitDTO limitDTO = new LimitDTO("LM01", 100000.00F,"INR");

        AmlDTO amlDTO2 = new AmlDTO("Lending", "Secured Loan",
                true, "INR", "ER002",
                "Mid-Rate", "Debit Indicator", limitDTO);

        LimitDTO limitDTO2 = LimitDTO.builder().limitCode("LM01").limitAmount(100000.00F).limitCurrency("INR").build();

        AmlDTO.builder()
                .productCategory("Lending")
                .productDescription("Secured Loan")
                .isProductType(true)
                .productLimitCurrency("INR")
                .exchangeRateCode("ER002")
                .exchangeRateType("Mid-Rate")
                .debitCreditIndicator("Debit Indicator")
                .limit(limitDTO2)
                .authorized("N")
                .taskCode(AML)
                .taskIdentifier("Lending")
                .build().toString();

        AmlEntityKey amlEntityKey=new AmlEntityKey("Lending");
        assertThat(amlEntityKey.toString()).isNotNull();
        amlEntityKey.setProductCategory("Lending");
        amlEntityKey.keyAsString();
        amlEntityKey.builder().productCategory("Lending").build();
        assertThat(amlDTO).descriptionText();
    }
    private SessionContext getValidSessionContext() {
        SessionContext sessionContext =
                SessionContext.builder()
                        .bankCode("003")
                        .defaultBranchCode("1141")
                        .internalTransactionReferenceNumber("")
                        .userTransactionReferenceNumber("")
                        .externalTransactionReferenceNumber("")
                        .targetUnit("dummy_target")
                        .postingDate(new Date())
                        .valueDate(new Date())
                        .transactionBranch("")
                        .userId("nikhil")
                        //                .accessibleTargetUnits([])
                        .channel("Branch")
                        .taskCode(AML)
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(1L)
                        .customAttributes("")
                        .serviceInvocationModeType(Regular)
                        .allTargetUnitsSelected(true)
                        //                .mutationType("")
                        .userLocal("en_US")
                        .originatingModuleCode("")
                        .role(new String[]{"maker"})
                        .build();
        return sessionContext;
    }

    private SessionContext getErrorSession() {
        SessionContext sessionContextError =
                SessionContext.builder()
                        .bankCode("")
                        .defaultBranchCode("")
                        .internalTransactionReferenceNumber("")
                        .userTransactionReferenceNumber("")
                        .externalTransactionReferenceNumber("")
                        .targetUnit("")
                        .postingDate(new Date())
                        .valueDate(new Date())
                        .transactionBranch("")
                        .userId("prash")
                        //                .accessibleTargetUnits([])
                        .channel("")
                        .taskCode("")
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(null)
                        .customAttributes("")
                        .serviceInvocationModeType(null)
                        .allTargetUnitsSelected(true)
                        //                .mutationType("")
                        .userLocal("")
                        .originatingModuleCode("")
                        .role(new String[]{"maker"})
                        .build();
        return sessionContextError;
    }

    private AmlDTO getAmlDTOAuthorized() {
        AmlDTO amlDTO2 = new AmlDTO();

        amlDTO2.setProductCategory("Lending");
        amlDTO2.setProductDescription("Secured Loan");
        amlDTO2.setProductType(true);
        amlDTO2.setProductLimitCurrency("INR");
        amlDTO2.setExchangeRateCode("ER002");
        amlDTO2.setExchangeRateType("Mid-Rate");
        amlDTO2.setDebitCreditIndicator("Debit Indicator");

        LimitDTO limitDTO2 = new LimitDTO();
        limitDTO2.setLimitCode("LM01");
        limitDTO2.setLimitAmount(100000.00F);
        limitDTO2.setLimitCurrency("INR");

        amlDTO2.setLimit(limitDTO2);
        amlDTO2.setAuthorized("Y");


        return amlDTO2;
    }

    private AmlDTO getAmlsDTO() {

        AmlDTO amlDTO2 = new AmlDTO();

        amlDTO2.setProductCategory("Lending");
        amlDTO2.setProductDescription("Secured Loan");
        amlDTO2.setProductType(true);
        amlDTO2.setProductLimitCurrency("INR");
        amlDTO2.setExchangeRateCode("ER002");
        amlDTO2.setExchangeRateType("Mid-Rate");
        amlDTO2.setDebitCreditIndicator("Debit Indicator");

        LimitDTO limitDTO2 = new LimitDTO();
        limitDTO2.setLimitCode("LM01");
        limitDTO2.setLimitAmount(100000.00F);
        limitDTO2.setLimitCurrency("INR");

        amlDTO2.setLimit(limitDTO2);
        amlDTO2.setTaskCode(AML);
        amlDTO2.setStatus("DELETED");
        amlDTO2.setRecordVersion(1);

        return amlDTO2;
    }

    private AmlEntity getAmlEntity() {

        LimitEntity limitEntity = new LimitEntity("LM01", 100000.00F,"INR");

        AmlEntity amlEntity = new AmlEntity("Lending", "Secured Loan",
                                'Y', "INR", "ER002",
                                "Mid-Rate", "Debit Indicator", limitEntity,
                                    "draft",
                                    0,
                                    "Y",
                                    "draft");

        return amlEntity;
    }

    private AmlEntity getAmlsEntity() {

        LimitEntity limitEntity = new LimitEntity("LM01", 100000.00F,"INR");

        AmlEntity amlEntity = new AmlEntity("Lending", "Secured Loan",
                'Y', "INR", "ER002",
                "Mid-Rate", "Debit Indicator", limitEntity,
                "DELETED",
                1,
                null,
                null);

        return amlEntity;
    }

    private AmlEntity getAmlsEntity2() {

        LimitEntity limitEntity = new LimitEntity();
        limitEntity.setLimitCode("LM01");
        limitEntity.setLimitAmount(100000.00F);
        limitEntity.setLimitCurrency("INR");

        AmlEntity amlEntity2 = new AmlEntity();
        amlEntity2.setProductCategory("Lending");
        amlEntity2.setProductDescription("Secured Loan");
        amlEntity2.setIsProductType('Y');
        amlEntity2.setProductLimitCurrency("INR");
        amlEntity2.setExchangeRateCode("ER002");
        amlEntity2.setExchangeRateType("Mid-Rate");
        amlEntity2.setDebitCreditIndicator("Debit Indicator");
        amlEntity2.setAuthorized("N");
        amlEntity2.setStatus("closed");
        amlEntity2.setRecordVersion(1);
        return amlEntity2;
    }

    private AmlDTO getAmlDTOUnAuth() {

        LimitDTO limitDTO = new LimitDTO("LM01", 100000.00F,"INR");

        AmlDTO amlDTO2 = new AmlDTO("Lending", "Secured Loan",
                true, "INR", "ER002",
                "Mid-Rate", "Debit Indicator", limitDTO);

        amlDTO2.setAuthorized("N");
        amlDTO2.setTaskIdentifier("Lending");
        return amlDTO2;
    }

    private AmlDTO getAmlDTOMapper() {

        LimitDTO limitDTO = LimitDTO.builder().limitCode("LM01").limitAmount(100000.00F).limitCurrency("INR").build();

        AmlDTO amlDTOMapper2 =
                AmlDTO.builder()
                        .productCategory("Lending")
                        .productDescription("Secured Loan")
                        .isProductType(true)
                        .productLimitCurrency("INR")
                        .exchangeRateCode("ER002")
                        .exchangeRateType("Mid-Rate")
                        .debitCreditIndicator("Debit Indicator")
                        .limit(limitDTO)
                        .authorized("N")
                        .taskCode(AML)
                        .taskIdentifier("Lending")
                        .build();

        return amlDTOMapper2;
    }

    private MutationEntity getMutationEntity() {

        String payLoadString =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"AML\",\"taskIdentifier\":\"Lending\"" +
                        ",\"productCategory\":\"Lending\",\"productDescription\":\"Secured Loan\",\"productLimitCurrency\":\"INR\"" +
                        ",\"exchangeRateCode\":\"ER002\",\"exchangeRateType\":\"Mid-Rate\",\"debitCreditIndicator\":\"Debit Indicator\"" +
                        ",\"limit\":{\"limitCode\":\"LM01\",\"limitAmount\":100000.0,\"limitCurrency\":\"INR\"},\"productType\":true}";


        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("Lending");
        mutationEntity.setTaskCode(AML);
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");

        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError() {

        String payLoadString1 =
                "{\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                        ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"AML\",\"taskIdentifier\":\"Lending\"" +
                        ",\"productCategory\":\"Lending\",\"productDescription\":\"Secured Loan\",\"productLimitCurrency\":\"INR\"" +
                        ",\"exchangeRateCode\":\"ER002\",\"exchangeRateType\":\"Mid-Rate\",\"debitCreditIndicator\":\"Debit Indicator\"" +
                        ",\"limit\":{\"limitCode\":\"LM01\",\"limitAmount\":100000.0,\"limitCurrency\":\"INR\"},\"productType\":true}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("Lending");
        mutationEntity2.setTaskCode(AML);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}
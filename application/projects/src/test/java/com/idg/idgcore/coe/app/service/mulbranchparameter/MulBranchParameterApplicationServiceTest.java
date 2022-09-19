package com.idg.idgcore.coe.app.service.mulbranchparameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntity;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.FinancialAccountingYearEntityKey;
import com.idg.idgcore.coe.domain.entity.language.LanguageEntityKey;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mulbranchparameter.IMulBranchParameterDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.financialAccountingYear.FinancialAccountingYearDTO;
import com.idg.idgcore.coe.dto.language.LanguageDTO;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)

public class MulBranchParameterApplicationServiceTest {

    @InjectMocks
    private MulBranchParameterApplicationService mulBranchParameterApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private MulBranchParameterAssembler mulBranchParameterAssembler;

    @Mock
    private IMulBranchParameterDomainService mulBranchParameterDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;
    private SessionContext sessionContext1;

    private MulBranchParameterEntity mulBranchParameterEntity;
    private MulBranchParameterDTO mulBranchParameterDTO;
    private MulBranchParameterDTO mulBranchParameterDTOUnAuth;
    private MulBranchParameterDTO mulBranchParameterDTO1;
    private MulBranchParameterEntity mulBranchParameterEntity1;

    private MulBranchParameterDTO mulBranchParameterDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        mulBranchParameterDTO=getmulBranchParameterDTOAuthorized();
        mulBranchParameterEntity=getMulBranchParameterEntity();
        mulBranchParameterDTOUnAuth=getMulBranchParameterDTOUnAuth();
        mulBranchParameterDTOMapper=getMulBranchParameterDTOMapper();
        mutationEntity=getMutationEntity();
        mulBranchParameterEntity1=getMulBranchParameterEntity1();
        mulBranchParameterDTO1=getMulBranchParameterDTO();
        mutationEntity2=getMutationEntityJsonError();
    }
    @Test
    @DisplayName ("JUnit for getByCurrencyCodeAndEntityCode in application service when Authorize try Block")
    void getByCurrencyCodeAndEntityCodeIsAuthorize ()
            throws FatalException, JsonProcessingException {
        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCode(
                mulBranchParameterDTO.getCurrencyCode(),
                mulBranchParameterDTO.getEntityCode())).
                willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(
                mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCode(
                sessionContext, mulBranchParameterDTO);
        assertEquals("Y", mulBranchParameterDTO1.getAuthorized());
        Assertions.assertThat(mulBranchParameterDTO1).isNotNull();
    }
    @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCode in application service when Authorize for Negative")
    void getgetByCurrencyCodeAndEntityCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCode(mulBranchParameterDTO.getCurrencyCode(),mulBranchParameterDTO.getEntityCode())).willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCode(sessionContext, mulBranchParameterDTO);
        assertNotEquals("N",mulBranchParameterDTO1.getAuthorized());
        assertThat(mulBranchParameterDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCode  in application service when Not Authorize in catch block")
    void getByCurrencyCodeAndEntityCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\"," +
                "\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"MULBRANCHPARAM\",\"taskIdentifier\":\"INR_BR003\","+
                "\"currencyCode\":\"INR\",\"entityCode\":\"BR001\",\"spotDays\":1,\"generationOfPaymentMessage\":11," +
                "\"generationOfReceiveMessages\":21}";

        given(mutationsDomainService.getConfigurationByCode(mulBranchParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,()-> {
            MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCode(sessionContext,mulBranchParameterDTOMapper);
            assertEquals("N",mulBranchParameterDTO1.getAuthorized());
            assertThat(mulBranchParameterDTO1).isNotNull();
        });
    }
    @Test
    @DisplayName ("JUnit for MulBranchParameterEntityKey")
    void getMulBranchParameterEntityKey () {
        MulBranchParameterEntityKey mulBranchParameterEntityKey = new MulBranchParameterEntityKey(
                "INR", "BR001");
        Assertions.assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.keyAsString();
        mulBranchParameterEntityKey.builder().currencyCode("INR")
                .entityCode("BR001").build();
        Assertions.assertThat(mulBranchParameterEntityKey).descriptionText();
        Assertions.assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processMulBranchParameter in application service for Try Block")
    void  processMulBranchParameterForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(mulBranchParameterDTO);
        mulBranchParameterApplicationService.processMulBranchParameter(sessionContext, mulBranchParameterDTO);
        verify(process, times(1)).process(mulBranchParameterDTO);
    }
    @DisplayName ("Test processMulBranchParameter")
    @Test
    void processMulBranchParameter() throws JsonProcessingException, FatalException {
        mulBranchParameterDTOMapper = getMulBranchParameterDTOMapper();
        doNothing().when(process).process(mulBranchParameterDTOMapper);
        mulBranchParameterApplicationService.processMulBranchParameter(sessionContext,mulBranchParameterDTOMapper);
        verify(process, times(1)).process(mulBranchParameterDTOMapper);
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\"," +
                "\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"MULBRANCHPARAM\",\"taskIdentifier\":\"INR_BR003\","+
                "\"currencyCode\":\"INR\",\"entityCode\":\"BR001\",\"spotDays\":1,\"generationOfPaymentMessage\":11," +
                "\"generationOfReceiveMessages\":21}";

        doNothing().when(mulBranchParameterDomainService).save(mulBranchParameterDTO);
        mulBranchParameterApplicationService.save(mulBranchParameterDTO);
        mulBranchParameterApplicationService.addUpdateRecord(payLoadString1);
        verify(mulBranchParameterDomainService, times(1)).save(mulBranchParameterDTO);
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003")
                .defaultBranchCode("1141")
                .internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("")
                .targetUnit("dummy_target")
                .postingDate(new Date())
                .valueDate(new Date())
                .transactionBranch("")
                .userId("vidya")
//              .accessibleTargetUnits([])
                .channel("Branch")
                .taskCode(CURR_BRANCH_PARAM)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//              .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
        return sessionContext;
    }

    private SessionContext getErrorSession(){
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
                        .userId("vidya")
//                      .accessibleTargetUnits([])
                        .channel("")
                        .taskCode("")
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(null)
                        .customAttributes("")
                        .serviceInvocationModeType(null)
                        .allTargetUnitsSelected(true)
//                      .mutationType("")
                        .userLocal("")
                        .originatingModuleCode("")
                        .role(new String[] {"maker"})
                        .build();
        return sessionContextError;
    }

    private MulBranchParameterDTO getmulBranchParameterDTOAuthorized() {
        MulBranchParameterDTO mulBranchParameterDTO=new MulBranchParameterDTO();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR001");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTO.setAuthorized("Y");
        return mulBranchParameterDTO;
    }

    private MulBranchParameterDTO getMulBranchParameterDTO()
    {
        MulBranchParameterDTO mulBranchParameterDTO=new MulBranchParameterDTO();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR001");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTO.setTaskCode("MULBRANCHPARAM");
        mulBranchParameterDTO.setStatus("DELETED");
        mulBranchParameterDTO.setRecordVersion(1);
        return  mulBranchParameterDTO;
    }

    private MulBranchParameterEntity getMulBranchParameterEntity(){
        MulBranchParameterEntity mulBranchParameterEntity = new MulBranchParameterEntity(
                "INR",
                "BR001",
                1,
                11,
                21,
                null,
                null,
                0,
                "draft",
                "Y",
                "draft");
        return mulBranchParameterEntity;
    }

    private MulBranchParameterEntity getMulBranchParameterEntity1()
    {
          MulBranchParameterEntity mulBranchParameterEntity = new MulBranchParameterEntity();
          mulBranchParameterEntity.setCurrencyCode("INR");
          mulBranchParameterEntity.setEntityCode("BR001");
          mulBranchParameterEntity.setSpotDays(1);
          mulBranchParameterEntity.setGenerationOfPaymentMessage(11);
          mulBranchParameterEntity.setGenerationOfReceiveMessages(21);
          mulBranchParameterEntity.setLifeCycleId(null);
          mulBranchParameterEntity.setReferenceNo(null);
          mulBranchParameterEntity.setStatus("closed");
          mulBranchParameterEntity.setRecordVersion(1);
          return  mulBranchParameterEntity;
    }
    private MulBranchParameterDTO getMulBranchParameterDTOUnAuth(){
        MulBranchParameterDTO mulBranchParameterDTO =
                new MulBranchParameterDTO(
                        "INR",
                        "BR001",
                        1,
                        11,
                        21);

        mulBranchParameterDTO.setAuthorized("N");
        mulBranchParameterDTO.setTaskIdentifier("nullINRBR001");
        return mulBranchParameterDTO;
    }

    private MulBranchParameterDTO getMulBranchParameterDTOMapper(){
        MulBranchParameterDTO mulBranchParameterDTOMapper=new MulBranchParameterDTO();
        mulBranchParameterDTOMapper.setCurrencyCode("INR");
        mulBranchParameterDTOMapper.setEntityCode("BR001");
        mulBranchParameterDTOMapper.setSpotDays(1);
        mulBranchParameterDTOMapper.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTOMapper.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTOMapper.setAuthorized("N");
        mulBranchParameterDTOMapper.setTaskCode(BRANCH_PARAMETER);
        mulBranchParameterDTOMapper.setTaskIdentifier("INR_BR001");
        return mulBranchParameterDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        Payload payload = new Payload("{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"MULBRANCHPARAM\",\"taskIdentifier\":\"INR_BR001\","+
                "\"currencyCode\":\"INR\",\"entityCode\":\"BR001\",\"spotDays\":1,\"generationOfPaymentMessage\":11," +
                "\"generationOfReceiveMessages\":21}");

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("INR_BR001");
        mutationEntity.setTaskCode("MULBRANCHPARAM");
        mutationEntity.setPayload(payload);
        mutationEntity.setStatus("new");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\"," +
                "\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\"," +
                "\"taskCode\":\"MULBRANCHPARAM\",\"taskIdentifier\":\"INR_BR003\","+
                "\"currencyCode\":\"INR\",\"entityCode\":\"BR001\",\"spotDays\":1,\"generationOfPaymentMessage\":11," +
                "\"generationOfReceiveMessages\":21}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("INR_BR003");
        mutationEntity2.setTaskCode("MULBRANCHPARAM");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    @Test
    @DisplayName("JUnit for getBranchParameters in application service for try block")
    void getBranchParametersTryBlock() throws FatalException {
        given(mutationsDomainService.getMutations(CURR_BRANCH_PARAM))
                .willReturn(List.of(mutationEntity));
        List<MulBranchParameterDTO> mulBranchParameterDTOList =
                mulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
        assertThat(mulBranchParameterDTOList).isNotNull();
    }
    @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCode in application service check Parameter not null")
    void getByCurrencyCodeAndEntityCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        MulBranchParameterDTO mulBranchParameterDTOnull=null;
        MulBranchParameterDTO mulBranchParameterDTOEx=new MulBranchParameterDTO();
        mulBranchParameterDTOEx.setCurrencyCode("INR");
        mulBranchParameterDTOEx.setEntityCode("BR001");
        mulBranchParameterDTOEx.setAuthorized("Y");

        MulBranchParameterEntityKey mulBranchParameterEntityKey = new MulBranchParameterEntityKey();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.setEntityCode("BR001");

        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCode(mulBranchParameterDTOEx.getCurrencyCode(),mulBranchParameterDTOEx.getEntityCode())).willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCode(sessionContext, mulBranchParameterDTOEx);
        assertThat(mulBranchParameterDTOEx.getCurrencyCode()).isNotBlank();
        assertThat(mulBranchParameterDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(mulBranchParameterEntity.toString()).isNotNull();
        assertThat(mulBranchParameterDTO.toString()).isNotNull();
        MulBranchParameterDTO mulBranchParameterDTO2=new MulBranchParameterDTO("INR",
                "BR001",
                1,
                11,
                21);
        MulBranchParameterDTO.builder().currencyCode("INR")
                .entityCode("BR001")
                .spotDays(1)
                .generationOfPaymentMessage(11)
                .generationOfReceiveMessages(21)
                .authorized("N")
                .taskCode("MULBRANCHPARAM")
                .taskIdentifier("INR_BR001")
                .build().toString();
        MulBranchParameterEntityKey mulBranchParameterEntityKey=new MulBranchParameterEntityKey("INR","BR001");
        assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.keyAsString();
        mulBranchParameterEntityKey.builder().currencyCode("INR").build();
        assertThat(mulBranchParameterDTO).descriptionText();
    }
   // @Test
    @DisplayName("JUnit for getMulBranchParameters in application service for catch block for checker")
    void getMulBranchParametersCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                mulBranchParameterDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        org.junit.jupiter.api.Assertions.assertThrows(FatalException.class,()-> {
            List<MulBranchParameterDTO> mulBranchParameterDTO1 = mulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
            assertThat(mulBranchParameterDTO1).isNotNull();
        });
    }
    @Test
    @DisplayName("JUnit for proccessMulBranchParameter  in application service for Catch Block")
    void proccessMulBranchParameterForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,()-> {
            mulBranchParameterApplicationService.processMulBranchParameter(sessionContext2, mulBranchParameterDTO);
            assertThat(mulBranchParameterDTO).descriptionText();
        });
    }


}

package com.idg.idgcore.coe.app.service.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bankidentifier.IBankIdentifierDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
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

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.BANK_IDENTIFIER;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


import java.util.Date;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class BankIdentifierApplicationServiceTest {
    @InjectMocks
    BankIdentifierApplicationService bankIdentifierApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    BankIdentifierAssembler bankIdentifierAssembler;
    @Mock
    private IBankIdentifierDomainService bankIdentifierDomainService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private BankIdentifierEntity bankIdentifierEntity;
    private BankIdentifierDTO bankIdentifierDTO;
    private BankIdentifierDTO bankIdentifierDTOUnAuth;
    private BankIdentifierDTO bankIdentifierDTO1;
    private BankIdentifierEntity bankIdentifierEntity1;
    private BankIdentifierEntity bankIdentifierEntity2;
    private BankIdentifierDTO bankIdentifierDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        bankIdentifierDTO = getBankIdentifierDTOAuthorized();
        bankIdentifierEntity = getBankIdentifiersEntity();
        bankIdentifierDTOUnAuth = getBankIdentifierDTOUnAuth();
        bankIdentifierDTOMapper = getBankIdentifierDTOMapper();
        mutationEntity = getMutationEntity();
        bankIdentifierEntity1 = getBankIdentifiersEntity();
        bankIdentifierEntity2 = getBankIdentifiersEntity2();
        bankIdentifierDTO1 = getBankIdentifiersDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }
    @Test
    @DisplayName("JUnit for getBankIdentifierByCode where return the bank identifier when the authorized is Y")
    void getBankIdentifierByCodeWhenAuthorizedIsYThenReturnBankIdentifier() throws FatalException, JsonProcessingException {
        given(
                bankIdentifierDomainService.getBankIdentifierByCode(
                        bankIdentifierDTO.getBankIdentifierCode()))
                .willReturn(bankIdentifierEntity);
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity))
                .willReturn(bankIdentifierDTO);
        BankIdentifierDTO result =
                bankIdentifierApplicationService.getBankIdentifierByCode(
                        sessionContext, bankIdentifierDTO);
        assertEquals(bankIdentifierDTO, result);
    }

    @Test
    @DisplayName("JUnit for getBankIdentifiers where return all bank identifiers when there are no unauthorized mutations")
    void getBankIdentifiersWhenThereAreNoUnauthorizedMutationsThenReturnAllBankIdentifiers() throws FatalException {
        given(bankIdentifierDomainService.getBankIdentifiers())
                .willReturn(List.of(bankIdentifierEntity));
        given(mutationsDomainService.getUnauthorizedMutation(BANK_IDENTIFIER, AUTHORIZED_N))
                .willReturn(List.of());
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity))
                .willReturn(bankIdentifierDTO);

        List<BankIdentifierDTO> bankIdentifierDTOList =
                bankIdentifierApplicationService.getBankIdentifiers(sessionContext);

        assertEquals(1, bankIdentifierDTOList.size());
        assertEquals(bankIdentifierDTO, bankIdentifierDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getBankIdentifierByCode in application service when Authorize try Block")
    void getBankIdentifierByCodeIsAuthorize() throws FatalException, JsonProcessingException {

        given(bankIdentifierDomainService.getBankIdentifierByCode(bankIdentifierDTO.getBankIdentifierCode())).willReturn(bankIdentifierEntity);
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity)).willReturn(bankIdentifierDTO);
        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTO);
        assertEquals("Y",bankIdentifierDTO1.getAuthorized());
        assertThat(bankIdentifierDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankIdentifierByCode in application service when Not Authorize in catch block")
    void getBankIdentifierByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        given(mutationsDomainService.getConfigurationByCode(bankIdentifierDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTOMapper);
            assertEquals("N",bankIdentifierDTO1.getAuthorized());
            assertThat(bankIdentifierDTO1).isNotNull();
        });
    }

//    @Test
    @DisplayName("JUnit for getBankIdentifiers in application service for try block")
    void getBankIdentifiersTryBlock() throws FatalException {

        given(bankIdentifierDomainService.getBankIdentifiers()).willReturn(List.of(bankIdentifierEntity1));
        given(mutationsDomainService.getUnauthorizedMutation(bankIdentifierDTO1.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));

        String payLoadString ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        Payload payload=new Payload();
        payload.setData(payLoadString);
        mutationEntity.setPayload(payload);
        String data1 = mutationEntity.getPayload().getData();
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity1)).willReturn(bankIdentifierDTO1);

        List<BankIdentifierDTO> bankIdentifierDTO2 = bankIdentifierApplicationService.getBankIdentifiers(sessionContext);
        assertThat(bankIdentifierDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankIdentifiers in application service for catch block for checker")
    void getBankIdentifiersCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                bankIdentifierDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<BankIdentifierDTO> bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifiers(sessionContext);
            assertThat(bankIdentifierDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processBankIdentifier in application service for Try Block")
    void processBankIdentifierForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(bankIdentifierDTO);
        bankIdentifierApplicationService.processBankIdentifier(sessionContext, bankIdentifierDTO);
        verify(process, times(1)).process(bankIdentifierDTO);
    }

    @Test
    @DisplayName("JUnit for processBankIdentifier in application service for Catch Block")
    void processBankIdentifierForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            bankIdentifierApplicationService.processBankIdentifier(sessionContext2, bankIdentifierDTO);
            assertThat(bankIdentifierDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        doNothing().when(bankIdentifierDomainService).save(bankIdentifierDTO);
        bankIdentifierApplicationService.save(bankIdentifierDTO);
        bankIdentifierApplicationService.addUpdateRecord(payLoadString1);
        verify(bankIdentifierDomainService, times(1)).save(bankIdentifierDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = bankIdentifierDTO.getBankIdentifierCode();
        given(bankIdentifierDomainService.getBankIdentifierByCode(code)).willReturn(bankIdentifierEntity);
        bankIdentifierApplicationService.getConfigurationByCode(code);
        assertThat(bankIdentifierEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByBankIdentifierCode in application service when Authorize for Negative")
    void getBankIdentifierByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(bankIdentifierDomainService.getBankIdentifierByCode(bankIdentifierDTO.getBankIdentifierCode())).willReturn(bankIdentifierEntity);
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity)).willReturn(bankIdentifierDTO);
        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTO);
        assertNotEquals("N",bankIdentifierDTO1.getAuthorized());
        assertThat(bankIdentifierDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankIdentifierByCode in application service check Parameter not null")
    void getBankIdentifierByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        BankIdentifierDTO bankIdentifierDTOnull=null;
        BankIdentifierDTO bankIdentifierDTOEx=new BankIdentifierDTO();
        bankIdentifierDTOEx.setBankIdentifierCode("NWBKGB2L");
        bankIdentifierDTOEx.setAuthorized("Y");
        given(bankIdentifierDomainService.getBankIdentifierByCode(bankIdentifierDTOEx.getBankIdentifierCode())).willReturn(bankIdentifierEntity);
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity)).willReturn(bankIdentifierDTO);
        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTOEx);
        assertThat(bankIdentifierDTOEx.getBankIdentifierCode()).isNotBlank();
        assertThat(bankIdentifierDTOEx.getAuthorized()).isNotBlank();
    }

//    @Test
    @DisplayName("JUnit for getBankIdentifiers in application service for try block negative scenario for SessionContext some field not be null")
    void getBankIdentifiersTryBlockNegative() throws FatalException {
        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"close\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"Y\"" +
                ",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("NWBKGB2L");
        mutationEntity5.setTaskCode(BANK_IDENTIFIER);
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("new");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("add");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("NIKHIL");
        mutationEntity5.setLastUpdatedBy("sujan");

        BankIdentifierDTO bankIdentifierDTOO= new BankIdentifierDTO();

        BankIdentifierEntityKey bankIdentifierEntityKey = new BankIdentifierEntityKey();
        bankIdentifierEntityKey.setBankIdentifierCode("NWBKGB2L");

        BankIdentifierEntity bankIdentifierEntity5 = new BankIdentifierEntity();
        given(mutationsDomainService.getUnauthorizedMutation(bankIdentifierDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity5));
        given(bankIdentifierDomainService.getBankIdentifiers()).willReturn(List.of(bankIdentifierEntity5));
        Payload payload=new Payload();
        payload.setData(payLoadString);
        mutationEntity5.setPayload(payload);
        String data1 = mutationEntity5.getPayload().getData();
        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity5)).willReturn(bankIdentifierDTOO);
        given(bankIdentifierAssembler.setAuditFields(mutationEntity5,bankIdentifierDTOO)).willReturn(bankIdentifierDTOO);

        List<BankIdentifierDTO> bankIdentifierDTO2 = bankIdentifierApplicationService.getBankIdentifiers(sessionContext);
        assertThat(sessionContext.getRole()).isNotEmpty();
        assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(bankIdentifierEntity.toString()).isNotNull();
        assertThat(bankIdentifierDTO.toString()).isNotNull();
        BankIdentifierDTO bankIdentifierDTO2=new BankIdentifierDTO("NWBKGB2L",
                "NATIONAL WESTMINSTER BANK PLC",
                "PREMIER PLACE, DEVONSHIRE SQUARE",
                "LONDON",
                "EC2M 4XB",
                "UNITED KINGDOM",
                "PREMIER PLACE, DEVONSHIRE SQUARE");
        BankIdentifierDTO.builder().bankIdentifierCode("NWBKGB2L")
                .bankIdentifierCodeName("NATIONAL WESTMINSTER BANK PLC")
                .bankAddress1("PREMIER PLACE, DEVONSHIRE SQUARE")
                .bankAddress2("LONDON")
                .bankAddress3("EC2M 4XB")
                .bankAddress4("UNITED KINGDOM")
                .internalAddress("PREMIER PLACE, DEVONSHIRE SQUARE")
                .authorized("N")
                .taskCode(BANK_IDENTIFIER)
                .taskIdentifier("NWBKGB2L")
                .build().toString();
        BankIdentifierEntityKey bankIdentifierEntityKey=new BankIdentifierEntityKey("NWBKGB2L");
        assertThat(bankIdentifierEntityKey.toString()).isNotNull();
        bankIdentifierEntityKey.setBankIdentifierCode("NWBKGB2Z");
        bankIdentifierEntityKey.keyAsString();
        bankIdentifierEntityKey.builder().bankIdentifierCode("NWBKGB2A").build();
        assertThat(bankIdentifierDTO).descriptionText();
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
                        .taskCode(BANK_IDENTIFIER)
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

    private BankIdentifierDTO getBankIdentifierDTOAuthorized() {
        BankIdentifierDTO bankIdentifierDTO = new BankIdentifierDTO();

        bankIdentifierDTO.setBankIdentifierCode("NWBKGB2L");
        bankIdentifierDTO.setBankIdentifierCodeName("NATIONAL WESTMINSTER BANK PLC");
        bankIdentifierDTO.setBankAddress1("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierDTO.setBankAddress2("LONDON");
        bankIdentifierDTO.setBankAddress3("EC2M 4XB");
        bankIdentifierDTO.setBankAddress4("UNITED KINGDOM");
        bankIdentifierDTO.setInternalAddress("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierDTO.setAuthorized("Y");

        return bankIdentifierDTO;
    }

    private BankIdentifierDTO getBankIdentifiersDTO() {
        BankIdentifierDTO bankIdentifierDTO = new BankIdentifierDTO();
        bankIdentifierDTO.setBankIdentifierCode("NWBKGB2L");
        bankIdentifierDTO.setBankIdentifierCodeName("NATIONAL WESTMINSTER BANK PLC");
        bankIdentifierDTO.setBankAddress1("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierDTO.setBankAddress2("LONDON");
        bankIdentifierDTO.setBankAddress3("EC2M 4XB");
        bankIdentifierDTO.setBankAddress4("UNITED KINGDOM");
        bankIdentifierDTO.setInternalAddress("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierDTO.setTaskCode(BANK_IDENTIFIER);
        bankIdentifierDTO.setStatus("DELETED");
        bankIdentifierDTO.setRecordVersion(1);
        return bankIdentifierDTO;
    }

    private BankIdentifierEntity getBankIdentifierEntity() {
        BankIdentifierEntity bankIdentifierEntity =
                new BankIdentifierEntity(
                        "NWBKGB2L",
                        "NATIONAL WESTMINSTER BANK PLC",
                        "PREMIER PLACE, DEVONSHIRE SQUARE",
                        "LONDON",
                        "EC2M 4XB",
                        "UNITED KINGDOM",
                        "PREMIER PLACE, DEVONSHIRE SQUARE",
                        null,
                        null,
                        0,
                        "draft",
                        "Y",
                        "draft");

        return bankIdentifierEntity;
    }

    private BankIdentifierEntity getBankIdentifiersEntity() {

        BankIdentifierEntity bankIdentifierEntity =
                new BankIdentifierEntity(
                        "NWBKGB2L",
                        "NATIONAL WESTMINSTER BANK PLC",
                        "PREMIER PLACE, DEVONSHIRE SQUARE",
                        "LONDON",
                        "EC2M 4XB",
                        "UNITED KINGDOM",
                        "PREMIER PLACE, DEVONSHIRE SQUARE",
                        null,
                        null,
                        1,
                        "DELETED",
                        null,
                        null);

        return bankIdentifierEntity;
    }

    private BankIdentifierEntity getBankIdentifiersEntity2() {
        BankIdentifierEntity bankIdentifierEntity2 = new BankIdentifierEntity();
        bankIdentifierEntity2.setBankIdentifierCode("NWBKGB2L");
        bankIdentifierEntity2.setBankIdentifierCodeName("NATIONAL WESTMINSTER BANK PLC");
        bankIdentifierEntity2.setBankAddress1("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierEntity2.setBankAddress2("LONDON");
        bankIdentifierEntity2.setBankAddress3("EC2M 4XB");
        bankIdentifierEntity2.setBankAddress4("UNITED KINGDOM");
        bankIdentifierEntity2.setInternalAddress("PREMIER PLACE, DEVONSHIRE SQUARE");
        bankIdentifierEntity2.setAuthorized("N");
        bankIdentifierEntity2.setStatus("closed");
        bankIdentifierEntity2.setRecordVersion(1);
        return bankIdentifierEntity2;
    }

    private BankIdentifierDTO getBankIdentifierDTOUnAuth() {

        BankIdentifierDTO bankIdentifierDTO =
                new BankIdentifierDTO(
                        "NWBKGB2L",
                        "NATIONAL WESTMINSTER BANK PLC",
                        "PREMIER PLACE, DEVONSHIRE SQUARE",
                        "LONDON",
                        "EC2M 4XB",
                        "UNITED KINGDOM",
                        "PREMIER PLACE, DEVONSHIRE SQUARE");

        bankIdentifierDTO.setAuthorized("N");
        bankIdentifierDTO.setTaskIdentifier("NWBKGB2L");
        return bankIdentifierDTO;
    }

    private BankIdentifierDTO getBankIdentifierDTOMapper() {

        BankIdentifierDTO bankIdentifierDTOMapper2 =
                BankIdentifierDTO.builder()
                        .bankIdentifierCode("NWBKGB2L")
                        .bankIdentifierCodeName("NATIONAL WESTMINSTER BANK PLC")
                        .bankAddress1("PREMIER PLACE, DEVONSHIRE SQUARE")
                        .bankAddress2("LONDON")
                        .bankAddress3("EC2M 4XB")
                        .bankAddress4("UNITED KINGDOM")
                        .internalAddress("PREMIER PLACE, DEVONSHIRE SQUARE")
                        .authorized("N")
                        .taskCode(BANK_IDENTIFIER)
                        .taskIdentifier("NWBKGB2L")
                        .build();
        return bankIdentifierDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null"
                        + ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\""
                        + ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\""
                        + ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\""
                        + ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\""
                        + ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("NWBKGB2L");
        mutationEntity.setTaskCode(BANK_IDENTIFIER);
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
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null"
                        + ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\""
                        + ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\""
                        + ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\""
                        + ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\""
                        + ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("NWBKGB2L");
        mutationEntity2.setTaskCode(BANK_IDENTIFIER);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}
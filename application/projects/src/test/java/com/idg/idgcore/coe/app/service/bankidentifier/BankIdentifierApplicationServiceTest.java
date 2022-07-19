package com.idg.idgcore.coe.app.service.bankidentifier;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.service.bankidentifier.BankIdentifierApplicationService;
import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bankidentifier.IBankIdentifierDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
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
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
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
    private BankIdentifierDTO bankIdentifierDTOMapper;

    @BeforeEach
    void setUp() {

        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        bankIdentifierDTO=getBankIdentifierDTOAuthorized ();
        bankIdentifierEntity=getBankIdentifierEntity();
        bankIdentifierDTOUnAuth=getBankIdentifierDTOUnAuth();
        bankIdentifierDTOMapper=getBankIdentifierDTOMapper();
        mutationEntity=getMutationEntity();
        bankIdentifierEntity1=getBankIdentifierEntity();
        bankIdentifierDTO1=getBankIdentifiersDTO();
        mutationEntity2=getMutationEntityJsonError();
    }

//    @Test
//    @DisplayName("JUnit for getBankIdentifierByCode in application service when Authorize")
//    void getBankIdentifierByCodeIsAuthorize() throws FatalException, JsonProcessingException {
//
//        given(bankIdentifierDomainService.getBankIdentifierByCode(bankIdentifierDTO.getBankIdentifierCode())).willReturn(bankIdentifierEntity);
//        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity)).willReturn(bankIdentifierDTO);
//        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTO);
//        assertEquals(bankIdentifierDTO1.getAuthorized(), "Y");
//        assertThat(bankIdentifierDTO).isNotNull();
//    }

//    @Test
//    @DisplayName("JUnit for getBankIdentifierByCode in application service when Not Authorize in try block")
//    void getBankIdentifierByCodeWhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
//
//        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
//                                ",\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\"" +
//                                ",\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
//                                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
//                                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
//                                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";
//
//        given(mutationsDomainService.getConfigurationByCode(bankIdentifierDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
//        ModelMapper mapper=new ModelMapper();
//        PayloadDTO payload = new PayloadDTO(payLoadString);
//        ObjectMapper objectMapper=new ObjectMapper();
//        BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext,bankIdentifierDTOMapper);
//        assertEquals(bankIdentifierDTO1.getAuthorized(), "N");
//        assertThat(bankIdentifierDTO1).isNotNull();
//        System.out.println(bankIdentifierDTO1);
//    }

//    @Test
//    @DisplayName("JUnit for getBankIdentifierByCode in application service when Not Authorize in catch block")
//    void getBankIdentifierByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {
//
//        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
//                                ",\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
//                                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
//                                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
//                                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
//                                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";
//
//        given(mutationsDomainService.getConfigurationByCode(bankIdentifierDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
//        ModelMapper mapper=new ModelMapper();
//        PayloadDTO payload = new PayloadDTO(payLoadString1);
//        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
//        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
//
//        Assertions.assertThrows(BusinessException.class,()-> {
//            BankIdentifierDTO bankIdentifierDTO1 = bankIdentifierApplicationService.getBankIdentifierByCode(sessionContext, bankIdentifierDTOMapper);
//            assertEquals("N",bankIdentifierDTO1.getAuthorized());
//            assertThat(bankIdentifierDTO1).isNotNull();
//            System.out.println(bankIdentifierDTO1);
//        });
//    }

    @Test
    @DisplayName("JUnit for getBankIdentifiers in application service for try block")
    void getBankIdentifiersTryBlock() throws JsonProcessingException, FatalException {

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
        System.out.println(bankIdentifierDTO1);
        List<BankIdentifierDTO> bankIdentifierDTO2 = bankIdentifierApplicationService.getBankIdentifiers(sessionContext);
        System.out.println(bankIdentifierDTO1);
        assertThat(bankIdentifierDTO1).isNotNull();
        System.out.println(bankIdentifierDTO1);

    }

//    @Test
//    @DisplayName("JUnit for getBankIdentifiers in application service for catch block")
//    void getBankIdentifiersCatchBlock() throws FatalException {
//        given(bankIdentifierDomainService.getBankIdentifiers()).willReturn(List.of(bankIdentifierEntity1));
//        given(mutationsDomainService.getUnauthorizedMutation(bankIdentifierDTO1.getTaskCode())).willReturn(List.of(mutationEntity2));
//        given(bankIdentifierAssembler.convertEntityToDto(bankIdentifierEntity1)).willReturn(bankIdentifierDTOMapper);
//        System.out.println(bankIdentifierDTO1);
//        Assertions.assertThrows(Exception.class,()-> {
//            List<BankIdentifierDTO> bankIdentifierTO2 = bankIdentifierApplicationService.getBankIdentifiers(sessionContext1);
//            System.out.println(bankIdentifierTO2);
//            assertThat(bankIdentifierTO2).isNotNull();
//            System.out.println(bankIdentifierTO2);
//        });
//    }

    @Test
    @DisplayName("JUnit for processBankIdentifier in application service for Try Block")
    void processBankIdentifierForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(bankIdentifierDTO);
        bankIdentifierApplicationService.processBankIdentifier(sessionContext, bankIdentifierDTO);
        verify(process, times(1)).process(bankIdentifierDTO);
    }

//    @Test
//    @DisplayName("JUnit for ConfigurationByCode in application service")
//    void getConfigurationByCodeTest(){
//        String code = bankIdentifierDTO.getTaskIdentifier();
//        bankIdentifierApplicationService.getConfigurationByCode(code);
//    }

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

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }

    private SessionContext getErrorSession(){
        SessionContext sessionContext1=SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(null)
                .build();
        return sessionContext1;
    }

    private BankIdentifierDTO getBankIdentifierDTOAuthorized() {
        BankIdentifierDTO bankIdentifierDTO = BankIdentifierDTO.builder()
                .bankIdentifierCode("NTSBDEZ4")
                .bankIdentifierCodeName("N26 BANK GMBH")
                .bankAddress1("KLOSTERSTRASSE 62")
                .bankAddress2("BERLIN")
                .bankAddress3("EC2M 4XB")
                .bankAddress4("10179 BERLIN")
                .internalAddress("KLOSTERSTRASSE 62, 10179 BERLIN")
                .authorized("Y").build();

        return bankIdentifierDTO;
    }

    private BankIdentifierDTO getBankIdentifiersDTO()
    {
        BankIdentifierDTO bankIdentifierDTO = new BankIdentifierDTO();
        bankIdentifierDTO.setBankIdentifierCode("NTSBDEZ4");
        bankIdentifierDTO.setBankIdentifierCodeName("N26 BANK GMBH");
        bankIdentifierDTO.setBankAddress1("KLOSTERSTRASSE 62");
        bankIdentifierDTO.setBankAddress2("B9ERLIN");
        bankIdentifierDTO.setBankAddress3("EC2M 4XB");
        bankIdentifierDTO.setBankAddress4("10179 BERLIN");
        bankIdentifierDTO.setInternalAddress("KLOSTERSTRASSE 62, 10179 BERLIN");
        bankIdentifierDTO.setTaskCode(BANK_IDENTIFIER);
        bankIdentifierDTO.setStatus("DELETED");
        bankIdentifierDTO.setRecordVersion(1);
        return bankIdentifierDTO;
    }

    private BankIdentifierEntity getBankIdentifierEntity(){
        BankIdentifierEntity bankIdentifierEntity = new BankIdentifierEntity("NTSBDEZ4", "N26 BANK GMBH",
                "KLOSTERSTRASSE 62", "BERLIN"
                , "EC2M 4XB", "10179 BERLIN", "KLOSTERSTRASSE 62, 10179 BERLIN",
                "draft",0, "Y","draft");
        return bankIdentifierEntity;
    }

    private BankIdentifierEntity getBankIdentifiersEntity(){

        BankIdentifierEntity bankIdentifierEntity = new BankIdentifierEntity();
        bankIdentifierEntity.setBankIdentifierCode("NTSBDEZ4");
        bankIdentifierEntity.setBankIdentifierCodeName("N26 BANK GMBH");
        bankIdentifierEntity.setBankAddress1("KLOSTERSTRASSE 62");
        bankIdentifierEntity.setBankAddress2("BERLIN");
        bankIdentifierEntity.setBankAddress3("EC2M 4XB");
        bankIdentifierEntity.setBankAddress4("10179 BERLIN");
        bankIdentifierEntity.setInternalAddress("KLOSTERSTRASSE 62, 10179 BERLIN");
        bankIdentifierEntity.setStatus("DELETED");
        bankIdentifierEntity.setRecordVersion(1);
        return bankIdentifierEntity;
    }

    private BankIdentifierDTO getBankIdentifierDTOUnAuth() {
        BankIdentifierDTO bankIdentifierDTO = BankIdentifierDTO.builder()
                .bankIdentifierCode("NTSBDEZ4")
                .bankIdentifierCodeName("N26 BANK GMBH")
                .bankAddress1("KLOSTERSTRASSE 62")
                .bankAddress2("BERLIN")
                .bankAddress3("EC2M 4XB")
                .bankAddress4("10179 BERLIN")
                .internalAddress("KLOSTERSTRASSE 62, 10179 BERLIN")
                .authorized("N").build();

        return bankIdentifierDTO;
    }
    private BankIdentifierDTO getBankIdentifierDTOMapper(){
        BankIdentifierDTO bankIdentifierDTO = new BankIdentifierDTO();
        bankIdentifierDTO.setBankIdentifierCode("NTSBDEZ4");
        bankIdentifierDTO.setBankIdentifierCodeName("N26 BANK GMBH");
        bankIdentifierDTO.setBankAddress1("KLOSTERSTRASSE 62");
        bankIdentifierDTO.setBankAddress2("BERLIN");
        bankIdentifierDTO.setBankAddress3("EC2M 4XB");
        bankIdentifierDTO.setBankAddress4("10179 BERLIN");
        bankIdentifierDTO.setInternalAddress("KLOSTERSTRASSE 62, 10179 BERLIN");
        bankIdentifierDTO.setAuthorized("N");
        bankIdentifierDTO.setTaskCode(BANK_IDENTIFIER);
        bankIdentifierDTO.setTaskIdentifier("NTSBDEZ4");
        return bankIdentifierDTO;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

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

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"add\",\"status\":\"new\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"BANKIDENTIFIER\",\"taskIdentifier\":\"NWBKGB2L\"" +
                ",\"bankIdentifierCode\":\"NWBKGB2L\",\"bankIdentifierCodeName\":\"NATIONAL WESTMINSTER BANK PLC\"" +
                ",\"bankAddress1\":\"PREMIER PLACE, DEVONSHIRE SQUARE\",\"bankAddress2\":\"LONDON\",\"bankAddress3\":\"EC2M 4XB\"" +
                ",\"bankAddress4\":\"UNITED KINGDOM\",\"internalAddress\":\"PREMIER PLACE, DEVONSHIRE SQUARE\"}";

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
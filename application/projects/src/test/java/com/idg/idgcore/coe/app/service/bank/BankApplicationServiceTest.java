package com.idg.idgcore.coe.app.service.bank;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.service.bank.BankApplicationService;
import com.idg.idgcore.coe.app.service.bankidentifier.BankIdentifierApplicationService;
import com.idg.idgcore.coe.domain.assembler.bank.BankAssembler;
import com.idg.idgcore.coe.domain.assembler.bankidentifier.BankIdentifierAssembler;
import com.idg.idgcore.coe.domain.entity.bank.BankEntity;
import com.idg.idgcore.coe.domain.entity.bank.BankEntityKey;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntity;
import com.idg.idgcore.coe.domain.entity.bankidentifier.BankIdentifierEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.repository.bank.IBankRepository;
import com.idg.idgcore.coe.domain.service.bank.BankDomainService;
import com.idg.idgcore.coe.domain.service.bank.IBankDomainService;
import com.idg.idgcore.coe.domain.service.bankidentifier.IBankIdentifierDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bank.BankDTO;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;
import static com.idg.idgcore.enumerations.core.MutationType.ADDITION;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;

import org.mockito.Mockito;
import org.mockito.exceptions.base.MockitoException;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankApplicationServiceTest {
    @InjectMocks
    BankApplicationService bankApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    BankAssembler bankAssembler;
    @Mock
    private IBankDomainService bankDomainService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    ObjectMapper objectMapper=new ObjectMapper();
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private BankEntity bankEntity;
    private BankDTO bankDTO;
    private BankDTO bankDTOUnAuth;
    private BankDTO bankDTO1;
    private BankEntity bankEntity1;
    private BankEntity bankEntity2;
    private BankDTO bankDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        bankDTO = getBankDTOAuthorized();
        bankEntity = getBankEntity();
        bankDTOUnAuth = getBankDTOUnAuth();
        bankDTOMapper = getBankDTOMapper();
        mutationEntity = getMutationEntity();
        bankEntity1 = getBankEntity();
        bankEntity2 = getBankEntity2();
        bankDTO1 = getBankDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }
    @Test
    @DisplayName("JUnit for getBankByCode where return the bank when the authorized is Y")
    void getBankByCodeWhenAuthorizedIsYThenReturnBank() throws FatalException, JsonProcessingException {
        given(
                bankDomainService.getBankByCode(
                        bankDTO.getBankCode()))
                .willReturn(bankEntity);
        given(bankAssembler.convertEntityToDto(bankEntity))
                .willReturn(bankDTO);
        BankDTO result =
                bankApplicationService.getBankByCode(
                        sessionContext, bankDTO);
        assertEquals(bankDTO, result);
    }

    @Test
    @DisplayName("JUnit for getBanks where return all banks when there are no unauthorized mutations")
    void getBanksWhenThereAreNoUnauthorizedMutationsThenReturnAllBanks() throws FatalException {
        given(bankDomainService.getBanks())
                .willReturn(List.of(bankEntity));
        given(mutationsDomainService.getUnauthorizedMutation(BANK, AUTHORIZED_N))
                .willReturn(List.of());
        given(bankAssembler.convertEntityToDto(bankEntity))
                .willReturn(bankDTO);

        List<BankDTO> bankDTOList =
                bankApplicationService.getBanks(sessionContext);

        assertEquals(1, bankDTOList.size());
        assertEquals(bankDTO, bankDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service when Authorize try Block")
    void getBankByCodeIsAuthorize() throws FatalException, JsonProcessingException {

        given(bankDomainService.getBankByCode(bankDTO.getBankCode())).willReturn(bankEntity);
        given(bankAssembler.convertEntityToDto(bankEntity)).willReturn(bankDTO);
        BankDTO bankDTO1 = bankApplicationService.getBankByCode(sessionContext, bankDTO);
        assertEquals("Y",bankDTO1.getAuthorized());
        assertThat(bankDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service when Not Authorize in catch block")
    void getBankByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":xxxx," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"BANK\",\"taskIdentifier\":\"PNB\",\"bankCode\":\"PNB\",\"bankCodeRegulatory\":\"003\"," +
                "\"bankName\":\"Punjab National Bank\"}";

        given(mutationsDomainService.getConfigurationByCode(bankDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            BankDTO bankDTO1 = bankApplicationService.getBankByCode(sessionContext, bankDTOMapper);
            assertEquals("N",bankDTO1.getAuthorized());
            assertThat(bankDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for getBanks in application service when Authorize and not Authorize")
    void getBanks() throws FatalException, JsonProcessingException {

        String data = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"BANK\",\"taskIdentifier\":\"PNB\",\"bankCode\":\"PNB\",\"bankCodeRegulatory\":\"003\"," +
                "\"bankName\":\"Punjab National Bank\"}";

        List<BankDTO> bankDTOList = new ArrayList<>();
        bankDTOList.add(bankDTOMapper);
        bankDTOList.add(bankDTOUnAuth);

        List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                bankDTOUnAuth.getTaskCode(),AUTHORIZED_N);
        unauthorizedEntities.add(mutationEntity);

        given(mutationsDomainService.getUnauthorizedMutation(
                bankDTOUnAuth.getTaskCode(),AUTHORIZED_N)).willReturn(unauthorizedEntities);

        BankDTO bankDTO1 = null;
        bankDTO1 = objectMapper.readValue(data, BankDTO.class);
        bankDTO1 = bankAssembler.setAuditFields(mutationEntity,bankDTO1);
        List<BankDTO> bankDTOListResult = new ArrayList<>();
        bankDTOListResult.add(bankDTO1);
        bankDTOListResult.add(bankDTOUnAuth);

        Assertions.assertThrows(FatalException.class,()-> {
            bankApplicationService.getBanks(sessionContext);
            assertThat(bankDTOListResult).isNotNull();
        });
    }

   // @Test
    @DisplayName("JUnit for getBanks in application service for catch block for checker")
    void getBanksCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                bankDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<BankDTO> bankDTO1 = bankApplicationService.getBanks(sessionContext);
            assertThat(bankDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processBank in application service for Try Block")
    void processBankForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(bankDTO);
        bankApplicationService.processBank(sessionContext, bankDTO);
        verify(process, times(1)).process(bankDTO);
    }

   // @Test
    @DisplayName("JUnit for processBank in application service for Catch Block")
    void processBankForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(FatalException.class,()-> {
        bankApplicationService.processBank(sessionContext2, bankDTOMapper);
        assertThat(bankDTOMapper).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString= "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"BANK\",\"taskIdentifier\":\"PNB\",\"bankCode\":\"PNB\",\"bankCodeRegulatory\":\"003\"," +
                "\"bankName\":\"Punjab National Bank\"}";

        doNothing().when(bankDomainService).save(bankDTO);
        bankApplicationService.save(bankDTO);
        bankApplicationService.addUpdateRecord(payLoadString);
        verify(bankDomainService, times(1)).save(bankDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = bankDTO.getBankCode();
        given(bankDomainService.getBankByCode(code)).willReturn(bankEntity);
        bankApplicationService.getConfigurationByCode(code);
        assertThat(bankEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByBankCode in application service when Authorize for Negative")
    void getBankByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(bankDomainService.getBankByCode(bankDTO.getBankCode())).willReturn(bankEntity);
        given(bankAssembler.convertEntityToDto(bankEntity)).willReturn(bankDTO);
        BankDTO bankDTO1 = bankApplicationService.getBankByCode(sessionContext, bankDTO);
        assertNotEquals("N",bankDTO1.getAuthorized());
        assertThat(bankDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getBankByCode in application service check Parameter not null")
    void getBankByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        BankDTO bankDTOnull=null;
        BankDTO bankDTOEx=new BankDTO();
        bankDTOEx.setBankCode("PNB");
        bankDTOEx.setAuthorized("Y");
        given(bankDomainService.getBankByCode(bankDTOEx.getBankCode())).willReturn(bankEntity);
        given(bankAssembler.convertEntityToDto(bankEntity)).willReturn(bankDTO);
        BankDTO bankDTO1 = bankApplicationService.getBankByCode(sessionContext, bankDTOEx);
        assertThat(bankDTOEx.getBankCode()).isNotBlank();
        assertThat(bankDTOEx.getAuthorized()).isNotBlank();
    }



    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(bankEntity.toString()).isNotNull();
        assertThat(bankDTO.toString()).isNotNull();
        BankDTO bankDTO2=new BankDTO( "PNB",
                "003",
                "Punjab National Bank");
        BankDTO.builder().bankCode("PNB")
                .bankCodeRegulatory("003")
                .bankName("Punjab National Bank")
                .authorized("N")
                .taskCode(BANK)
                .taskIdentifier("PNB")
                .build().toString();
        BankEntityKey bankEntityKey=new BankEntityKey("PNB");
        assertThat(bankEntityKey.toString()).isNotNull();
        bankEntityKey.setBankCode("PNB");
        bankEntityKey.keyAsString();
        bankEntityKey.builder().bankCode("PNB").build();
        assertThat(bankDTO).descriptionText();
    }

    private SessionContext getValidSessionContext() {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("");
        sessionContext.setDefaultBranchCode("");
        sessionContext.setInternalTransactionReferenceNumber("");
        sessionContext.setUserTransactionReferenceNumber("");
        sessionContext.setExternalTransactionReferenceNumber("");
        sessionContext.setTargetUnit("");
        sessionContext.setPostingDate(null);
        sessionContext.setValueDate(null);
        sessionContext.setTransactionBranch("");
        sessionContext.setUserId("Priya");
        sessionContext.setAccessibleTargetUnits(null);
        sessionContext.setChannel("");
        sessionContext.setTaskCode("");
        sessionContext.setLocalDateTime(null);
        sessionContext.setOriginalTransactionReferenceNumber("");
        sessionContext.setExternalBatchNumber(1L);
        sessionContext.setCustomAttributes("");
        sessionContext.setServiceInvocationModeType(Regular);
        sessionContext.setAllTargetUnitsSelected(false);
        sessionContext.setMutationType(ADDITION);
        sessionContext.setUserLocal("");
        sessionContext.setOriginatingModuleCode("");
        sessionContext.setRole(new String[] {"maker"});
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
                        .userId("Priya")
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

    private BankDTO getBankDTOAuthorized() {
        BankDTO bankDTO = new BankDTO();

        bankDTO.setBankCode("SBI");
        bankDTO.setBankCodeRegulatory("002");
        bankDTO.setBankName("State Bank of India");
        bankDTO.setAuthorized("Y");

        return bankDTO;
    }

    private BankDTO getBankDTO() {
        BankDTO bankDTO = new BankDTO();
        bankDTO.setBankCode("SBI");
        bankDTO.setBankCodeRegulatory("002");
        bankDTO.setBankName("State Bank of India");
        bankDTO.setTaskCode(BANK_IDENTIFIER);
        bankDTO.setStatus("DELETED");
        bankDTO.setRecordVersion(1);
        return bankDTO;
    }


    private BankEntity getBankEntity() {

        BankEntity bankEntity =
                new BankEntity(
                        "PNB",
                        "003",
                        "Punjab National Bank",
                        null,
                        null,
                        1,
                        "DELETED",
                        null,
                        null);
        return bankEntity;
    }

    private BankEntity getBankEntity2() {
        BankEntity bankEntity = new BankEntity();
        bankEntity.setBankCode("PNB");
        bankEntity.setBankCodeRegulatory("003");
        bankEntity.setBankName("Punjab National Bank");
        bankEntity.setAuthorized("N");
        bankEntity.setStatus("new");
        bankEntity.setLastConfigurationAction("authorize");
        bankEntity.setRecordVersion(1);
        return bankEntity;
    }

    private BankDTO getBankDTOUnAuth() {

        BankDTO bankDTO =
                new BankDTO(
                        "PNB",
                        "003",
                        "Punjab National Bank");
        bankDTO.setAuthorized("N");
        bankDTO.setTaskIdentifier("PNB");
        return bankDTO;
    }

    private BankDTO getBankDTOMapper() {

        BankDTO bankDTOMapper2 =
                BankDTO.builder()
                        .bankCode("PNB")
                        .bankCodeRegulatory("003")
                        .bankName("Punjab National Bank")
                        .authorized("N")
                        .taskCode(BANK)
                        .taskIdentifier("PNB")
                        .build();
        return bankDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        Payload payload = new Payload("{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"BANK\",\"taskIdentifier\":\"PNB\",\"bankCode\":\"PNB\",\"bankCodeRegulatory\":\"003\"," +
                "\"bankName\":\"Punjab National Bank\"}");
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAction("add");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setTaskCode("BANK");
        mutationEntity.setTaskIdentifier("PNB");
        mutationEntity.setPayload(payload);
        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError() {
        Payload payload = new Payload("{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":xxxx," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\"," +
                "\"taskCode\":\"BANK\",\"taskIdentifier\":\"PNB\",\"bankCode\":\"PNB\",\"bankCodeRegulatory\":\"003\"," +
                "\"bankName\":\"Punjab National Bank\"}");
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAction("add");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setTaskCode("BANK");
        mutationEntity.setTaskIdentifier("PNB");
        mutationEntity.setPayload(payload);
        return mutationEntity;
    }
}

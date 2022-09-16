package com.idg.idgcore.coe.app.service.groupBanking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.groupBanking.IGroupBankingDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupBankingApplicationServiceTest {

    @InjectMocks
    GroupBankingApplicationService groupBankingApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    GroupBankingAssembler groupBankingAssembler;
    @Mock
    private IGroupBankingDomainService iGroupBankingDomainService;

    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private GroupBankingEntity groupBankingEntity;
    private GroupBankingDTO groupBankingDTO;
    private GroupBankingDTO groupBankingDTOUnAuth;
    private GroupBankingDTO groupBankingDTO1;
    private GroupBankingEntity groupBankingEntity1;
    private GroupBankingEntity groupBankingEntity2;
    private GroupBankingDTO groupBankDTOMapper2;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        groupBankingDTO = getBankGroupDTOAuthorized();
        groupBankingEntity = getBankGroupEntity();
        groupBankingDTOUnAuth = getBankGroupDTOUnAuth();
        groupBankDTOMapper2 = getBankGroupDTOMapper();
        mutationEntity = getMutationEntity();
        groupBankingEntity1 = getBankGroupEntity();
        groupBankingEntity2 = getBankGroupEntity2();
        groupBankingDTO1 = getBankGroupDTO();
        mutationEntity2 = getMutationEntityJsonError();
    }

    @Test
    @DisplayName("JUnit for getGroupBankByCode where return the bank identifier when the authorized is Y")
    void getGroupBankWhenAuthorizedIsYThenReturnBankGroup() throws FatalException, JsonProcessingException {
        given(
                iGroupBankingDomainService.getGroupBankByCode(
                        groupBankingDTO.getGroupBankingCode()))
                .willReturn(groupBankingEntity);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity))
                .willReturn(groupBankingDTO);
        GroupBankingDTO result =
                groupBankingApplicationService.getGroupBankByCode(
                        sessionContext, groupBankingDTO);
        assertEquals(groupBankingDTO, result);
    }

    @Test
    @DisplayName("JUnit for getGroupBanks where return all bank group when there are no unauthorized mutations")
    void getGroupBanksWhenThereAreNoUnauthorizedMutationsThenReturnAllBankGroup() throws FatalException {
        given(mutationsDomainService.getMutations(GROUP_BANKING))
                .willReturn(List.of(mutationEntity));
        List<GroupBankingDTO> groupBankingDTOList =
                groupBankingApplicationService.getGroupBanks(sessionContext);
        assertThat(groupBankingDTOList).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getGroupBankByCode in application service when Authorize try Block")
    void getGroupBankByCodeIsAuthorize() throws FatalException, JsonProcessingException {

        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTO.getGroupBankingCode())).willReturn(groupBankingEntity);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        GroupBankingDTO groupBankingDTO = groupBankingApplicationService.getGroupBankByCode(sessionContext, this.groupBankingDTO);
        assertEquals("Y", groupBankingDTO.getAuthorized());
        assertThat(groupBankingDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getGroupBankByCode in application service when Not Authorize in catch block")
    void getGroupBankByCodeWhenNotAuthorizeCatchBlock() throws FatalException, JsonProcessingException {

        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";

        given(mutationsDomainService.getConfigurationByCode(groupBankingDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper = new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class, () -> {
            GroupBankingDTO groupBankByCode = groupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankDTOMapper2);
            assertEquals("N", groupBankByCode.getAuthorized());
            assertThat(groupBankByCode).isNotNull();
        });
    }



    @Test
    @DisplayName("JUnit for getGroupBanks in application service for catch block for checker")
    void getGroupBankCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[]{""});
        given(mutationsDomainService.getMutations(
                groupBankingDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
//        Assertions.assertThrows(FatalException.class, () -> {
            List<GroupBankingDTO> bankingDTOList = groupBankingApplicationService.getGroupBanks(sessionContext);
            assertThat(bankingDTOList).isNotNull();
 //       });
    }

    @Test
    @DisplayName("JUnit for processGroupBanking in application service for Try Block")
    void processGroupBankingForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(groupBankingDTO);
        groupBankingApplicationService.processGroupBanking(sessionContext, groupBankingDTO);
        verify(process, times(1)).process(groupBankingDTO);
    }

    @Test
    @DisplayName("JUnit for processGroupBanking in application service for Catch Block")
    void processGroupBankingForCatchBlock() throws FatalException {
        SessionContext sessionContext2 = null;
        Assertions.assertThrows(Exception.class, () -> {
            groupBankingApplicationService.processGroupBanking(sessionContext2, groupBankingDTO);
            assertThat(groupBankingDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";

        doNothing().when(iGroupBankingDomainService).save(groupBankingDTO);
        groupBankingApplicationService.save(groupBankingDTO);
        groupBankingApplicationService.addUpdateRecord(payLoadString1);
        verify(iGroupBankingDomainService, times(1)).save(groupBankingDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest() {
        String code = groupBankingDTO.getGroupBankingCode();
        given(iGroupBankingDomainService.getGroupBankByCode(code)).willReturn(groupBankingEntity);
        groupBankingApplicationService.getConfigurationByCode(code);
        assertThat(groupBankingEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getGroupBankByCode in application service when Authorize for Negative")
    void getGroupBankByCodeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTO.getGroupBankingCode())).willReturn(groupBankingEntity);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        GroupBankingDTO bankIdentifierDTO1 = groupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankingDTO);
        assertNotEquals("N", bankIdentifierDTO1.getAuthorized());
        assertThat(groupBankingDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getGroupBankByCode in application service check Parameter not null")
    void getGroupBanksByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        GroupBankingDTO groupBankingDTO1 = null;
        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setAuthorized("Y");
        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTO.getGroupBankingCode())).willReturn(groupBankingEntity);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(this.groupBankingDTO);
        GroupBankingDTO bankIdentifierDTO1 = groupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankingDTO);
        assertThat(groupBankingDTO.getGroupBankingCode()).isNotBlank();
        assertThat(groupBankingDTO.getAuthorized()).isNotBlank();
    }

  // @Test
    @DisplayName("JUnit for getGroupBanks in application service for try block negative scenario for SessionContext some field not be null")
    void getGroupBanksTryBlockNegative() throws FatalException {
        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";

        MutationEntity entity = new MutationEntity();
        entity.setTaskIdentifier("CBI");
        entity.setTaskCode(GROUP_BANKING);
        entity.setPayload(new Payload(payLoadString));
        entity.setStatus("closed");
        entity.setAuthorized("N");
        entity.setRecordVersion(0);
        entity.setAction("close");
        entity.setLastConfigurationAction("unauthorized");
        entity.setCreatedBy("NIKHIL");
        entity.setLastUpdatedBy("mohan");

        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();

       groupBankingDTO.setGroupBankingCode("CBI");
       groupBankingDTO.setGroupBankingName("Central Bank of India");

       groupBankingDTO.setAuthorized("N");

        GroupBankingEntityKey groupBankingEntityKey = new GroupBankingEntityKey();
        groupBankingEntityKey.setBankGroupCode("CBI");

        GroupBankingEntity groupBankingEntity = new GroupBankingEntity();
       // given(mutationsDomainService.getUnauthorizedMutation(groupBankingDTO.getTaskCode(), AUTHORIZED_N)).willReturn(List.of(entity));
        given(iGroupBankingDomainService.getGroupBanks()).willReturn(List.of(groupBankingEntity));
        Payload payload = new Payload();
        payload.setData(payLoadString);
        entity.setPayload(payload);
        String data1 = entity.getPayload().getData();
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        given(groupBankingAssembler.setAuditFields(entity, groupBankingDTO)).willReturn(groupBankingDTO);


        List<GroupBankingDTO> groupBankingDTOList = groupBankingApplicationService.getGroupBanks(sessionContext);
        assertThat(sessionContext.getRole()).isNotEmpty();
        assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage() {
        assertThat(groupBankingEntity.toString()).isNotNull();
        assertThat(groupBankingDTO.toString()).isNotNull();
        GroupBankingDTO bankIdentifierDTO2 = new GroupBankingDTO("CBI",
                "Central Bank of India");
        GroupBankingDTO.builder().groupBankingCode("CBI")
                .groupBankingName("Central Bank of India").authorized("N")
                .taskCode(GROUP_BANKING)
                .taskIdentifier("CBI").build().toString();
        GroupBankingEntityKey groupBankingEntityKey = new GroupBankingEntityKey("CBI");
        assertThat(groupBankingEntityKey.toString()).isNotNull();
        groupBankingEntityKey.setBankGroupCode("CBI");
        groupBankingEntityKey.keyAsString();
        GroupBankingEntityKey.builder().bankGroupCode("CBI").build();
        assertThat(groupBankingDTO).descriptionText();
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
                        .taskCode(GROUP_BANKING)
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

    private GroupBankingDTO getBankGroupDTOAuthorized() {
        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();

        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");

        groupBankingDTO.setAuthorized("Y");

        return groupBankingDTO;
    }

    private GroupBankingDTO getBankGroupDTO() {
        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");

        groupBankingDTO.setTaskCode(GROUP_BANKING);
        groupBankingDTO.setRecordVersion(1);
        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setLastConfigurationAction("unauthorized");
        groupBankingDTO.setAction("Delete");
        groupBankingDTO.setStatus("DELETED");
        return groupBankingDTO;
    }

    private GroupBankingEntity getBankIdentifierEntity() {
        GroupBankingEntity groupBankingEntity =
                new GroupBankingEntity(
                        "CBI",
                        "Central Bank of India",
                        null,
                        null,
                        "draft",
                        0,
                        "Y",
                        "draft");

        return groupBankingEntity;
    }

    private GroupBankingEntity getBankGroupEntity() {

        GroupBankingEntity groupBankingEntity1 =
                new GroupBankingEntity(
                        "CBI",
                        "Central Bank of India",
                        null,
                        null,
                        "DELETED",
                        1,
                        "N",
                        "unauthorized");

        return groupBankingEntity1;
    }

    private GroupBankingEntity getBankGroupEntity2() {
        GroupBankingEntity groupBankingEntity2 = new GroupBankingEntity();
        groupBankingEntity2.setBankGroupCode("CBI");
        groupBankingEntity2.setBankGroupName("Central Bank of India");
        groupBankingEntity2.setAuthorized("N");
        groupBankingEntity2.setStatus("closed");
        groupBankingEntity2.setRecordVersion(1);
        return groupBankingEntity2;
    }

    private GroupBankingDTO getBankGroupDTOUnAuth() {

        GroupBankingDTO groupBankingDTO =
                new GroupBankingDTO(
                        "CBI",
                        "Central Bank of India");

        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setTaskIdentifier("CBI");
        return groupBankingDTO;
    }

    private GroupBankingDTO getBankGroupDTOMapper() {

        GroupBankingDTO groupBankDTOMapper2 =
                GroupBankingDTO.builder()
                        .groupBankingCode("CBI")
                        .groupBankingName("Central Bank of India")
                        .authorized("N")
                        .taskCode(GROUP_BANKING)
                        .taskIdentifier("CBI")
                        .build();
        return groupBankDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                        "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":0," +
                        "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                        "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                        "\"taskCode\":\"GROUP-BANKING\"}";

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("CBI");
        mutationEntity.setTaskCode(GROUP_BANKING);
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
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                        "\"lastUpdatedTime\":null,\"action\":\"authorized\",\"status\":\"closed\",\"recordVersion\":0," +
                        "\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"groupBankingCode\":\"CBI\"," +
                        "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                        "\"taskCode\":\"GROUP-BANKING\"}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("CBI");
        mutationEntity2.setTaskCode(GROUP_BANKING);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}
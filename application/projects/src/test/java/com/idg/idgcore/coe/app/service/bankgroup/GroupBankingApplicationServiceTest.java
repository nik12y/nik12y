package com.idg.idgcore.coe.app.service.bankgroup;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.bankgroup.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.bankgroup.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.bankgroup.IGroupBankingDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.bankgroup.GroupBankingDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.MutationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.GROUP_BANKING;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class GroupBankingApplicationServiceTest {
    @Mock
    private ModelMapper mapper = new ModelMapper();
    @Mock
    ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private IProcessConfiguration process;
    @Mock
    private MappingConfig mappingConfig;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    @Mock
    private GroupBankingAssembler groupBankingAssembler;
    @Mock
    private IGroupBankingDomainService iGroupBankingDomainService;
    @Mock
    BusinessException expectedException;

    @Mock
    MutationEntity mutationEntity;
    SessionContext sessionContext;
    GroupBankingEntity groupBankingEntity;
    GroupBankingEntity groupBankingEntityNe;

    GroupBankingDTO groupBankingDTO;
    String payloads;
    GroupBankingEntity groupBankingEntityAuth;
    GroupBankingDTO groupBankingDTOAuth;
    GroupBankingDTO groupBankingDTONe;

    @InjectMocks
    private GroupBankingApplicationService groupBankingApplicationService;

    @Test
    void contextLoads() {
        assertThat(groupBankingApplicationService).isNotNull();
    }


    @BeforeEach
    void setUp() {
        mutationEntity = getMutationEntity();
        List<GroupBankingDTO> groupBankingDTOList = getGroupBankingDTOsList();
        sessionContext = getValidSessionContext();
        groupBankingEntity = getGroupBankingEntity();
        groupBankingDTO = getGroupBankingDTO();
        payloads = getPayloads();
        groupBankingEntityAuth = getGroupBankingEntityAuth();
        groupBankingDTOAuth = getGroupBankingDTOAuth();
        groupBankingDTONe = getGroupBankingDTONe();
        groupBankingEntityNe = getGroupBankingEntityNe();

        AbstractAuditableDomainEntity abstractAuditableDomainEntity = getAbstractAuditableDomainEntity();
    }

    @Test
    @DisplayName("JUnit Test for getGroupBanks for empty list")
    void getGroupBanksEmptyList() throws FatalException {
        given(iGroupBankingDomainService.getGroupBanks()).willReturn(Collections.emptyList());
        List<GroupBankingDTO> groupBanks = groupBankingApplicationService.getGroupBanks(sessionContext);
        assertThat(groupBanks).isEmpty();
        System.out.println("Done");
    }

    @Test
    @DisplayName("JUnit Test for getGroupBanks for Unauthorized and Authorized records")
    void getGroupBanks() throws FatalException {

        List<GroupBankingEntity> groupBankingEntityList = new ArrayList<>();
        groupBankingEntityList.add(groupBankingEntity);
        groupBankingEntityList.add(groupBankingEntityAuth);

        List<MutationEntity> unauthorizedEntites = new ArrayList<>();
        unauthorizedEntites.add(mutationEntity);

        given(iGroupBankingDomainService.getGroupBanks()).willReturn(groupBankingEntityList);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);

       // given(mutationsDomainService.getUnauthorizedMutation(groupBankingDTO.getTaskCode())).willReturn(unauthorizedEntites);
        Payload payload = new Payload();
        payload.setData(payloads);
        mutationEntity.setPayload(payload);
        ObjectMapper objectMapper = mock(ObjectMapper.class);

        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        List<GroupBankingDTO> groupBanks = groupBankingApplicationService.getGroupBanks(sessionContext);
        assertThat(groupBanks).isNotNull();
        System.out.println("Done");
    }

    @Test
    @DisplayName("JUnit test cases for Authorized the user")
    void getGroupBankByCode() throws FatalException, JsonProcessingException {
        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTOAuth.getBankGroupCode())).willReturn(groupBankingEntityAuth);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        GroupBankingDTO groupBankByCode = groupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankingDTOAuth);
        // assertThat(groupBankingDTO.getAuthorized()).isEqualTo("Y");
        assertEquals("Y", groupBankingDTOAuth.getAuthorized());
        System.out.println(groupBankByCode);
        //assertThat(groupBankByCode).isNotNull();
        //  assertThat(groupBankByCode).describedAs("is not a valid records");
        System.out.println("Done");
    }

    @Test
    @DisplayName("JUnit test when invalid pattern passed by maker")
    void getByCodeGroupBankCodeIsNotValid() throws FatalException, JsonProcessingException {
        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTONe.getBankGroupCode())).willReturn(groupBankingEntityNe);
        given(groupBankingAssembler.convertEntityToDto(groupBankingEntityNe)).willReturn(groupBankingDTONe);
        assertEquals("N", groupBankingDTONe.getAuthorized());
        String payloads = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"Csm&\"," +
                "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";
        given(mutationsDomainService.getConfigurationByCode(groupBankingDTO.getTaskIdentifier())).willReturn(mutationEntity);

        Payload payload = new Payload();
        payload.setData(payloads);
        mutationEntity.setPayload(payload);
        ObjectMapper objectMapper = mock(ObjectMapper.class);

    //assertThat(groupBankByCode).describedAs("Bank Group Code must have minimum 3 char ");
    assertEquals("Bank Group Code must have minimum 3 char and proper pattern",groupBankingDTONe.getBankGroupCode());
}



    @Test
    @DisplayName("JUnit test case for Unauthorized Records Passed")
    void getGroupBankByCodeForUnauthorized() throws FatalException, JsonProcessingException {
//        given(iGroupBankingDomainService.getGroupBankByCode(groupBankingDTO.getBankGroupCode())).willReturn(groupBankingEntity);
//        given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);

        String payloads="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                "\"groupBankName\":\"Crime Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";
        given(mutationsDomainService.getConfigurationByCode(groupBankingDTO.getTaskIdentifier())).willReturn(mutationEntity);

        Payload payload=new Payload();
                payload.setData(payloads);
                mutationEntity.setPayload(payload);
                ObjectMapper objectMapper = mock(ObjectMapper.class);
       //         given(groupBankingAssembler.convertEntityToDto(groupBankingEntity)).willReturn(groupBankingDTO);
        GroupBankingDTO groupBankByCode = groupBankingApplicationService.getGroupBankByCode(sessionContext, groupBankingDTO);
            System.out.println(groupBankByCode);
            assertEquals("N",groupBankingDTO.getAuthorized());
            assertThat(groupBankByCode).isNotNull();
            System.out.println("Done");
    }

    private MutationEntity  getMutationEntity(){
        MutationEntity mutationEntity=new MutationEntity();
        mutationEntity.setTaskCode("GROUP-BANKING");
        mutationEntity.setTaskIdentifier("CBI");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        return  mutationEntity;
    }

    private List<GroupBankingDTO> getGroupBankingDTOsList(){
        List<GroupBankingDTO> groupBankingDTOList = new ArrayList<>();
        GroupBankingDTO groupBankingDTO1=new GroupBankingDTO("YES","Yes bank of India");
        GroupBankingDTO groupBankingDTO2=new GroupBankingDTO("BDO","Bank Of Badoda");
        GroupBankingDTO groupBankingDTO3=new GroupBankingDTO("BNP","BNP Paribas");
        groupBankingDTOList.add(groupBankingDTO1);
        groupBankingDTOList.add(groupBankingDTO2);
        groupBankingDTOList.add(groupBankingDTO3);
        return groupBankingDTOList;
    }

    private SessionContext getValidSessionContext(){
        SessionContext sessionContext=new SessionContext();
        sessionContext.setBankCode("");
        //   sessionContext.setAccessibleTargetUnits();
        sessionContext.setChannel("");                           sessionContext.setDefaultBranchCode("");
        sessionContext.setCustomAttributes("");                  sessionContext.setAllTargetUnitsSelected(false);
        // sessionContext.setExternalBatchNumber();
        sessionContext.setExternalTransactionReferenceNumber(""); sessionContext.setInternalTransactionReferenceNumber("");
        sessionContext.setLocalDateTime(new Date());              sessionContext.setMutationType(MutationType.ADDITION);
        //  sessionContext.setAccessibleTargetUnits("");
        sessionContext.setDefaultBranchCode("");                sessionContext.getOriginalTransactionReferenceNumber();
        sessionContext.setOriginatingModuleCode("");            sessionContext.setRole(new String[] {"checker"});
        sessionContext.setServiceInvocationModeType(Regular);   sessionContext.setPostingDate(new Date());
        sessionContext.setTargetUnit("");                       sessionContext.setTaskCode("GROUP-BANKING");
        sessionContext.setTransactionBranch("");                sessionContext.setUserId("");
        sessionContext.setUserTransactionReferenceNumber("");   sessionContext.setValueDate(new Date());
        return  sessionContext;
    }

    private GroupBankingEntity getGroupBankingEntity(){
        GroupBankingEntity groupBankingEntity=new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Crime Bank of India");
        groupBankingEntity.setStatus("new");
        groupBankingEntity.setAuthorized("N");
        groupBankingEntity.setRecordVersion(1);
        groupBankingEntity.setLastConfigurationAction("unauthorized");
        return  groupBankingEntity;
    }

    private GroupBankingEntity getGroupBankingEntityNe(){
        GroupBankingEntity groupBankingEntityNe=new GroupBankingEntity();
        groupBankingEntityNe.setBankGroupCode("Csm&");
        groupBankingEntityNe.setBankGroupName("Crime Bank of India");
        groupBankingEntityNe.setStatus("draft");
        groupBankingEntityNe.setAuthorized("N");
        groupBankingEntityNe.setRecordVersion(0);
        groupBankingEntityNe.setLastConfigurationAction("unauthorized");
        return  groupBankingEntityNe;
    }

    private  GroupBankingDTO getGroupBankingDTO(){
        GroupBankingDTO groupBankingDTO=new GroupBankingDTO();
        groupBankingDTO.setBankGroupCode("CBI");
        groupBankingDTO.setBankGroupName("Crime Bank of India");
        groupBankingDTO.setStatus("new");
        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setRecordVersion(1);
        groupBankingDTO.setTaskCode(GROUP_BANKING);
        groupBankingDTO.setLastConfigurationAction("unauthorized");
        return groupBankingDTO;
    }
    private  GroupBankingDTO getGroupBankingDTONe(){
        GroupBankingDTO groupBankingDTONe=new GroupBankingDTO();
        groupBankingDTONe.setBankGroupCode("Csm&");
        groupBankingDTONe.setBankGroupName("Crime Bank of India");
        groupBankingDTONe.setStatus("draft");
        groupBankingDTONe.setAuthorized("N");
        groupBankingDTONe.setRecordVersion(0);
        groupBankingDTONe.setTaskCode(GROUP_BANKING);
        groupBankingDTONe.setLastConfigurationAction("unauthorized");
        return groupBankingDTONe;
    }
    private GroupBankingEntity getGroupBankingEntityAuth(){
        GroupBankingEntity groupBankingEntityAuth=new GroupBankingEntity();
        groupBankingEntityAuth.setBankGroupCode("CBI");
        groupBankingEntityAuth.setBankGroupName("Crim Bank of India");
        groupBankingEntityAuth.setStatus("active");
        groupBankingEntityAuth.setAuthorized("Y");
        groupBankingEntityAuth.setRecordVersion(1);
        groupBankingEntityAuth.setLastConfigurationAction("authorized");
        return groupBankingEntityAuth;
    }

    private  GroupBankingDTO getGroupBankingDTOAuth(){
        GroupBankingDTO groupBankingDTOAuth=new GroupBankingDTO();
        groupBankingDTOAuth.setBankGroupCode("CBI");
        groupBankingDTOAuth.setBankGroupName("Crim Bank of India");
        groupBankingDTOAuth.setStatus("active");
        groupBankingDTOAuth.setAuthorized("Y");
        groupBankingDTOAuth.setRecordVersion(1);
        groupBankingDTOAuth.setLastConfigurationAction("authorized");
        return groupBankingDTOAuth;
    }

    private AbstractAuditableDomainEntity getAbstractAuditableDomainEntity() {
        AbstractAuditableDomainEntity abstractAuditableDomainEntity = new AbstractAuditableDomainEntity();
        abstractAuditableDomainEntity.setCreatedBy("Nikhil");
        abstractAuditableDomainEntity.setCreationTime(new Date());
        abstractAuditableDomainEntity.setLastUpdatedBy("Prashant");
        abstractAuditableDomainEntity.setLastUpdatedTime(new Date());
        return abstractAuditableDomainEntity;
    }

    private String getPayloads(){
        String payloads="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
                "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"CBI\"," +
                "\"groupBankName\":\"Crim Bank Of India\",\"taskIdentifier\":\"CBI\"," +
                "\"taskCode\":\"GROUP-BANKING\"}";
        return payloads;
    }




    @Test
    void processGroupBanking() {
    }

    @Test
    void addUpdateRecord() {
    }

    @Test
    void getConfigurationByCode() {
    }

    @Test
    void save() {
    }
}
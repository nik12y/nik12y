package com.idg.idgcore.coe.app.service.branchtype;//package com.idg.idgcore.coe.app.service.branchtype;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.idg.idgcore.coe.app.config.MappingConfig;
//import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
//
//import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
//import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
//import com.idg.idgcore.coe.domain.entity.mutation.Payload;
//import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
//
//import com.idg.idgcore.coe.domain.service.branchtype.IBranchTypeDomainService;
//import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
//import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
//import com.idg.idgcore.datatypes.exceptions.BusinessException;
//import com.idg.idgcore.datatypes.exceptions.FatalException;
//import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
//import com.idg.idgcore.dto.context.SessionContext;
//import com.idg.idgcore.enumerations.core.MutationType;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.modelmapper.ModelMapper;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.BDDMockito.given;
//import static org.mockito.Mockito.mock;
//
//@ExtendWith(MockitoExtension.class)
//class BranchTypeApplicationServiceTest {
//
//    @Mock
//    private ModelMapper mapper = new ModelMapper();
//    @Mock
//    ObjectMapper objectMapper = new ObjectMapper();
//    @Mock
//    private IProcessConfiguration process;
//    @Mock
//    private MappingConfig mappingConfig;
//    @Mock
//    private IMutationsDomainService mutationsDomainService;
//    @Mock
//    private BranchTypeAssembler branchTypeAssembler;
//    @Mock
//    private IBranchTypeDomainService iBranchTypeDomainService;
//    @Mock
//    BusinessException expectedException;
//
//    @Mock
//    MutationEntity mutationEntity;
//    SessionContext sessionContext;
//    BranchTypeEntity branchTypeEntity;
//    BranchTypeDTO branchTypeDTO;
//    String payloads;
//    BranchTypeEntity branchTypeEntityAuth;
//    BranchTypeDTO branchTypeDTOAuth;
//
//    @InjectMocks
//    private BranchTypeApplicationService branchTypeApplicationService;
//
//    @BeforeEach
//    void setUp() {
//        mutationEntity = getMutationEntity();
//        List<BranchTypeDTO> branchTypeDTOList = getBranchTypeDTOsList();
//        sessionContext = getValidSessionContext();
//        branchTypeEntity = getBranchTypeEntity();
//        branchTypeEntity.setAuthorized("Y");
//        branchTypeDTO = getGroupBankingDTO();
//        payloads = getPayloads();
//        branchTypeEntityAuth = getGroupBankingEntityAuth();
//        branchTypeDTOAuth = getGroupBankingDTOAuth();
//
//        AbstractAuditableDomainEntity abstractAuditableDomainEntity = getAbstractAuditableDomainEntity();
//
//        @Test
//        @DisplayName("JUnit Test for getBranchType for for empty list")
//        void getBranchTypeByCode () throws FatalException {
//            given(iBranchTypeDomainService.getBranchTypeByCode(branchTypeDTOAuth.getBranchTypeCode())).willReturn(branchTypeEntityAuth);
//            given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
//            BranchTypeDTO groupBankByCode = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTOAuth);
//            // assertThat(groupBankingDTO.getAuthorized()).isEqualTo("Y");
//            assertEquals("Y", branchTypeDTOAuth.getAuthorized());
//            assertThat(groupBankByCode).isNotNull();
//            System.out.println("Done");
//        }
//
//        @Test
//        @DisplayName("JUnit Test for getGroupBanks for Unauthorized and Authorized records")
//        void getBranchType () throws FatalException {
//
//            List<BranchTypeEntity> branchTypeEntityList = new ArrayList<>();
//            branchTypeEntityList.add(branchTypeEntity);
//            branchTypeEntityList.add(branchTypeEntityAuth);
//
//            List<BranchTypeDTO> groupBankingDTOList = new ArrayList<>();
//            groupBankingDTOList.add(branchTypeDTO);
//            groupBankingDTOList.add(branchTypeDTOAuth);
//
//            List<MutationEntity> unauthorizedEntites = new ArrayList<>();
//            unauthorizedEntites.add(mutationEntity);
//
//            given(iBranchTypeDomainService.getBranchType()).willReturn(branchTypeEntityList);
//            given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
//            //Now code for unauthorized records
//            given(mutationsDomainService.getUnauthorizedMutation(branchTypeDTO.getTaskCode())).willReturn(unauthorizedEntites);
//            Payload payload = new Payload();
//            payload.setData(payloads);
//            mutationEntity.setPayload(payload);
//            ObjectMapper objectMapper = mock(ObjectMapper.class);
////        when(objectMapper.readValue(data, GroupBankingEntity.class)).thenReturn(groupBankingEntity);
//            given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
//            List<BranchTypeDTO> groupBanks = branchTypeApplicationService.getbranchType(sessionContext);
//            assertThat(groupBanks).isNotNull();
//            System.out.println("Done");
//        }
//
//        @Test
//        @DisplayName("JUnit test cases for Authorized the user")
//        void getGroupBankByCode () throws FatalException, JsonProcessingException {
//            given(iBranchTypeDomainService.getBranchTypeByCode(branchTypeDTOAuth.getBankGroupCode())).willReturn(branchTypeEntityAuth);
//            given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
//            BranchTypeDTO groupBankByCode = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTOAuth);
//            // assertThat(groupBankingDTO.getAuthorized()).isEqualTo("Y");
//            assertEquals("Y", branchTypeDTOAuth.getAuthorized());
//            assertThat(groupBankByCode).isNotNull();
//            System.out.println("Done");
//        }
//
//
//        private MutationEntity getMutationEntity () {
//            MutationEntity mutationEntity = new MutationEntity();
//            mutationEntity.setTaskCode("BRANCH-TYPE");
//            mutationEntity.setTaskIdentifier("BNP");
//            mutationEntity.setLastConfigurationAction("Deleted");
//            mutationEntity.setAuthorized("N");
//            mutationEntity.setRecordVersion(1);
//            mutationEntity.setStatus("Deleted");
//            mutationEntity.setAction("Delete");
//            return mutationEntity;
//        }
//
//
//        private List<BranchTypeDTO> getGroupBankingDTOsList () {
//            List<BranchTypeDTO> branchTypeDTOList = new ArrayList<>();
//            BranchTypeDTO branchTypeDTOO1 = new BranchTypeDTO("CBB", "Corporate Banking Branch");
//            BranchTypeDTO branchTypeDTOO2 = new BranchTypeDTO("PCL", "Personal & Consumer Lending Branch");
//            BranchTypeDTO branchTypeDTOO3 = new BranchTypeDTO("CLR", "Clearing Branch");
//            branchTypeDTOList.add(branchTypeDTOO1);
//            branchTypeDTOList.add(branchTypeDTOO2);
//            branchTypeDTOList.add(branchTypeDTOO3);
//            return branchTypeDTOList;
//        }
//
//
//        private SessionContext getValidSessionContext () {
//            SessionContext sessionContext = new SessionContext();
//            sessionContext.setBankCode("");
//            //   sessionContext.setAccessibleTargetUnits();
//            sessionContext.setChannel("");
//            sessionContext.setDefaultBranchCode("");
//            sessionContext.setCustomAttributes("");
//            sessionContext.setAllTargetUnitsSelected(false);
//            // sessionContext.setExternalBatchNumber();
//            sessionContext.setExternalTransactionReferenceNumber("");
//            sessionContext.setInternalTransactionReferenceNumber("");
//            sessionContext.setLocalDateTime(new Date());
//            sessionContext.setMutationType(MutationType.ADDITION);
//            //  sessionContext.setAccessibleTargetUnits("");
//            sessionContext.setDefaultBranchCode("");
//            sessionContext.getOriginalTransactionReferenceNumber();
//            sessionContext.setOriginatingModuleCode("");
//            sessionContext.setRole(new String[]{"maker"});
//            sessionContext.setServiceInvocationModeType(Regular);
//            sessionContext.setPostingDate(new Date());
//            sessionContext.setTargetUnit("");
//            sessionContext.setTaskCode("GROUP-BANKING");
//            sessionContext.setTransactionBranch("");
//            sessionContext.setUserId("");
//            sessionContext.setUserTransactionReferenceNumber("");
//            sessionContext.setValueDate(new Date());
//            return sessionContext;
//
//
//        }
//
//
//        private GroupBankingEntity getGroupBankingEntity(){
//            GroupBankingEntity groupBankingEntity=new GroupBankingEntity();
//            groupBankingEntity.setBankGroupCode("CBI");
//            groupBankingEntity.setBankGroupName("Crime Bank of India");
//            groupBankingEntity.setStatus("new");
//            groupBankingEntity.setAuthorized("N");
//            groupBankingEntity.setRecordVersion(1);
//            groupBankingEntity.setLastConfigurationAction("unauthorized");
//
//            return  groupBankingEntity;
//        }
//        private GroupBankingEntity getGroupBankingEntityAuth(){
//            GroupBankingEntity groupBankingEntityAuth=new GroupBankingEntity();
//            groupBankingEntityAuth.setBankGroupCode("CBI");
//            groupBankingEntityAuth.setBankGroupName("Crim Bank of India");
//            groupBankingEntityAuth.setStatus("active");
//            groupBankingEntityAuth.setAuthorized("Y");
//            groupBankingEntityAuth.setRecordVersion(1);
//            groupBankingEntityAuth.setLastConfigurationAction("authorized");
//            return groupBankingEntityAuth;
//        }
//
//        private  GroupBankingDTO getGroupBankingDTO(){
//            GroupBankingDTO groupBankingDTO=new GroupBankingDTO();
//            groupBankingDTO.setBankGroupCode("CBI");
//            groupBankingDTO.setBankGroupName("Crime Bank of India");
//            groupBankingDTO.setStatus("new");
//            groupBankingDTO.setAuthorized("N");
//            groupBankingDTO.setRecordVersion(1);
//            groupBankingDTO.setLastConfigurationAction("unauthorized");
//            return groupBankingDTO;
//        }
//
//        private  GroupBankingDTO getGroupBankingDTOAuth(){
//            GroupBankingDTO groupBankingDTOAuth=new GroupBankingDTO();
//            groupBankingDTOAuth.setBankGroupCode("CBI");
//            groupBankingDTOAuth.setBankGroupName("Crim Bank of India");
//            groupBankingDTOAuth.setStatus("active");
//            groupBankingDTOAuth.setAuthorized("Y");
//            groupBankingDTOAuth.setRecordVersion(1);
//            groupBankingDTOAuth.setLastConfigurationAction("authorized");
//            return groupBankingDTOAuth;
//        }
//
//        private AbstractAuditableDomainEntity getAbstractAuditableDomainEntity() {
//            AbstractAuditableDomainEntity abstractAuditableDomainEntity = new AbstractAuditableDomainEntity();
//            abstractAuditableDomainEntity.setCreatedBy("Nikhil");
//            abstractAuditableDomainEntity.setCreationTime(new Date());
//            abstractAuditableDomainEntity.setLastUpdatedBy("Prashant");
//            abstractAuditableDomainEntity.setLastUpdatedTime(new Date());
//            return abstractAuditableDomainEntity;
//        }
//
//        private String getPayloads(){
//            String payloads="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null," +
//                    "\"lastUpdatedTime\":null,\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1," +
//                    "\"authorized\":\"N\",\"lastConfigurationAction\":\"unauthorized\",\"groupBankingCode\":\"SBI\"," +
//                    "\"groupBankName\":\"State Bank Of India\",\"taskIdentifier\":\"SBI\"," +
//                    "\"taskCode\":\"GROUP-BANKING\"}";
//            return payloads;
//        }
//
//
//        @Test
//        void getBranches () {
//        }
//
//        @Test
//        void processBranchType () {
//        }
//
//        @Test
//        void addUpdateRecord () {
//        }
//
//        @Test
//        void getConfigurationByCode () {
//        }
//
//        @Test
//        void save () {
//        }
//    }
//}
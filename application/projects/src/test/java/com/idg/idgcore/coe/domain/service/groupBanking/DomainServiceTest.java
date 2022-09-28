package com.idg.idgcore.coe.domain.service.groupBanking;


import com.idg.idgcore.coe.app.service.groupBanking.GroupBankingApplicationService;
import com.idg.idgcore.coe.domain.assembler.groupBanking.GroupBankingAssembler;
import com.idg.idgcore.coe.domain.entity.groupBanking.GroupBankingEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.repository.groupBanking.IGroupBankingRepository;
import com.idg.idgcore.coe.dto.groupBanking.GroupBankingDTO;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.MutationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.GROUP_BANKING;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DomainServiceTest {
    @Mock
    private IGroupBankingRepository iGroupBankingRepository;

    @InjectMocks
    private GroupBankingDomainService groupBankingDomainService;

    @Mock
    private GroupBankingAssembler groupBankingAssembler;

    @Mock
    MutationEntity mutationEntity;
    SessionContext sessionContext;
    GroupBankingEntity groupBankingEntity;
    GroupBankingEntity bankGroupEntityNe;

    GroupBankingDTO questionDTO;
    String payloads;
    GroupBankingEntity bankGroupEntityAuth;
    GroupBankingDTO bankGroupDTOAuth;
    GroupBankingDTO bankGroupDTONe;

    @InjectMocks
    private GroupBankingApplicationService groupBankingApplicationService;

    @Test
    void contextLoads() {
        assertThat(groupBankingApplicationService).isNotNull();
    }


    @BeforeEach
    void setUp() {
        mutationEntity = getMutationEntity();
        List<GroupBankingDTO> questionDTOList = getBankGroupDTOsList();
        sessionContext = getValidSessionContext();
        groupBankingEntity = getBankGroupEntity();
        questionDTO = getBankGroupDTO();
       // payloads = getPayloads();
        bankGroupEntityAuth = getBankGroupEntityAuth();
        bankGroupDTOAuth = getBankGroupDTOAuth();
        bankGroupDTONe = getBankGroupDTONe();
        bankGroupEntityNe = getBankGroupEntityNe();
   //     AbstractAuditableDomainEntity abstractAuditableDomainEntity = getAbstractAuditableDomainEntity();
    }

    @Test
    @DisplayName("Junit test for getGroupBankByCode")
    void getGroupBankByCodeReturnEntity() {

        given(iGroupBankingRepository.findByBankGroupCode("CBI")).willReturn(groupBankingEntity);
        GroupBankingEntity groupBankByCode = groupBankingDomainService.getEntityByIdentifier(groupBankingEntity.getBankGroupCode());
        assertThat(groupBankByCode).isNotNull();
    }

    @Test
    @DisplayName("Junit test for getConfigurationByCode")
    void getConfigurationByCodeReturnsQuestionEntity(){
        groupBankingEntity =new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");
        GroupBankingDTO  groupBankingDTO=new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");
        given(iGroupBankingRepository.findByBankGroupCode("CBI")).willReturn(groupBankingEntity);
        GroupBankingEntity groupBankingEntity = groupBankingDomainService.getEntityByIdentifier(groupBankingDTO.getGroupBankingCode());
        assertThat(groupBankingEntity).isNotNull();
    }

    @Test
    @DisplayName("Junit test for getGroupBankByCode")
    void getGroupBankByCodeReturnQuestionEntityObject(){
        groupBankingEntity =new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");

        given(iGroupBankingRepository.findByBankGroupCode("CBI")).willReturn(groupBankingEntity);
        GroupBankingEntity groupBankByCode1 = groupBankingDomainService.getEntityByIdentifier(groupBankingEntity.getBankGroupCode());
        assertThat(groupBankByCode1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getGroupBanks for negative scenario")
    void getGroupBanksReturnQuestionEntityObject() {
        groupBankingEntity = new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");

        given(iGroupBankingRepository.findAll()).willReturn(Collections.emptyList());
        List<GroupBankingEntity> questionEntityList = groupBankingDomainService.getAllEntities();
        assertThat(questionEntityList).isEmpty();
        assertThat(questionEntityList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("JUnit test for getBanksGroup list")
    void getBanksGroupReturnListOfGroupBanks(){
        groupBankingEntity = new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");

        given(iGroupBankingRepository.findAll()).willReturn(List.of(groupBankingEntity));
        List<GroupBankingEntity> questions = groupBankingDomainService.getAllEntities();
        assertThat(questions).isNotNull();
        assertThat(questions.size()).isEqualTo(1);
    }
    @Test
    @DisplayName("JUnit test for save BankGroup")
    void BankGroupReturnTrue() {
        groupBankingEntity = new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");

        GroupBankingDTO groupBankingDTO = new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");
        given(groupBankingAssembler.toEntity(groupBankingDTO)).willReturn(groupBankingEntity);
        when(iGroupBankingRepository.save(any(GroupBankingEntity.class))).thenReturn(groupBankingEntity);
        groupBankingDomainService.save(groupBankingDTO);
        assertThat(groupBankingEntity).isNotNull();
    }
    private MutationEntity  getMutationEntity(){
        MutationEntity mutationEntity=new MutationEntity();
        mutationEntity.setTaskCode(GROUP_BANKING);
        mutationEntity.setTaskIdentifier("CBI");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        return  mutationEntity;
    }

    private List<GroupBankingDTO> getBankGroupDTOsList() {
        List<GroupBankingDTO> groupBankingDTOList = new ArrayList<>();
        GroupBankingDTO groupBankingDTO = new GroupBankingDTO("CBI","Central Bank of India");
        groupBankingDTOList.add(groupBankingDTO);
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
        sessionContext.setOriginatingModuleCode("");            sessionContext.setRole(new String[] {"maker"});
        sessionContext.setServiceInvocationModeType(Regular);   sessionContext.setPostingDate(new Date());
        sessionContext.setTargetUnit("");                       sessionContext.setTaskCode(GROUP_BANKING);
        sessionContext.setTransactionBranch("");                sessionContext.setUserId("");
        sessionContext.setUserTransactionReferenceNumber("");   sessionContext.setValueDate(new Date());
        return  sessionContext;
    }

    private GroupBankingEntity getBankGroupEntity(){
        GroupBankingEntity groupBankingEntity=new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank Of India");

        groupBankingEntity.setStatus("new");
        groupBankingEntity.setAuthorized("N");
        groupBankingEntity.setRecordVersion(1);
        groupBankingEntity.setLastConfigurationAction("unauthorized");
        return  groupBankingEntity;
    }

    private GroupBankingEntity getBankGroupEntityNe(){
        GroupBankingEntity groupBankingEntity=new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank Of India");
        groupBankingEntity.setStatus("draft");
        groupBankingEntity.setAuthorized("N");
        groupBankingEntity.setRecordVersion(0);
        groupBankingEntity.setLastConfigurationAction("unauthorized");
        return  groupBankingEntity;
    }

    private  GroupBankingDTO getBankGroupDTO(){
        GroupBankingDTO groupBankingDTO=new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");
        groupBankingDTO.setStatus("new");
        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setRecordVersion(1);
        groupBankingDTO.setTaskCode(GROUP_BANKING);
        groupBankingDTO.setLastConfigurationAction("unauthorized");
        return groupBankingDTO;
    }
    private  GroupBankingDTO getBankGroupDTONe(){
        GroupBankingDTO groupBankingDTO=new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");

        groupBankingDTO.setStatus("draft");
        groupBankingDTO.setAuthorized("N");
        groupBankingDTO.setRecordVersion(0);
        groupBankingDTO.setTaskCode(GROUP_BANKING);
        groupBankingDTO.setLastConfigurationAction("unauthorized");
        return groupBankingDTO;
    }
    private GroupBankingEntity getBankGroupEntityAuth(){
        GroupBankingEntity groupBankingEntity=new GroupBankingEntity();
        groupBankingEntity.setBankGroupCode("CBI");
        groupBankingEntity.setBankGroupName("Central Bank of India");

        groupBankingEntity.setStatus("active");
        groupBankingEntity.setAuthorized("Y");
        groupBankingEntity.setRecordVersion(1);
        groupBankingEntity.setLastConfigurationAction("authorized");
        return groupBankingEntity;
    }

    private GroupBankingDTO getBankGroupDTOAuth(){
        GroupBankingDTO groupBankingDTO=new GroupBankingDTO();
        groupBankingDTO.setGroupBankingCode("CBI");
        groupBankingDTO.setGroupBankingName("Central Bank of India");
        groupBankingDTO.setStatus("active");
        groupBankingDTO.setAuthorized("Y");
        groupBankingDTO.setRecordVersion(1);
        groupBankingDTO.setLastConfigurationAction("authorized");
        return groupBankingDTO;
    }
}














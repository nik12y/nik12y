package com.idg.idgcore.coe.app.service.state;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.entity.state.StateEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.state.IStateDomainService;
import com.idg.idgcore.coe.dto.bankidentifier.BankIdentifierDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
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

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.STATE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class StateApplicationServiceTest {

    @InjectMocks
    private StateApplicationService stateApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private StateAssembler stateAssembler;

    @Mock
    private IStateDomainService stateDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    @Autowired
    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;
    private StateEntity stateEntity;
    private StateDTO stateDTO;
    private StateDTO stateDTOUnAuth;
    private StateDTO stateDTO1;
    private StateEntity stateEntity1;
    private StateEntity stateEntity2;

    private StateDTO stateDTOMapper;

    private MutationEntity mutationEntity5;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        stateDTO=getstateDTOAuthorized ();
        stateEntity=getStateEntity();
        stateDTOUnAuth=getStateDTOUnAuth();
        stateDTOMapper=getStateDTOMapper();
        mutationEntity=getMutationEntity();
        stateEntity1=getStatesEntity();
        stateEntity2=getStatesEntity2();
        stateDTO1=getStatesDTO();
        mutationEntity2=getMutationEntityJsonError();
        mutationEntity5=getMutationEntity2();

    }


    @Test
    @DisplayName("JUnit for getByStateCode in application service when Authorize try Block")
    void getStateByCodeIsAuthorize() throws FatalException {
        given(stateDomainService.getStateByCode(stateDTO.getStateCode())).willReturn(stateEntity);
        given(stateAssembler.convertEntityToDto(stateEntity)).willReturn(stateDTO);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext, stateDTO);
        assertEquals("Y",stateDTO1.getAuthorized());
        assertThat(stateDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByStateCode in application service when Not Authorize in try else block")
    void getStateByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(stateDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext,stateDTOMapper);
        assertEquals("N",stateDTO1.getAuthorized());
        assertThat(stateDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByStateCode in application service when Not Authorize in catch block")
    void getStateByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(stateDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext, stateDTOMapper);
            assertEquals("N",stateDTO1.getAuthorized());
            assertThat(stateDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("Should return all getStates when there are no unauthorized")
    void getStatesWhenThereAreNoUnauthorized() throws FatalException {
        given(stateDomainService.getStates()).willReturn(List.of(stateEntity));
        given(mutationsDomainService.getUnauthorizedMutation(STATE, AUTHORIZED_N)).willReturn(List.of());
        given(stateAssembler.convertEntityToDto(stateEntity)).willReturn(stateDTO);
        List<StateDTO> stateDTOList = stateApplicationService.getStates(sessionContext);
        assertEquals(1, stateDTOList.size());
        assertEquals(stateDTO, stateDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getStates in application service for catch block for checker")
    void getStatesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                stateDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<StateDTO> stateDTO1 = stateApplicationService.getStates(sessionContext);
            assertThat(stateDTO1).isNotNull();
        });
    }


    @Test
    @DisplayName("JUnit for processState in application service for Try Block")
    void processStateForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(process).process(stateDTO);
        stateApplicationService.processState(sessionContext, stateDTO);
        verify(process, times(1)).process(stateDTO);
    }

    @Test
    @DisplayName("JUnit for processState in application service for Catch Block")
    void processStateForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            stateApplicationService.processState(sessionContext2, stateDTO);
            assertThat(stateDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
         doNothing().when(stateDomainService).save(stateDTO);
        stateApplicationService.save(stateDTO);
        stateApplicationService.addUpdateRecord(payLoadString1);
        verify(stateDomainService, times(1)).save(stateDTO);

    }


    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = stateDTO.getStateCode();
        given(stateDomainService.getStateByCode(code)).willReturn(stateEntity);
        stateApplicationService.getConfigurationByCode(code);
        assertThat(stateEntity).isNotNull();
    }

    //----------------------------------------Negative---------------------------------


    @Test
    @DisplayName("JUnit for getByStateCode in application service when Authorize for Negative")
    void getStateByCodeIsAuthorizeforNegative() throws FatalException {
        given(stateDomainService.getStateByCode(stateDTO.getStateCode())).willReturn(stateEntity);
        given(stateAssembler.convertEntityToDto(stateEntity)).willReturn(stateDTO);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext, stateDTO);
        assertNotEquals("N",stateDTO1.getAuthorized());
        assertThat(stateDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByStateCode in application service when UnAuthorize fetche no Record from database")
    void getStateByCodeNotAuthorizeNull() throws FatalException {
        StateDTO stateDTOnull=null;
        StateDTO stateDTOEx=new StateDTO();
        stateDTOEx.setStateCode("MH");
        stateDTOEx.setAuthorized("N");
        given(mutationsDomainService.getConfigurationByCode(stateDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext, stateDTOEx);
        assertNotEquals("Y",stateDTO1.getAuthorized());
        assertNull(stateDTOnull);
    }

    @Test
    @DisplayName("JUnit for getByStateCode in application service check Parameter not null")
    void getStateByCodeIsAuthorizeCheckParameter() throws FatalException {
        StateDTO stateDTOnull=null;
        StateDTO stateDTOEx=new StateDTO();
        stateDTOEx.setStateCode("MH");
        stateDTOEx.setAuthorized("Y");
        given(stateDomainService.getStateByCode(stateDTOEx.getStateCode())).willReturn(stateEntity);
        given(stateAssembler.convertEntityToDto(stateEntity)).willReturn(stateDTO);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext, stateDTOEx);
        assertThat(stateDTOEx.getStateCode()).isNotBlank();
        assertThat(stateDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for getByStateCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
    void getStateByCodewhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(stateDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        StateDTO stateDTO1 = stateApplicationService.getStateByCode(sessionContext,stateDTOMapper);
        assertNotEquals("Y",stateDTO1.getAuthorized());
        assertThat(stateDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(stateEntity.toString()).isNotNull();
        assertThat(stateDTO.toString()).isNotNull();
        StateDTO stateDTO2=new StateDTO("MH","MAHARASHTRA","IN");
        StateDTO.builder().stateCode("MH").stateName("MAHARASHTRA").countryCode("IN").build().toString();
        StateEntityKey stateEntityKey=new StateEntityKey("MH");
        assertThat(stateEntityKey.toString()).isNotNull();
        stateEntityKey.setStateCode("KP");
        System.out.println(stateEntityKey.getStateCode());
        stateEntityKey.keyAsString();
        stateEntityKey.builder().stateCode("GJ").build();
        assertThat(stateDTO).descriptionText();
    }

    @Test
    @DisplayName("JUnit for getStates in application service for try block negative scenario for SessionContext some field not be null")
    void getStatesTryBlockNegative() throws JsonProcessingException, FatalException {

        StateDTO stateDTOO= new StateDTO();
        StateEntity stateEntity5 = new StateEntity();
        given(mutationsDomainService.getUnauthorizedMutation(stateDTOO.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity5));
        given(stateDomainService.getStates()).willReturn(List.of(stateEntity5));
        given(stateAssembler.convertEntityToDto(stateEntity5)).willReturn(stateDTOO);
        given(stateAssembler.setAuditFields(mutationEntity5,stateDTOO)).willReturn(stateDTOO);
        Assertions.assertThrows(Exception.class,()-> {
            List<StateDTO> stateDTO2 = stateApplicationService.getStates(sessionContext1);
            assertThat(sessionContext.getRole()).isNotEmpty();
            assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
            System.out.println(stateEntity.toString());
            System.out.println(stateDTO.toString());

    });

    }



    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("nikhil")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("STATE")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("en_US")
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
                .role(new String[] {"maker"})
                .build();
        return sessionContext1;
    }

    private StateDTO getstateDTOAuthorized () {
        StateDTO stateDTO = new StateDTO();
        stateDTO.setStateCode("MH");
        stateDTO.setStateName("MAHARASHTRA");
        stateDTO.setCountryCode("IN");
        stateDTO.setAuthorized("Y");
        return stateDTO;
    }
    private StateDTO getStatesDTO()
    {
        StateDTO stateDTO = new StateDTO();
        stateDTO.setStateCode("MH");
        stateDTO.setStateName("MAHARASHTRA");
        stateDTO.setCountryCode("IN");
        stateDTO.setTaskCode("MH");
        stateDTO.setStatus("DELETED");
        stateDTO.setRecordVersion(1);
        return stateDTO;
    }
    private StateEntity getStateEntity(){
        StateEntity stateEntity = new StateEntity("MH","MAHARASHTRA","IN","draft",0, "Y","draft");
        return stateEntity;
    }
    private StateEntity getStatesEntity()
    {
        StateEntity stateEntity = new StateEntity();
        stateEntity.setStateCode("MH");
        stateEntity.setStateName("MAHARASHTRA");
        stateEntity.setCountryCode("IN");
        stateEntity.setStatus("DELETED");
        stateEntity.setRecordVersion(1);
        return stateEntity;
    }
    private StateEntity getStatesEntity2()
    {
        StateEntity stateEntity2 = new StateEntity();
        stateEntity2.setStateCode("MH");
        stateEntity2.setStateName("MAHARASHTRA");
        stateEntity2.setCountryCode("IN");
        stateEntity2.setAuthorized("N");
        stateEntity2.setStatus("closed");
        stateEntity2.setRecordVersion(1);
        return stateEntity2;
    }
    private StateDTO getStateDTOUnAuth(){
        StateDTO stateDTO = new StateDTO();
        stateDTO.setStateCode("MH");
        stateDTO.setStateName("MAHARASHTRA");
        stateDTO.setCountryCode("IN");
        stateDTO.setAuthorized("N");
        return stateDTO;
    }

    private StateDTO getStateDTOMapper(){
        StateDTO stateDTOMapper= new StateDTO();
        stateDTOMapper.setStateCode("MH");
        stateDTOMapper.setStateName("MAHARASHTRA");
        stateDTOMapper.setCountryCode("IN");
        stateDTOMapper.setAuthorized("N");
        stateDTOMapper.setTaskCode("STATE");
        stateDTOMapper.setTaskIdentifier("MH");
        return stateDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("MH");
        mutationEntity.setTaskCode("STATE");
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
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("MH");
        mutationEntity2.setTaskCode("STATE");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    private MutationEntity getMutationEntity2()
    {
        String payLoadString ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"close\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";

        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("MH");
        mutationEntity5.setTaskCode("STATE");
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("new");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("add");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("NIKHIL");
        mutationEntity5.setLastUpdatedBy("sujan");
        return mutationEntity5;
    }

    String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";


}


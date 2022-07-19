package com.idg.idgcore.coe.app.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.app.service.state.StateApplicationService;
import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.state.IStateDomainService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;

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

    private StateDTO stateDTOMapper;

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
        stateDTO1=getStatesDTO();
        mutationEntity2=getMutationEntityJsonError();

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
        System.out.println(stateDTO1);
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
                .serviceInvocationModeType(Regular)
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
//        stateEntity.setStateCode("MH");
//        stateEntity.setStateName("MAHARASHTRA");
//        stateEntity.setCountryCode("IN");
//        stateEntity.setAuthorized("Y");
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

}


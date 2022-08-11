package com.idg.idgcore.coe.domain.service;

import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.repository.state.IStateRepository;
import com.idg.idgcore.coe.domain.service.state.StateDomainService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class StateDomainServiceTest {
    @Mock
    private IStateRepository stateRepository;

    @InjectMocks
    private StateDomainService stateDomainService;

    private StateEntity stateEntity;
    private StateDTO stateDTO;


    @BeforeEach
    void setUp() {
        stateDTO=getStateDTO ();
        stateEntity=getStateEntity();
    }

    @Test
    @DisplayName("Junit test for getStates method ")
    void getStatesReturnStatesList() {
        given(stateRepository.findAll()).willReturn(List.of(stateEntity));
        List<StateEntity> stateEntityList = stateDomainService.getStates();
        assertThat(stateEntityList).isNotNull();
        assertThat(stateEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getStates method for negative scenario")
    void getStatesEmptyStateEntityList()
    {
        given(stateRepository.findAll()).willReturn(Collections.emptyList());
        List<StateEntity> stateEntityList = stateDomainService.getStates();
        assertThat(stateEntityList).isEmpty();
        assertThat(stateEntityList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("JUnit test for getStateById method")
   void getStateByCodeReturnStateEntityObject() {
        given(stateRepository.findByStateCode("MH")).willReturn(stateEntity);
        StateEntity stateEntity1 =stateDomainService.getStateByCode(stateEntity.getStateCode());
        assertThat(stateEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getStateById catch block method")
    void getStateByCodeReturnCatchBolock() {
        StateEntity stateEntity1=null;

        assertThrows(Exception.class,()-> {
            StateEntity stateEntity2 = stateDomainService.getStateByCode(stateEntity1.getStateCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(stateRepository.findByStateCode("MH")).willReturn(stateEntity);
        StateEntity stateByCode = stateDomainService.getConfigurationByCode(stateDTO);
        assertThat(stateByCode).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
     void getConfigurationByCodeCatchBlock() {
         StateDTO stateDTO = null;
        assertThrows(BusinessException.class,()-> {
            StateEntity stateByCode = stateDomainService.getConfigurationByCode(stateDTO);
        });
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
     void getSaveCodeCatchBlock() {
        StateDTO stateDTO = null;
        assertThrows(Exception.class,()-> {
            stateDomainService.save(stateDTO);
        });
    }


    private StateEntity getStateEntity()
    {
        StateEntity stateEntity=new StateEntity();
        stateEntity.setStateCode("MH");
        stateEntity.setStateName("MAHARASHTRA");
        stateEntity.setCountryCode("IN");
        return stateEntity;
    }

    private StateDTO getStateDTO()
    {
        StateDTO stateDTO=new StateDTO();
        stateDTO.setStateCode("MH");
        stateDTO.setStateName("MAHARASHTRA");
        stateDTO.setCountryCode("IN");
        return stateDTO;
    }

}
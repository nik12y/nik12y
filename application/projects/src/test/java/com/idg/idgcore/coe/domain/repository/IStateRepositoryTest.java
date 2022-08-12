package com.idg.idgcore.coe.domain.repository;//package com.idg.idgcore.coe.domain.repository;
//
//import com.idg.idgcore.coe.domain.entity.state.StateEntity;
//import com.idg.idgcore.coe.domain.repository.state.IStateRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class IStateRepositoryTest {
//
//    @Autowired
//    private IStateRepository stateRepository;
//
//    private StateEntity stateEntity;
//
//
//    @Test
//    @DisplayName("JUnit test for get state by stateCode operation")
//    public void findByStateCode() {
//       StateEntity stateEntity = new StateEntity();
//        stateEntity.setStateCode("MH");
//        stateEntity.setStateName("MAHARASHTRA");
//        stateEntity.setCountryCode("IN");
//        stateEntity.setRecordVersion(0);
//        stateEntity.setStatus("new");
//        stateEntity.setAuthorized("N");
//        stateEntity.setLastConfigurationAction("draft");
//        stateRepository.save(stateEntity);
//        StateEntity stateEntity1 = stateRepository.findByStateCode(stateEntity.getStateCode());
//        assertThat(stateEntity1).isNotNull();
//    }
//
//    @Test
//    @DisplayName("JUnit for getStates for StateList")
//        public void findAll()
//    {
//        StateEntity stateEntity1 = new StateEntity();
//        stateEntity1.setStateCode("MH");
//        stateEntity1.setStateName("MAHARASHTRA");
//        stateEntity1.setCountryCode("IN");
//        stateEntity1.setRecordVersion(0);
//        stateEntity1.setStatus("new");
//        stateEntity1.setAuthorized("N");
//        stateEntity1.setLastConfigurationAction("draft");
//
//        stateRepository.save(stateEntity1);
////        given(stateRepository.findAll()).willReturn(List.of(stateEntity1));
//        List<StateEntity> stateEntityList = stateRepository.findAll();
//        assertThat(stateEntityList).isNotNull();
//        assertThat(stateEntityList.size()).isGreaterThan(1);
//    }
//    @Test
//    @DisplayName("JUnit for getStates for StateList")
//    public void findAllForNull()
//    {
//        StateEntity stateEntity1 = new StateEntity();
//        stateEntity1.setStateCode("MH");
//        stateEntity1.setStateName("MAHARASHTRA");
//        stateEntity1.setCountryCode("IN");
//        stateEntity1.setRecordVersion(0);
//        stateEntity1.setStatus("new");
//        stateEntity1.setAuthorized("N");
//        stateEntity1.setLastConfigurationAction("draft");
//
//        stateRepository.save(stateEntity1);
//        List<StateEntity> stateEntityList = stateRepository.findAll();
//        assertThat(stateEntityList).isNotNull();
//        assertThat(stateEntityList.size()).isGreaterThan(1);
//    }
//
//    @Test
//    @DisplayName("JUnit test for getStates method for negative scenario")
//    public void getStatesEmptyStateEntityList()
//    {
//
//
//
//        StateEntity stateEntity = new StateEntity();
//        stateEntity.setStateCode("MH");
//        stateEntity.setStateName("MAHARASHTRA");
//        stateEntity.setCountryCode("IN");
//        stateEntity.setRecordVersion(0);
//        stateEntity.setStatus("new");
//        stateEntity.setAuthorized("N");
//        stateEntity.setLastConfigurationAction("draft");
//
//        stateRepository.save(stateEntity);
//
//        List<StateEntity> stateEntityList = stateRepository.findAll();
//        assertThat(stateEntityList.size()).isNotNull();
//
//    }
//    @DisplayName("JUnit test for update bank identifier operation")
//    @Test
//    public void givenStateObject_whenUpdateState_thenReturnUpdatedState() {
//        StateEntity stateEntity = new StateEntity();
//        stateEntity.setStateCode("MH");
//        stateEntity.setStateName("MAHARASHTRA");
//        stateEntity.setCountryCode("IN");
//        stateEntity.setRecordVersion(0);
//        stateEntity.setStatus("new");
//        stateEntity.setAuthorized("N");
//        stateEntity.setLastConfigurationAction("draft");
//
//        stateRepository.save(stateEntity);
//
//        StateEntity savedState = stateRepository.findByStateCode(stateEntity.getStateCode());
//        savedState.setStateName("MAHARASHTRA");
//
//        StateEntity updatedState = stateRepository.save(savedState);
//
//        assertThat(updatedState.getStateName()).isEqualTo("MAHARASHTRA");
//
//    }
//
//}
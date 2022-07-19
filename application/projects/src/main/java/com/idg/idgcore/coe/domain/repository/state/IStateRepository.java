package com.idg.idgcore.coe.domain.repository.state;


import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.entity.state.StateEntityKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IStateRepository extends JpaRepository<StateEntity, StateEntityKey> {
    StateEntity findByStateCode (String stateCode);
}

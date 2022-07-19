package com.idg.idgcore.coe.domain.service.state;


import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.dto.state.StateDTO;

import java.util.List;

public interface IStateDomainService {
    StateEntity getConfigurationByCode (StateDTO stateDTO);
    List<StateEntity> getStates ();
    StateEntity getStateByCode (String stateCode);
    void save (StateDTO stateDTO);

}

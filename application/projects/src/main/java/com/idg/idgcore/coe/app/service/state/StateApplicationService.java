package com.idg.idgcore.coe.app.service.state;


import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.service.state.StateDomainService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.STATE;

@Slf4j
@Service("stateApplicationService")
public class StateApplicationService extends GenericApplicationService<StateDTO, StateEntity,
        StateDomainService, StateAssembler> {

    protected StateApplicationService() {
        super(STATE);
    }

    public String getTaskCode () {
        return StateDTO.builder().build().getTaskCode();
    }
}


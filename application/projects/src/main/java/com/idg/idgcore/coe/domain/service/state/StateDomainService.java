package com.idg.idgcore.coe.domain.service.state;

import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.repository.state.IStateRepository;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class StateDomainService implements IStateDomainService {
    @Autowired
    private IStateRepository stateRepository;

    @Autowired
    private StateAssembler stateAssembler;

    public StateEntity getConfigurationByCode (StateDTO stateDTO) {
        StateEntity stateEntity = null;
        try {
            stateEntity = this.stateRepository.findByStateCode(stateDTO.getStateCode());
        }
        catch (Exception e){
            if (log.isErrorEnabled())
            {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return stateEntity;
    }

    public List<StateEntity> getStates () {
        return this.stateRepository.findAll();
    }

    public StateEntity getStateByCode (String stateCode) {
        StateEntity stateEntity = null;
        try {
            stateEntity = this.stateRepository.findByStateCode(stateCode);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return stateEntity;
    }

    public void save (StateDTO stateDTO) {
        try {
            StateEntity stateEntity = stateAssembler.convertDtoToEntity(stateDTO);
            this.stateRepository.save(stateEntity);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


package com.idg.idgcore.coe.domain.service.state;

import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.repository.state.IStateRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class StateDomainService extends DomainService<StateDTO, StateEntity> {

    @Autowired
    private IStateRepository stateRepository;

    @Autowired
    private StateAssembler stateAssembler;

    @Override
    public StateEntity getEntityByIdentifier(String stateCode) {
        StateEntity stateEntity = null;
        try {
            stateEntity = this.stateRepository.findByStateCode(stateCode);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return stateEntity;
    }

    @Override
    public List<StateEntity> getAllEntities() {
        return this.stateRepository.findAll();
    }

    public void save (StateDTO stateDTO) {
        try {
            StateEntity stateEntity = stateAssembler.toEntity(stateDTO);
            this.stateRepository.save(stateEntity);
        } catch (Exception e){
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}


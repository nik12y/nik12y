package com.idg.idgcore.coe.app.service.state;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.state.IStateDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.state.StateDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("stateApplicationService")
public class StateApplicationService extends AbstractApplicationService
        implements IStateApplicationService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IStateDomainService stateDomainService;
    @Autowired
    private StateAssembler stateAssembler;

    public StateDTO getStateByCode(SessionContext sessionContext, StateDTO stateDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In getStateByCode with parameters sessionContext{}, stateDTO {}",
                    sessionContext, stateDTO);
        }

       TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        StateDTO result = null;
       try{
           if (isAuthorized(stateDTO.getAuthorized())) {
               StateEntity stateEntity = stateDomainService.getStateByCode(
                       stateDTO.getStateCode());
               result = stateAssembler.convertEntityToDto(stateEntity);
           }
           else {
                 MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                       stateDTO.getTaskIdentifier());
                 ObjectMapper objectMapper = new ObjectMapper();
                 PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                 result = objectMapper.readValue(payload.getData(), StateDTO.class);
                 result = stateAssembler.setAuditFields(mutationEntity, result);
                 fillTransactionStatus(transactionStatus);
                 }
            }
            catch (JsonProcessingException jpe) {
                ExceptionUtil.handleException(JSON_PARSING_ERROR);
            }
            catch (Exception exception) {
                fillTransactionStatus(transactionStatus, exception);
            }
            finally {
                Interaction.close();
            }
           return result;
        }



    public List<StateDTO> getStates (SessionContext sessionContext) throws FatalException {

        if (log.isErrorEnabled()) {
            log.info("In getStates with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<StateDTO> stateDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            stateDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                StateDTO stateDTO = null;
                    try {
                        stateDTO = objectMapper.readValue(data, StateDTO.class);
                        stateDTO = stateAssembler.setAuditFields(entity,stateDTO);
                    } catch (JsonProcessingException e) {
                        ExceptionUtil.handleException(JSON_PARSING_ERROR);
                    }
                    return stateDTO;
                }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return stateDTOList;
    }


    public TransactionStatus processState (SessionContext sessionContext, StateDTO stateDTO)
            throws FatalException {
        if (log.isErrorEnabled()) {
            log.info("In processState with parameters sessionContext {}, stateDTO {}",
                sessionContext, stateDTO);
             }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(stateDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    @Override
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        StateDTO stateDTO = objMapper.readValue(data, StateDTO.class);
        save(stateDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return stateAssembler.convertEntityToDto(stateDomainService.getStateByCode(code));
    }

    @Override
    public void save (StateDTO stateDTO) {
        stateDomainService.save(stateDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return StateDTO.builder().build().getTaskCode();
    }


}


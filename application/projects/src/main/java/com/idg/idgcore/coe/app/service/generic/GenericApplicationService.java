package com.idg.idgcore.coe.app.service.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Slf4j
// @Service ("GenericApplicationService")
public abstract class GenericApplicationService <T_DTO extends CoreEngineBaseDTO,
                                        T_ENTITY extends AbstractAuditableDomainEntity,
                                        T_DOMAIN_SERVICE extends DomainService<T_DTO, T_ENTITY>,
                                        T_ASSEMBLER extends Assembler<T_DTO, T_ENTITY>>
                                        extends AbstractApplicationService 
                                        implements IGenericApplicationService < T_DTO >
{
    protected ModelMapper mapper = new ModelMapper();
    @Autowired
    protected IProcessConfiguration process;
    @Autowired
    protected IMutationsDomainService mutationsDomainService;
    @Autowired
    protected T_DOMAIN_SERVICE domainService;
    @Autowired
    protected T_ASSEMBLER assembler;

    private String taskCode;
    
    public abstract String getTaskCode();

    protected GenericApplicationService(String paramTaskCode) {
        taskCode = paramTaskCode;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public T_DTO getByIdentifier(SessionContext sessionContext, T_DTO valDTO)
            throws FatalException {
        if (log.isInfoEnabled()) { // Q: is this check necessary? I doubt!
            log.info("getByIdentifier: sessionContext {}, valDTO {}", sessionContext, valDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        T_DTO result = null;
        try {
            if (isAuthorized(valDTO.getAuthorized())) {
                T_ENTITY entity = domainService.getEntityByIdentifier(valDTO.getTaskIdentifier());
                result = assembler.toDTO(entity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getEntityByIdentifier(
                        valDTO.getTaskIdentifier());
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = assembler.payloadToDTO(payload.getData());
                assembler.setAuditFields(mutationEntity,result);
                fillTransactionStatus(transactionStatus);
            }
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return result;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<T_DTO> getAll(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("getAll: sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        List<T_DTO> valDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(taskCode);
            valDTOList.addAll(entities.stream().map(entity -> {
                T_DTO valDTO = assembler.payloadToDTO(entity.getPayload().getData());
                assembler.setAuditFields(entity,valDTO);
                return valDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return valDTOList;
    }

//    public List<T_DTO> getAllFromMain(SessionContext sessionContext) throws FatalException {
//        if (log.isInfoEnabled()) {
//            log.info("getAll: sessionContext {}", sessionContext);
//        }
//        TransactionStatus transactionStatus = fetchTransactionStatus();
//        Interaction.begin(sessionContext);
//        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
//        List<T_DTO> valDTOList = new ArrayList<>();
//
//        try {
//            List<T_ENTITY> entities = domainService.getAllEntities();
//            valDTOList.addAll(entities.stream().map(entity -> {
//                return assembler.toDTO(entity);
//            }).toList());
//            fillTransactionStatus(transactionStatus);
//        }
//        catch (Exception exception) {
//            fillTransactionStatus(transactionStatus, exception);
//        }
//        finally {
//            Interaction.close();
//        }
//        return valDTOList;
//    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TransactionStatus process(SessionContext sessionContext, T_DTO valDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In process with parameters sessionContext {}, valDTO {}",
                    sessionContext, valDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(valDTO);
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
        T_DTO valDTO = assembler.payloadToDTO(data);
        save(valDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return assembler.toDTO(domainService.getEntityByIdentifier(code));
    }

    @Override
    public void save (T_DTO valDTO) {
        domainService.save(valDTO);
    }

    protected boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }
}
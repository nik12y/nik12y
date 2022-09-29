package com.idg.idgcore.coe.app.service.virtualentity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.virtualentity.IVirtualEntityDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
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
@Service("virtualEntityApplicationService")
public class VirtualEntityApplicationService extends AbstractApplicationService implements IVirtualEntityApplicationService{

    ModelMapper mapper = new ModelMapper();

    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;

    @Autowired
    private IVirtualEntityDomainService virtualEntityDomainService;

    @Autowired
    private VirtualEntityAssembler virtualEntityAssembler;

    @Autowired
    private MutationAssembler mutationAssembler;

    public TransactionStatus processVirtualEntity(SessionContext sessionContext, VirtualEntityDTO virtualEntityDTO) throws FatalException, JsonProcessingException {
        if(log.isInfoEnabled()) {
            log.info("In processVirtualEntity with parameters sessionContext {}, mitigantDTO {}", sessionContext, virtualEntityDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(virtualEntityDTO);
            fillTransactionStatus(transactionStatus);
        }
        catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            if(!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        VirtualEntityDTO virtualEntityDTO = objMapper.readValue(data, VirtualEntityDTO.class);
        save(virtualEntityDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String code) {
        return virtualEntityAssembler.convertEntityToDto(virtualEntityDomainService.getByVirtualEntityCode(code));
    }

    @Override
    public void save(VirtualEntityDTO virtualEntityDTO) {
        virtualEntityDomainService.save(virtualEntityDTO);
    }

    public VirtualEntityDTO getVirtualEntityByEntityCode(SessionContext sessionContext, VirtualEntityDTO virtualEntityDTO) throws FatalException {
        if(log.isInfoEnabled()) {
            log.info("In getVirtualEntityByCode with parameters sessionContext {}, virtualEntityDTO {}", sessionContext, virtualEntityDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        VirtualEntityDTO result = null;
        try {
            if(isAuthorized(virtualEntityDTO.getAuthorized())) {
                VirtualEntity virtualEntity = virtualEntityDomainService.getByVirtualEntityCode(virtualEntityDTO.getEntityCode());
                result = virtualEntityAssembler.convertEntityToDto(virtualEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        virtualEntityDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), VirtualEntityDTO.class);
                result = virtualEntityAssembler.setAuditFields(mutationEntity,result);
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

    public List<VirtualEntityDTO> getVirtualEntityAll(SessionContext sessionContext) throws FatalException {

        if (log.isInfoEnabled()) {
            log.info("In getVirtualEntityAll with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<VirtualEntityDTO> virtualEntityDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(getTaskCode());
            virtualEntityDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                VirtualEntityDTO virtualEntityDTO = null;
                try {
                    virtualEntityDTO = objectMapper.readValue(data, VirtualEntityDTO.class);
                    virtualEntityDTO = virtualEntityAssembler.setAuditFields(entity, virtualEntityDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return virtualEntityDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return virtualEntityDTOList;

    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return VirtualEntityDTO.builder().build().getTaskCode();
    }


}

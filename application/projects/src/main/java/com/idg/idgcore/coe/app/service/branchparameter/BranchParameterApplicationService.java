package com.idg.idgcore.coe.app.service.branchparameter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.branchparameter.BranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.branchparameter.BranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchparameter.IBranchParameterDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("branchParameterApplicationService")
public class BranchParameterApplicationService extends AbstractApplicationService
        implements IBranchParameterApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBranchParameterDomainService branchParameterDomainService;
    @Autowired
    private BranchParameterAssembler branchParameterAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public BranchParameterDTO getBranchParameterByBranchCode (SessionContext sessionContext,
            BranchParameterDTO branchParameterDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getBranchParameterByCode with parameters sessionContext {}, branchParameterDTO {}",
                    sessionContext, branchParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BranchParameterDTO result = null;
        try {
            if (isAuthorized(branchParameterDTO.getAuthorized())) {
                BranchParameterEntity branchParameterEntity = branchParameterDomainService.getBranchParameterByBranchCode(
                        branchParameterDTO.getBranchCode());
                result = branchParameterAssembler.convertEntityToDto(branchParameterEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        branchParameterDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BranchParameterDTO.class);
                result = branchParameterAssembler.setAuditFields(mutationEntity, result);
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

    public List<BranchParameterDTO> getBranchParameters (SessionContext sessionContext)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<BranchParameterDTO> branchParameterDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            branchParameterDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                BranchParameterDTO branchParameterDTO = null;
                try {
                    branchParameterDTO = objectMapper.readValue(data, BranchParameterDTO.class);
                    branchParameterDTO = branchParameterAssembler.setAuditFields(entity,
                            branchParameterDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return branchParameterDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return branchParameterDTOList;
    }

    public TransactionStatus processBranchParameter (SessionContext sessionContext,
            BranchParameterDTO branchParameterDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In processBranchParameter with parameters sessionContext {}, branchParameterDTO {}",
                    sessionContext, branchParameterDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(branchParameterDTO);
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
        BranchParameterDTO branchParameterDTO = objMapper.readValue(data, BranchParameterDTO.class);
        save(branchParameterDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return branchParameterAssembler.convertEntityToDto(
                branchParameterDomainService.getBranchParameterByBranchCode(code));
    }

    @Override
    public void save (BranchParameterDTO branchParameterDTO) {
        branchParameterDomainService.save(branchParameterDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return BranchParameterDTO.builder().build().getTaskCode();
    }

}
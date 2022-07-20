package com.idg.idgcore.coe.app.service.branchtype;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchtype.IBranchTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
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
@Service("branchTypeApplicationService")
public class BranchTypeApplicationService extends AbstractApplicationService
        implements IBranchTypeApplicationService{

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IBranchTypeDomainService branchTypeDomainService;
    @Autowired
    private BranchTypeAssembler branchtypeAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;


    public BranchTypeDTO getBranchTypeByCode (SessionContext sessionContext, BranchTypeDTO branchTypeDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getBranchTypeByCode with parameters sessionContext {}, branchTypeDTO {}",
                    sessionContext, branchTypeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        BranchTypeDTO result = null;
        try {
            if (isAuthorized(branchTypeDTO.getAuthorized())) {
                BranchTypeEntity branchTypeEntity = branchTypeDomainService.getBranchTypeByCode(
                        branchTypeDTO.getBranchTypeCode());
                result = branchtypeAssembler.convertEntityToDto(branchTypeEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        branchTypeDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), BranchTypeDTO.class);
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

public List<BranchTypeDTO> getBranches (SessionContext sessionContext) throws FatalException {
    if (log.isInfoEnabled()) {
        log.info("In getCountries with parameters sessionContext {}", sessionContext);
    }
    TransactionStatus transactionStatus = fetchTransactionStatus();
    Interaction.begin(sessionContext);
    prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
    ObjectMapper objectMapper = new ObjectMapper();
    List<BranchTypeDTO> branchTypeDTOList = new ArrayList<>();

    try {
        List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                getTaskCode(),AUTHORIZED_N);
        branchTypeDTOList.addAll(branchTypeDomainService.getBranches().stream()
                .map(entity -> branchtypeAssembler.convertEntityToDto(entity))
                .collect(Collectors.toList()));
        branchTypeDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
            String data = entity.getPayload().getData();
            BranchTypeDTO branchTypeDTO = null;
            try {
                branchTypeDTO = objectMapper.readValue(data, BranchTypeDTO.class);
                branchTypeDTO = branchtypeAssembler.setAuditFields(entity,branchTypeDTO);
            }
            catch (JsonProcessingException e) {
                ExceptionUtil.handleException(JSON_PARSING_ERROR);
            }
            return branchTypeDTO;
        }).collect(Collectors.toList()));
        branchTypeDTOList = branchTypeDTOList.stream().collect(
                Collectors.groupingBy(BranchTypeDTO::getBranchTypeCode, Collectors.collectingAndThen(
                        Collectors.maxBy(Comparator.comparing(BranchTypeDTO::getRecordVersion)),
                        Optional::get))).values().stream().collect(Collectors.toList());
        fillTransactionStatus(transactionStatus);
    }
    catch (Exception exception) {
        fillTransactionStatus(transactionStatus, exception);
    }
    finally {
        Interaction.close();
    }
    return branchTypeDTOList;
}




    @Transactional
    public TransactionStatus processBranchType (SessionContext sessionContext, BranchTypeDTO branchtypeDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processBranchType with parameters sessionContext {}, branchtypeDTO {}",
                    sessionContext, branchtypeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(branchtypeDTO);
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
        BranchTypeDTO branchtypeDTO = objMapper.readValue(data, BranchTypeDTO.class);
        save(branchtypeDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return branchtypeAssembler.convertEntityToDto(branchTypeDomainService.getBranchTypeByCode(code));
    }




    @Override
    public void save (BranchTypeDTO branchtypeDTO) {
        branchTypeDomainService.save(branchtypeDTO);
    }



    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return BranchTypeDTO.builder().build().getTaskCode();
    }


}





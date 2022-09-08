package com.idg.idgcore.coe.app.service.regulatoryRegion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.regulatoryRegion.IRegulatoryRegionDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.questionnaireChecklist.QuestionnaireChecklistDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
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
@Service("regulatoryRegionApplicationService")
public class RegulatoryRegionApplicationService extends AbstractApplicationService implements IRegulatoryRegionApplicationService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IRegulatoryRegionDomainService iRegulatoryRegionDomainService;
    @Autowired
    private RegulatoryRegionAssembler regulatoryRegionAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public RegulatoryRegionConfigDTO getRegulatoryRegionByCode(SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In getRegulatoryRegionByCode with parameters sessionContext {}, regulatoryRegionConfigDTO {}",
                    sessionContext, regulatoryRegionConfigDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        RegulatoryRegionConfigDTO result = null;
        try {
            if (isAuthorized(regulatoryRegionConfigDTO.getAuthorized())) {
                RegulatoryRegionConfigEntity regulatoryRegionByCode = iRegulatoryRegionDomainService.getRegulatoryRegionByCode(
                        regulatoryRegionConfigDTO.getRegulatoryRegionCode());
                result = regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionByCode);
            } else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        regulatoryRegionConfigDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), RegulatoryRegionConfigDTO.class);
                result = regulatoryRegionAssembler.setAuditFields(mutationEntity, result);
                fillTransactionStatus(transactionStatus);
            }
        } catch (JsonProcessingException jpe) {
            ExceptionUtil.handleException(JSON_PARSING_ERROR);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return result;
    }

    public List<RegulatoryRegionConfigDTO> getRegulatoryRegionCodes(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getRegulatoryRegionCodes with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<RegulatoryRegionConfigDTO> regulatoryRegionConfigDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(), AUTHORIZED_N);
            regulatoryRegionConfigDTOList.addAll(iRegulatoryRegionDomainService.getRegulatoryRegionCodes().stream()
                    .map(entity -> regulatoryRegionAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            regulatoryRegionConfigDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = null;
                try {
                    regulatoryRegionConfigDTO = objectMapper.readValue(data, RegulatoryRegionConfigDTO.class);
                    regulatoryRegionConfigDTO = regulatoryRegionAssembler.setAuditFields(entity, regulatoryRegionConfigDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return regulatoryRegionConfigDTO;
            }).collect(Collectors.toList()));
            regulatoryRegionConfigDTOList = regulatoryRegionConfigDTOList.stream().collect(
                    Collectors.groupingBy(RegulatoryRegionConfigDTO::getRegulatoryRegionCode, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(RegulatoryRegionConfigDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return regulatoryRegionConfigDTOList;
    }


    public TransactionStatus processRegulatoryRegion(SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processRegulatoryRegion with parameters sessionContext {}, regulatoryRegionConfigDTO {}",
                    sessionContext, regulatoryRegionConfigDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(regulatoryRegionConfigDTO);
            fillTransactionStatus(transactionStatus);
        } catch (FatalException fatalException) {
            fillTransactionStatus(transactionStatus, fatalException);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            if (!Interaction.isLastInteraction()) {
                Interaction.close();
            }
        }
        return transactionStatus;
    }

    @Override
    public void save(RegulatoryRegionConfigDTO regulatoryRegionConfigDTO) {
        iRegulatoryRegionDomainService.save(regulatoryRegionConfigDTO);
    }

    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = objMapper.readValue(data, RegulatoryRegionConfigDTO.class);
        save(regulatoryRegionConfigDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode(String regionCode) {
        return regulatoryRegionAssembler.convertEntityToDto(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(regionCode));
    }

    private boolean isAuthorized(final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode() {
        return RegulatoryRegionConfigDTO.builder().build().getTaskCode();
    }

}

package com.idg.idgcore.coe.app.service.iban;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.*;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.domain.assembler.iban.IbanAssembler;
import com.idg.idgcore.coe.domain.entity.iban.IbanEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.iban.IIbanDomainService;
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
@Service ("ibanApplicationService")
public class IbanApplicationService extends AbstractApplicationService
        implements IIbanApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IIbanDomainService ibanDomainService;
    @Autowired
    private IbanAssembler ibanAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;

    public IbanDTO getIbanByIbanCountryCode (SessionContext sessionContext,
            IbanDTO ibanDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In getIbanByIbanCountryCode with sessionContext {}, ibanDTO {}",
                    sessionContext, ibanDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        IbanDTO result = null;
        try {
            if (isAuthorized(ibanDTO.getAuthorized())) {
                IbanEntity ibanEntity = ibanDomainService.getIbanByIbanCountryCode(
                        ibanDTO.getIbanCountryCode());
                result = ibanAssembler.convertEntityToDto(ibanEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        ibanDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), IbanDTO.class);
                result = ibanAssembler.setAuditFields(mutationEntity, result);
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

    public List<IbanDTO> getIbans(SessionContext sessionContext)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getIban sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<IbanDTO> ibanDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            ibanDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                IbanDTO ibanDTO = null;
                try {
                    ibanDTO = objectMapper.readValue(data, IbanDTO.class);
                    ibanDTO = ibanAssembler.setAuditFields(entity,
                            ibanDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return ibanDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return ibanDTOList;
    }

    public TransactionStatus processIban (SessionContext sessionContext,
            IbanDTO ibanDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(
                    "In processIban with sessionContext {}, ibanDTO {}",
                    sessionContext, ibanDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(ibanDTO);
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
        IbanDTO ibanDTO = objMapper.readValue(data, IbanDTO.class);
        save(ibanDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return ibanAssembler.convertEntityToDto(
                ibanDomainService.getIbanByIbanCountryCode(code));
    }

    @Override
    public void save (IbanDTO ibanDTO) {
        ibanDomainService.save(ibanDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return IbanDTO.builder().build().getTaskCode();
    }

}
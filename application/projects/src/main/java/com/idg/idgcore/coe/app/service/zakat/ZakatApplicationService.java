package com.idg.idgcore.coe.app.service.zakat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.zakat.ZakatAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.zakat.IZakatDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
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
@Service("zakatApplicationService")
public class ZakatApplicationService extends AbstractApplicationService
        implements IZakatApplicationService {
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IZakatDomainService zakatDomainService;
    @Autowired
    private ZakatAssembler zakatAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;


    public ZakatDTO getZakatByYear(SessionContext sessionContext, ZakatDTO zakatDTO) throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In getZakatByYear with parameters sessionContext {}, zakatDTO {}",
                    sessionContext, zakatDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ZakatDTO result = null;
        try {
            if (isAuthorized(zakatDTO.getAuthorized())) {
                ZakatEntity zakatEntity = zakatDomainService.getZakatByYear(
                        zakatDTO.getZakatYear());
                result = zakatAssembler.convertEntityToDto(zakatEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        zakatDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), ZakatDTO.class);
                result = zakatAssembler.setAuditFields(mutationEntity,result);
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


    public List<ZakatDTO> getZakats(SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getZakats with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<ZakatDTO> zakatDTOList = new ArrayList<>();

        try {
            List<MutationEntity> unauthorizedEntities = mutationsDomainService.getUnauthorizedMutation(
                    getTaskCode(),AUTHORIZED_N);
            zakatDTOList.addAll(zakatDomainService.getZakats().stream()
                    .map(entity -> zakatAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
            zakatDTOList.addAll(unauthorizedEntities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                ZakatDTO zakatDTO = null;
                try {
                    zakatDTO = objectMapper.readValue(data, ZakatDTO.class);
                    zakatDTO = zakatAssembler.setAuditFields(entity,zakatDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return zakatDTO;
            }).collect(Collectors.toList()));
            zakatDTOList = zakatDTOList.stream().collect(
                    Collectors.groupingBy(ZakatDTO::getZakatYear, Collectors.collectingAndThen(
                            Collectors.maxBy(Comparator.comparing(ZakatDTO::getRecordVersion)),
                            Optional::get))).values().stream().collect(Collectors.toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return zakatDTOList;
    }



    public TransactionStatus processZakat(SessionContext sessionContext, ZakatDTO zakatDTO)throws FatalException, JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info("In processZakat with parameters sessionContext {}, zakatDTO {}",
                    sessionContext, zakatDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(zakatDTO);
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
        ZakatDTO zakatDTO = objMapper.readValue(data, ZakatDTO.class);
        save(zakatDTO);
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return zakatAssembler.convertEntityToDto(zakatDomainService.getZakatByYear(Integer.parseInt(code)));
    }

    @Override
    public void save (ZakatDTO zakatDTO) {
        zakatDomainService.save(zakatDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return ZakatDTO.builder().build().getTaskCode();
    }
}

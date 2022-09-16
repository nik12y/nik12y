package com.idg.idgcore.coe.app.service.riskcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.domain.assembler.audit.MutationAssembler;
import com.idg.idgcore.coe.domain.assembler.country.CountryAssembler;
import com.idg.idgcore.coe.domain.assembler.riskcategory.RiskCategoryAssembler;
import com.idg.idgcore.coe.domain.assembler.riskcode.RiskCodeAssembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.country.ICountryDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.riskcategory.IRiskCategoryDomainService;
import com.idg.idgcore.coe.domain.service.riskcode.IRiskCodeDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
import com.idg.idgcore.coe.exception.Error;
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
@Service("riskCodeApplicationService")
public class RiskCodeApplicationService extends AbstractApplicationService
    implements IRiskCodeApplicationService
{
    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IRiskCodeDomainService riskCodeDomainService;
    @Autowired
    private RiskCodeAssembler riskCodeAssembler;
    @Autowired
    private MutationAssembler mutationAssembler;







    public TransactionStatus processRiskCode (SessionContext sessionContext, RiskCodeDTO riskcodeDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In processCountry with parameters sessionContext {}, riskcodeDTO {}",
                    sessionContext, riskcodeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(riskcodeDTO);
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
    public void save(RiskCodeDTO riskcodedto)
    {riskCodeDomainService.save(riskcodedto);

    }


    public RiskCodeDTO getRiskCodeByCode (SessionContext sessionContext, RiskCodeDTO riskcodeDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getRiskCodeByCode with parameters sessionContext {}, riskcodeDTO {}",
                    sessionContext, riskcodeDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        RiskCodeDTO result = null;
        try {
            if (isAuthorized(riskcodeDTO.getAuthorized())) {
                RiskCodeEntity riskCodeEntity = riskCodeDomainService.getRiskCodeByCode(
                        riskcodeDTO.getRiskCode());
                result = riskCodeAssembler.convertEntityToDto(riskCodeEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        riskcodeDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result = objectMapper.readValue(payload.getData(), RiskCodeDTO.class);
                result = riskCodeAssembler.setAuditFields(mutationEntity,result);
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


    public List<RiskCodeDTO> getRiskCodes (SessionContext sessionContext) throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getCountries with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<RiskCodeDTO> riskcodeDTOList = new ArrayList<>();

        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());

            riskcodeDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                RiskCodeDTO riskCodeDTO = null;
                try {
                    riskCodeDTO = objectMapper.readValue(data, RiskCodeDTO.class);
                    riskCodeDTO = riskCodeAssembler.setAuditFields(entity,riskCodeDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return riskCodeDTO;
            }).toList());

            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        return riskcodeDTOList;
    }


    @Override
    public void addUpdateRecord(String data) throws JsonProcessingException {

        ObjectMapper objMapper = new ObjectMapper();
        RiskCodeDTO  riskCodeDTO = objMapper.readValue(data, RiskCodeDTO.class);
        save(riskCodeDTO);
    }

    @Override
public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return riskCodeAssembler.convertEntityToDto(riskCodeDomainService.getRiskCodeByCode(code));
        }

private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
        }



private String getTaskCode () {
        return RiskCodeDTO.builder().build().getTaskCode();
        }
}

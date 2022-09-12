package com.idg.idgcore.coe.app.service.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.domain.assembler.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.base.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.modelmapper.convention.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import javax.annotation.*;
import java.util.*;
import java.util.function.*;

import static com.idg.idgcore.coe.exception.Error.*;

@Slf4j
@Service ("amountInWordsApplicationService")
public class CurrencyAmountInWordService extends AbstractApplicationService implements ICurrencyAmountInWordService{

    private static final String CLASS_NAME="CurrencyAmountInWordService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Autowired
    private IMutationsDomainService mutationsDomainService;

    @Autowired
    private IProcessConfiguration process;

    @Autowired
    private CurrencyAmountInWordDomainService currencyAmountInWordDomainService;

    @Autowired
    CurrencyAmountInWordAssembler currencyAmountInWordAssembler;

    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);

    }

    ModelMapper mapper = new ModelMapper();

    public void addUpdateRecord (String data) throws JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"addUpdateRecord() with data{}",data);
        }
        ObjectMapper objMapper = new ObjectMapper();
        CurrencyAmountInWordDTO currencyAmountInWordDTO = objMapper.readValue(data, CurrencyAmountInWordDTO.class);
        save(currencyAmountInWordDTO);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"addUpdateRecord()");
        }
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return null;
    }

    public void save (CurrencyAmountInWordDTO currencyAmountInWordDTO) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyAmountInWordsDTO{}",
                    currencyAmountInWordDTO);
        }
        currencyAmountInWordDomainService.save(currencyAmountInWordDTO);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"save() by saving the above CurrencyAmountInWordDTO {} :",
                    currencyAmountInWordDTO);
        }
    }

    @Transactional
    public TransactionStatus processAmountInWords(SessionContext sessionContext,
            CurrencyAmountInWordDTO currencyAmountInWordDTO) throws FatalException,
            JsonProcessingException {

        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processAmountInWords() with SessionContext{} and CurrencyAmountInWordsDTO{}"
                    ,sessionContext, currencyAmountInWordDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(currencyAmountInWordDTO);
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
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"processAmountInWords() with following response transactionStatus {}",transactionStatus);
        }
        return transactionStatus;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CurrencyAmountInWordDTO> getAmountInWordsList(SessionContext sessionContext) throws
            FatalException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsList() with SessionContext{}",sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyAmountInWordDTO> currencyAmountInWordDTOList = null;

        try {
            currencyAmountInWordDTOList = new ArrayList<>();
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            currencyAmountInWordDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CurrencyAmountInWordDTO currencyAmountInWordDTO = null;
                try {
                    currencyAmountInWordDTO = objectMapper.readValue(data, CurrencyAmountInWordDTO.class);
                    currencyAmountInWordDTO = currencyAmountInWordAssembler.setAuditFields(entity,
                            currencyAmountInWordDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return currencyAmountInWordDTO;
            }).toList());

            Comparator<CurrencyAmountInWordDTO> compareByCreationTime = Comparator.comparing(
                    CurrencyAmountInWordDTO::getCreationTime);
            currencyAmountInWordDTOList = currencyAmountInWordDTOList.stream().sorted(compareByCreationTime.reversed()).toList();
            fillTransactionStatus(transactionStatus);

        }catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getAmountInWordsList() with following response CurrencyAmountInWordDTOList {}",
                    currencyAmountInWordDTOList);
        }
        return currencyAmountInWordDTOList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CurrencyAmountInWordDTO getAmountInWordsDetails (SessionContext sessionContext, CurrencyDetailsInputDTO currencyDetailsInputDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsDetails() with SessionContext{} and CurrencyDetailsInputDTO {}",sessionContext,
                    currencyDetailsInputDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyAmountInWordDTO currencyAmountInWordDTO = null;
        try {
            if (isAuthorized(currencyDetailsInputDTO.getAuthorized())) {
                    Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity = currencyAmountInWordDomainService.getCurrencyAmountInWordsDetails(
                        currencyDetailsInputDTO.getCurrencyCode());
                currencyAmountInWordDTO = currencyAmountInWordAssembler.convertEntityToDto(currencyAmountInWordsEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyDetailsInputDTO.getCurrencyCode());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                currencyAmountInWordDTO = objectMapper.readValue(payload.getData(), CurrencyAmountInWordDTO.class);
                currencyAmountInWordDTO = currencyAmountInWordAssembler.setAuditFields(mutationEntity,
                        currencyAmountInWordDTO);
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
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getAmountInWordsDetails() with following response CurrencyAmountInWordDTO {}",
                    currencyAmountInWordDTO);
        }
        return currencyAmountInWordDTO;
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return CurrencyAmountInWordDTO.builder().build().getTaskCode();
    }
}

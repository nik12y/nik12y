package com.idg.idgcore.coe.app.service.currencyAmountInWord;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.app.service.generic.*;
import com.idg.idgcore.coe.domain.assembler.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.currencyAmountInWord.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.service.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyAmountInWord.*;
import com.idg.idgcore.coe.dto.currencyConfiguration.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.coe.exception.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import lombok.extern.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service ("amountInWordsApplicationService")
public class CurrencyAmountInWordService extends
        GenericApplicationService<CurrencyAmountInWordDTO, CurrencyAmountInWordEntity, CurrencyAmountInWordDomainService, CurrencyAmountInWordAssembler>
{

    private static final String CLASS_NAME="CurrencyAmountInWordService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Autowired
    CurrencyAmountInWordAssembler currencyAmountInWordAssembler;

    @Autowired
    CurrencyAmountInWordDomainService currencyAmountInWordDomainService;

    protected CurrencyAmountInWordService () {
        super(AMOUNT_IN_WORDS);
    }

    public String getTaskCode () {
        return CurrencyAmountInWordDTO.builder().build().getTaskCode();
    }

    @Transactional (propagation = Propagation.REQUIRED)
    public CurrencyAmountInWordDTO getAmountInWordsDetails (SessionContext sessionContext, CurrencyAmountInWordsInputDTO currencyAmountInWordsInputDTO)
            throws FatalException {

        log.info(ENTERED_STRING+CLASS_NAME+"getAmountInWordsDetails() with SessionContext{} and CurrencyAmountInWordsInputDTO {}",sessionContext,
                currencyAmountInWordsInputDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyAmountInWordDTO currencyAmountInWordDTO = null;
        try {
            if (isAuthorized(currencyAmountInWordsInputDTO.getAuthorized())) {
                Optional<CurrencyAmountInWordEntity> currencyAmountInWordsEntity = currencyAmountInWordDomainService.getCurrencyAmountInWordsDetails(
                        currencyAmountInWordsInputDTO.getCurrencyCode());
                currencyAmountInWordDTO = currencyAmountInWordAssembler.convertEntityToDto(currencyAmountInWordsEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyAmountInWordsInputDTO.getCurrencyCode());
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

        log.info(EXIT_STRING+CLASS_NAME+"getAmountInWordsDetails() with following response CurrencyDetailsInputDTO {}",
                currencyAmountInWordDTO);

        return currencyAmountInWordDTO;
    }
}

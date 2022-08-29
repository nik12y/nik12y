package com.idg.idgcore.coe.app.service.currencyconfiguration;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.common.*;
import com.idg.idgcore.coe.domain.assembler.currencyconfiguration.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.repository.currencyconfiguration.*;
import com.idg.idgcore.coe.domain.service.currencyconfiguration.*;
import com.idg.idgcore.coe.dto.base.*;
import com.idg.idgcore.coe.dto.currencyconfiguration.*;
import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import com.idg.idgcore.enumerations.core.*;
import lombok.extern.slf4j.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

import java.util.*;
import java.util.stream.*;


@Slf4j
@Service ("currencyApplicationService")
public class CurrencyConfigurationService extends AbstractApplicationService implements ICurrencyConfigurationService {

    private static final String CLASS_NAME="CurrencyConfigurationService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;

    @Autowired
    private ICurrencyConfigurationRepository iCurrencyConfigurationRepository;

    @Autowired
    private CurrencyConfigurationDomainService currencyConfigurationDomainService;

    @Autowired
    private CurrencyConfigurationAssembler currencyConfigurationAssembler;



    public CurrencyDetailsDTO getCurrencyDetails(SessionContext sessionContext,String countryCode) throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyDetails() with SessionContext{} and countryCode{}",sessionContext,countryCode);
        }
        CurrencyDetailsDTO currencyDetailsDTO = null;
        try {
            if(iCurrencyConfigurationRepository.findById(countryCode).isPresent()){
                currencyDetailsDTO = new CurrencyDetailsDTO(iCurrencyConfigurationRepository.findById(countryCode).get());
            }
        }catch (Exception exception){
            throw new FatalException("Exception in getCurrencyDetails()"+ exception);
        }
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getCurrencyDetails() with following response currencyDetailsDTO {}",currencyDetailsDTO);
        }
        return currencyDetailsDTO;
    }

    @Transactional
    public TransactionStatus processCurrencyConfiguration(SessionContext sessionContext,
            CurrencyDetailsDTO currencyDetailsDTO) throws FatalException,
            JsonProcessingException {

        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processCurrencyConfiguration() with SessionContext{} and CurrencyDetailsDTO{}"
                    ,sessionContext,currencyDetailsDTO);
        }
            TransactionStatus transactionStatus = fetchTransactionStatus();
            try {
                Interaction.begin(sessionContext);
                prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
                process.process(currencyDetailsDTO);
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
            log.info(EXIT_STRING+CLASS_NAME+"processCurrencyConfiguration() with following response transactionStatus {}",transactionStatus);
        }
            return transactionStatus;
        }


    public void addUpdateRecord (String data) throws JsonProcessingException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"addUpdateRecord() with data{}",data);
        }
        ObjectMapper objMapper = new ObjectMapper();
        CurrencyDetailsDTO currencyDetailsDTO = objMapper.readValue(data, CurrencyDetailsDTO.class);
        save(currencyDetailsDTO);
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"addUpdateRecord()");
        }
    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return null;
    }

    public void save (CurrencyDetailsDTO currencyDetailsDTO) {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"save() with CurrencyDetailsDTO{}",currencyDetailsDTO);
        }
        currencyConfigurationDomainService.save(currencyDetailsDTO);
    }

    public List<CurrencyDetailsDTO> getCurrencies(SessionContext sessionContext) throws
            FatalException {
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencies() with SessionContext{}",sessionContext);
        }
        List<CurrencyDetailsDTO> currenciesList = null;
        try {
            currenciesList= new ArrayList<>();
            currenciesList.addAll(iCurrencyConfigurationRepository.findAll().stream()
                    .map(entity -> currencyConfigurationAssembler.convertEntityToDto(entity))
                    .collect(Collectors.toList()));
        }catch (Exception exception) {
            throw new FatalException("Exception in getAllCurrencyList()"+ exception);
        }
        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"getCurrencies() with following response currenciesList {}",currenciesList);
        }
        return currenciesList;
    }

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException{
        if (log.isInfoEnabled()) {
            log.info(ENTERED_STRING+CLASS_NAME+"processAmountFormatting() with SessionContext{} and AmountInputDTO{}",sessionContext,amountInputDTO);
        }
        CurrencyDetailsDTO currencyDetailsDTO = null;
        FormattedAmountDTO formattedAmountDTO = null;
        try {
            if(iCurrencyConfigurationRepository.findById(amountInputDTO.getCountryCode()).isPresent()){
                currencyDetailsDTO = new CurrencyDetailsDTO(iCurrencyConfigurationRepository.findById(amountInputDTO.getCountryCode()).get());
                formattedAmountDTO = new FormattedAmountDTO();
                formattedAmountDTO.setFormattedAmount(formatAmount(currencyDetailsDTO.getAmountFormatMaskPtt(),amountInputDTO.getAmount()));
            }

        }catch (Exception exception) {
            throw new FatalException("Exception in processAmountFormatting()"+ exception);
        }

        if (log.isInfoEnabled()) {
            log.info(EXIT_STRING+CLASS_NAME+"processAmountFormatting() with following response formattedAmountDTO{}",formattedAmountDTO);
        }
        return formattedAmountDTO;
    }


    public static String formatAmount(String pattern ,String amount) {

        int count = 0;
        int positionOfDecimal = 0;
        char decimalChar = 'D';
        int countOfDecimalChar = 0;
        int  positionOfdecimalTobeAdded = 0;
        String commaFormatting = null;
        String commaFormattingPattern = null;
        StringBuilder formAmount = new StringBuilder(amount);

        for(int i=0; i< pattern.length();i++) {
            if(pattern.charAt(i) == decimalChar) {
                countOfDecimalChar++;
            }
        }

        if(countOfDecimalChar == 1) {
            positionOfDecimal = pattern.length() - (pattern.indexOf("D")+1);
            positionOfdecimalTobeAdded = amount.length() - positionOfDecimal;
            if(positionOfdecimalTobeAdded != amount.length())
                formAmount.insert(positionOfdecimalTobeAdded, ".");

        }else if(countOfDecimalChar < 1){
            return Constants.CURR_FORM_ERROR_MSG;

        }else if(countOfDecimalChar > 1) {
            return Constants.CURR_FORM_ERROR_MSG2;
        }

        commaFormatting = formAmount.toString().substring(0, positionOfdecimalTobeAdded);
        commaFormattingPattern = pattern.substring(0, pattern.indexOf("D"));

        for(int j=commaFormattingPattern.length()-1;j>0;j--) {
            char charAt = commaFormattingPattern.charAt(j);

            if(String.valueOf(charAt).contains("9")) {
                count = count +1;
            }else if(String.valueOf(charAt).contains("G")){
                if(commaFormatting.length()-count <= 0) {
                    return formAmount.toString();
                }else {
                    formAmount.insert(commaFormatting.length()-count, ",");
                }

            }
        }

        return formAmount.toString();
    }

}

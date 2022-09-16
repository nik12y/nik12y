package com.idg.idgcore.coe.app.service.currencyConfiguration;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.common.*;
import com.idg.idgcore.coe.domain.assembler.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.entity.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.repository.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.service.currencyConfiguration.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.base.*;
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
@Service ("currencyApplicationService")
public class CurrencyConfigurationService extends AbstractApplicationService implements ICurrencyConfigurationService {
    private static final String CLASS_NAME="CurrencyConfigurationService.";
    private static final String ENTERED_STRING="Entered into ";
    private static final String EXIT_STRING="Exited from ";

    @Autowired
    private IMutationsDomainService mutationsDomainService;

    @Autowired
    private CurrencyConfigurationAssembler currencyConfigurationAssembler;
    @Autowired
    private IProcessConfiguration process;

    @Autowired
    private CurrencyConfigurationDomainService currencyConfigurationDomainService;

    @Autowired
    private ICurrencyConfigurationDetailsRepository icurrencyConfigurationDetailsRepository;

    @Autowired
    private ICurrencyCutOffRepository icurrencyCutOffRepository;

    @Autowired
    private ICurrencyConfigurationRepository icurrencyConfigurationRepository;


    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }



    ModelMapper mapper = new ModelMapper();


    public void addUpdateRecord (String data) throws JsonProcessingException {

            log.info(ENTERED_STRING+CLASS_NAME+"addUpdateRecord() with data{}",data);

        ObjectMapper objMapper = new ObjectMapper();
        CurrencyConfigurationDTO currencyConfigurationDTO = objMapper.readValue(data, CurrencyConfigurationDTO.class);
        save(currencyConfigurationDTO);

            log.info(EXIT_STRING+CLASS_NAME+"addUpdateRecord()");

    }

    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return null;
    }

    public void save (CurrencyConfigurationDTO currencyConfigurationDTO) {

            log.info(ENTERED_STRING+CLASS_NAME+"getConfigurationByCode() with CurrencyTestDTO{}",
                    currencyConfigurationDTO);

            log.info(EXIT_STRING+CLASS_NAME+"getConfigurationByCode() :",
                    currencyConfigurationDTO);

        currencyConfigurationDomainService.save(currencyConfigurationDTO);
    }

    @Transactional
    public TransactionStatus processCurrencyConfiguration(SessionContext sessionContext,
            CurrencyConfigurationDTO currencyConfigurationDTO) throws FatalException,
            JsonProcessingException {


            log.info(ENTERED_STRING+CLASS_NAME+"processCurrencyConfiguration() with SessionContext{} and CurrencyConfigurationDTO{}"
                    ,sessionContext, currencyConfigurationDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        try {
            Interaction.begin(sessionContext);
            prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
            process.process(currencyConfigurationDTO);
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

            log.info(EXIT_STRING+CLASS_NAME+"processCurrencyConfiguration() with following response transactionStatus {}",transactionStatus);

        return transactionStatus;
    }

    public FormattedAmountDTO processAmountFormatting(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException{

            log.info(ENTERED_STRING+CLASS_NAME+"processAmountFormatting() with SessionContext{} and AmountInputDTO{}",sessionContext,amountInputDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        CurrencyCutOffRoundingDTO currencyCutOffRoundingDTO= null;
        FormattedAmountDTO formattedAmountDTO = null;
        Optional<CurrencyConfigurationCutOffRoundingEntity> currencyConfigCutOffRoundingEntity;

        try {
                currencyConfigCutOffRoundingEntity = currencyConfigurationDomainService.getCurrencyConfigCutOffRoundDetails(amountInputDTO.getCurrencyCode());
                currencyCutOffRoundingDTO=currencyConfigurationAssembler.convertEntityToDto(currencyConfigCutOffRoundingEntity);
                formattedAmountDTO = new FormattedAmountDTO();
                formattedAmountDTO.setFormattedAmount(formatAmount(currencyCutOffRoundingDTO.getAmountFormatMaskPtt(),amountInputDTO.getAmount()));

        }catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }

            log.info(EXIT_STRING+CLASS_NAME+"processAmountFormatting() with following response formattedAmountDTO{}",formattedAmountDTO);

        return formattedAmountDTO;
    }

    public FormattedAmountDTO processAmountRounding(SessionContext sessionContext,AmountInputDTO amountInputDTO)throws FatalException{

            log.info(ENTERED_STRING+CLASS_NAME+"processAmountRounding() with SessionContext{} and AmountInputDTO{}",sessionContext,amountInputDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        FormattedAmountDTO formattedAmountDTO = null;
        Optional<CurrencyConfigurationEntity> currencyConfigEntity;
        Optional<CurrencyConfigurationCutOffRoundingEntity> currencyConfigCutOffRoundingEntity;
        CurrencyConfigurationDTO currencyConfigurationDTO =null;
        CurrencyCutOffRoundingDTO currencyCutOffRoundingDTO = null;


        try {

            currencyConfigEntity = currencyConfigurationDomainService.getCurrencyConfigDetails(amountInputDTO.getCurrencyCode());
            currencyConfigCutOffRoundingEntity = currencyConfigurationDomainService.getCurrencyConfigCutOffRoundDetails(amountInputDTO.getCurrencyCode());
            currencyConfigurationDTO =currencyConfigurationAssembler.currencyConfigConvertEntityToDto(currencyConfigEntity);
            currencyCutOffRoundingDTO=currencyConfigurationAssembler.convertEntityToDto(currencyConfigCutOffRoundingEntity);
            formattedAmountDTO = new FormattedAmountDTO();
                if(currencyCutOffRoundingDTO.getRoundingRule().equalsIgnoreCase("Truncate")){
                    formattedAmountDTO.setFormattedAmount(truncateAmount(amountInputDTO.getAmount(),
                            currencyConfigurationDTO.getDecimals()));
                }else if(currencyCutOffRoundingDTO.getRoundingRule().equalsIgnoreCase("Round Near")){
                    formattedAmountDTO.setFormattedAmount(roundNear(amountInputDTO.getAmount(),
                            currencyConfigurationDTO.getDecimals(),currencyConfigurationDTO.getCurrencyCutOff().getRoundingUnit()));
                }

        }catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }


            log.info(EXIT_STRING+CLASS_NAME+"processAmountRounding() with following response formattedAmountDTO{}",formattedAmountDTO);

        return formattedAmountDTO;
    }

    public static String truncateAmount(String amount, short decimals){

            log.info(ENTERED_STRING+CLASS_NAME+"truncateAmount() with amount:"+amount+
                    ", decimals:"+decimals);

        String roundedAmount = null;
        if(amount.contains(".")) {
            String [] splitAmountByDecimal = amount.split("[.]");
            String decimalFigures = splitAmountByDecimal[1];
            decimalFigures = (String) decimalFigures.subSequence(0, decimals);
            roundedAmount = splitAmountByDecimal[0] +"."+decimalFigures;


                log.info(EXIT_STRING+CLASS_NAME+"truncateAmount() :", roundedAmount);


            return roundedAmount;
        }else {

                log.info(EXIT_STRING+CLASS_NAME+"truncateAmount():");

            return "Entered amount is not suitable for rounding :"+amount;
        }
    }

    public static String roundNear(String amount, short decimals, float roundingUnit){

            log.info(ENTERED_STRING+CLASS_NAME+"roundNear() with amount:"+amount+
                    ", decimals:"+decimals,"and rounding unit:"+roundingUnit);

        String roundedAmount = null;
        String [] splitAmountByDecimal = amount.split("[.]");
        splitAmountByDecimal[1] = "0."+splitAmountByDecimal[1];
        float amountDecimal = Float.valueOf(splitAmountByDecimal[1]);
        amountDecimal = (float) (amountDecimal+roundingUnit);
        String splitDecimals = String.valueOf(amountDecimal);
        String [] split = splitDecimals.split("[.]");
        roundedAmount = splitAmountByDecimal[0] + "."+ split[1];
        String [] splitAmountByDecimal1 = roundedAmount.split("[.]");
        String decimalFigures = splitAmountByDecimal1[1];
        decimalFigures = (String) decimalFigures.subSequence(0, decimals);
        roundedAmount = splitAmountByDecimal1[0] +"."+decimalFigures;

            log.info(EXIT_STRING+CLASS_NAME+"roundNear():");

        return roundedAmount;
    }


    public static String formatAmount(String pattern ,String amount) {

            log.info(ENTERED_STRING+CLASS_NAME+"formatAmount() with pattern and amount",pattern,amount);


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

            log.info(EXIT_STRING+CLASS_NAME+"formatAmount() with the following formatted amount :",formAmount.toString());

        return formAmount.toString();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<CurrencyConfigurationDTO> getCurrencies(SessionContext sessionContext) throws
            FatalException {

            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencies() with SessionContext{}",sessionContext);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        List<CurrencyConfigurationDTO> currenciesDTOList = null;

        try {
            currenciesDTOList= new ArrayList<>();
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            currenciesDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CurrencyConfigurationDTO currencyConfigurationDTO = null;
                try {
                    currencyConfigurationDTO = objectMapper.readValue(data, CurrencyConfigurationDTO.class);
                    currencyConfigurationDTO = currencyConfigurationAssembler.setAuditFields(entity,
                            currencyConfigurationDTO);
                }
                catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return currencyConfigurationDTO;
            }).toList());

            Comparator<CurrencyConfigurationDTO> compareByCreationTime = Comparator.comparing(
                    CurrencyConfigurationDTO::getCreationTime);
            currenciesDTOList = currenciesDTOList.stream().sorted(compareByCreationTime.reversed()).toList();
            fillTransactionStatus(transactionStatus);

        }catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }

            log.info(EXIT_STRING+CLASS_NAME+"getCurrencies() with following response currenciesList {}",currenciesDTOList);

        return currenciesDTOList;
    }
    @Transactional(propagation = Propagation.REQUIRED)
    public CurrencyConfigurationDTO getCurrencyDetails (SessionContext sessionContext, CurrencyDetailsInputDTO currencyDetailsInputDTO)
            throws FatalException {

            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyDetails() with SessionContext{} and CurrencyDetailsInputDTO {}",sessionContext,
                    currencyDetailsInputDTO);

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        CurrencyConfigurationDTO currencyConfigurationDTO = null;
        try {
            if (isAuthorized(currencyDetailsInputDTO.getAuthorized())) {
                Optional<CurrencyConfigurationEntity> currencyConfigEntity = currencyConfigurationDomainService.getCurrencyConfigDetails(
                        currencyDetailsInputDTO.getCurrencyCode());
                currencyConfigurationDTO = currencyConfigurationAssembler.convertEntityToDtoCurrencyConfigDetails(currencyConfigEntity);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        currencyDetailsInputDTO.getCurrencyCode());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                currencyConfigurationDTO = objectMapper.readValue(payload.getData(), CurrencyConfigurationDTO.class);
                currencyConfigurationDTO = currencyConfigurationAssembler.setAuditFields(mutationEntity,
                        currencyConfigurationDTO);
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

            log.info(ENTERED_STRING+CLASS_NAME+"getCurrencyDetails() with  CurrencyConfigurationDTO {} :",currencyConfigurationDTO);

        return currencyConfigurationDTO;
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return CurrencyConfigurationDTO.builder().build().getTaskCode();
    }

}

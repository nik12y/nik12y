package com.idg.idgcore.coe.app.service.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.country.CountryAssembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.service.country.CountryDomainService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.TransactionMessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.common.Constants.COUNTRY;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;

@Slf4j
@Service("countryApplicationService")
public class CountryApplicationService extends GenericApplicationService<CountryDTO, CountryEntity,
        CountryDomainService, CountryAssembler> {

    protected CountryApplicationService() {
        super(COUNTRY);
    }

    public List<CountryDTO> searchCountry(SessionContext sessionContext, CountryDTO countryDTO)
            throws FatalException, JsonProcessingException {

        if (log.isInfoEnabled()) {
            log.info("In getCountryByCode with parameters sessionContext {}, countryDTO {}",
                    sessionContext, countryDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);

        List<CountryDTO> result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(countryDTO.getTaskCode(), countryDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                CountryDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, CountryDTO.class);
                    assembler.setAuditFields(entity, dto);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return dto;
            }).toList();
            fillTransactionStatus(transactionStatus);
        } catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        } finally {
            Interaction.close();
        }
        return result;
    }

    public String getTaskCode() {
        return CountryDTO.builder().build().getTaskCode();
    }

}
package com.idg.idgcore.coe.app.service.holidaylist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.app.Interaction;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.app.service.policy.calendar.factory.HolidayCalendarBusinessPolicyFactory;
import com.idg.idgcore.coe.domain.assembler.holidaylist.HolidayListAssembler;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.process.IProcessConfiguration;
import com.idg.idgcore.coe.domain.service.holidaylist.IHolidayListDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListResponse;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.policy.calendar.HolidayCalendarBusinessPolicyDTO;
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
import java.util.List;
import java.util.function.Predicate;

import static com.idg.idgcore.coe.common.CalendarBusinessPolicyConstants.HOLIDAY_CALENDAR_BUSINESS_POLICY;
import static com.idg.idgcore.coe.exception.Error.JSON_PARSING_ERROR;


@Slf4j
@Service("holidayListApplicationService")
public class HolidayListApplicationService extends AbstractApplicationService
        implements IHolidayListApplicationService {


    ModelMapper mapper = new ModelMapper();
    @Autowired
    private IProcessConfiguration process;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private HolidayCalendarBusinessPolicyFactory holidayCalendarBusinessPolicyFactory;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private IHolidayListDomainService holidayListDomainService;
    @Autowired
    private HolidayListAssembler holidayListAssembler;


    public HolidayListResponse searchHolidayList(SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException {

        if (log.isInfoEnabled()) {
            log.info("In searchHolidayList with parameters sessionContext {}, holidayListDTO {}",
                    sessionContext, holidayListDTO);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        HolidayListResponse holidayListResponse=new HolidayListResponse();

        List<HolidayListDTO> result = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            List<MutationEntity> entities = mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(holidayListDTO.getTaskCode(), holidayListDTO.getTaskIdentifier());
            result = entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                HolidayListDTO dto = null;
                try {
                    dto = objectMapper.readValue(data, HolidayListDTO.class);
                    dto = holidayListAssembler.setAuditFields(entity, dto);
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
        holidayListResponse.setHolidayList(result);
        holidayListResponse.setTransactionStatus(transactionStatus);
        return holidayListResponse;
    }


    @Override
    public HolidayListResponse getHolidayListById(SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException {
        if (log.isInfoEnabled()) {
            log.info("In getHolidayListById with parameters sessionContext{}, holidayListDTO {}",
                    sessionContext, holidayListDTO);
        }

        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        HolidayListResponse holidayListResponse=new HolidayListResponse();
        List<HolidayListDTO> result = new ArrayList<>();
        try{
            if (isAuthorized(holidayListDTO.getAuthorized())) {
                HolidayListEntity holidayListEntity = holidayListDomainService.getHolidayListById(
                        holidayListDTO.getHolidayListId());
                result.add(holidayListAssembler.convertEntityToDto(holidayListEntity));
                fillTransactionStatus(transactionStatus);
            }
            else {
                MutationEntity mutationEntity = mutationsDomainService.getConfigurationByCode(
                        holidayListDTO.getTaskIdentifier());
                ObjectMapper objectMapper = new ObjectMapper();
                PayloadDTO payload = mapper.map(mutationEntity.getPayload(), PayloadDTO.class);
                result.add( objectMapper.readValue(payload.getData(), HolidayListDTO.class));
                result.stream().map(dto->{
                    return holidayListAssembler.setAuditFields(mutationEntity, dto);
                }).toList();
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
        holidayListResponse.setHolidayList(result);
        holidayListResponse.setTransactionStatus(transactionStatus);
        return holidayListResponse;
    }


    public HolidayListResponse getHolidayLists (SessionContext sessionContext) throws FatalException {

        if (log.isInfoEnabled()) {
            log.info("In getHolidayLists with parameters sessionContext {}", sessionContext);
        }
        TransactionStatus transactionStatus = fetchTransactionStatus();
        Interaction.begin(sessionContext);
        prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);
        ObjectMapper objectMapper = new ObjectMapper();
        HolidayListResponse holidayListResponse=new HolidayListResponse();
        List<HolidayListDTO> holidayListDTOList = new ArrayList<>();
        try {
            List<MutationEntity> entities = mutationsDomainService.getMutations(
                    getTaskCode());
            holidayListDTOList.addAll(entities.stream().map(entity -> {
                String data = entity.getPayload().getData();
                HolidayListDTO holidayListDTO = null;
                try {
                    holidayListDTO = objectMapper.readValue(data, HolidayListDTO.class);
                    holidayListDTO = holidayListAssembler.setAuditFields(entity,holidayListDTO);
                } catch (JsonProcessingException e) {
                    ExceptionUtil.handleException(JSON_PARSING_ERROR);
                }
                return holidayListDTO;
            }).toList());
            fillTransactionStatus(transactionStatus);
        }
        catch (Exception exception) {
            fillTransactionStatus(transactionStatus, exception);
        }
        finally {
            Interaction.close();
        }
        holidayListResponse.setHolidayList(holidayListDTOList);
        holidayListResponse.setTransactionStatus(transactionStatus);
        return holidayListResponse;
    }


//        @Transactional
    public TransactionStatus processHolidayList (SessionContext sessionContext, HolidayListDTO holidayListDTO)
            throws FatalException{
        if (log.isInfoEnabled()) {
            log.info("In processHolidayList with parameters sessionContext {}, holidayListDTO {}",
                    sessionContext, holidayListDTO);
        }

            TransactionStatus transactionStatus = fetchTransactionStatus();
            try {
                Interaction.begin(sessionContext);
                prepareTransactionContext(sessionContext, TransactionMessageType.NORMAL_MESSAGE);


                holidayCalendarBusinessPolicyFactory.getInstance().getPolicyInstance(HOLIDAY_CALENDAR_BUSINESS_POLICY,new HolidayCalendarBusinessPolicyDTO(holidayListDTO)).validate();
                    process.process(holidayListDTO);
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
    public void addUpdateRecord (String data) throws JsonProcessingException {
        ObjectMapper objMapper = new ObjectMapper();
        HolidayListDTO holidayListDTO = objMapper.readValue(data, HolidayListDTO.class);
        save(holidayListDTO);
    }


    @Override
    public CoreEngineBaseDTO getConfigurationByCode (String code) {
        return holidayListAssembler.convertEntityToDto(holidayListDomainService.getHolidayListById(code));
    }

    @Override
    public void save(HolidayListDTO holidayListDTO){
        holidayListDomainService.save(holidayListDTO);
    }

    private boolean isAuthorized (final String authorized) {
        Predicate<String> isAuthorized = s -> s.equals("Y");
        return isAuthorized.test(authorized);
    }

    private String getTaskCode () {
        return HolidayListDTO.builder().build().getTaskCode();
    }

}

package com.idg.idgcore.domain.process;

import com.idg.idgcore.app.config.ServiceBeanConfig;
import com.idg.idgcore.app.config.MappingConfig;
import com.idg.idgcore.app.dto.*;
import com.idg.idgcore.app.service.*;
import com.idg.idgcore.domain.service.*;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.annotation.PostConstruct;
import java.util.List;

import static com.idg.idgcore.common.Constants.MappingConfig.AUTHORIZE;
import static com.idg.idgcore.common.Constants.MappingConfig.CHECKER;
import static com.idg.idgcore.common.Constants.MappingConfig.MAKER;
import static com.idg.idgcore.common.Constants.Status.INACTIVE;
import static com.idg.idgcore.common.Constants.Y;

@Slf4j
@Service
public class ProcessConfiguration implements IProcessConfiguration {
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private List<MappingDTO> mappings;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private MutationsDomainService mutationsDomainService;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private ServiceBeanConfig serviceBeanConfig;

    @PostConstruct
    public void init () {
        mappings = mappingConfig.getMappings();
    }

    @Value("${audit.history.kafka.enabled}")
    String auditHistoryKafkaEnabled;

    public void process (CoreEngineBaseDTO baseDTO) throws JsonProcessingException {
        log.info("In process with parameters BaseDTO {}", baseDTO);
        MappingDTO mapping = mappingConfig.getMappingByCrietria(baseDTO.getAction(),
                getUserRole(baseDTO.getAction()), baseDTO.getStatus());
        MutationDTO mutationDto = getMutationDTO(baseDTO, mapping);
        log.info("In process with enriched MutationDTO {}", baseDTO);
        if (isTrue(mapping.getInactivePreviousRecord()) && mutationDto.getRecordVersion() > 1) {
            CoreEngineBaseDTO baseRecord = getPreviousRecord(mutationDto);
            baseRecord.setStatus(INACTIVE);
            insertIntoAuditHistory(getMutation(baseRecord));
        }
        addUpdateRecord(mutationDto);
        if (isTrue(mapping.getInsertRecordIntoAudit())) {
            insertIntoAuditHistory(mutationDto);
        }
        if (isTrue(mapping.getInsertRecordIntoBasetable())) {
            insertIntoBaseTable(mutationDto);
        }
    }

    private IBaseApplicationService getService (String key) {
        log.info("In getService with parameters key {}", key);
        String[] data = serviceBeanConfig.getBeanConfig().get(key).split("&&");
        String beanName = data[0];
        String className = data[1];
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                className);
        return (IBaseApplicationService) appContext.getBean(beanName);
    }

    public MutationDTO getMutationDTO (CoreEngineBaseDTO baseDto, MappingDTO mappingDto)
            throws JsonProcessingException {
        log.info("In getMutationDTO with parameters BaseDTO {}, MappingDTO {}", baseDto,
                mappingDto);
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.map(mappingDto, baseDto);
        baseDto.setStatus(mappingDto.getMappingStatus());
        if (isTrue(mappingDto.getUpdateRecordVersion())) {
            baseDto.setRecordVersion(baseDto.getRecordVersion() + 1);
        }
        String data = objectMapper.writeValueAsString(baseDto);
        PayloadDTO payload = PayloadDTO.builder().data(data).build();
        MutationDTO mutationDTO = modelMapper.map(baseDto, MutationDTO.class);
        mutationDTO.setPayload(payload);
        mutationDTO.setTaskCode(baseDto.getTaskCode());
        mutationDTO.setTaskIdentifier(baseDto.getTaskIdentifier());
        return mutationDTO;
    }

    public CoreEngineBaseDTO getPreviousRecord (MutationDTO dto) {
        log.info("In getPreviousRecord with parameters MappingDTO {}", dto);
        CoreEngineBaseDTO baseDTO = getService(dto.getTaskCode()).getConfigurationByCode(
                dto.getTaskIdentifier());
        baseDTO.setStatus(INACTIVE);
        return baseDTO;
    }

    public void addUpdateRecord (MutationDTO dto) {
        log.info("In addUpdateRecord with parameters MappingDTO {}", dto);
        mutationsDomainService.addUpdate(dto);
    }

    public void insertIntoAuditHistory (MutationDTO dto) {
        log.info("In insertIntoAuditHistory with parameters MappingDTO {}", dto);
        if(!isKafkaEnabled(auditHistoryKafkaEnabled))
        {
            mutationsDomainService.insertIntoAuditHistory(dto);
        }
    }

    public void insertIntoBaseTable (MutationDTO dto) throws JsonProcessingException {
        log.info("In insertIntoBaseTable with parameters MappingDTO {}", dto);
        getService(dto.getTaskCode()).addUpdateRecord(dto.getPayload().getData());
    }

    public boolean isTrue (String data) {
        return data.equals(Y);
    }

    private MutationDTO getMutation (CoreEngineBaseDTO dto) throws JsonProcessingException {
        MutationDTO mutationDTO = null;
        String data = objectMapper.writeValueAsString(dto);
        PayloadDTO payload = PayloadDTO.builder().data(data).build();
        mutationDTO = MutationDTO.builder().payload(payload).taskCode(dto.getTaskCode())
                .taskIdentifier(dto.getTaskIdentifier()).build();
        return modelMapper.map(dto, MutationDTO.class);
    }

    private String getUserRole (String action) {
        log.info("In getUserRole with parameters action {}", action);
        return action.equals(AUTHORIZE) ? CHECKER : MAKER;
    }

    private boolean isKafkaEnabled (String value) {
        log.info("In isKafkaEnabled with parameters value {}", value);
        return value.equals("false") ? false : true;
    }

}

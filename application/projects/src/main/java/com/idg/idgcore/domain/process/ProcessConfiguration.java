package com.idg.idgcore.domain.process;

import com.idg.idgcore.app.config.ServiceBeanConfig;
import com.idg.idgcore.app.config.MappingConfig;
import com.idg.idgcore.app.dto.MappingDTO;
import com.idg.idgcore.app.dto.CoreEngineBaseDTO;
import com.idg.idgcore.app.dto.PayloadDTO;
import com.idg.idgcore.app.dto.MutationDTO;
import com.idg.idgcore.app.service.IBaseApplicationService;
import com.idg.idgcore.domain.service.IMutationsDomainService;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.infra.ThreadAttribute;
import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import static com.idg.idgcore.common.Constants.STRING_Y;
import static com.idg.idgcore.common.Constants.Status.INACTIVE;

@Slf4j
@Service
public class ProcessConfiguration implements IProcessConfiguration {
    private final ModelMapper modelMapper = new ModelMapper();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value ("${audit.history.kafka.enabled}")
    String auditHistoryKafkaEnabled;
    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private IMutationsDomainService mutationsDomainService;
    @Autowired
    private MappingConfig mappingConfig;
    @Autowired
    private ServiceBeanConfig serviceBeanConfig;
    private List<MappingDTO> mappings;

    @PostConstruct
    public void init () {
        mappings = mappingConfig.getMappings();
    }

    public void process (CoreEngineBaseDTO baseDTO) throws JsonProcessingException {
        log.info("In process with parameters BaseDTO {}", baseDTO);
        SessionContext sessionContext = (SessionContext) ThreadAttribute.get(ThreadAttribute.SESSION_CONTEXT);
        MappingDTO mapping = getMapping(baseDTO.getAction(),
                Arrays.stream(sessionContext.getRole()).findAny().get(),
                baseDTO.getStatus());

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
        String beanName = serviceBeanConfig.getBeanConfig().get(key);
        return (IBaseApplicationService) appContext.getBean(beanName);
    }

    private MappingDTO getMapping (String action, String role, String status) {
        Predicate<MappingDTO> filter = mapping -> mapping.getAction().equals(action)
                && mapping.getStatus().equals(status) && mapping.getRole().equals(role);
        Optional<MappingDTO> mapping = mappings.stream().filter(filter).findFirst();
        if (mapping.isPresent()) {
            return mapping.get();
        }
        else {
            log.info("Invalid request.Please check the configuration details");
            throw new BusinessException("BBE002", "No mapping found for the request");
        }
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
        if (!isKafkaEnabled(auditHistoryKafkaEnabled)) {
            mutationsDomainService.insertIntoAuditHistory(dto);
        }
    }

    public void insertIntoBaseTable (MutationDTO dto) throws JsonProcessingException {
        log.info("In insertIntoBaseTable with parameters MappingDTO {}", dto);
        getService(dto.getTaskCode()).addUpdateRecord(dto.getPayload().getData());
    }

    public boolean isTrue (String data) {
        return data.equals(STRING_Y);
    }

    private MutationDTO getMutation (CoreEngineBaseDTO dto) {
        return modelMapper.map(dto, MutationDTO.class);
    }

    private boolean isKafkaEnabled (String value) {
        log.info("In isKafkaEnabled with parameters value {}", value);
        return value.equals("Y") || value.equals("y");
    }

}

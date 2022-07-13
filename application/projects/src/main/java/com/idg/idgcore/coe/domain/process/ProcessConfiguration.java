package com.idg.idgcore.coe.domain.process;

import com.idg.idgcore.coe.app.config.ServiceBeanConfig;
import com.idg.idgcore.coe.app.config.MappingConfig;
import com.idg.idgcore.coe.dto.mapping.MappingDTO;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.mutation.MutationDTO;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
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

import static com.idg.idgcore.coe.common.Constants.INACTIVE;
import static com.idg.idgcore.coe.common.Constants.STRING_Y;

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
        SessionContext sessionContext = (SessionContext) ThreadAttribute.get(
                ThreadAttribute.SESSION_CONTEXT);
        MappingDTO mapping = getMapping(baseDTO.getAction(), sessionContext.getRole(),
                baseDTO.getStatus());
        MutationDTO mutationDto = getMutationDTO(baseDTO, mapping);
        log.info("In process with enriched MutationDTO {}", baseDTO);
        if (isTrue(mapping.getInactivePreviousRecord()) && mutationDto.getRecordVersion() > 1) {
            CoreEngineBaseDTO baseRecord = getPreviousRecord(mutationDto);
            baseRecord.setStatus(INACTIVE);
            baseRecord.setAction(baseRecord.getAction());
            insertIntoAuditHistory(getMutationDTO(baseRecord));
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

    private MappingDTO getMapping (String action, String[] role, String status) {
        Predicate<MappingDTO> filter = mapping -> mapping.getAction().equals(action)
                && mapping.getStatus().equals(status) && Arrays.stream(role)
                .anyMatch(mapping.getRole()::equals);
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
        return getService(dto.getTaskCode()).getConfigurationByCode(dto.getTaskIdentifier());
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

    private boolean isKafkaEnabled (String value) {
        log.info("In isKafkaEnabled with parameters value {}", value);
        return value.equals("Y") || value.equals("y");
    }

    public MutationDTO getMutationDTO (CoreEngineBaseDTO baseDto) throws JsonProcessingException {
        log.info("In getMutationDTO with parameters BaseDTO {}", baseDto);
        MutationDTO mutationDTO = MutationDTO.builder().build();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        modelMapper.map(baseDto, mutationDTO);
        String data = objectMapper.writeValueAsString(baseDto);
        PayloadDTO payload = PayloadDTO.builder().data(data).build();
        mutationDTO.setPayload(payload);
        mutationDTO.setTaskCode(baseDto.getTaskCode());
        mutationDTO.setTaskIdentifier(baseDto.getTaskIdentifier());
        return mutationDTO;
    }

}

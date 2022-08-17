package com.idg.idgcore.coe.app.service.mapping;

import static org.assertj.core.api.Assertions.assertThat;

import com.idg.idgcore.coe.domain.entity.mapping.*;
import com.idg.idgcore.coe.domain.service.mapping.*;
import com.idg.idgcore.coe.dto.mapping.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.*;
import java.util.*;

import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class MappingApplicationServiceTest {
    @InjectMocks
    private MappingApplicationService mappingApplicationService;
    @Mock
    MappingDomainService mappingDomainService;
    private final ModelMapper mapper = new ModelMapper();
    private MappingEntity mapping;
    private MappingEntity mapping1;

    @BeforeEach
    public void setup () {
        mapping = getValidMappingEntity();
        mapping1 = getMappingentityBuilder();
    }

    @Test
    void getMappingByCrietria () {
        MappingDTO mappingDTO = getdMappingDTOGetterSetter("add", "maker", "new");
        given(mappingDomainService.getMappingByCrietria("add", "maker", "new")).willReturn(mapping);
        MappingDTO mappingByCrietria = mappingApplicationService.getMappingByCrietria("add",
                "maker", "new");
        assertThat(mappingDTO).isNotNull();
        assertThat(mappingByCrietria.getStatus().equals("new"));

    }

    @Test
    void getMappings () {

        given(mappingDomainService.getAllMappings())
                .willReturn(List.of(mapping,mapping1));
        List<MappingDTO> entityList = mappingApplicationService.getMappings();
        assertThat(entityList).isNotNull();
        assertThat(entityList).hasSize(2);

    }

    private MappingDTO getdMappingDTOGetterSetter (String action, String role, String status) {
        MappingDTO mappingDTO = new MappingDTO();
        mappingDTO.setAction("add");
        mappingDTO.setRole("maker");
        mappingDTO.setAuthorized("N");
        mappingDTO.setStatus("new");
        mappingDTO.setUpdateRecordVersion("Y");
        mappingDTO.setInactivePreviousRecord("N");
        mappingDTO.setLastConfigurationAction("unauthorized");
        mappingDTO.setInsertRecordIntoAudit("Y");
        mappingDTO.setInsertRecordIntoBasetable("N");
        mappingDTO.setMappingStatus("new");
        return mappingDTO;
    }

    private MappingEntity getMappingentityBuilder () {
        MappingEntity mapping1 = new MappingEntity();
        mapping1.setAction("draft");
        mapping1.setRole("maker");
        mapping1.setAuthorized("N");
        mapping1.setStatus("draft");
        mapping1.setLastConfigurationAction("draft");
        mapping1.setUpdateRecordVersion("0");
        mapping1.setInactivePreviousRecord("N");
        mapping1.setInsertRecordIntoAudit("Y");
        mapping1.setInsertRecordIntoBasetable("N");
        mapping1.setMappingStatus("draft");
        return mapping1;
    }

    private MappingEntity getValidMappingEntity () {
        MappingEntity mappingEntity = MappingEntity.builder()
                .action("add").role("maker")
                .authorized("N").status("new")
                .updateRecordVersion("0").inactivePreviousRecord("N")
                .lastConfigurationAction("unauthorized").insertRecordIntoAudit("N")
                .insertRecordIntoBasetable("N").mappingStatus("new")
                .build();
        return mappingEntity;
    }

}
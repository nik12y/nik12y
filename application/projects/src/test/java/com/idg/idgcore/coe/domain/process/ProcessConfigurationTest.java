package com.idg.idgcore.coe.domain.process;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith (MockitoExtension.class)
class ProcessConfigurationTest {
//    @InjectMocks
//    private ProcessConfiguration processConfiguration;
//    @Mock
//    MappingConfig mappingConfig;
//    @Mock
//    MappingDomainService mappingDomainService;
//    @Mock
//    IMutationsDomainService mutationsDomainService;
//    @Mock
//    IMutationRepository mutationRepository;
//    private MappingDTO mapping;
//    private MappingDTO mapping1;
//    private MappingEntity mappingEntity;
//    private SessionContext sessionContext;
//    private PayloadDTO payloadDTO;
//    private List<MappingDTO> mappings;
//    private CoreEngineBaseDTO baseDTO;
//    private CoreEngineBaseDTO baseDTOAllArgsConstructor;
//
//    @BeforeEach
//    public void setup () {
//        mapping = getValidMapping();
//        mapping1 = getValidMapping1();
//        mappingEntity = getValidMappingEntity();
//        sessionContext = SessionContext.builder()
//                .userId("MUser")
//                .bankCode("03").defaultBranchCode("030001")
//                .postingDate(new Date()).transactionBranch("0300001")
//                .targetUnit("PM")
//                .build();
//        baseDTO = CoreEngineBaseDTO.builder().
//                action("draft").status("draft").recordVersion(0).authorized("N")
//                .lastConfigurationAction("draft")
//                .build();
//        baseDTOAllArgsConstructor =  CityDTO.builder().cityCode("CITYCODE")
//                ."draft", "draft", 0, "N", "unauthorized",
//                "CITY", "MU");
//        payloadDTO = PayloadDTO.builder().
//                data("Data").
//                build();
//    }
//
//    @Test
//    void init () {
//        given(mappingConfig.getMappings()).willReturn(List.of(mapping, mapping1));
//        processConfiguration.init();
//        verify(mappingConfig, times(1)).getMappings();
//    }
//
//    @Test
//    void getMutationDTO () throws JsonProcessingException {
//        MutationDTO mutationDTO = processConfiguration.getMutationDTO(baseDTO, mapping);
//        assertThat(mutationDTO).isNotNull();
//        assertThat(mutationDTO.getStatus()).isEqualTo("draft");
//    }
//
//    @Test
//    void getMutationDTRecordVersion () throws JsonProcessingException {
//        mapping.setUpdateRecordVersion("Y");
//        MutationDTO mutationDTO = processConfiguration.getMutationDTO(baseDTOAllArgsConstructor,
//                mapping);
//        assertThat(mutationDTO).isNotNull();
//        assertThat(mutationDTO.getStatus()).isEqualTo("draft");
//    }
//
//    @Test
//    void getMutationDTOBaseDto () throws JsonProcessingException {
//        MutationDTO mutationDTO = processConfiguration.getMutationDTO(baseDTO);
//        assertThat(mutationDTO).isNotNull();
//        assertThat(mutationDTO.getStatus()).isEqualTo("draft");
//    }
//
//    @Test
//    void addUpdateRecord () {
//        MutationDTO mutationDTO = MutationDTO.builder()
//                .taskCode("").taskIdentifier("").payload(getValidPayloadDto()).build();
//        System.out.println(" from getValidMutationDTO " + mutationDTO);
//        MutationEntity validMutationEntity = getValidMutationEntity();
//        given(mutationsDomainService.addUpdate(mutationDTO))
//                .willReturn(validMutationEntity);
//        processConfiguration.addUpdateRecord(mutationDTO);
//        verify(mutationsDomainService, times(1)).addUpdate(mutationDTO);
//    }
//
//
//    private MappingEntity getValidMappingEntity () {
//
//        return new MappingEntity(1L, "draft", "maker",
//                "N", "draft", "N",
//                "N", "draft", "Y",
//                "N", "N","draft");
//    }
//
//    private MappingDTO getValidMapping () {
//        return new MappingDTO("draft", "maker",
//                "N", "draft", "N",
//                "N", "draft", "Y","N",
//                "N", "draft");
//    }
//
//    private MappingDTO getValidMapping1 () {
//        return new MappingDTO("add", "maker", "N",
//                "new", "Y", "N",
//                "unauthorized", "Y","N", "N", "new");
//    }
//
//    private MutationEntity getValidMutationEntity () {
//        Payload payLoad = new Payload("data");
//        return new MutationEntity("", "", payLoad, "draft", "N", 0, "draft",
//                "draft", "","");
//    }
//
//    private MutationDTO getNewMutationDTO () {
//        ModelMapper mapper = new ModelMapper();
//        return mapper.map(getValidMutationEntity(), MutationDTO.class);
//    }
//
//    private MutationDTO getValidMutationDTO () {
//        PayloadDTO payloadDTO = new PayloadDTO();
//        payloadDTO.setData(
//                "\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\"");
//        return new MutationDTO(payloadDTO);
//    }
//
//    private PayloadDTO getValidPayloadDto () {
//        PayloadDTO payloadDTO = new PayloadDTO(
//                "\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\"");
//        return payloadDTO;
//    }

}
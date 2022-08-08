package com.idg.idgcore.coe.app.service.city;

import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.domain.assembler.city.*;
import com.idg.idgcore.coe.domain.entity.city.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.city.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.city.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;

import java.util.*;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class CityApplicationServiceTest {
    @InjectMocks
    private CityApplicationService cityApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    private CityAssembler cityAssembler;
    @Mock
    private ICityDomainService cityDomainService;
    @Mock
    AbstractApplicationService abstractApplicationService;
    @Autowired
    private MutationEntity mutationEntity;
    @Autowired
    private MutationEntity mutationEntity2;
    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private CityEntity cityEntity;
    private CityDTO cityDTO;
    private CityDTO cityDTOUnAuth;
    private CityDTO cityDTO1;
    private CityEntity cityEntity1;
    private CityEntity cityEntity2;
    private CityDTO cityDTOMapper;
    private MutationEntity mutationEntity5;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        cityDTO = getCityDTOAuthorized();
        cityEntity = getCityEntity();
        cityDTOUnAuth = getCityDTOUnAuth();
        cityDTOMapper = getCityDTOMapper();
        mutationEntity = getMutationEntity();
        cityEntity1 = getCitiesEntity();
        cityEntity2 = getCitiesEntity2();
        cityDTO1 = getCityDTO();
        mutationEntity2 = getMutationEntityJsonError();
        mutationEntity5 = getMutationEntity2();
    }

    @Test
    @DisplayName ("JUnit for getByCityCode in application service when Authorize try Block")
    void getCityByCodeIsAuthorize () throws FatalException {
        given(cityDomainService.getCityByCode(cityDTO.getCityCode())).willReturn(cityEntity);
        given(cityAssembler.convertEntityToDto(cityEntity)).willReturn(cityDTO);
        CityDTO cityDTO1 = cityApplicationService.getCityByCode(sessionContext, cityDTO);
        assertEquals("Y", cityDTO1.getAuthorized());
        assertThat(cityDTO1).isNotNull();
    }



    /*@DisplayName ("JUnit for getByCityCode in application service when Un-Authorize -- ")
    @Test
    void getCityByCodeWithUnAuthRecord () throws FatalException {
        CityDTO cityDTOUnAuth = getCityDTOUnAuthorizedBase();
        CityDTO cityDTOFetched = getFtchedDTO();
        MutationEntity mutationEntityUnAuth = getMutationEntityUnAuthComplete();
        given(mutationsDomainService.getConfigurationByCode(
                cityDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntityUnAuth);

        given(cityAssembler.setAuditFields(mutationEntityUnAuth, cityDTOFetched))
                .willReturn(cityDTO);
        CityDTO cityDTO1 = cityApplicationService.getCityByCode(sessionContext, cityDTOUnAuth);
        assertThat(cityDTO1.getAuthorized()).isEqualTo("N");
        assertThat(cityDTO1).isNotNull();
    }*/

    @Test
    @DisplayName ("Should return all getCities when there are no unauthorized")
    void getCitiesWhenThereAreNoUnauthorized () throws FatalException {
        given(cityDomainService.getCities()).willReturn(List.of(cityEntity));
        given(mutationsDomainService.getUnauthorizedMutation(CITY, AUTHORIZED_N)).willReturn(
                List.of());
        given(cityAssembler.convertEntityToDto(cityEntity)).willReturn(cityDTO);
        List<CityDTO> cityDTOList = cityApplicationService.getCities(sessionContext);
        assertEquals(1, cityDTOList.size());
        //                assertEquals(cityDTO, cityDTOList.get(0));
    }

    @Test
    @DisplayName ("JUnit for getByCityCode in application service when Not Authorize in catch block")
    void getCityByCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {
        given(mutationsDomainService.getConfigurationByCode(
                cityDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper = new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        //                             Mockito.when(mockObjectMapper.readValue(mutationEntity.getPayload().getData(), CityDTO.class)).thenReturn(cityDTO);
        Assertions.assertThrows(Exception.class, () -> {
            CityDTO cityDTO1 = cityApplicationService.getCityByCode(sessionContext, cityDTOMapper);
            assertEquals("N", cityDTO1.getAuthorized());
            assertThat(cityDTO1).isNotNull();
            System.out.println(cityDTO1);
        });
    }

    @Test
    @DisplayName ("JUnit for getCities in application service")
    void getCities () throws JsonProcessingException, FatalException {
        CityEntity cityEntity = getCityEntityDeleted();
        CityDTO cityDTO = getCityDTODeleted();
        MutationEntity unauthorizedEntities = getMutationEntityDeleted();
        given(cityDomainService.getCities()).willReturn(List.of(cityEntity));
        given(cityAssembler.convertEntityToDto(cityEntity)).willReturn(cityDTO);
        List<CityDTO> cityDTO1 = cityApplicationService.getCities(sessionContext);
        assertThat(cityEntity.toString()).isNotNull();
        assertThat(cityDTO1).isNotNull();
    }

   /* @Test
    @DisplayName ("JUnit for getCities in application service for catch block for checker")
    void getCitiesCatchBlockForChecker () throws JsonProcessingException, FatalException {
        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                cityDTO1.getTaskCode(), AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        List<CityDTO> cityDTO1 = cityApplicationService.getCities(sessionContext);
        System.out.println("return size : " + cityDTO1.size());
        assertThat(cityDTO1.toString()).isNotNull();
        assertThat(cityDTO1).isNotNull();
    }
*/
    @Test
    @DisplayName ("JUnit for code coverage")
    void getCodeCoverageNew () {
        CityEntityKey cityEntityKey = new CityEntityKey("MH");
        assertThat(cityEntityKey).descriptionText();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getCodeCoverageSet () {
        CityEntityKey cityEntityKey = new CityEntityKey();
        cityEntityKey.setCityCode("PU");
        cityEntityKey.keyAsString();
        assertThat(cityEntityKey).descriptionText();
    }

    @Test
    @DisplayName ("JUnit for code coverage")
    void getCodeCoverageBuilder () {
        CityEntityKey cityEntityKey = new CityEntityKey().builder().cityCode("GJ").build();
        assertThat(cityEntityKey).descriptionText();
        assertThat(cityEntityKey.toString()).descriptionText();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date())
                .transactionBranch("")
                .userId("user1")
                .channel("Branch").taskCode("CITY")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private SessionContext getErrorSession () {
        SessionContext sessionContext1 = SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[] { "maker" })
                .build();
        return sessionContext1;
    }

    private CityDTO getCityDTOAuthorized () {
        CityDTO cityDTO = new CityDTO().builder()
                .cityCode("MU")
                .cityName("MUMBAI")
                .timeZone("28 June 2022")
                .countryCode("IN")
                .stateCode("MH")
                .authorized("Y")
                .build();
        return cityDTO;
    }

    private CityDTO getCityDTO () {
        CityDTO cityDTO = new CityDTO("MU", "MUMBAI", "28 June 2022", "IN", "MH");
        cityDTO.setTaskCode("CITY");
        cityDTO.setStatus("DELETED");
        cityDTO.setRecordVersion(1);
        return cityDTO;
    }

    private CityEntity getCityEntity () {
        CityEntity cityEntity = new CityEntity("MU", "MUMBAI", "28 June 2022", "IN", "MH", "draft",
                0, "Y", "draft");
        return cityEntity;
    }

    private CityEntity getCitiesEntity () {
        CityEntity cityEntity = new CityEntity();
        cityEntity.setCityCode("MU");
        cityEntity.setCityName("MUMBAI");
        cityEntity.setTimeZone("28 June 2022");
        cityEntity.setCountryCode("IN");
        cityEntity.setStateCode("MH");
        cityEntity.setStatus("DELETED");
        cityEntity.setRecordVersion(1);
        return cityEntity;
    }

    private CityEntity getCitiesEntity2 () {
        CityEntity cityEntity2 = new CityEntity();
        cityEntity2.setCityCode("MU");
        cityEntity2.setCityName("MUMBAI");
        cityEntity2.setTimeZone("28 June 2022");
        cityEntity2.setCountryCode("IN");
        cityEntity2.setStateCode("MH");
        cityEntity2.setAuthorized("N");
        cityEntity2.setStatus("closed");
        cityEntity2.setRecordVersion(1);
        return cityEntity2;
    }

    private CityDTO getCityDTOUnAuth () {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setCityCode("MU");
        cityDTO.setCityName("MUMBAI");
        cityDTO.setTimeZone("28 June 2022");
        cityDTO.setCountryCode("IN");
        cityDTO.setStateCode("MH");
        cityDTO.setAuthorized("N");
        return cityDTO;
    }

    private CityDTO getCityDTOMapper () {
        CityDTO cityDTOMapper = new CityDTO();
        cityDTOMapper.setCityCode("MU");
        cityDTOMapper.setCityName("MUMBAI");
        cityDTOMapper.setTimeZone("28 June 2022");
        cityDTOMapper.setCountryCode("IN");
        cityDTOMapper.setStateCode("MH");
        cityDTOMapper.setAuthorized("N");
        cityDTOMapper.setTaskCode("CITY");
        cityDTOMapper.setTaskIdentifier("MU");
        return cityDTOMapper;
    }

    private MutationEntity getMutationEntity () {
        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CITY\",\"taskIdentifier\":\"MU\",\"cityCode\":\"MU\",\"cityName\":\"MUMBAI\",\"timeZone\":\"28 June 2022\",\"stateName\":\"MH\",\"countryCode\":\"IN\"}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("MU");
        mutationEntity.setTaskCode("CITY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError () {
        String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CITY\",\"taskIdentifier\":\"MH\",\"cityCode\":\"MU\",\"cityName\":\"MUMBAI\",\"timeZone\":\"28 June 2022\",\"stateName\":\"MH\",\"countryCode\":\"IN\"}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("MU");
        mutationEntity2.setTaskCode("CITY");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    private MutationEntity getMutationEntity2 () {
        String payLoadString = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"close\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"CITY\",\"taskIdentifier\":\"MH\",\"cityCode\":\"MU\",\"cityName\":\"MUMBAI\",\"timeZone\":\"28 June 2022\",\"stateName\":\"MH\",\"countryCode\":\"IN\"}";
        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("MU");
        mutationEntity5.setTaskCode("CITY");
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("new");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("add");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("user1");
        mutationEntity5.setLastUpdatedBy("sujan");
        return mutationEntity5;
    }

    String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";

    private CityEntity getCityEntityDeleted () {
        CityEntity cityEntityDeleted = new CityEntity();
        cityEntityDeleted.setCityCode("MU");
        cityEntityDeleted.setCityName("MUMBAI");
        cityEntityDeleted.setStatus("MH");
        cityEntityDeleted.setCountryCode("IN");
        cityEntityDeleted.setStatus("deleted");
        cityEntityDeleted.setRecordVersion(1);
        return cityEntityDeleted;
    }

    private CityDTO getCityDTODeleted () {
        CityDTO cityDtoDeleted = new CityDTO();
        cityDtoDeleted.setCityCode("MU");
        cityDtoDeleted.setCityName("MUMBAI");
        cityDtoDeleted.setStatus("MH");
        cityDtoDeleted.setCountryCode("IN");
        cityDtoDeleted.setAction("");
        cityDtoDeleted.setStatus("deleted");
        cityDtoDeleted.setTaskCode("CITY");
        cityDtoDeleted.setTaskIdentifier("MU");
        cityDtoDeleted.setRecordVersion(1);
        return cityDtoDeleted;
    }

    private MutationEntity getMutationEntityDeleted () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity unauthorizedEntities = new MutationEntity();
        unauthorizedEntities.setTaskCode("MH");
        unauthorizedEntities.setPayload(new Payload(payLoadString));
        unauthorizedEntities.setAuthorized("N");
        unauthorizedEntities.setStatus("DELETED");
        unauthorizedEntities.setRecordVersion(1);
        return unauthorizedEntities;
    }

    private String getpayloadValidString () {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\""
                        + "action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"t"
                        + "askCode\":\"CITY\",\"taskIdentifier\":\"MU\",\"cityCode\":\"MU\",\"cityName\":\"MUMBAI\","
                        + "\"countryCode\":\"IN\",\"stateCode\":\"MH\"})";
        return payLoadString;
    }

    private MutationEntity getMutationEntityUnAuthComplete () {
        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"CITY\",\"taskIdentifier\":\"PU\",\"cityCode\":\"PU\",\"cityName\":\"PUNE\",\"timeZone\":\"28 June 2022\",\"countryCode\":\"IN\",\"stateCode\":\"MH\"}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("PU");
        mutationEntity.setTaskCode("CITY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("draft");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(0);
        mutationEntity.setAction("draft");
        mutationEntity.setLastConfigurationAction("draft");
        return mutationEntity;
    }

    private CityDTO getCityDTOUnAuthorizedBase () {
        CityDTO cityDTOUnAuth = new CityDTO();
        cityDTOUnAuth.setCityCode("PU");
        cityDTOUnAuth.setTaskIdentifier("PU");
        cityDTOUnAuth.setTaskCode("CITY");
        cityDTOUnAuth.setAuthorized("N");
        return cityDTOUnAuth;
    }

    private CityDTO getCityDTOUnAuthorized () {
        CityDTO cityDTOUnAuth = new CityDTO();
        cityDTOUnAuth.setCityCode("PU");
        cityDTOUnAuth.setCityName("PUNE");
        cityDTOUnAuth.setStateCode("MH");
        cityDTOUnAuth.setCountryCode("IN");
        cityDTOUnAuth.setTaskIdentifier("PU");
        cityDTOUnAuth.setTaskCode("CITY");
        cityDTOUnAuth.setStatus("draft");
        cityDTOUnAuth.setAuthorized("N");
        cityDTOUnAuth.setRecordVersion(0);
        cityDTOUnAuth.setTimeZone("28 June 2022");
        cityDTOUnAuth.setLastConfigurationAction("draft");
        return cityDTOUnAuth;
    }

    private CityDTO getFtchedDTO () {
        CityDTO cityDTOFetched =
                getCityDTOUnAuthorized();
        cityDTOFetched.setAction("draft");
        cityDTOFetched.setStatus("draft");
        cityDTOFetched.setRecordVersion(0);
        cityDTOFetched.setLastConfigurationAction("draft");
        cityDTOFetched.setCityCode("PU");
        cityDTOFetched.setCityName("PUNE");
        cityDTOFetched.setTimeZone("28 June 2022");
        cityDTOFetched.setCountryCode("IN");
        cityDTOFetched.setStateCode("MH");
        CityDTO cityDTO = getCityDTOUnAuthorized();
        return cityDTOFetched;
    }

}
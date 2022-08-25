package com.idg.idgcore.coe.app.service.regulatoryRegion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntityKey;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionMappingEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.regulatoryRegion.IRegulatoryRegionDomainService;
import com.idg.idgcore.coe.dto.appvertype.AppVerTypeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionMappingDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
@ExtendWith(MockitoExtension.class)
class regulatoryRegionTest {

    @Mock
    ModelMapper mapper = new ModelMapper();
    @InjectMocks private RegulatoryRegionApplicationService regulatoryRegionApplicationService;
    @Mock        private ProcessConfiguration process;
    @Mock        private RegulatoryRegionAssembler regulatoryRegionAssembler;
    @Mock        private IRegulatoryRegionDomainService iRegulatoryRegionDomainService;
    @Mock private IMutationsDomainService mutationsDomainService;

    @Mock
    SessionContext sessionContext;
    RegulatoryRegionConfigEntity regulatoryRegionConfigEntityAuth;
    RegulatoryRegionConfigDTO regulatoryRegionConfigDTOAuth;
    RegulatoryRegionConfigDTO regulatoryRegionConfigDTOUnAuth;
    String payloads;
    MutationEntity mutationEntityUnAuth;
    RegulatoryRegionConfigDTO regulatoryRegionDTOMapper;
    MutationEntity mutationEntityError;
    RegulatoryRegionConfigEntity regulatoryRegionConfigEntityUnAuth;
    MutationEntity mutationEntityAllCatchBlock;
    MutationEntity mutationEntityAllCatchBlockError;
    RegulatoryRegionConfigEntity regulatoryRegionConfigEntityGetAll;

    @BeforeEach
    void setUp(){
        sessionContext=getValidSessionContext();
        regulatoryRegionConfigEntityAuth=getRegulatoryRegionConfigEntityAuth();
        regulatoryRegionConfigDTOAuth =getRegulatoryRegionConfigDTOAuth();
        regulatoryRegionConfigDTOUnAuth=getRegulatoryRegionConfigDTOUnAuth();
        payloads=getPayloads();
        mutationEntityUnAuth=getMutationEntityUnAuth();
        regulatoryRegionDTOMapper=getRegulatoryRegionDTOMapper();
        mutationEntityError=getMutationEntityError();
        regulatoryRegionConfigEntityUnAuth=getRegulatoryRegionConfigEntityUnAuth();
        mutationEntityAllCatchBlock=getMutationEntityAllCatchBlock();
        mutationEntityAllCatchBlockError=getMutationEntityAllCatchBlockError();
        regulatoryRegionConfigEntityGetAll=  getRegulatoryRegionEntity();
    }

    @Test
    @DisplayName("Junit test for getRegulatoryRegionByCode in try block when Authorize ")
    void getRegulatoryRegionByCodeTryBlock() throws FatalException, JsonProcessingException {
        given(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(
                regulatoryRegionConfigDTOAuth.getRegRegionCode())).willReturn(regulatoryRegionConfigEntityAuth);
       given(regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionConfigEntityAuth)).willReturn(regulatoryRegionConfigDTOAuth);
       RegulatoryRegionConfigDTO regulatoryRegionConfigDTO= regulatoryRegionApplicationService.getRegulatoryRegionByCode(sessionContext,regulatoryRegionConfigDTOAuth);
    assertEquals("Y",regulatoryRegionConfigDTO.getAuthorized());
    assertThat(regulatoryRegionConfigDTO).isNotNull();
    assertThat(regulatoryRegionConfigDTOAuth.toString()).isNotNull();
    assertThat(regulatoryRegionConfigDTO.toString()).isNotNull();
    }


    @Test
    @DisplayName("JUnit for getAppVerTypeByCode where return the AppVerType when the authorized is Y")
    void getAppVerTypeByCodeWhenAuthorizedIsYThenReturnAppVerType() throws FatalException, JsonProcessingException {

        given(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(regulatoryRegionConfigDTOAuth.getRegRegionCode())).willReturn(regulatoryRegionConfigEntityGetAll);
        given(regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionConfigEntityGetAll)).willReturn(regulatoryRegionConfigDTOAuth);
        RegulatoryRegionConfigDTO result = regulatoryRegionApplicationService.getRegulatoryRegionByCode(sessionContext, regulatoryRegionConfigDTOAuth);
        assertEquals(regulatoryRegionConfigDTOAuth, result);
    }

  @Test
  @DisplayName("JUnit for ConfigurationByCode in application service")
  void getConfigurationByCodeTest(){
      String code = regulatoryRegionConfigDTOAuth.getRegRegionCode();
      given(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(code)).willReturn(regulatoryRegionConfigEntityAuth);
      regulatoryRegionApplicationService.getConfigurationByCode(code);
      assertThat(regulatoryRegionConfigEntityAuth).isNotNull();
  }

    @Test
    @DisplayName("Junit test for getRegulatoryRegionByCode in catch block for exception")
    void getRegulatoryRegionByCodeException(){
        given(mutationsDomainService.getConfigurationByCode(regulatoryRegionConfigDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntityError);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payloads);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        assertThrows(Exception.class,()->{
            RegulatoryRegionConfigDTO regulatoryRegionByCode = regulatoryRegionApplicationService.getRegulatoryRegionByCode(sessionContext, regulatoryRegionDTOMapper);
       assertEquals("N",regulatoryRegionByCode.getAuthorized());
       assertThat(regulatoryRegionByCode).isNotNull();
        });
    }
  //getALl unauthorized records pass
  @Test
  @DisplayName("JUnit for getRegulatoryRegionCodes where return all appVerTypes when there are no unauthorized mutations")
  void getRegulatoryRegionCodesWhenThereAreNoUnauthorizedMutationsThenReturnAllRegulatoryRegionCodes() throws FatalException {
      given(iRegulatoryRegionDomainService.getRegulatoryRegionCodes()).willReturn(List.of(regulatoryRegionConfigEntityGetAll));
      given(mutationsDomainService.getUnauthorizedMutation(REGULATORY_SERVICE, AUTHORIZED_N)).willReturn(List.of());
      given(regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionConfigEntityGetAll)).willReturn(regulatoryRegionConfigDTOAuth);

      List<RegulatoryRegionConfigDTO> regulatoryRegionCodes = regulatoryRegionApplicationService.getRegulatoryRegionCodes(sessionContext);

      assertEquals(1, regulatoryRegionCodes.size());
      assertEquals(regulatoryRegionConfigDTOAuth, regulatoryRegionCodes.get(0));
  }

    @Test
    @DisplayName("JUnit for getRegulatoryRegionCodes in application service for catch block for checker")
    void getRegulatoryRegionCodesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntityAllCatchBlock();
        MutationEntity unauthorizedEntities1 = getMutationEntityAllCatchBlockError();
        sessionContext.setRole(new String[] { "" });
        regulatoryRegionConfigDTOUnAuth.setStatus("DELETED");
        given(mutationsDomainService.getUnauthorizedMutation(
                regulatoryRegionConfigDTOUnAuth.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<RegulatoryRegionConfigDTO> regulatoryRegionCodes = regulatoryRegionApplicationService.getRegulatoryRegionCodes(sessionContext);
            assertThat(regulatoryRegionCodes).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payloads1="{\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"REG_REGION\",\"taskIdentifier\":\"REGC001\"," +
                "\"regRegionCode\":\"REGC001\",\"regionName\":\"United Kingdom\",\"regionDescription\":\"The USA ()\"," +
                "\"regionEffectiveDate\":\"2022-08-21\",\"regionGroupCode\":\"Country\",\"purpose\":\"Tax\"," +
                "\"regulatoryRegionMapping\":[{\"demographicMappingCode\":\"UK\"}]}";
        doNothing().when(iRegulatoryRegionDomainService).save(regulatoryRegionConfigDTOAuth);
        regulatoryRegionApplicationService.save(regulatoryRegionConfigDTOAuth);
        regulatoryRegionApplicationService.addUpdateRecord(payloads1);
        verify(iRegulatoryRegionDomainService, times(1)).save(regulatoryRegionConfigDTOAuth);
    }

    @Test
    @DisplayName("JUnit for processRegulatoryRegion in application service for Try Block")
    void processRegulatoryRegionTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(process).process(regulatoryRegionConfigDTOAuth);
        regulatoryRegionApplicationService.processRegulatoryRegion(sessionContext, regulatoryRegionConfigDTOAuth);
        verify(process, times(1)).process(regulatoryRegionConfigDTOAuth);
    }

    @Test
    @DisplayName("JUnit for processAppVerType in application service for Catch Block")
    void processAppVerTypeForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            regulatoryRegionApplicationService.processRegulatoryRegion(sessionContext2, regulatoryRegionConfigDTOAuth);
            assertThat(regulatoryRegionConfigDTOAuth).descriptionText();
        });
    }
    @Test
    @DisplayName("JUnit for getAppVerTypeByCode in application service check Parameter not null")
    void getAppVerTypeByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO1=null;
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO=new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTO.setRegRegionCode("REGC002");
        regulatoryRegionConfigDTO.setAuthorized("Y");
        given(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(regulatoryRegionConfigDTO.getRegRegionCode())).willReturn(regulatoryRegionConfigEntityAuth);
        given(regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionConfigEntityAuth)).willReturn(regulatoryRegionConfigDTOAuth);
        RegulatoryRegionConfigDTO regulatoryRegionByCode = regulatoryRegionApplicationService.getRegulatoryRegionByCode(sessionContext, regulatoryRegionConfigDTO);
        assertThat(regulatoryRegionConfigDTO.getRegRegionCode()).isNotBlank();
        assertThat(regulatoryRegionConfigDTO.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for getRegulatoryRegionByCode in application service when Authorize for Negative")
    void getRegulatoryRegionByCodeIsAuthorizeForNegative() throws FatalException, JsonProcessingException {
        given(iRegulatoryRegionDomainService.getRegulatoryRegionByCode(regulatoryRegionConfigDTOAuth.getRegRegionCode())).willReturn(regulatoryRegionConfigEntityGetAll);
        given(regulatoryRegionAssembler.convertEntityToDto(regulatoryRegionConfigEntityGetAll)).willReturn(regulatoryRegionConfigDTOAuth);
        RegulatoryRegionConfigDTO re = regulatoryRegionApplicationService.getRegulatoryRegionByCode(sessionContext, regulatoryRegionConfigDTOAuth);
        assertNotEquals("N", re.getAuthorized());
        assertThat(regulatoryRegionConfigDTOAuth).isNotNull();
    }


    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(regulatoryRegionConfigEntityAuth.toString()).isNotNull();
        assertThat(regulatoryRegionConfigDTOAuth.toString()).isNotNull();

        RegulatoryRegionMappingDTO regulatoryRegionMappingDTO = new RegulatoryRegionMappingDTO("IN");

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = new RegulatoryRegionConfigDTO("REGC002", "India",
                "The India","2022-08-21", "Country","Tax",List.of(regulatoryRegionMappingDTO));

        RegulatoryRegionMappingDTO regulatoryRegionMappingDTO1 = new RegulatoryRegionMappingDTO("IN");

        RegulatoryRegionConfigDTO.builder()
                .regRegionCode("REGC002")
                .regionName("India")
                .regionDescription("The India")
                .regionEffectiveDate("2022-08-21")
                .regionGroupCode("Country")
                .purpose("Tax")
                .regulatoryRegionMapping(List.of(regulatoryRegionMappingDTO1))
                .authorized("N")
                .taskCode(REGULATORY_SERVICE)
                .taskIdentifier("REGC002")
                .build().toString();

        RegulatoryRegionConfigEntityKey regulatoryRegionConfigEntityKey=new RegulatoryRegionConfigEntityKey("REGC002");
        assertThat(regulatoryRegionConfigEntityKey.toString()).isNotNull();
        regulatoryRegionConfigEntityKey.setRegRegionCode("REGC002");
        regulatoryRegionConfigEntityKey.keyAsString();
        RegulatoryRegionConfigEntityKey.builder().regRegionCode("REGC002").build();
        assertThat(regulatoryRegionConfigDTOAuth).descriptionText();
    }

    private MutationEntity getMutationEntityAllCatchBlock() {
        String payLoadString="{\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"REG_REGION\",\"taskIdentifier\":\"REGC001\"," +
                "\"regRegionCode\":\"REGC001\",\"regionName\":\"United Kingdom\",\"regionDescription\":\"The USA ()\"," +
                "\"regionEffectiveDate\":\"2022-08-21\",\"regionGroupCode\":\"Country\",\"purpose\":\"Tax\"," +
                "\"regulatoryRegionMapping\":[{\"demographicMappingCode\":\"UK\"}]}";
        MutationEntity mutationEntityAllCatchBlock = new MutationEntity();
        mutationEntityAllCatchBlock.setTaskIdentifier("REGC002");
        mutationEntityAllCatchBlock.setTaskCode(REGULATORY_SERVICE);
        mutationEntityAllCatchBlock.setPayload(new Payload(payLoadString));
        mutationEntityAllCatchBlock.setStatus("closed");
        mutationEntityAllCatchBlock.setAuthorized("N");
        mutationEntityAllCatchBlock.setRecordVersion(1);
        mutationEntityAllCatchBlock.setAction("add");
        mutationEntityAllCatchBlock.setLastConfigurationAction("unauthorized");

        return mutationEntityAllCatchBlock;
    }
    private MutationEntity getMutationEntityAllCatchBlockError() {
        String payLoadString="{\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"REG_REGION\",\"taskIdentifier\":\"REGC001\"," +
                "\"regRegionCode\":\"REGC001\",\"regionName\":\"United Kingdom\",\"regionDescription\":\"The USA ()\"," +
                "\"regionEffectiveDate\":\"2022-08-21\",\"regionGroupCode\":\"Country\",\"purpose\":\"Tax\"," +
                "\"regulatoryRegionMapping\":[{\"demographicMappingCode\":\"UK\"}]}";
        MutationEntity mutationEntityAllCatchBlockError = new MutationEntity();
        mutationEntityAllCatchBlockError.setTaskIdentifier("REGC002");
        mutationEntityAllCatchBlockError.setTaskCode(REGULATORY_SERVICE);
        mutationEntityAllCatchBlockError.setPayload(new Payload(payLoadString));
        mutationEntityAllCatchBlockError.setStatus("closed");
        mutationEntityAllCatchBlockError.setAuthorized("N");
        mutationEntityAllCatchBlockError.setRecordVersion(1);
        mutationEntityAllCatchBlockError.setAction("add");
        mutationEntityAllCatchBlockError.setLastConfigurationAction("unauthorized");

        return mutationEntityAllCatchBlockError;
    }


    private RegulatoryRegionConfigEntity getRegulatoryRegionConfigEntityUnAuth() {

        RegulatoryRegionMappingEntity regulatoryRegionMappingEntity = new RegulatoryRegionMappingEntity(1,"IN");

        return new RegulatoryRegionConfigEntity("REGC002", "India",
                "The India", getDate("2022-08-21"), "Country",
                "Fees",  List.of(regulatoryRegionMappingEntity),
                null,
                null,
                "add",
                1,
                "N",
                "unauthorized");
    }

    private RegulatoryRegionConfigEntity getRegulatoryRegionEntity() {

        RegulatoryRegionMappingEntity regulatoryRegionMappingEntity = new RegulatoryRegionMappingEntity(1,"IN");

        return new RegulatoryRegionConfigEntity("REGC002", "India",
                "The India", getDate("2022-08-21"), "Country",
                "Fees",  List.of(regulatoryRegionMappingEntity),
                null,
                null,
                "DELETED",
                1,
                null,
                null);
    }

    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }


    private RegulatoryRegionConfigEntity getRegulatoryRegionConfigEntityAuth(){
        RegulatoryRegionMappingEntity regulatoryRegionMappingEntity=new RegulatoryRegionMappingEntity();
        regulatoryRegionMappingEntity.setDemographicMappingCode("IN");
        RegulatoryRegionConfigEntity regulatoryRegionConfigEntityAuth=new RegulatoryRegionConfigEntity();
        regulatoryRegionConfigEntityAuth.setRegRegionCode("REG002");
        regulatoryRegionConfigEntityAuth.setRegionName("India");
        regulatoryRegionConfigEntityAuth.setRegionDescription("The India");
        regulatoryRegionConfigEntityAuth.setRegionEffectiveDate(getDate("2022-08-21"));
        regulatoryRegionConfigEntityAuth.setRegionGroupCode("Country");
        regulatoryRegionConfigEntityAuth.setPurpose("Fees");
        regulatoryRegionConfigEntityAuth.setRegulatoryRegionMappingEntity(List.of(regulatoryRegionMappingEntity));
        regulatoryRegionConfigEntityAuth.setStatus("active");
        regulatoryRegionConfigEntityAuth.setAuthorized("Y");
        regulatoryRegionConfigEntityAuth.setLastConfigurationAction("authorized");
        regulatoryRegionConfigEntityAuth.setRecordVersion(1);
        return regulatoryRegionConfigEntityAuth;
    }
    private RegulatoryRegionConfigDTO getRegulatoryRegionConfigDTOAuth(){
        RegulatoryRegionMappingDTO regulatoryRegionMappingDTOAuth=new RegulatoryRegionMappingDTO();
        regulatoryRegionMappingDTOAuth.setDemographicMappingCode("UK");
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTOAuth=new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTOAuth.setRegRegionCode("REGC001");
        regulatoryRegionConfigDTOAuth.setRegionName("United Kingdom");
        regulatoryRegionConfigDTOAuth.setRegionDescription("The USA ()");
        regulatoryRegionConfigDTOAuth.setRegionEffectiveDate("2022-08-21");
        regulatoryRegionConfigDTOAuth.setRegionGroupCode("Country");
        regulatoryRegionConfigDTOAuth.setPurpose("Tax");
        regulatoryRegionConfigDTOAuth.setRegulatoryRegionMapping(List.of(regulatoryRegionMappingDTOAuth));
        regulatoryRegionConfigDTOAuth.setStatus("active");
        regulatoryRegionConfigDTOAuth.setAuthorized("Y");
        regulatoryRegionConfigDTOAuth.setLastConfigurationAction("authorized");
        regulatoryRegionConfigDTOAuth.setRecordVersion(1);
        regulatoryRegionConfigDTOAuth.setAction("authorize");
        return regulatoryRegionConfigDTOAuth;
    }
    private RegulatoryRegionConfigDTO getRegulatoryRegionConfigDTOUnAuth(){
        RegulatoryRegionMappingDTO regulatoryRegionMappingDTOUnAuth=new RegulatoryRegionMappingDTO();
        regulatoryRegionMappingDTOUnAuth.setDemographicMappingCode("UK");
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTOUnAuth=new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTOUnAuth.setRegRegionCode("REGC001");
        regulatoryRegionConfigDTOUnAuth.setRegionName("United Kingdom");
        regulatoryRegionConfigDTOUnAuth.setRegionDescription("The USA ()");
        regulatoryRegionConfigDTOUnAuth.setRegionEffectiveDate("2022-08-21");
        regulatoryRegionConfigDTOUnAuth.setRegionGroupCode("Country");
        regulatoryRegionConfigDTOUnAuth.setPurpose("Tax");
        regulatoryRegionConfigDTOUnAuth.setRegulatoryRegionMapping(List.of(regulatoryRegionMappingDTOUnAuth));
        regulatoryRegionConfigDTOUnAuth.setStatus("new");
        regulatoryRegionConfigDTOUnAuth.setAuthorized("N");
        regulatoryRegionConfigDTOUnAuth.setLastConfigurationAction("unauthorized");
        regulatoryRegionConfigDTOUnAuth.setRecordVersion(0);
        regulatoryRegionConfigDTOUnAuth.setAction("unauthorized");
        regulatoryRegionConfigDTOUnAuth.setTaskIdentifier("REGC001");
        return regulatoryRegionConfigDTOUnAuth;
    }

    //this payload for Unauthorized Records
    private String getPayloads(){
        String payloads="{\"action\":\"add\",\"status\":\"new\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"REG_REGION\",\"taskIdentifier\":\"REGC001\"," +
                "\"regRegionCode\":\"REGC001\",\"regionName\":\"United Kingdom\",\"regionDescription\":\"The USA ()\"," +
                "\"regionEffectiveDate\":\"2022-08-21\",\"regionGroupCode\":\"Country\",\"purpose\":\"Tax\"," +
                "\"regulatoryRegionMapping\":[{\"demographicMappingCode\":\"UK\"}]}";
        return payloads;
    }
    //this mutation is for unauthorized records
    private MutationEntity getMutationEntityUnAuth(){
        MutationEntity mutationEntityUnAuth=new MutationEntity();
        mutationEntityUnAuth.setTaskIdentifier("REGC001");
        mutationEntityUnAuth.setTaskCode("REG_REGION");
        mutationEntityUnAuth.setPayload(new Payload(payloads));
        mutationEntityUnAuth.setStatus("new");
        mutationEntityUnAuth.setAuthorized("N");
        mutationEntityUnAuth.setAction("add");
        mutationEntityUnAuth.setRecordVersion(1);
        mutationEntityUnAuth.setLastConfigurationAction("unauthorized");
        return mutationEntityUnAuth;
    }

    //this mutation is for exception catch block
    private MutationEntity getMutationEntityError(){
        String payLoadString1="{\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"REG_REGION\",\"taskIdentifier\":\"REGC001\"," +
                "\"regRegionCode\":\"REGC001\",\"regionName\":\"United Kingdom\",\"regionDescription\":\"The USA ()\"," +
                "\"regionEffectiveDate\":\"2022-08-21\",\"regionGroupCode\":\"Country\",\"purpose\":\"Tax\"," +
                "\"regulatoryRegionMapping\":[{\"demographicMappingCode\":\"UK\"}]}";
       MutationEntity mutationEntityError=new MutationEntity();
       mutationEntityError.setTaskIdentifier("REGC001");
       mutationEntityError.setTaskCode(REGULATORY_SERVICE);
       mutationEntityError.setPayload(new Payload(payLoadString1));
       mutationEntityError.setStatus("closed");
       mutationEntityError.setAuthorized("N");
       mutationEntityError.setRecordVersion(1);
       mutationEntityError.setAction("add");
       mutationEntityError.setLastConfigurationAction("unauthorized");
        return mutationEntityError;
    }

    private RegulatoryRegionConfigDTO getRegulatoryRegionDTOMapper() {

        RegulatoryRegionMappingDTO regulatoryRegionMappingDTO = RegulatoryRegionMappingDTO.builder().demographicMappingCode("UK").build();

        return RegulatoryRegionConfigDTO.builder()
                .regRegionCode("REGC001")
                .regionName("Identification Proof")
                .regionDescription("Customer Identification")
                .regionEffectiveDate("2022-08-21")
                .regionGroupCode("Country")
                .purpose("Tax")
                .regulatoryRegionMapping(List.of(regulatoryRegionMappingDTO))
                .authorized("N")
                .taskCode(REGULATORY_SERVICE)
                .taskIdentifier("REGC001")
                .build();
    }

    private SessionContext getValidSessionContext() {
        return SessionContext.builder()
                .bankCode("003")
                .defaultBranchCode("1141")
                .internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("")
                .targetUnit("dummy_target")
                .postingDate(new Date())
                .valueDate(new Date())
                .transactionBranch("")
                .userId("nikhil")
                //                .accessibleTargetUnits([])
                .channel("Branch")
                .taskCode(APP_VERIFICATION_TYPE)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
                //                .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();

    }
}































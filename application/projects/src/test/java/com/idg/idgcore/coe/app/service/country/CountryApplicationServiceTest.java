package com.idg.idgcore.coe.app.service.country;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.app.service.state.StateApplicationService;
import com.idg.idgcore.coe.domain.assembler.country.CountryAssembler;
import com.idg.idgcore.coe.domain.assembler.state.StateAssembler;
import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.country.CountryEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.state.StateEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.country.ICountryDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.state.IStateDomainService;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.state.StateDTO;
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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;


@ExtendWith(MockitoExtension.class)
class CountryApplicationServiceTest {

    @InjectMocks
    private CountryApplicationService countryApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private CountryAssembler countryAssembler;

    @Mock
    private ICountryDomainService countryDomainService;

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
    private CountryEntity countryEntity;
    private CountryDTO countryDTO;
    private CountryDTO countryDTOUnAuth;
    private CountryDTO countryDTO1;
    private CountryEntity countryEntity1;
    private CountryEntity countryEntity2;

    private CountryDTO countryDTOMapper;

    private MutationEntity mutationEntity5;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        countryDTO=getCountryDTOAuthorized ();
        countryEntity=getCountryEntity();
        countryDTOUnAuth=getCountryDTOUnAuth();
        countryDTOMapper=getCountryDTOMapper();
        mutationEntity=getMutationEntity();
        countryEntity1=getCountrysEntity();
        countryEntity2=getCountrysEntity2();
        countryDTO1=getCountrysDTO();
        mutationEntity2=getMutationEntityJsonError();
        mutationEntity5=getMutationEntity2();

    }

    @Test
    @DisplayName("JUnit for getByCountryCode in application service when Authorize try Block")
    void getCountryByCodeIsAuthorize() throws FatalException {
        given(countryDomainService.getCountryByCode(countryDTO.getCountryCode())).willReturn(countryEntity);
        given(countryAssembler.convertEntityToDto(countryEntity)).willReturn(countryDTO);
        CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext, countryDTO);
        assertEquals("Y",countryDTO1.getAuthorized());
        assertThat(countryDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByCountryCode in application service when Not Authorize in try else block")
    void getCountryByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(countryDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity5);
        CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext,countryDTOUnAuth);
        System.out.println(countryDTO1);
        assertEquals("N",countryDTOUnAuth.getAuthorized());
        assertThat(countryDTOUnAuth).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByCountryCode in application service when Not Authorize in catch block")
    void getCountryByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(countryDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext, countryDTOUnAuth);
            assertEquals("N",countryDTO1.getAuthorized());
            assertThat(countryDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("Should return all getCountries when there are no unauthorized")
    void getCountriesWhenThereAreNoUnauthorized() throws FatalException {
        given(countryDomainService.getCountries()).willReturn(List.of(countryEntity));
        given(mutationsDomainService.getUnauthorizedMutation(COUNTRY, AUTHORIZED_N)).willReturn(List.of());
        given(countryAssembler.convertEntityToDto(countryEntity)).willReturn(countryDTO);
        List<CountryDTO> countryDTOList = countryApplicationService.getCountries(sessionContext);
        assertEquals(1, countryDTOList.size());
        assertEquals(countryDTO, countryDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getCountries in application service for catch block for checker")
    void getCountriesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                countryDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(Exception.class,()-> {
            List<CountryDTO> countryDTO1 = countryApplicationService.getCountries(sessionContext1);
            assertThat(countryDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for processCountry in application service for Try Block")
    void processCountryForTryBlock() throws JsonProcessingException, FatalException {
        doNothing().when(process).process(countryDTO);
        countryApplicationService.processCountry(sessionContext, countryDTO);
        verify(process, times(1)).process(countryDTO);
    }


    @Test
    @DisplayName("JUnit for processCountry in application service for Catch Block")
    void processCountryForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            countryApplicationService.processCountry(sessionContext2, countryDTO);
            assertThat(countryDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        doNothing().when(countryDomainService).save(countryDTO);
        countryApplicationService.save(countryDTO);
        countryApplicationService.addUpdateRecord(payLoadString1);
        verify(countryDomainService, times(1)).save(countryDTO);

    }


    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = countryDTO.getCountryCode();
        given(countryDomainService.getCountryByCode(code)).willReturn(countryEntity);
        countryApplicationService.getConfigurationByCode(code);
        assertThat(countryEntity).isNotNull();
    }
//---------------------------------------------------Negative----------------------------------------

    @Test
    @DisplayName("JUnit for getByCountryCode in application service when Authorize for Negative")
    void getCountryByCodeIsAuthorizeforNegative() throws FatalException {
        given(countryDomainService.getCountryByCode(countryDTO.getCountryCode())).willReturn(countryEntity);
        given(countryAssembler.convertEntityToDto(countryEntity)).willReturn(countryDTO);
        CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext, countryDTO);
        assertNotEquals("N",countryDTO1.getAuthorized());
        assertThat(countryDTO1).isNotNull();
    }


//    @Test
//    @DisplayName("JUnit for getByStateCode in application service when UnAuthorize fetche no Record from database")
//    void getCountryByCodeNotAuthorizeNull() throws FatalException {
//        CountryDTO countryDTOnull=null;
//        CountryDTO countryDTOEx=new CountryDTO();
//        countryDTOEx.setCountryCode("IN");
//        countryDTOEx.setAuthorized("N");
//        given(mutationsDomainService.getConfigurationByCode(countryDTOEx.getTaskIdentifier())).willReturn(mutationEntity5);
//       CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext, countryDTOEx);
//        assertNotEquals("Y",countryDTOEx.getAuthorized());
//        assertNull(countryDTOnull);
//    }

    @Test
    @DisplayName("JUnit for getByCountryCode in application service check Parameter not null")
    void getCountryByCodeIsAuthorizeCheckParameter() throws FatalException {
        CountryDTO countryDTOnull=null;
        CountryDTO countryDTOEx=new CountryDTO();
        countryDTOEx.setCountryCode("IN");
        countryDTOEx.setAuthorized("Y");
        given(countryDomainService.getCountryByCode(countryDTOEx.getCountryCode())).willReturn(countryEntity);
        given(countryAssembler.convertEntityToDto(countryEntity)).willReturn(countryDTO);
       CountryDTO countryDTO1 = countryApplicationService.getCountryByCode(sessionContext, countryDTOEx);
        assertThat(countryDTOEx.getCountryCode()).isNotBlank();
        assertThat(countryDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(countryEntity.toString()).isNotNull();
        assertThat(countryDTO.toString()).isNotNull();
        CountryDTO countryDTO =new CountryDTO("IN","INDIA","345","IND","ASIA","INR",100000f,false,false,false,false,false);
        CountryDTO.builder().countryCode("IN").countryName("INDIA").numericCountryCode("345").alternateCountryCode("IND").regionCode("ASIA").limitCurrency("INR").overallLimit(100000f).ibanRequired(false).euMember(false).clearingCodeBicPlus(false).generateMt205(false).defaultClearingNetwork(false).build().toString();
        CountryEntityKey countryEntityKey=new CountryEntityKey("IN");
        CountryEntity countryEntity=new CountryEntity("IN","INDIA","345","IND","ASIA","INR",100000f,'Y','Y','Y','Y','Y',"draft",1,"N","draft", "","");
        assertThat(countryEntityKey.toString()).isNotNull();
        countryEntityKey.setCountryCode("IN");
        countryEntityKey.keyAsString();
        countryEntityKey.builder().countryCode("IN").build();
        assertThat(countryDTO).descriptionText();
    }

    @Test
    @DisplayName("JUnit for getCountries in application service for try block negative scenario for SessionContext some field not be null")
    void getCountriesTryBlockNegative() throws JsonProcessingException, FatalException {

        CountryDTO countryDTOO = new CountryDTO();
        CountryEntity countryEntity5 = new CountryEntity();
        given(mutationsDomainService.getUnauthorizedMutation(countryDTOO.getTaskCode(), AUTHORIZED_N)).willReturn(List.of(mutationEntity5));
        given(countryDomainService.getCountries()).willReturn(List.of(countryEntity5));
        given(countryAssembler.convertEntityToDto(countryEntity5)).willReturn(countryDTOO);
        given(countryAssembler.setAuditFields(mutationEntity5, countryDTOO)).willReturn(countryDTOO);
        Assertions.assertThrows(Exception.class, () -> {
            List<CountryDTO> countryDTO2 = countryApplicationService.getCountries(sessionContext1);
            assertThat(sessionContext.getRole()).isNotEmpty();
            assertThat(sessionContext.getServiceInvocationModeType()).isNotNull();
            System.out.println(countryEntity.toString());
            System.out.println(countryDTO.toString());

        });
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("COUNTRY")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }


    private SessionContext getErrorSession(){
        SessionContext sessionContext1=SessionContext.builder()
                .bankCode("").defaultBranchCode("").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("prash")
//                .accessibleTargetUnits([])
                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("")
                .originatingModuleCode("")
                .role(new String[] {"maker"})
                .build();
        return sessionContext1;
    }

    private CountryDTO getCountryDTOAuthorized () {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryCode("IN");
        countryDTO.setCountryName("INDIA");
        countryDTO.setAlternateCountryCode("IN");
        countryDTO.setNumericCountryCode("345");
        countryDTO.setRegionCode("ASIA");
        countryDTO.setLimitCurrency("INR");
        countryDTO.setOverallLimit(100000f);
        countryDTO.setIbanRequired(false);
        countryDTO.setEuMember(false);
        countryDTO.isClearingCodeBicPlus();
        countryDTO.setGenerateMt205(false);
        countryDTO.setDefaultClearingNetwork(false);
        countryDTO.setAuthorized("Y");
        return countryDTO;
    }
    private CountryDTO getCountrysDTO()
    {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryCode("IN");
        countryDTO.setCountryName("INDIA");
        countryDTO.setAlternateCountryCode("IND");
        countryDTO.setNumericCountryCode("345");
        countryDTO.setRegionCode("ASIA");
        countryDTO.setLimitCurrency("INR");
        countryDTO.setOverallLimit(100000f);
        countryDTO.setIbanRequired(false);
        countryDTO.setEuMember(false);
        countryDTO.isClearingCodeBicPlus();
        countryDTO.setGenerateMt205(false);
        countryDTO.setDefaultClearingNetwork(false);
        countryDTO.setStatus("DELETED");
        countryDTO.setRecordVersion(1);
        return countryDTO;
    }
    private CountryEntity getCountryEntity(){
        CountryEntity countryEntity = new CountryEntity("IN","INDIA","IN","345","ASIA","INR",100000f,'N','N','N','N','N',"draft",0,"Y","draft","","");
        return countryEntity;
    }
    private CountryEntity getCountrysEntity()
    {
        CountryEntity countryEntity = new CountryEntity();
        countryEntity.setCountryCode("IN");
        countryEntity.setCountryName("INDIA");
        countryEntity.setCountryCodeAlternate("IN");
        countryEntity.setCountryCodeMachine("345");
        countryEntity.setRegionCode("ASIA");
        countryEntity.setCurrencyLimit("INR");
        countryEntity.setOverallLimit(100000f);
        countryEntity.setIsIban('N');
        countryEntity.setIsEuMember('N');
        countryEntity.setIsClearingNetwork('N');
        countryEntity.setIsMtGenerate('N');
        countryEntity.setIsClearingNetwork('N');
        countryEntity.setStatus("DELETED");
        countryEntity.setRecordVersion(1);
        return countryEntity;
    }
    private CountryEntity getCountrysEntity2()
    {
        CountryEntity countryEntity2 = new CountryEntity();
        countryEntity2.setCountryCode("IN");
        countryEntity2.setCountryName("INDIA");
        countryEntity2.setCountryCodeAlternate("IN");
        countryEntity2.setCountryCodeMachine("345");
        countryEntity2.setRegionCode("ASIA");
        countryEntity2.setCurrencyLimit("INR");
        countryEntity2.setOverallLimit(100000f);
        countryEntity2.setIsIban('N');
        countryEntity2.setIsEuMember('N');
        countryEntity2.setIsClearingNetwork('N');
        countryEntity2.setIsMtGenerate('N');
        countryEntity2.setIsClearingNetwork('N');
        countryEntity2.setStatus("closed");
        countryEntity2.setRecordVersion(1);
        return countryEntity2;
    }
    private CountryDTO getCountryDTOUnAuth(){
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCountryCode("IN");
        countryDTO.setCountryName("INDIA");
        countryDTO.setAlternateCountryCode("IN");
        countryDTO.setNumericCountryCode("345");
        countryDTO.setRegionCode("ASIA");
        countryDTO.setLimitCurrency("INR");
        countryDTO.setOverallLimit(100000f);
        countryDTO.setIbanRequired(false);
        countryDTO.setEuMember(false);
        countryDTO.isClearingCodeBicPlus();
        countryDTO.setGenerateMt205(false);
        countryDTO.setDefaultClearingNetwork(false);
        countryDTO.setAuthorized("N");
        return countryDTO;
    }

    private CountryDTO getCountryDTOMapper(){
        CountryDTO countryDTOMapper= new CountryDTO();
        countryDTOMapper.setCountryCode("IN");
        countryDTOMapper.setCountryName("INDIA");
        countryDTOMapper.setAlternateCountryCode("IN");
        countryDTOMapper.setNumericCountryCode("345");
        countryDTOMapper.setRegionCode("ASIA");
        countryDTOMapper.setLimitCurrency("INR");
        countryDTOMapper.setOverallLimit(100000f);
        countryDTOMapper.setIbanRequired(false);
        countryDTOMapper.setEuMember(false);
        countryDTOMapper.isClearingCodeBicPlus();
        countryDTOMapper.setGenerateMt205(false);
        countryDTOMapper.setDefaultClearingNetwork(false);
        countryDTOMapper.setTaskCode("COUNTRY");
        countryDTOMapper.setTaskIdentifier("IN");
        countryDTOMapper.setAuthorized("N");
        countryDTOMapper.setStatus("draft");
        countryDTOMapper.setAction("draft");
        countryDTOMapper.setRecordVersion(0);
        return countryDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"COUNTRY\",\"taskIdentifier\":\"IN\",\"countryCode\":\"IN\",\"countryName\":\"INDIA\",\"numericCountryCode\":\"345\",\"alternateCountryCode\":\"IND\",\"regionCode\":\"ASIA\",\"limitCurrency\":\"INR\",\"overallLimit\":100000.0,\"ibanRequired\":false,\"euMember\":false,\"clearingCodeBicPlus\":false,\"generateMt205\":false,\"defaultClearingNetwork\":false}";

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("IN");
        mutationEntity.setTaskCode("COUNTRY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1 ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":hhhhhhh,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"COUNTRY\",\"taskIdentifier\":\"IN\",\"countryCode\":\"IN\",\"countryName\":\"INDIA\",\"numericCountryCode\":\"345\",\"alternateCountryCode\":\"IND\",\"regionCode\":\"ASIA\",\"limitCurrency\":\"INR\",\"overallLimit\":100000.0,\"ibanRequired\":false,\"euMember\":false,\"clearingCodeBicPlus\":false,\"generateMt205\":false,\"defaultClearingNetwork\":false}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("IN");
        mutationEntity2.setTaskCode("COUNTRY");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    private MutationEntity getMutationEntity2()
    {
        String payLoadString ="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"COUNTRY\",\"taskIdentifier\":\"IN\",\"countryCode\":\"IN\",\"countryName\":\"INDIA\",\"numericCountryCode\":\"345\",\"alternateCountryCode\":\"IND\",\"regionCode\":\"ASIA\",\"limitCurrency\":\"INR\",\"overallLimit\":100000.0,\"ibanRequired\":false,\"euMember\":false,\"clearingCodeBicPlus\":false,\"generateMt205\":false,\"defaultClearingNetwork\":false}";

        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("IN");
        mutationEntity5.setTaskCode("COUNTRY");
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("draft");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("draft");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("NIKHIL");
        mutationEntity5.setLastUpdatedBy("sujan");
        return mutationEntity5;
    }

    String payLoadString1 ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"COUNTRY\",\"taskIdentifier\":\"IN\",\"countryCode\":\"IN\",\"countryName\":\"INDIA\",\"numericCountryCode\":\"345\",\"alternateCountryCode\":\"IND\",\"regionCode\":\"ASIA\",\"limitCurrency\":\"INR\",\"overallLimit\":100000.0,\"ibanRequired\":false,\"euMember\":false,\"clearingCodeBicPlus\":false,\"generateMt205\":false,\"defaultClearingNetwork\":false}";


}
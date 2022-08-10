package com.idg.idgcore.coe.app.service.financialAccountingYear;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.idg.idgcore.app.*;
import com.idg.idgcore.coe.domain.assembler.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.entity.mutation.*;
import com.idg.idgcore.coe.domain.process.*;
import com.idg.idgcore.coe.domain.service.financialAccountingYear.*;
import com.idg.idgcore.coe.domain.service.mutation.*;
import com.idg.idgcore.coe.dto.financialAccountingYear.*;
import com.idg.idgcore.coe.dto.mutation.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.*;
import org.mockito.*;
import org.mockito.junit.jupiter.*;
import org.modelmapper.*;
import org.springframework.beans.factory.annotation.*;

import java.text.*;
import java.util.*;

import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;

@ExtendWith (MockitoExtension.class)
class FinancialAccountingYearApplicationServiceTest {
    @InjectMocks
    private FinancialAccountingYearApplicationService financialAccountingYearApplicationService;
    @Mock
    private ProcessConfiguration process;
    @Mock
    private FinancialAccountingYearAssembler assembler;
    @Mock
    private IFinancialAccountingYearDomainService domainService;
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
    private FinancialAccountingYearEntity financialAccountingYearEntity;
    private FinancialAccountingYearDTO financialAccountingYearDTO;
    private FinancialAccountingYearDTO financialAccountingYearDTO1;
    private FinancialAccountingYearEntity financialAccountingYearEntity1;
    private FinancialAccountingYearEntity financialAccountingYearEntity2;
    private FinancialAccountingYearDTO financialAccountingYearDTOMapper;
    private MutationEntity mutationEntityPayload;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        financialAccountingYearDTO = getFinancialAccountingYearDTOAuthorized();
        financialAccountingYearEntity = getFinancialAccountingYearEntity();

        mutationEntity = getMutationEntity();
        financialAccountingYearEntity1 = getCitiesEntity();
        financialAccountingYearEntity2 = getCitiesEntity2();
        financialAccountingYearDTO1 = getFinancialAccountingYearDTO();
        mutationEntity2 = getMutationEntityJsonError();
        mutationEntityPayload = getMutationEntityPayload();
    }

    @Test
    @DisplayName ("JUnit for getByFinancialAccountingYearCode in application service when Authorize try Block")
    void getFinancialAccountingYearByCodeIsAuthorize () throws FatalException {
        given(domainService.getFinancialAccountingYearByCode(
                financialAccountingYearDTO.getBankCode())).willReturn(
                financialAccountingYearEntity);
        given(assembler.convertEntityToDto(
                financialAccountingYearEntity)).willReturn(financialAccountingYearDTO);

        FinancialAccountingYearDTO financialAccountingYearDTO1 = financialAccountingYearApplicationService.getFinancialAccountingYearByCode(
                sessionContext, financialAccountingYearDTO);
        assertEquals("Y", financialAccountingYearDTO1.getAuthorized());
        assertThat(financialAccountingYearDTO1).isNotNull();
    }

    /*@Test
    @DisplayName ("JUnit for getByFinancialAccountingYearCode in application service when Authorize try Block")
    void getFinancialAccountingYearByCodeUnAuthorize () throws FatalException {
        MutationEntity mutationEntity1=getMutationEntity();
        FinancialAccountingYearDTO financialAccountingYearDTOUnAuth = getFinancialAccountingYearDTOUnAuth();
        FinancialAccountingYearDTO financialAccountingYearDTOUnAuthUpdated  = getFinancialAccountingYearDTOUnAuth();
        ObjectMapper objectMapper = new ObjectMapper();
        given(mutationsDomainService.getConfigurationByCode(
                financialAccountingYearDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity1);
        System.out.println("financialAccountingYearDTOUnAuth auth "+financialAccountingYearDTOUnAuth.getAuthorized());
        System.out.println("financialAccountingYearDTOUnAuth : "+financialAccountingYearDTOUnAuth);

        financialAccountingYearDTOUnAuthUpdated.setAction(mutationEntity1.getAction());
        financialAccountingYearDTOUnAuthUpdated.setAuthorized(mutationEntity1.getAuthorized());
        financialAccountingYearDTOUnAuthUpdated.setRecordVersion(mutationEntity1.getRecordVersion());
        financialAccountingYearDTOUnAuthUpdated.setStatus(mutationEntity1.getStatus());
        financialAccountingYearDTOUnAuthUpdated.setLastConfigurationAction(mutationEntity1.getLastConfigurationAction());
        financialAccountingYearDTOUnAuthUpdated.setCreatedBy(mutationEntity1.getCreatedBy());
        financialAccountingYearDTOUnAuthUpdated.setCreationTime(mutationEntity1.getCreationTime());
        financialAccountingYearDTOUnAuthUpdated.setLastUpdatedBy(mutationEntity1.getLastUpdatedBy());
        financialAccountingYearDTOUnAuthUpdated.setLastUpdatedTime(mutationEntity1.getLastUpdatedTime());
        financialAccountingYearDTOUnAuthUpdated.setTaskCode(mutationEntity1.getTaskCode());
        financialAccountingYearDTOUnAuthUpdated.setTaskIdentifier(mutationEntity1.getTaskIdentifier());
        System.out.println(" ----- financialAccountingYearDTOUnAuthUpdated : "+financialAccountingYearDTOUnAuthUpdated);
//        given(assembler.setAuditFields(mutationEntity1,financialAccountingYearDTOUnAuth)).willReturn(financialAccountingYearDTOUnAuthUpdated);
        FinancialAccountingYearDTO financialAccountingYearDTO1 =
                financialAccountingYearApplicationService.getFinancialAccountingYearByCode(
                sessionContext, financialAccountingYearDTOUnAuth);
//        assertEquals("N", financialAccountingYearDTO1.getAuthorized());
        assertThat(financialAccountingYearDTO1).isNotNull();
    }
    */
    @Test
    @DisplayName ("JUnit for getFinancialAccountingYears in application service")
    void getFinancialAccountingYears () throws JsonProcessingException, FatalException {
        FinancialAccountingYearEntity financialAccountingYearEntity = getFinancialAccountingYearEntityDeleted();
        FinancialAccountingYearDTO financialAccountingYearDTO = getFinancialAccountingYearDTODeleted();
        MutationEntity unauthorizedEntities = getMutationEntityDeleted();
        given(domainService.getFinancialAccountingYears()).willReturn(List.of(financialAccountingYearEntity));
        given(assembler.convertEntityToDto(financialAccountingYearEntity)).willReturn(financialAccountingYearDTO);
        List<FinancialAccountingYearDTO> financialAccountingYearDTO1 = financialAccountingYearApplicationService.getFinancialAccountingYears(sessionContext);
        assertThat(financialAccountingYearEntity.toString()).isNotNull();
        assertThat(financialAccountingYearDTO1).isNotNull();
    }



    @Test
    @DisplayName ("JUnit for getByFinancialAccountingYearCode in application service when Not Authorize in catch block")
    void getFinancialAccountingYearByCodeWhenNotAuthorizeCatchBlock () throws FatalException,
            JsonProcessingException {
        ModelMapper mapper = new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        System.out.println(" payload : "+payload);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class, () -> {
            FinancialAccountingYearDTO financialAccountingYearDTO1 = financialAccountingYearApplicationService.getFinancialAccountingYearByCode(sessionContext, financialAccountingYearDTOMapper);
            assertEquals("N", financialAccountingYearDTO1.getAuthorized());
            assertThat(financialAccountingYearDTO1).isNotNull();
            System.out.println(financialAccountingYearDTO1);
        });
    }

    @DisplayName ("Test processFinancialAccountingYear")
    @Test
    void processFinancialAccountingYear () throws JsonProcessingException, FatalException {

        financialAccountingYearDTOMapper = getFinancialAccountingYearDTOMapper();
        doNothing().when(process).process(financialAccountingYearDTOMapper);
        financialAccountingYearApplicationService.processFinancialAccountingYear(sessionContext, financialAccountingYearDTOMapper);
        verify(process, times(1)).process(financialAccountingYearDTOMapper);
    }

    @DisplayName ("JUnit test for addUpdateRecord method")
    @Test
    void addUpdateRecord () throws JsonProcessingException, FatalException {
        String payloadStr = getpayloadValidString();
        financialAccountingYearDTOMapper = getFinancialAccountingYearDTOMapper();
        doNothing().when(domainService).save(financialAccountingYearDTOMapper);
        financialAccountingYearApplicationService.save(financialAccountingYearDTOMapper);
        financialAccountingYearApplicationService.addUpdateRecord(payloadStr);
        verify(domainService, times(1)).save(financialAccountingYearDTOMapper);
    }

    @Test
    void getConfigurationByCode () {

        FinancialAccountingYearEntity financialAccountingYearEntity = getFinancialAccountingYearEntityDeleted();
        FinancialAccountingYearDTO financialAccountingYearDTO = getFinancialAccountingYearDTODeleted();
            String code = financialAccountingYearDTO.getBankCode();
            given(domainService.getFinancialAccountingYearByCode(code)).willReturn(financialAccountingYearEntity);
        financialAccountingYearApplicationService.getConfigurationByCode(code);
            assertThat(financialAccountingYearEntity).isNotNull();
    }

    @Test
    void save () {
        financialAccountingYearDTOMapper = getFinancialAccountingYearDTOMapper();
        doNothing().when(domainService).save(financialAccountingYearDTOMapper);
        financialAccountingYearApplicationService.save( financialAccountingYearDTOMapper);
        verify(domainService, times(1)).save(financialAccountingYearDTOMapper);

    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date())
                .transactionBranch("")
                .userId("user1")
                .channel("Branch").taskCode("FIN_ACC_YEAR")
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

    private FinancialAccountingYearDTO getFinancialAccountingYearDTOAuthorized () {
        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-12-31");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        financialAccountingYearDTO =
                new FinancialAccountingYearDTO(1L, "BNP",
                        "CBB", sDate, eDate,
                        "FY2022", "Financial Accounting Year FY 2022",
                        "Half-Yearly", "new", 1, "Y", "new",
                        financialAccountingYearPeriodicCodeList);
        financialAccountingYearDTO.setAuthorized("Y");
        return financialAccountingYearDTO;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTO () {
        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-12-31");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        FinancialAccountingYearDTO financialAccountingYearDTO1 =
                new FinancialAccountingYearDTO(1L, "BNP", "CBB", sDate, eDate,
                        "FY2022", "Financial Accounting Year FY 2022",
                        "Half-Yearly", "new", 1, "Y", "new",
                        financialAccountingYearPeriodicCodeList);
        return financialAccountingYearDTO1;
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

    private FinancialAccountingYearEntity getFinancialAccountingYearEntity () {
        List<FinancialAccountingYearPeriodicCodeEntity> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        FinancialAccountingYearPeriodicCodeEntity entity1 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-01-01"), getDate("2022-12-31"));
        FinancialAccountingYearPeriodicCodeEntity entity2 = new FinancialAccountingYearPeriodicCodeEntity();
        entity2.setPeriodCode("Feb");
        entity2.setFinAccYearPeriodCodesId(2L);
        entity2.setFinancialAccountingYearId(2021L);
        entity2.setStartDateAccountingPeriod(getDate("2022-02-01"));
        entity2.setEndDateAccountingPeriod(getDate("2022-02-28"));
        FinancialAccountingYearPeriodicCodeEntity entity3 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-02-28"), getDate("2022-02-28"));
        List<FinancialAccountingYearPeriodicCodeEntity> periodicCodeEntityList = new ArrayList<FinancialAccountingYearPeriodicCodeEntity>();
        periodicCodeEntityList.add(entity1);
        periodicCodeEntityList.add(entity2);
        periodicCodeEntityList.add(entity3);
        financialAccountingYearEntity =
                new FinancialAccountingYearEntity(1L, "BNP", "CBB", getDate("2022-01-01"), getDate("2022-02-28"),
                        "FY2022", "Financial Accounting Year FY 2022",
                        "Half-Yearly", "new", 1, "Y", "new",
                        financialAccountingYearPeriodicCodeList);
        return financialAccountingYearEntity;

    }

    private FinancialAccountingYearEntity getCitiesEntity () {
        FinancialAccountingYearEntity financialAccountingYearEntity = new FinancialAccountingYearEntity();
        financialAccountingYearEntity.setFinancialAccountingYearCode("BNP");
        financialAccountingYearEntity.setFinancialAccountingYearName("MUMBAI");
        financialAccountingYearEntity.setStatus("DELETED");
        financialAccountingYearEntity.setRecordVersion(1);
        return financialAccountingYearEntity;
    }

    private FinancialAccountingYearEntity getCitiesEntity2 () {
        FinancialAccountingYearEntity financialAccountingYearEntity2 = new FinancialAccountingYearEntity();
        financialAccountingYearEntity2.setFinancialAccountingYearCode("BNP");
        financialAccountingYearEntity2.setFinancialAccountingYearName("MUMBAI");
        financialAccountingYearEntity2.setAuthorized("N");
        financialAccountingYearEntity2.setStatus("closed");
        financialAccountingYearEntity2.setRecordVersion(1);
        return financialAccountingYearEntity2;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTOUnAuth () {

        FinancialAccountingYearDTO financialAccountingYearDTOUnAuth;
        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-01-01");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        financialAccountingYearDTOUnAuth=new FinancialAccountingYearDTO();
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearId(1L);
        financialAccountingYearDTOUnAuth.setBankCode("BNP");
        financialAccountingYearDTOUnAuth.setBranchCode("CBB");
        financialAccountingYearDTOUnAuth.setStartDate(sDate);
        financialAccountingYearDTOUnAuth.setEndDate(eDate);
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearCode("FY2022");
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearName("Financial Accounting Year FY 2022");
        financialAccountingYearDTOUnAuth.setPeriodCodeFrequency("Half-Yearly");
        financialAccountingYearDTOUnAuth.setStatus("draft");
        financialAccountingYearDTOUnAuth.setRecordVersion(0);
        financialAccountingYearDTOUnAuth.setAuthorized("N");
        financialAccountingYearDTOUnAuth.setLastConfigurationAction("draft");
        financialAccountingYearDTOUnAuth.setAction("draft");
        financialAccountingYearDTOUnAuth.setTaskCode("FIN_ACC_YEAR");
        financialAccountingYearDTOUnAuth.setTaskIdentifier("BNP");
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearPeriodicCode(financialAccountingYearPeriodicCodeList);
        return financialAccountingYearDTOUnAuth;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTOMapper () {

        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-12-31");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);
        FinancialAccountingYearDTO financialAccountingYearDTOMapper =
                new FinancialAccountingYearDTO(1L, "BNP", "CBB", sDate, eDate,
                        "FY2022", "Financial Accounting Year FY 2022",
                        "Half-Yearly", "new", 1, "Y", "new",
                        financialAccountingYearPeriodicCodeList);
        financialAccountingYearDTOMapper.setFinancialAccountingYearCode("BNP");
        financialAccountingYearDTOMapper.setAuthorized("N");
        financialAccountingYearDTOMapper.setTaskCode("FIN_ACC_YEAR");
        financialAccountingYearDTOMapper.setTaskIdentifier("BNP");
        return financialAccountingYearDTOMapper;
    }

    private MutationEntity getMutationEntity () {
        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"FIN_ACC_YEAR\",\"taskIdentifier\":\"BNP\",\"bankCode\":\"BNP\",\"branchCode\":\"CBB\",\"startDate\":1640995200000,\"endDate\":1672444800000,\"financialAccountingYearCode\":\"FY2022\",\"financialAccountingYearName\":\"Financial Accounting Year FY 2022\",\"periodCodeFrequency\":\"Half-Yearly\",\"financialAccountingYearPeriodicCode\":[{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"JAN\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FEB\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FC\",\"startDateAccountingPeriod\":1672444800000,\"endDateAccountingPeriod\":1672444800000}]}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("BNP");
        mutationEntity.setTaskCode("FIN_ACC_YEAR");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("draft");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(0);
        mutationEntity.setAction("draft");
        mutationEntity.setLastConfigurationAction("draft");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError () {
        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":hhh,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"FIN_ACC_YEAR\",\"taskIdentifier\":\"BNP\",\"bankCode\":\"BNP\",\"branchCode\":\"CBB\",\"startDate\":1640995200000,\"endDate\":1672444800000,\"financialAccountingYearCode\":\"FY2022\",\"financialAccountingYearName\":\"Financial Accounting Year FY 2022\",\"periodCodeFrequency\":\"Half-Yearly\",\"financialAccountingYearPeriodicCode\":[{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"JAN\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FEB\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FC\",\"startDateAccountingPeriod\":1672444800000,\"endDateAccountingPeriod\":1672444800000}]}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("BNP");
        mutationEntity2.setTaskCode("FIN_ACC_YEAR");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    private MutationEntity getMutationEntityPayload () {
        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"FIN_ACC_YEAR\",\"taskIdentifier\":\"BNP\",\"bankCode\":\"BNP\",\"branchCode\":\"CBB\",\"startDate\":1640995200000,\"endDate\":1672444800000,\"financialAccountingYearCode\":\"FY2022\",\"financialAccountingYearName\":\"Financial Accounting Year FY 2022\",\"periodCodeFrequency\":\"Half-Yearly\",\"financialAccountingYearPeriodicCode\":[{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"JAN\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FEB\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FC\",\"startDateAccountingPeriod\":1672444800000,\"endDateAccountingPeriod\":1672444800000}]}";
        MutationEntity mutationEntity5 = new MutationEntity();
        mutationEntity5.setTaskIdentifier("BNP");
        mutationEntity5.setTaskCode("FIN_ACC_YEAR");
        mutationEntity5.setPayload(new Payload(payLoadString));
        mutationEntity5.setStatus("new");
        mutationEntity5.setAuthorized("N");
        mutationEntity5.setRecordVersion(0);
        mutationEntity5.setAction("add");
        mutationEntity5.setLastConfigurationAction("unauthorized");
        mutationEntity5.setCreatedBy("user1");
        mutationEntity5.setLastUpdatedBy("Preeti");
        return mutationEntity5;
    }

    String payLoadString1 = "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\",\"taskIdentifier\":\"MH\",\"stateCode\":\"MH\",\"stateName\":\"MAHARASHTRA\",\"countryCode\":\"IN\"}";

    private FinancialAccountingYearEntity getFinancialAccountingYearEntityDeleted () {
        FinancialAccountingYearEntity financialAccountingYearEntityDeleted = new FinancialAccountingYearEntity();

        List<FinancialAccountingYearPeriodicCodeEntity> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        FinancialAccountingYearPeriodicCodeEntity entity1 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-01-01"), getDate("2022-12-31"));
        FinancialAccountingYearPeriodicCodeEntity entity2 = new FinancialAccountingYearPeriodicCodeEntity();
        entity2.setPeriodCode("Feb");
        entity2.setFinAccYearPeriodCodesId(2L);
        entity2.setFinancialAccountingYearId(2021L);
        entity2.setStartDateAccountingPeriod(getDate("2022-02-01"));
        entity2.setEndDateAccountingPeriod(getDate("2022-02-28"));
        FinancialAccountingYearPeriodicCodeEntity entity3 = new FinancialAccountingYearPeriodicCodeEntity(
                1L, 2021L, "JAN", getDate("2022-02-28"), getDate("2022-02-28"));
        List<FinancialAccountingYearPeriodicCodeEntity> periodicCodeEntityList = new ArrayList<FinancialAccountingYearPeriodicCodeEntity>();
        periodicCodeEntityList.add(entity1);
        periodicCodeEntityList.add(entity2);
        periodicCodeEntityList.add(entity3);
        financialAccountingYearEntityDeleted =
                new FinancialAccountingYearEntity(1L, "BNP", "CBB", getDate("2022-01-01"), getDate("2022-02-28"),
                        "FY2022", "Financial Accounting Year FY 2022",
                        "Half-Yearly", "new", 1, "Y", "new",
                        financialAccountingYearPeriodicCodeList);
        financialAccountingYearEntityDeleted.setStatus("deleted");
        financialAccountingYearEntityDeleted.setRecordVersion(1);

        return financialAccountingYearEntityDeleted;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTODeleted () {


        List<FinancialAccountingYearPeriodicCodeDTO> financialAccountingYearPeriodicCodeList = new ArrayList<>();
        Date sDate = getDate("2022-01-01");
        Date eDate = getDate("2022-01-01");
        FinancialAccountingYearPeriodicCodeDTO dto1 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "JAN", sDate, eDate);
        FinancialAccountingYearPeriodicCodeDTO dto2 = new FinancialAccountingYearPeriodicCodeDTO();
        dto2.setPeriodCode("FEB");
        dto2.setFinAccYearPeriodCodesId(1L);
        dto2.setStartDateAccountingPeriod(sDate);
        dto2.setEndDateAccountingPeriod(eDate);
        FinancialAccountingYearPeriodicCodeDTO dto3 = new FinancialAccountingYearPeriodicCodeDTO(
                1L, "FC", sDate, eDate);
        financialAccountingYearPeriodicCodeList.add(dto1);
        financialAccountingYearPeriodicCodeList.add(dto2);
        financialAccountingYearPeriodicCodeList.add(dto3);

        FinancialAccountingYearDTO financialAccountingYearDtoDeleted=new FinancialAccountingYearDTO();
        financialAccountingYearDtoDeleted.setFinancialAccountingYearId(1L);
        financialAccountingYearDtoDeleted.setBankCode("BNP");
        financialAccountingYearDtoDeleted.setBranchCode("CBB");
        financialAccountingYearDtoDeleted.setStartDate(sDate);
        financialAccountingYearDtoDeleted.setEndDate(eDate);
        financialAccountingYearDtoDeleted.setFinancialAccountingYearCode("FY2022");
        financialAccountingYearDtoDeleted.setFinancialAccountingYearName("Financial Accounting Year FY 2022");
        financialAccountingYearDtoDeleted.setPeriodCodeFrequency("Half-Yearly");
        financialAccountingYearDtoDeleted.setRecordVersion(0);
        financialAccountingYearDtoDeleted.setAuthorized("N");
        financialAccountingYearDtoDeleted.setLastConfigurationAction("draft");
        financialAccountingYearDtoDeleted.setFinancialAccountingYearPeriodicCode(financialAccountingYearPeriodicCodeList);

        financialAccountingYearDtoDeleted.setStatus("deleted");
        financialAccountingYearDtoDeleted.setTaskCode("FIN_ACC_YEAR");
        financialAccountingYearDtoDeleted.setTaskIdentifier("BNP");
        financialAccountingYearDtoDeleted.setRecordVersion(1);
        return financialAccountingYearDtoDeleted;
    }

    private MutationEntity getMutationEntityDeleted () {
        String payLoadString =
                getpayloadValidString();
        MutationEntity unauthorizedEntities = new MutationEntity();
        unauthorizedEntities.setTaskCode("FIN_ACC_YEAR");
        unauthorizedEntities.setPayload(new Payload(payLoadString));
        unauthorizedEntities.setAuthorized("N");
        unauthorizedEntities.setStatus("DELETED");
        unauthorizedEntities.setRecordVersion(1);
        return unauthorizedEntities;
    }

    private String getpayloadValidString () {
           String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"FIN_ACC_YEAR\",\"taskIdentifier\":\"BNP\",\"bankCode\":\"BNP\",\"branchCode\":\"CBB\",\"startDate\":1640995200000,\"endDate\":1672444800000,\"financialAccountingYearCode\":\"FY2022\",\"financialAccountingYearName\":\"Financial Accounting Year FY 2022\",\"periodCodeFrequency\":\"Half-Yearly\",\"financialAccountingYearPeriodicCode\":[{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"JAN\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FEB\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FC\",\"startDateAccountingPeriod\":1672444800000,\"endDateAccountingPeriod\":1672444800000}]}";
        return payLoadString;
    }

    private MutationEntity getMutationEntityUnAuthComplete () {
        String payLoadString = "{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"draft\",\"taskCode\":\"FIN_ACC_YEAR\",\"taskIdentifier\":\"BNP\",\"bankCode\":\"BNP\",\"branchCode\":\"CBB\",\"startDate\":1640995200000,\"endDate\":1672444800000,\"financialAccountingYearCode\":\"FY2022\",\"financialAccountingYearName\":\"Financial Accounting Year FY 2022\",\"periodCodeFrequency\":\"Half-Yearly\",\"financialAccountingYearPeriodicCode\":[{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"JAN\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FEB\",\"startDateAccountingPeriod\":1640995200000,\"endDateAccountingPeriod\":1672444800000},{\"finAccYearPeriodCodesId\":null,\"periodCode\":\"FC\",\"startDateAccountingPeriod\":1672444800000,\"endDateAccountingPeriod\":1672444800000}]}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("BNP");
        mutationEntity.setTaskCode("FIN_ACC_YEAR");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("draft");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(0);
        mutationEntity.setAction("draft");
        mutationEntity.setLastConfigurationAction("draft");
        return mutationEntity;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTOUnAuthorizedBase () {
        FinancialAccountingYearDTO financialAccountingYearDTOUnAuth = new FinancialAccountingYearDTO();
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearCode("BNP");
        financialAccountingYearDTOUnAuth.setTaskIdentifier("BNP");
        financialAccountingYearDTOUnAuth.setTaskCode("FIN_ACC_YEAR");
        financialAccountingYearDTOUnAuth.setAuthorized("N");
        return financialAccountingYearDTOUnAuth;
    }

    private FinancialAccountingYearDTO getFinancialAccountingYearDTOUnAuthorized () {
        FinancialAccountingYearDTO financialAccountingYearDTOUnAuth = new FinancialAccountingYearDTO();
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearCode("BNP");
        financialAccountingYearDTOUnAuth.setFinancialAccountingYearName("PUNE");
        financialAccountingYearDTOUnAuth.setTaskIdentifier("BNP");
        financialAccountingYearDTOUnAuth.setTaskCode("FIN_ACC_YEAR");
        financialAccountingYearDTOUnAuth.setStatus("draft");
        financialAccountingYearDTOUnAuth.setAuthorized("N");
        financialAccountingYearDTOUnAuth.setRecordVersion(0);
        financialAccountingYearDTOUnAuth.setLastConfigurationAction("draft");
        return financialAccountingYearDTOUnAuth;
    }

    private FinancialAccountingYearDTO getFtchedDTO () {
        FinancialAccountingYearDTO financialAccountingYearDTOFetched =
                getFinancialAccountingYearDTOUnAuthorized();
        financialAccountingYearDTOFetched.setAction("draft");
        financialAccountingYearDTOFetched.setStatus("draft");
        financialAccountingYearDTOFetched.setRecordVersion(0);
        financialAccountingYearDTOFetched.setLastConfigurationAction("draft");
        financialAccountingYearDTOFetched.setFinancialAccountingYearCode("BNP");
        financialAccountingYearDTOFetched.setFinancialAccountingYearName("PUNE");
        FinancialAccountingYearDTO financialAccountingYearDTO = getFinancialAccountingYearDTOUnAuthorized();
        return financialAccountingYearDTOFetched;
    }

}
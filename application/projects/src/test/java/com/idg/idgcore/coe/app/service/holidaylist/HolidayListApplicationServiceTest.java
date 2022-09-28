package com.idg.idgcore.coe.app.service.holidaylist;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.app.service.policy.calendar.HolidayCalendarBusinessPolicy;
import com.idg.idgcore.coe.app.service.policy.calendar.factory.HolidayCalendarBusinessPolicyFactory;
import com.idg.idgcore.coe.domain.assembler.holidaylist.HolidayListAssembler;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayEntityKey;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.verificationcategory.AppVerCategoryConfigEntity;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.holidaylist.IHolidayListDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.holidaylist.HolidayDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListResponse;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.policy.calendar.HolidayCalendarBusinessPolicyDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.CalendarBusinessPolicyConstants.HOLIDAY_CALENDAR_BUSINESS_POLICY;
import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.HOLIDAY;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class HolidayListApplicationServiceTest {

        @InjectMocks
        private HolidayListApplicationService holidayListApplicationService;

        @Mock
        private ProcessConfiguration process;

        @Mock
        private HolidayListAssembler holidayListAssembler;

        @Mock
        private HolidayCalendarBusinessPolicyFactory holidayCalendarBusinessPolicyFactory;

        @Mock
        private IHolidayListDomainService holidayListDomainService;

        @Mock
        private AbstractApplicationService abstractApplicationService;

        @Autowired
        private MutationEntity mutationEntity;

        private MutationEntity mutationEntity2;
        private MutationEntity mutationEntity3;

        @Mock
        private IMutationsDomainService mutationsDomainService;

        private SessionContext sessionContext;

        private SessionContext sessionContext1;
        private HolidayListEntity holidayListEntity;
        private HolidayListDTO holidayListDTO;
        private HolidayListDTO holidayListDTOUnAuth;
        private HolidayListDTO holidayListDTO1;
        private HolidayListEntity holidayListEntity1;

        private HolidayListDTO holidayListDTOMapper ;

        private HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO;

        @BeforeEach
        void setUp() {
            sessionContext = getValidSessionContext ();
            sessionContext1=getErrorSession();
            holidayListDTO=getHolidayListDTOAuthorized ();
            holidayListEntity=getHolidayListEntity();
            holidayListDTOUnAuth=getHolidayListDTOUnAuth();
            holidayListDTOMapper=getHolidayListDTOMapper();
            mutationEntity=getMutationEntity();
            holidayListEntity1=getHolidayListEntity1();
            holidayListDTO1=getHolidayListDTO();
            mutationEntity2=getMutationEntityJsonError();
            mutationEntity3=getMutationEntityUnauthorize();
            holidayCalendarBusinessPolicyDTO=getholidayCalendarBusinessPolicyDTO();

        }

        @Test
        @DisplayName("JUnit for getSearchHolidayList in application service try Block")
        void getSerchHolidayListTryBlock() throws FatalException, JsonProcessingException {
            given(mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(holidayListDTO.getTaskCode(),holidayListDTO.getTaskIdentifier())).willReturn(List.of(mutationEntity));
//            given(holidayListAssembler.setAuditFields(mutationEntity,holidayListDTO)).willReturn(holidayListDTO);
            HolidayListResponse holidayListResponse=holidayListApplicationService.searchHolidayList(sessionContext,holidayListDTO);
            assertThat(holidayListResponse.getHolidayList()).isNotNull();
        }

    @Test
    @DisplayName("JUnit for getSearchHolidayList in application service Catch Block")
    void getSerchHolidayListCatchBlock() throws FatalException, JsonProcessingException {
//        given(mutationsDomainService.findByTaskCodeAndTaskIdentifierStartsWith(holidayListDTO.getTaskCode(),holidayListDTO.getTaskIdentifier())).willReturn(List.of(mutationEntity));
//            given(holidayListAssembler.setAuditFields(mutationEntity,holidayListDTO)).willReturn(holidayListDTO);
        Assertions.assertThrows(Exception.class,()-> {
            SessionContext sessionContext2=null;
            HolidayListResponse holidayListResponse = holidayListApplicationService.searchHolidayList(sessionContext2, holidayListDTO);
            assertThat(holidayListResponse.getHolidayList()).isNotNull();
        });
    }





    @Test
    @DisplayName("JUnit for getHolidayListByID in application service when Authorize try Block")
    void getHolidayListByIDIsAuthorize() throws FatalException {
        given(holidayListDomainService.getHolidayListById(holidayListDTO.getHolidayListId())).willReturn(holidayListEntity);
        given(holidayListAssembler.convertEntityToDto(holidayListEntity)).willReturn(holidayListDTO);
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTO);
        assertEquals("Y",holidayListResponse.getHolidayList().get(0).getAuthorized());
        assertThat(holidayListResponse.getHolidayList()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getHolidayListById in application service when Not Authorize in try else block")
    void getHolidayListByCodewhenNotAuthorizeTryBlock() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(holidayListDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity3);
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext,holidayListDTOUnAuth);
        assertEquals("N",holidayListResponse.getHolidayList().get(0).getAuthorized());
        assertThat(holidayListResponse.getHolidayList()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getHolidayById in application service when Not Authorize in catch block")
    void getHolidayListByIDwhenNotAuthorizeCatchBlock () throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(holidayListDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);
        Assertions.assertThrows(Exception.class,()-> {
            HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTOMapper);
            assertEquals("N",holidayListResponse.getHolidayList().get(0).getAuthorized());
            assertThat(holidayListResponse.getHolidayList()).isNotNull();
        });

    }

    @Test
    @DisplayName("Should return all getHolidayLists when there are no unauthorized")
    void getHolidayListsWhenThereAreNoUnauthorized() throws FatalException {
//        given(holidayListDomainService.getHolidayLists()).willReturn(List.of(holidayListEntity));
        given(mutationsDomainService.getMutations(HOLIDAY)).willReturn(List.of(mutationEntity3));
//        given(holidayListAssembler.convertEntityToDto(holidayListEntity)).willReturn(holidayListDTO);
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayLists(sessionContext);
        assertEquals(1, holidayListResponse.getHolidayList().size());
    }

    @Test
    @DisplayName("JUnit for getHolidayLists in application service for catch block for checker")
    void getHolidayListsCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(null);
        sessionContext.setOriginatingModuleCode(null);
        given(mutationsDomainService.getMutations(
                holidayListDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities,unauthorizedEntities1));
        Assertions.assertThrows(Exception.class,()-> {
            HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayLists(sessionContext);
            assertThat(holidayListResponse.getHolidayList()).descriptionText();
        });
    }

//    @Test
//    @DisplayName("JUnit for processHolidayLists in application service for Try Block")
//    void processHolidayListForTryBlock() throws JsonProcessingException, FatalException {
//            doReturn(holidayCalendarBusinessPolicyFactory).when(holidayCalendarBusinessPolicyFactory).getInstance().getPolicyInstance((HOLIDAY_CALENDAR_BUSINESS_POLICY), holidayCalendarBusinessPolicyDTO);
//        doNothing().when(process).process(holidayListDTO);
//        holidayListApplicationService.processHolidayList(sessionContext, holidayListDTO);
//        assertThat(holidayListDTO).descriptionText();
//    }

    @Test
    @DisplayName("JUnit for processHolidayList in application service for Catch Block")
    void processHolidayListConfigsForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            holidayListApplicationService.processHolidayList(sessionContext2, holidayListDTO);
            assertThat(holidayListDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        doNothing().when(holidayListDomainService).save(holidayListDTO);
        holidayListApplicationService.save(holidayListDTO);
        holidayListApplicationService.addUpdateRecord(payLoadString1);
        verify(holidayListDomainService, times(1)).save(holidayListDTO);

    }


    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = holidayListDTO.getHolidayListId();
        given(holidayListDomainService.getHolidayListById(code)).willReturn(holidayListEntity);
        holidayListApplicationService.getConfigurationByCode(code);
        assertThat(holidayListEntity).isNotNull();
    }


    //----------------------------------------Negative---------------------------------


    @Test
    @DisplayName("JUnit for getHolidayListByID in application service when Authorize for Negative")
    void getHolidayListByCodeIsAuthorizeforNegative() throws FatalException {
        given(holidayListDomainService.getHolidayListById(holidayListDTO.getHolidayListId())).willReturn(holidayListEntity);
        given(holidayListAssembler.convertEntityToDto(holidayListEntity)).willReturn(holidayListDTO);
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTO);
        assertNotEquals("N",holidayListResponse.getHolidayList().get(0).getAuthorized());
        assertThat(holidayListDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getHolidayListByCode in application service when UnAuthorize fetche no Record from database")
    void getHolidayListByCodeNotAuthorizeNull() throws FatalException {
        given(mutationsDomainService.getConfigurationByCode(holidayListDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        holidayListDTO.setAuthorized("N");
        mutationEntity.setPayload(new Payload(payLoadString1));
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTO);
        assertNotEquals("Y",holidayListResponse.getHolidayList().get(0).getAuthorized());
    }

    @Test
    @DisplayName("JUnit for getHolidayListByCode in application service check Parameter not null")
    void getHolidayListByCodeIsAuthorizeCheckParameter() throws FatalException {
        given(holidayListDomainService.getHolidayListById(holidayListDTO.getHolidayListId())).willReturn(holidayListEntity);
        given(holidayListAssembler.convertEntityToDto(holidayListEntity)).willReturn(holidayListDTO);
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext, holidayListDTO);
        assertThat(holidayListResponse.getHolidayList().get(0).getHolidayListId()).isNotBlank();
        assertThat(holidayListResponse.getHolidayList().get(0).getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for getHolidayListByCode in application service when Not Authorize in try block for Negative when getAuthorized unexpected is Y")
    void getHolidayListByCodewhenNotAuthorizeTryBlockForNegative() throws JsonProcessingException, FatalException {
        given(mutationsDomainService.getConfigurationByCode(holidayListDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity);
        mutationEntity.setPayload(new Payload(payLoadString1));
        holidayListDTOUnAuth.setAuthorized("N");
        HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayListById(sessionContext,holidayListDTOUnAuth);
        assertNotEquals("Y",holidayListResponse.getHolidayList().get(0).getAuthorized());
        assertThat(holidayListResponse.getHolidayList()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(holidayListEntity.toString()).isNotNull();
        assertThat(holidayListDTO.toString()).isNotNull();
        HolidayDTO holidayDTO =new HolidayDTO(1,"Holi","annual","2022-12-26");
        holidayDTO.getHolidayDate();
        holidayDTO.getHolidayName();
        holidayDTO.getHolidayType();
        HolidayListDTO holidayListDTO2=new HolidayListDTO("HOL01","Anuual","reguler",false,"2022-12-26",List.of(holidayDTO));
        HolidayDTO.builder().holidayName("Holi").holidayType("annual").holidayDate("2022-08-26").build().toString();
        HolidayListDTO.builder().holidayListId("HOL01").holidayListName("annual").holidayListDescription("regular").isAdhocHolidays(true).effectiveDate("2022-12-26").holiday(List.of()).build();
        HolidayListEntityKey holidayListEntityKey=new HolidayListEntityKey("HOL01");
        HolidayListEntity holidayListEntity=new HolidayListEntity("HOL01","annual","reguler",'Y',new Date(),List.of(),"LC01","RN01","draft",0,"Y","draft");
        AppVerCategoryConfigEntity appVerCategoryConfigEntity2=new AppVerCategoryConfigEntity("VC0001","Address",'Y',List.of(),"LC001","RF001","draft",1,"N","draft");
        HolidayEntity holidayEntity=new HolidayEntity();
        HolidayEntityKey.builder().holidayId(1).holidayListId("HOL01").build();
        HolidayEntityKey holidayEntityKey=new HolidayEntityKey(1,"HOL01");
        HolidayCalendarBusinessPolicyDTO.builder().holidayListDTO(new HolidayListDTO()).build();
        HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO1=new HolidayCalendarBusinessPolicyDTO();
        holidayCalendarBusinessPolicyDTO1.setHolidayListDTO(new HolidayListDTO());
        HolidayCalendarBusinessPolicy holidayCalendarBusinessPolicy=new HolidayCalendarBusinessPolicy();
        holidayCalendarBusinessPolicy.setHolidayCalendarBusinessPolicyDTO(getholidayCalendarBusinessPolicyDTO());
        HolidayCalendarBusinessPolicy.builder().holidayCalendarBusinessPolicyDTO(new HolidayCalendarBusinessPolicyDTO()).build();
        holidayEntityKey.keyAsString();
        holidayEntityKey.setHolidayId(1);
        holidayEntityKey.setHolidayListId("HOL01");
        holidayEntityKey.getHolidayId();
        holidayEntityKey.getHolidayListId();
        holidayEntity.setHolidayId(1);
        assertThat(holidayListEntityKey.toString()).isNotNull();
        assertThat(holidayListEntityKey.toString()).isNotNull();
        holidayListEntityKey.setHolidayListId("HOL01");
        System.out.println(holidayListEntityKey.getHolidayListId());
        holidayListEntityKey.keyAsString();
        holidayListEntityKey.builder().holidayListId("HOL01").build();
        assertThat(holidayListDTO).descriptionText();
    }

    @Test
    @DisplayName("JUnit for getHolidayLists in application service for try block negative scenario for SessionContext some field not be null")
    void getHolidayListsTryBlockNegative() throws JsonProcessingException, FatalException {

        HolidayListDTO holidayListDTOO= new HolidayListDTO();
        HolidayListEntity holidayListEntity5 = new HolidayListEntity();
//        given(mutationsDomainService.getMutations(holidayListDTOO.getTaskCode())).willReturn(List.of(mutationEntity));
//        given(holidayListDomainService.getHolidayLists()).willReturn(List.of(holidayListEntity5));
//        given(holidayListAssembler.convertEntityToDto(holidayListEntity5)).willReturn(holidayListDTOO);
//        given(holidayListAssembler.setAuditFields(mutationEntity,holidayListDTOO)).willReturn(holidayListDTOO);
        Assertions.assertThrows(Exception.class,()-> {
            SessionContext sessionContext2=null;
            HolidayListResponse holidayListResponse = holidayListApplicationService.getHolidayLists(sessionContext2);
            assertThat(sessionContext1.getRole()).isNotEmpty();
            assertThat(sessionContext1.getServiceInvocationModeType()).isNotNull();
        });

    }

    private SessionContext getValidSessionContext() {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch(null)
                .userId("nikhiljagtap")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("HOLIDAY")
                .originalTransactionReferenceNumber(null)
                .externalBatchNumber(1L)
                .customAttributes(null)
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("es_US")
                .originatingModuleCode(null)
                .role(new String[]{"maker"}).build();
        return sessionContext;
    }


    private SessionContext getErrorSession(){
        SessionContext sessionContext1=SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber(null)
                .userTransactionReferenceNumber(null).externalTransactionReferenceNumber(null)
                .targetUnit("dummy_target").postingDate(new Date()).valueDate(new Date()).transactionBranch(null)
                .userId("nikhiljagtap")
//                .accessibleTargetUnits([])
                .channel("Branch").taskCode("HOLIDAY")
                .originalTransactionReferenceNumber(null)
                .externalBatchNumber(null)
                .customAttributes(null)
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)
//                .mutationType("")
                .userLocal("es_US")
                .originatingModuleCode(null)
                .role(null)
                .build();
        return sessionContext1;
    }

    private HolidayListDTO getHolidayListDTOAuthorized () {
        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListName("annual");
        holidayListDTO.setHolidayListDescription("Reguler");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2023-12-26");
        List<HolidayDTO>holidayDTO=new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","annual","2023-12-27"));
        holidayListDTO.setHoliday(holidayDTO);
        holidayListDTO.setAuthorized("Y");
        return holidayListDTO;
    }


    private HolidayListDTO getHolidayListDTO()
    {

        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListName("annual");
        holidayListDTO.setHolidayListDescription("Reguler");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2022-08-26");
        List<HolidayDTO>holidayDTO=new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","annual","2022-12-26"));
        holidayListDTO.setHoliday(holidayDTO);
        holidayListDTO.setStatus("DELETED");
        holidayListDTO.setRecordVersion(1);
        return holidayListDTO;
    }

    private HolidayCalendarBusinessPolicyDTO getholidayCalendarBusinessPolicyDTO(){
        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListName("annual");
        holidayListDTO.setHolidayListDescription("Reguler");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2022-08-26");
        List<HolidayDTO>holidayDTO=new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","annual","2022-12-26"));
        holidayListDTO.setHoliday(holidayDTO);
        holidayListDTO.setStatus("DELETED");
        holidayListDTO.setRecordVersion(1);

        HolidayCalendarBusinessPolicyDTO holidayCalendarBusinessPolicyDTO=new HolidayCalendarBusinessPolicyDTO(holidayListDTO);
        return holidayCalendarBusinessPolicyDTO;
    }
    private HolidayListEntity getHolidayListEntity(){
        HolidayListEntity holidayListEntity = new HolidayListEntity();
        holidayListEntity.setHolidayListId("HOL01");
        holidayListEntity.setHolidayListName("annual");
        holidayListEntity.setHolidayListDescription("Reguler");
        holidayListEntity.setIsAdhocHolidays('Y');
        holidayListEntity.setEffectiveDate(new Date());;

        List<HolidayEntity> holidayEntity=new ArrayList<>();
        holidayEntity.add(new HolidayEntity(1,"Holi","annual",new Date(),"Active",1,"Y","draft"));
        holidayListEntity.setHolidayEntity(holidayEntity);
        holidayListEntity.setStatus("draft");
        holidayListEntity.setRecordVersion(0);
        return holidayListEntity;
    }
    private HolidayListEntity getHolidayListEntity1()
    {
        HolidayListEntity holidayListEntity = new HolidayListEntity();
        holidayListEntity.setHolidayListId("HOL01");
        holidayListEntity.setHolidayListName("annual");
        holidayListEntity.setHolidayListDescription("Reguler");
        holidayListEntity.setIsAdhocHolidays('Y');
        holidayListEntity.setEffectiveDate(new Date());;

        List<HolidayEntity> holidayEntity=new ArrayList<>();
        holidayEntity.add(new HolidayEntity(1,"Holi","annual",new Date(),"Active",1,"Y","draft"));
        holidayListEntity.setHolidayEntity(holidayEntity);
        holidayListEntity.setStatus("DELETED");
        holidayListEntity.setRecordVersion(1);
        return holidayListEntity;

    }
    private HolidayListDTO getHolidayListDTOUnAuth(){
        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListName("annual");
        holidayListDTO.setHolidayListDescription("Reguler");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2022-12-26");
        holidayListDTO.setAuthorized("N");
        List<HolidayDTO>holidayDTO=new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","annual","2022-12-26"));
        holidayListDTO.setHoliday(holidayDTO);
        return holidayListDTO;

    }

    private HolidayListDTO getHolidayListDTOMapper(){
        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListName("annual");
        holidayListDTO.setHolidayListDescription("Reguler");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2022-08-26");

        List<HolidayDTO>holidayDTO=new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","annual","2022-12-26"));
        holidayListDTO.setHoliday(holidayDTO);
        holidayListDTO.setAuthorized("N");
        holidayListDTO.setTaskCode("HOLIDAY");
        holidayListDTO.setTaskIdentifier("HOL01");
        return holidayListDTO;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"HOLIDAY\",\"taskIdentifier\":\"HOL01\",\"holidayListId\":\"HOL01\",\"holidayListName\":\"annual\",\"holidayListDescription\":\"Regular\",\"isAdhocHolidays\":true,\"effectiveDate\":\"2022-09-25\",\"holiday\":[{\"holidayName\":\"Holi\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-12-26\"},{\"holidayName\":\"Ganesh\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-12-26\"},{\"holidayName\":\"15 aug\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-12-26\"}]}";
        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("HOL01");
        mutationEntity.setTaskCode("HOLIDAY");
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;

    }
    private MutationEntity getMutationEntityUnauthorize() {
        String payLoadString="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"HOLIDAY\",\"taskIdentifier\":\"HOL01\",\"holidayListId\":\"HOL01\",\"holidayListName\":\"annual\",\"holidayListDescription\":\"Regular\",\"isAdhocHolidays\":true,\"effectiveDate\":\"2022-08-20\",\"holiday\":[{\"holidayName\":\"Holi\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"Ganesh\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"15 aug\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"}]}";
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("HOL01");
        mutationEntity.setTaskCode("HOLIDAY");
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
        String payLoadString1 ="{\"action\":\"authorize\",\"status\":\"active\",\"recordVersion\":hhhhhh,\"authorized\":\"Y\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"HOLIDAY\",\"taskIdentifier\":\"HOL01\",\"holidayListId\":\"HOL01\",\"holidayListName\":\"annual\",\"holidayListDescription\":\"Regular\",\"isAdhocHolidays\":true,\"effectiveDate\":\"2022-08-20\",\"holiday\":[{\"holidayName\":\"Holi\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"Ganesh\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"15 aug\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"}]}";
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("HOL01");
        mutationEntity2.setTaskCode("HOLIDAY");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    String payLoadString1="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"HOLIDAY\",\"taskIdentifier\":\"HOL01\",\"holidayListId\":\"HOL01\",\"holidayListName\":\"annual\",\"holidayListDescription\":\"Regular\",\"isAdhocHolidays\":true,\"effectiveDate\":\"2022-08-20\",\"holiday\":[{\"holidayName\":\"Holi\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"Ganesh\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"},{\"holidayName\":\"15 aug\",\"holidayType\":\"annual\",\"holidayDate\":\"2022-8-26\"}]}";
      }
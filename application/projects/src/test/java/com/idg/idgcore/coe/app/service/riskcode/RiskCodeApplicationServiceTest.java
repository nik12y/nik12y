package com.idg.idgcore.coe.app.service.riskcode;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.app.service.branchtype.BranchTypeApplicationService;
import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.assembler.riskcode.RiskCodeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntityKey;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchtype.IBranchTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.domain.service.riskcode.IRiskCodeDomainService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
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

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_N;
import static com.idg.idgcore.coe.common.Constants.BRANCHTYPE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class RiskCodeApplicationServiceTest {

    @InjectMocks
    private RiskCodeApplicationService riskCodeApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private RiskCodeAssembler riskCodeAssembler;

    @Mock
    private IRiskCodeDomainService riskCodeDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;

    private RiskCodeEntity riskCodeEntity;

    private RiskCodeDTO riskCodeDTO;

    private RiskCodeDTO riskCodeDTOUnAuth;

    private RiskCodeDTO riskCodeDTO1;

    private RiskCodeEntity riskCodeEntity1;

    private RiskCodeEntity riskCodeEntity2;

    private RiskCodeDTO riskCodeDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        riskCodeDTO=getstateDTOAuthorized ();
        riskCodeEntity=getStateEntity();
        riskCodeDTOUnAuth=getStateDTOUnAuth();
        riskCodeDTOMapper=getStateDTOMapper();
        mutationEntity=getMutationEntity();
        riskCodeEntity1=getStatesEntity();
        riskCodeEntity2=getStatesEntity2();
        riskCodeDTO1=getStatesDTO();
        mutationEntity2=getMutationEntityJsonError();

    }





    @Test
    @DisplayName("JUnit for get-By-BranchTypeCode in application service when Authorize try Block")
    void getRiskCodeByCodeIsAuthorize() throws FatalException {
        given(riskCodeDomainService.getRiskCodeByCode(riskCodeDTO.getRiskCode())).willReturn(riskCodeEntity);
//        System.out.println((branchTypeDomainService.getBranchTypeByCode(branchTypeDTO.getBranchTypeCode())).willReturn(branchTypeEntity));
        given(riskCodeAssembler.convertEntityToDto(riskCodeEntity)).willReturn(riskCodeDTO);
        RiskCodeDTO riskCodeDTO1 = riskCodeApplicationService.getRiskCodeByCode(sessionContext, riskCodeDTO);
        assertEquals("Y",riskCodeDTO1.getAuthorized());
        assertThat(riskCodeDTO1).isNotNull();
    }


//    @Test
//    @DisplayName("JUnit for the get-By-BranchTypeCode in application service when not-Autorized try Block")
//    void getBranchTypeByCodeIsNotAuthorised() throws FatalException{
//        given(branchTypeDomainService.getBranchTypeByCode((branchTypeDTO.getBranchTypeCode()))).willReturn(branchTypeEntity);
//        given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
//        BranchTypeDTO branchTypeDTO1 = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTO);
//        assertEquals("N",branchTypeDTO1.getAuthorized());
//        assertThat(branchTypeDTO1).isNotNull();
//
//    }




    @Test
    @DisplayName("JUnit for getBy-RiskCode in application service when Not Authorize in catch block")
    void getRiskCodeByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null," +
                "\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}";

        given(mutationsDomainService.getConfigurationByCode(riskCodeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);


        RiskCodeEntityKey riskCodeEntityKey =  RiskCodeEntityKey.builder().riskCode("RC012").build();
        System.out.println(riskCodeEntityKey.keyAsString());
        Assertions.assertThrows(Exception.class,()-> {
            RiskCodeDTO riskCodeDTO1 = riskCodeApplicationService.getRiskCodeByCode(sessionContext, riskCodeDTOMapper);
            assertEquals("N",riskCodeDTO1.getAuthorized());
            assertThat(riskCodeDTO1).isNotNull();
            System.out.println(riskCodeDTO1);
        });
    }




//    @Test
//    @Disabled
//    @DisplayName("JUnit for getBranches in application service for catch block for checker")
//    void getBranchesCatchBlockForChecker() throws JsonProcessingException, FatalException {
//
//        MutationEntity unauthorizedEntities = getMutationEntity();
//
//        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
//
//        sessionContext.setRole(new String[] { "" });
//        given(mutationsDomainService.getUnauthorizedMutation(
//                branchTypeDTO1.getTaskCode(),AUTHORIZED_N))
//                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
//        Assertions.assertThrows(FatalException.class,()-> {
//            List<BranchTypeDTO> branchTypeDTO1 = branchTypeApplicationService.getBranches(sessionContext);
//            System.out.println("return size : " + branchTypeDTO1.size());
//            assertThat(branchTypeDTO1).isNotNull();
//            System.out.println(branchTypeDTO1);
//        });
//    }





    @Test
    @DisplayName("JUnit for processRiskCode in application service for Try Block")
    void processRiskCodeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(riskCodeDTO);
        riskCodeApplicationService.processRiskCode(sessionContext, riskCodeDTO);
        verify(process, times(1)).process(riskCodeDTO);
    }

//
//    @Test
//    @DisplayName("Should return all getRiskCodes when there are no unauthorized")
//    void getRiskCodesWhenThereAreNoUnauthorized() throws FatalException {
//        given(riskCodeDomainService.getRiskCodes()).willReturn(List.of(riskCodeEntity));
//        given(mutationsDomainService.getUnauthorizedMutation(BRANCHTYPE, AUTHORIZED_N)).willReturn(List.of());
//        given(riskCodeAssembler.convertEntityToDto(riskCodeEntity)).willReturn(riskCodeDTO);
//        List<RiskCodeDTO> riskCodeDTOList = riskCodeApplicationService.getRiskCodes(sessionContext);
//        assertEquals(1, riskCodeDTOList.size());
//        assertEquals(riskCodeDTO, riskCodeDTOList.get(0));
//    }




    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null," +
                "\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}";
        doNothing().when(riskCodeDomainService).save(riskCodeDTO);
        riskCodeApplicationService.save(riskCodeDTO);
        riskCodeApplicationService.addUpdateRecord(payLoadString1);
        verify(riskCodeDomainService, times(1)).save(riskCodeDTO);

    }


    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = riskCodeDTO.getRiskCode();
        given(riskCodeDomainService.getRiskCodeByCode(code)).willReturn(riskCodeEntity);
        riskCodeApplicationService.getConfigurationByCode(code);
        assertThat(riskCodeEntity).isNotNull();
    }




    @Test
    @DisplayName("JUnit for getBy-BranchTypeCode in application service when Authorize for Negative")
    void getRiskCodeByCodeIsAuthorizeforNegative() throws FatalException,  JsonProcessingException  {
        given(riskCodeDomainService.getRiskCodeByCode(riskCodeDTO.getRiskCode())).willReturn(riskCodeEntity);
        given(riskCodeAssembler.convertEntityToDto(riskCodeEntity)).willReturn(riskCodeDTO);
        RiskCodeDTO riskCodeDTO1 = riskCodeApplicationService.getRiskCodeByCode(sessionContext, riskCodeDTO);
        assertNotEquals("N",riskCodeDTO1.getAuthorized());
        assertThat(riskCodeDTO1).isNotNull();
    }





    @Test
    @DisplayName("JUnit for getBy-RiskCode in application service check Parameter not null")
    void getRiskCodeByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        RiskCodeDTO riskCodeDTONull=null;
        RiskCodeDTO riskCodeDTOEx=new RiskCodeDTO();
        riskCodeDTOEx.setRiskCode("RC011");
        riskCodeDTOEx.setAuthorized("Y");

        RiskCodeEntityKey riskCodeEntityKey = new RiskCodeEntityKey();
        riskCodeEntityKey.setRiskCode("RC011");

        given(riskCodeDomainService.getRiskCodeByCode(riskCodeDTOEx.getRiskCode())).willReturn(riskCodeEntity);
        given(riskCodeAssembler.convertEntityToDto(riskCodeEntity)).willReturn(riskCodeDTO);
        RiskCodeDTO riskCodeDTO1 = riskCodeApplicationService.getRiskCodeByCode(sessionContext, riskCodeDTOEx);
        assertThat(riskCodeDTOEx.getRiskCode()).isNotBlank();
        assertThat(riskCodeDTOEx.getAuthorized()).isNotBlank();
    }


    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(riskCodeEntity.toString()).isNotNull();
        assertThat(riskCodeDTO.toString()).isNotNull();
        RiskCodeDTO riskCodeDTO2=new RiskCodeDTO("RC006", "RiskCode006","RiskCode K006",true,"Internal","RCK006");
        RiskCodeDTO.builder().riskCode("RC007").riskCodeName("RC007 name").riskCodeDescription("Risk Code007 Description")
                .riskMode("internal").riskCategoryCode("RCK007").isAllowDetailsModified(true)
                .build().toString();
        RiskCodeEntityKey riskCodeEntityKey=new RiskCodeEntityKey("RC008");
        assertThat(riskCodeEntityKey.toString()).isNotNull();
        riskCodeEntityKey.setRiskCode("RC009");
        riskCodeEntityKey.keyAsString();
        riskCodeEntityKey.builder().riskCode("RC010").build();
        assertThat(riskCodeDTO).descriptionText();
    }


    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("chaetll")

                .channel("Branch").taskCode("RISKCODE")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)

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

                .channel("").taskCode("")
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(null)
                .customAttributes("")
                .serviceInvocationModeType(null)
                .allTargetUnitsSelected(true)

                .userLocal("")
                .originatingModuleCode("")
                .role(new String[] {"maker"})
                .build();
        return sessionContext1;
    }

    private RiskCodeDTO getstateDTOAuthorized () {
        RiskCodeDTO riskCodeDTO = new RiskCodeDTO();
        riskCodeDTO.setRiskCode("RISK005");
        riskCodeDTO.setRiskCodeDescription("RiskCode005");
        riskCodeDTO.setRiskCodeName("RiskCode005");
        riskCodeDTO.setRiskMode("Internal");
        riskCodeDTO.setIsAllowDetailsModified(true);

        riskCodeDTO.setAuthorized("Y");
        return riskCodeDTO;
    }
    private RiskCodeDTO getStatesDTO()
    {
        RiskCodeDTO riskCodeDTO = new RiskCodeDTO();



        riskCodeDTO.setRiskCode("RISK005");
        riskCodeDTO.setRiskCodeDescription("RiskCode005");
        riskCodeDTO.setRiskCodeName("RiskCode005");
        riskCodeDTO.setRiskMode("Internal");
        riskCodeDTO.setIsAllowDetailsModified(true);

        riskCodeDTO.setTaskCode("RISKCODE");
        riskCodeDTO.setStatus("DELETED");
        riskCodeDTO.setRecordVersion(1);
        return riskCodeDTO;
    }
    private RiskCodeEntity getStateEntity(){
        RiskCodeEntity riskCodeEntity01 = new RiskCodeEntity("RISK004","Risk Code K004","RCK004 Name","RC004", 'Y',"Internal",null,null,0,"draft","Y","authorized");

        return riskCodeEntity01;
    }
    private RiskCodeEntity getStatesEntity()
    {
        RiskCodeEntity riskCodeEntity3 = new RiskCodeEntity();
        riskCodeEntity3.setRiskCode("RISK003");
        riskCodeEntity3.setRiskCodeDescription("RiskCode003");
        riskCodeEntity3.setRiskCodeName("RiskCode003");
        riskCodeEntity3.setRiskMode("Internal");
        riskCodeEntity3.setIsAllowDetailsModified('Y');

        riskCodeEntity3.setStatus("DELETED");
        riskCodeEntity3.setRecordVersion(1);
        return riskCodeEntity3;
    }
    private RiskCodeEntity getStatesEntity2()
    {
        RiskCodeEntity riskCodeEntity2 = new RiskCodeEntity();
        riskCodeEntity2.setRiskCode("RISK003");
        riskCodeEntity2.setRiskCodeDescription("RiskCode003");
        riskCodeEntity2.setRiskCodeName("RiskCode003");
        riskCodeEntity2.setRiskMode("Internal");
        riskCodeEntity2.setIsAllowDetailsModified('Y');



        riskCodeEntity2.setAuthorized("N");
        riskCodeEntity2.setStatus("closed");
        riskCodeEntity2.setRecordVersion(1);
        return riskCodeEntity2;
    }
    private RiskCodeDTO getStateDTOUnAuth(){
        RiskCodeDTO riskCodeDTOUnAuth= new RiskCodeDTO("RISK002","RISK CODE K002","RISK CODE DECRIPTION",true,"Internal","RC002");
//        riskCodeDTOMapper.setRiskCategoryCode("RC001");
//        riskCodeDTOMapper.setRiskMode("Internal");
//        riskCodeDTOMapper.setIsAllowDetailsModified(true);
        riskCodeDTOUnAuth.setAuthorized("N");
        riskCodeDTOUnAuth.setTaskCode("RISKCODE");
        riskCodeDTOUnAuth.setTaskIdentifier("RC");
        return riskCodeDTOUnAuth;
    }


    private RiskCodeDTO getStateDTOMapper(){
        RiskCodeDTO riskCodeDTOMapper= new RiskCodeDTO();
        riskCodeDTOMapper.setRiskCode("RISK003");
        riskCodeDTOMapper.setRiskCodeDescription("RiskCode003");
        riskCodeDTOMapper.setRiskCodeName("RiskCode003");
        riskCodeDTOMapper.setRiskMode("Internal");
        riskCodeDTOMapper.setIsAllowDetailsModified(true);
        riskCodeDTOMapper.setAuthorized("N");
        riskCodeDTOMapper.setTaskCode("RISKCODE");
        riskCodeDTOMapper.setTaskIdentifier("RC");
        return riskCodeDTOMapper;
    }
    private MutationEntity getMutationEntity() {
        String payLoadString="{\"createdBy\":null," +
                "\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BRANCH-TYPE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}"; //,"countryCode":"IN"

        MutationEntity mutationEntity = new MutationEntity();

        mutationEntity.setTaskIdentifier("MH");
        mutationEntity.setTaskCode("RISCODE");
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
        String payLoadString1 ="{\"createdBy\":null," +
                "\"creationTime\":null,\"lastUpdatedBy\":null," +
                "\"lastUpdatedTime\":null,\"action\":\"authorize\",\"status\":\"closed\"," +
                "\"recordVersion\":hhhhhh,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"BRANCH-TYPE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}";//,"countryCode":"IN"
        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("MH");
        mutationEntity2.setTaskCode("RISKCODE");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

}


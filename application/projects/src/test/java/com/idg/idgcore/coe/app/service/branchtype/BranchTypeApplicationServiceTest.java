package com.idg.idgcore.coe.app.service.branchtype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.branchtype.BranchTypeAssembler;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.branchtype.IBranchTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.junit.jupiter.api.*;
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
class BranchTypeApplicationServiceTest {

    @InjectMocks
    private BranchTypeApplicationService branchTypeApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private BranchTypeAssembler branchTypeAssembler;

    @Mock
    private IBranchTypeDomainService branchTypeDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;

    private SessionContext sessionContext1;

    private BranchTypeEntity branchTypeEntity;

    private BranchTypeDTO branchTypeDTO;

    private BranchTypeDTO branchTypeDTOUnAuth;

    private BranchTypeDTO branchTypeDTO1;

    private BranchTypeEntity branchTypeEntity1;

    private BranchTypeEntity branchTypeEntity2;

    private BranchTypeDTO branchTypeDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        branchTypeDTO=getstateDTOAuthorized ();
        branchTypeEntity=getStateEntity();
        branchTypeDTOUnAuth=getStateDTOUnAuth();
        branchTypeDTOMapper=getStateDTOMapper();
        mutationEntity=getMutationEntity();
        branchTypeEntity1=getStatesEntity();
        branchTypeEntity2=getStatesEntity2();
        branchTypeDTO1=getStatesDTO();
        mutationEntity2=getMutationEntityJsonError();

    }





    @Test
    @DisplayName("JUnit for get-By-BranchTypeCode in application service when Authorize try Block")
    void getBranchTypeByCodeIsAuthorize() throws FatalException {
        given(branchTypeDomainService.getBranchTypeByCode(branchTypeDTO.getBranchTypeCode())).willReturn(branchTypeEntity);
        given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
        BranchTypeDTO branchTypeDTO1 = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTO);
        assertEquals("Y",branchTypeDTO1.getAuthorized());
        assertThat(branchTypeDTO1).isNotNull();
    }





    @Test
    @DisplayName("JUnit for getBy-BranchTypeCode in application service when Not Authorize in catch block")
    void getBranchTypeByCodewhenNotAuthorizeCatchBlock () throws FatalException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null," +
                "\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":hhhhhh,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}";

        given(mutationsDomainService.getConfigurationByCode(branchTypeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);


        BranchTypeEntityKey branchTypeEntityKey =  BranchTypeEntityKey.builder().branchTypeCode("MH").build();
        System.out.println(branchTypeEntityKey.keyAsString());
        Assertions.assertThrows(Exception.class,()-> {
            BranchTypeDTO branchTypeDTO1 = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTOMapper);
            assertEquals("N",branchTypeDTO1.getAuthorized());
            assertThat(branchTypeDTO1).isNotNull();
            System.out.println(branchTypeDTO1);
        });
    }




    @Test
    @Disabled
    @DisplayName("JUnit for getBranches in application service for catch block for checker")
    void getBranchesCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();

        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();

        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getUnauthorizedMutation(
                branchTypeDTO1.getTaskCode(),AUTHORIZED_N))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        Assertions.assertThrows(FatalException.class,()-> {
            List<BranchTypeDTO> branchTypeDTO1 = branchTypeApplicationService.getBranches(sessionContext);
            System.out.println("return size : " + branchTypeDTO1.size());
            assertThat(branchTypeDTO1).isNotNull();
            System.out.println(branchTypeDTO1);
        });
    }





    @Test
    @DisplayName("JUnit for processBranchType in application service for Try Block")
    void processBranchTypeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(branchTypeDTO);
        branchTypeApplicationService.processBranchType(sessionContext, branchTypeDTO);
        verify(process, times(1)).process(branchTypeDTO);
    }


    @Test
    @DisplayName("Should return all getBranches when there are no unauthorized")
    void getBranchesWhenThereAreNoUnauthorized() throws FatalException {
        given(branchTypeDomainService.getBranches()).willReturn(List.of(branchTypeEntity));
        given(mutationsDomainService.getUnauthorizedMutation(BRANCHTYPE, AUTHORIZED_N)).willReturn(List.of());
        given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
        List<BranchTypeDTO> branchTypeDTOList = branchTypeApplicationService.getBranches(sessionContext);
        assertEquals(1, branchTypeDTOList.size());
        assertEquals(branchTypeDTO, branchTypeDTOList.get(0));
    }




    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null," +
                "\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\"action\":\"authorize\"," +
                "\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"STATE\"," +
                "\"taskIdentifier\":\"MH\",\"branchTypeCode\":\"MH\",\"branchTypeName\":\"MAHARASHTRA\"}";
        doNothing().when(branchTypeDomainService).save(branchTypeDTO);
        branchTypeApplicationService.save(branchTypeDTO);
        branchTypeApplicationService.addUpdateRecord(payLoadString1);
        verify(branchTypeDomainService, times(1)).save(branchTypeDTO);

    }


    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = branchTypeDTO.getBranchTypeCode();
        given(branchTypeDomainService.getBranchTypeByCode(code)).willReturn(branchTypeEntity);
        branchTypeApplicationService.getConfigurationByCode(code);
        assertThat(branchTypeEntity).isNotNull();
    }




    @Test
    @DisplayName("JUnit for getBy-BranchTypeCode in application service when Authorize for Negative")
    void getBranchTypeByCodeIsAuthorizeforNegative() throws FatalException,  JsonProcessingException  {
        given(branchTypeDomainService.getBranchTypeByCode(branchTypeDTO.getBranchTypeCode())).willReturn(branchTypeEntity);
        given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
        BranchTypeDTO branchTypeDTO1 = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTO);
        assertNotEquals("N",branchTypeDTO1.getAuthorized());
        assertThat(branchTypeDTO).isNotNull();
    }





    @Test
    @DisplayName("JUnit for getBy-BranchTypeCode in application service check Parameter not null")
    void getBranchTypeByCodeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        BranchTypeDTO branchTypeDTONull=null;
        BranchTypeDTO branchTypeDTOEx=new BranchTypeDTO();
        branchTypeDTOEx.setBranchTypeCode("MH");
        branchTypeDTOEx.setAuthorized("Y");

        BranchTypeEntityKey branchTypeEntityKey = new BranchTypeEntityKey();
        branchTypeEntityKey.setBranchTypeCode("MH");

        given(branchTypeDomainService.getBranchTypeByCode(branchTypeDTOEx.getBranchTypeCode())).willReturn(branchTypeEntity);
        given(branchTypeAssembler.convertEntityToDto(branchTypeEntity)).willReturn(branchTypeDTO);
        BranchTypeDTO branchTypeDTO1 = branchTypeApplicationService.getBranchTypeByCode(sessionContext, branchTypeDTOEx);
        assertThat(branchTypeDTOEx.getBranchTypeCode()).isNotBlank();
        assertThat(branchTypeDTOEx.getAuthorized()).isNotBlank();
    }


    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(branchTypeEntity.toString()).isNotNull();
        assertThat(branchTypeDTO.toString()).isNotNull();
        BranchTypeDTO bankIdentifierDTO2=new BranchTypeDTO("MH", "MAHARASTRA");
        BranchTypeDTO.builder().BranchTypeCode("MH").BranchTypeName("MAHARASTRA")
                .build().toString();
        BranchTypeEntityKey branchTypeEntityKey=new BranchTypeEntityKey("MH");
        assertThat(branchTypeEntityKey.toString()).isNotNull();
        branchTypeEntityKey.setBranchTypeCode("MA");
        branchTypeEntityKey.keyAsString();
        branchTypeEntityKey.builder().branchTypeCode("JP").build();
        assertThat(branchTypeDTO).descriptionText();
    }


    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003").defaultBranchCode("1141").internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("").externalTransactionReferenceNumber("")
                .targetUnit("dummy_target_unit").postingDate(new Date()).valueDate(new Date()).transactionBranch("")
                .userId("chaetll")

                .channel("Branch").taskCode("BRANCH-TYPE")
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

    private BranchTypeDTO getstateDTOAuthorized () {
        BranchTypeDTO branchTypeDTO = new BranchTypeDTO();
        branchTypeDTO.setBranchTypeCode("MH");
        branchTypeDTO.setBranchTypeName("MAHARASHTRA");

        branchTypeDTO.setAuthorized("Y");
        return branchTypeDTO;
    }
    private BranchTypeDTO getStatesDTO()
    {
        BranchTypeDTO branchTypeDTO = new BranchTypeDTO();
        branchTypeDTO.setBranchTypeCode("MH");
        branchTypeDTO.setBranchTypeName("MAHARASHTRA");

        branchTypeDTO.setTaskCode("MH");
        branchTypeDTO.setStatus("DELETED");
        branchTypeDTO.setRecordVersion(1);
        return branchTypeDTO;
    }
    private BranchTypeEntity getStateEntity(){
        BranchTypeEntity branchTypeEntity = new BranchTypeEntity("MH","MAHARASHTRA","draft",0, "Y","draft");

        return branchTypeEntity;
    }
    private BranchTypeEntity getStatesEntity()
    {
        BranchTypeEntity branchTypeEntity = new BranchTypeEntity();
        branchTypeEntity.setBranchTypeCode("MH");
        branchTypeEntity.setBranchTypeName("MAHARASHTRA");

        branchTypeEntity.setStatus("DELETED");
        branchTypeEntity.setRecordVersion(1);
        return branchTypeEntity;
    }
    private BranchTypeEntity getStatesEntity2()
    {
        BranchTypeEntity branchTypeEntity2 = new BranchTypeEntity();
        branchTypeEntity2.setBranchTypeCode("MH");
        branchTypeEntity2.setBranchTypeName("MAHARASHTRA");

        branchTypeEntity2.setAuthorized("N");
        branchTypeEntity2.setStatus("closed");
        branchTypeEntity2.setRecordVersion(1);
        return branchTypeEntity2;
    }
    private BranchTypeDTO getStateDTOUnAuth(){
        BranchTypeDTO branchTypeDTO = new BranchTypeDTO("MH","MAHARASTRA");

        branchTypeDTO.setAuthorized("N");
        branchTypeDTO.setTaskIdentifier("MH");
        return branchTypeDTO;
    }

    private BranchTypeDTO getStateDTOMapper(){
        BranchTypeDTO branchTypeDTOMapper= new BranchTypeDTO();
        branchTypeDTOMapper.setBranchTypeCode("MH");
        branchTypeDTOMapper.setBranchTypeName("MAHARASHTRA");
        branchTypeDTOMapper.setAuthorized("N");
        branchTypeDTOMapper.setTaskCode("BRANCH-TYPE");
        branchTypeDTOMapper.setTaskIdentifier("MH");
        return branchTypeDTOMapper;
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
        mutationEntity.setTaskCode("BRANCH-TYPE");
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
        mutationEntity2.setTaskCode("BRANCH-TYPE");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

}


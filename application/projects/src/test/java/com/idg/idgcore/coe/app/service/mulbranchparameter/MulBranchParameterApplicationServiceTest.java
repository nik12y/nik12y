package com.idg.idgcore.coe.app.service.mulbranchparameter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.app.AbstractApplicationService;
import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.mulbranchparameter.IMulBranchParameterDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.*;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)

public class MulBranchParameterApplicationServiceTest {

    @InjectMocks
    private MulBranchParameterApplicationService mulBranchParameterApplicationService;

    @Mock
    private ProcessConfiguration process;

    @Mock
    private MulBranchParameterAssembler mulBranchParameterAssembler;

    @Mock
    private IMulBranchParameterDomainService mulBranchParameterDomainService;

    @Mock
    AbstractApplicationService abstractApplicationService;

    @Autowired
    private MutationEntity mutationEntity;

    private MutationEntity mutationEntity2;

    @Mock
    private IMutationsDomainService mutationsDomainService;

    private SessionContext sessionContext;
    private SessionContext sessionContext1;

    private MulBranchParameterEntity mulBranchParameterEntity;
    private MulBranchParameterDTO mulBranchParameterDTO;
    private MulBranchParameterDTO mulBranchParameterDTOUnAuth;
    private MulBranchParameterDTO mulBranchParameterDTO1;
    private MulBranchParameterEntity mulBranchParameterEntity1;

    private MulBranchParameterDTO mulBranchParameterDTOMapper;

    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext ();
        sessionContext1=getErrorSession();
        mulBranchParameterDTO=getmulBranchParameterDTOAuthorized();
        mulBranchParameterEntity=getMulBranchParameterEntity();
        mulBranchParameterDTOUnAuth=getMulBranchParameterDTOUnAuth();
        mulBranchParameterDTOMapper=getMulBranchParameterDTOMapper();
        mutationEntity=getMutationEntity();
        mulBranchParameterEntity1=getMulBranchParameterEntity1();
        mulBranchParameterDTO1=getMulBranchParameterDTO();
        mutationEntity2=getMutationEntityJsonError();
    }
    @Test
    @DisplayName ("JUnit for .getByCurrencyCodeAndEntityCodeAndEntityType in application service when Authorize try Block")
    void getByCurrencyCodeAndEntityCodeIsAuthorize ()
            throws FatalException, JsonProcessingException {
        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCodeAndEntityType(
                mulBranchParameterDTO.getCurrencyCode(),
                mulBranchParameterDTO.getEntityCode(),
                mulBranchParameterEntity.getEntityType())).
                willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(
                mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCodeAndEntityType(
                sessionContext, mulBranchParameterDTO);
        assertEquals("Y", mulBranchParameterDTO1.getAuthorized());
        Assertions.assertThat(mulBranchParameterDTO1).isNotNull();
    }
    @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCodeAndEntityType in application service when Authorize for Negative")
    void getByCurrencyCodeAndEntityCodeAndEntityTypeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCodeAndEntityType(mulBranchParameterDTO.getCurrencyCode(),mulBranchParameterDTO.getEntityCode(),mulBranchParameterDTO.getEntityType())).willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCodeAndEntityType(sessionContext, mulBranchParameterDTO);
        assertNotEquals("N",mulBranchParameterDTO1.getAuthorized());
        assertThat(mulBranchParameterDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCodeAndEntityType  in application service when Not Authorize in catch block")
    void getByCurrencyCodeAndEntityCodeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 ="{\"action\":\"draft\",\"status\":\"draft\",\"recordVersion\":0,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"draft\",\"taskCode\":\"MULBRANCHPARAM\",\"taskIdentifier\":\"INR_BR0021_Branch\"," +
                "\"currencyCode\":\"INR\",\"entityCode\":\"BR0021\",\"entityType\":\"Branch\",\"entityLevel\":\"Level4\",\"entityName\":\"Pune\"," +
                "\"currencyName\":\"Indian\",\"spotDays\":1,\"generationOfPaymentMessage\":2,\"generationOfReceiveMessages\":3}";

        given(mutationsDomainService.getConfigurationByCode(mulBranchParameterDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,()-> {
            MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCodeAndEntityType(sessionContext,mulBranchParameterDTOMapper);
            assertEquals("N",mulBranchParameterDTO1.getAuthorized());
            assertThat(mulBranchParameterDTO1).isNotNull();
        });
    }
 @Test
    @DisplayName ("JUnit for MulBranchParameterEntityKey")
    void getMulBranchParameterEntityKey () {
        MulBranchParameterEntityKey mulBranchParameterEntityKey = new MulBranchParameterEntityKey(
                "INR","BR0021","Branch");
        Assertions.assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.keyAsString();
        mulBranchParameterEntityKey.builder().currencyCode("INR")
                .entityCode("BR0021").entityType("Branch").build();
        Assertions.assertThat(mulBranchParameterEntityKey).descriptionText();
        Assertions.assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processMulBranchParameter in application service for Try Block")
    void  processMulBranchParameterForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(mulBranchParameterDTO);
        mulBranchParameterApplicationService.processMulBranchParameter(sessionContext, mulBranchParameterDTO);
        verify(process, times(1)).process(mulBranchParameterDTO);
    }

    @Test
    @DisplayName ("Test processMulBranchParameter")
    void processMulBranchParameter() throws JsonProcessingException, FatalException {
        mulBranchParameterDTOMapper = getMulBranchParameterDTOMapper();
        doNothing().when(process).process(mulBranchParameterDTOMapper);
        mulBranchParameterApplicationService.processMulBranchParameter(sessionContext,mulBranchParameterDTOMapper);
        verify(process, times(1)).process(mulBranchParameterDTOMapper);
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {
        String payLoadString1 ="{\"action\":\"modify\",\"status\":\"updated\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"MULBRANCHPARAM\"," +
                "\"taskIdentifier\":\"INR_BR0021_Branch\",\"currencyCode\":\"INR\",\"entityCode\":\"BR0021\"," +
                "\"entityType\":\"Branch\",\"entityLevel\":\"Level4\",\"entityName\":\"Nashik\",\"currencyName\":\"Indian\"," +
                "\"spotDays\":1,\"generationOfPaymentMessage\":2,\"generationOfReceiveMessages\":3}";

        doNothing().when(mulBranchParameterDomainService).save(mulBranchParameterDTO);
        mulBranchParameterApplicationService.save(mulBranchParameterDTO);
        mulBranchParameterApplicationService.addUpdateRecord(payLoadString1);
        verify(mulBranchParameterDomainService, times(1)).save(mulBranchParameterDTO);
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder()
                .bankCode("003")
                .defaultBranchCode("1141")
                .internalTransactionReferenceNumber("")
                .userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("")
                .targetUnit("dummy_target")
                .postingDate(new Date())
                .valueDate(new Date())
                .transactionBranch("")
                .userId("vidya")
//              .accessibleTargetUnits([])
                .channel("Branch")
                .taskCode(CURR_BRANCH_PARAM)
                .originalTransactionReferenceNumber("")
                .externalBatchNumber(1L)
                .customAttributes("")
                .serviceInvocationModeType(Regular)
                .allTargetUnitsSelected(true)
//              .mutationType("")
                .userLocal("en_US")
                .originatingModuleCode("")
                .role(new String[]{"maker"})
                .build();
        return sessionContext;
    }

    private SessionContext getErrorSession(){
        SessionContext sessionContextError =
                SessionContext.builder()
                        .bankCode("")
                        .defaultBranchCode("")
                        .internalTransactionReferenceNumber("")
                        .userTransactionReferenceNumber("")
                        .externalTransactionReferenceNumber("")
                        .targetUnit("")
                        .postingDate(new Date())
                        .valueDate(new Date())
                        .transactionBranch("")
                        .userId("vidya")
//                      .accessibleTargetUnits([])
                        .channel("")
                        .taskCode("")
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(null)
                        .customAttributes("")
                        .serviceInvocationModeType(null)
                        .allTargetUnitsSelected(true)
//                      .mutationType("")
                        .userLocal("")
                        .originatingModuleCode("")
                        .role(new String[] {"maker"})
                        .build();
        return sessionContextError;
    }

    private MulBranchParameterDTO getmulBranchParameterDTOAuthorized() {
        MulBranchParameterDTO mulBranchParameterDTO=new MulBranchParameterDTO();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR0021");
        mulBranchParameterDTO.setEntityType("Branch");
        mulBranchParameterDTO.setEntityLevel("Level4");
        mulBranchParameterDTO.setEntityName("Nashik");
        mulBranchParameterDTO.setCurrencyName("Indian");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTO.setAuthorized("Y");
        return mulBranchParameterDTO;
    }

    private MulBranchParameterDTO getMulBranchParameterDTO()
    {
        MulBranchParameterDTO mulBranchParameterDTO=new MulBranchParameterDTO();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR0021");
        mulBranchParameterDTO.setEntityType("Branch");
        mulBranchParameterDTO.setEntityLevel("Level4");
        mulBranchParameterDTO.setEntityName("Nashik");
        mulBranchParameterDTO.setCurrencyName("Indian");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTO.setTaskCode("MULBRANCHPARAM");
        mulBranchParameterDTO.setStatus("DELETED");
        mulBranchParameterDTO.setRecordVersion(1);
        return  mulBranchParameterDTO;

    }

    private MulBranchParameterEntity getMulBranchParameterEntity(){
        MulBranchParameterEntity mulBranchParameterEntity = new MulBranchParameterEntity(
                "INR",
                "BR0021",
                "Branch",
                "Level4",
                "Nashik",
                "India",
                1,
                11,
                21,
                null,
                null,
                0,
                "draft",
                "Y",
                "draft");
        return mulBranchParameterEntity;
    }

    private MulBranchParameterEntity getMulBranchParameterEntity1()
    {
          MulBranchParameterEntity mulBranchParameterEntity = new MulBranchParameterEntity();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR0021");
        mulBranchParameterDTO.setEntityType("Branch");
        mulBranchParameterDTO.setEntityLevel("Level4");
        mulBranchParameterDTO.setEntityName("Nashik");
        mulBranchParameterDTO.setCurrencyName("Indian");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
          mulBranchParameterEntity.setLifeCycleId(null);
          mulBranchParameterEntity.setReferenceNo(null);
          mulBranchParameterEntity.setStatus("closed");
          mulBranchParameterEntity.setRecordVersion(1);
          return  mulBranchParameterEntity;
    }
    private MulBranchParameterDTO getMulBranchParameterDTOUnAuth(){
        MulBranchParameterDTO mulBranchParameterDTO =
                new MulBranchParameterDTO(
                        "INR",
                        "BR0021",
                        "Branch",
                        "Level4",
                        "Nashik",
                        "India",
                        1,
                        11,
                        21);

        mulBranchParameterDTO.setAuthorized("N");
        mulBranchParameterDTO.setTaskIdentifier("INR_BR0021_Branch");
        return mulBranchParameterDTO;
    }

    private MulBranchParameterDTO getMulBranchParameterDTOMapper(){
        MulBranchParameterDTO mulBranchParameterDTOMapper=new MulBranchParameterDTO();
        mulBranchParameterDTO.setCurrencyCode("INR");
        mulBranchParameterDTO.setEntityCode("BR0021");
        mulBranchParameterDTO.setEntityType("Branch");
        mulBranchParameterDTO.setEntityLevel("Level4");
        mulBranchParameterDTO.setEntityName("Nashik");
        mulBranchParameterDTO.setCurrencyName("Indian");
        mulBranchParameterDTO.setSpotDays(1);
        mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
        mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
        mulBranchParameterDTOMapper.setAuthorized("N");
        mulBranchParameterDTOMapper.setTaskCode(BRANCH_PARAMETER);
        mulBranchParameterDTOMapper.setTaskIdentifier("INR_BR0021_Branch");
        return mulBranchParameterDTOMapper;
    }

    private MutationEntity getMutationEntity() {
        Payload payload = new Payload("{\"action\":\"modify\",\"status\":\"updated\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"MULBRANCHPARAM\"," +
                "\"taskIdentifier\":\"INR_BR0021_Branch\",\"currencyCode\":\"INR\",\"entityCode\":\"BR0021\"," +
                "\"entityType\":\"Branch\",\"entityLevel\":\"Level4\",\"entityName\":\"Nashik\",\"currencyName\":\"Indian\"," +
                "\"spotDays\":1,\"generationOfPaymentMessage\":2,\"generationOfReceiveMessages\":3}");

        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("INR_BR0021_Branch");
        mutationEntity.setTaskCode("MULBRANCHPARAM");
        mutationEntity.setPayload(payload);
        mutationEntity.setStatus("new");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");
        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError()
    {
        String payLoadString1 ="{\"action\":\"modify\",\"status\":\"updated\",\"recordVersion\":1,\"authorized\":\"N\"," +
                "\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"MULBRANCHPARAM\"," +
                "\"taskIdentifier\":\"INR_BR0021_Branch\",\"currencyCode\":\"INR\",\"entityCode\":\"BR0021\"," +
                "\"entityType\":\"Branch\",\"entityLevel\":\"Level4\",\"entityName\":\"Nashik\",\"currencyName\":\"Indian\"," +
                "\"spotDays\":1,\"generationOfPaymentMessage\":2,\"generationOfReceiveMessages\":3}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("INR_BR0021_Branch");
        mutationEntity2.setTaskCode("MULBRANCHPARAM");
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");
        return mutationEntity2;
    }

    @Test
    @DisplayName("JUnit for getBranchParameters in application service for try block")
    void getBranchParametersTryBlock() throws FatalException {
        given(mutationsDomainService.getMutations(CURR_BRANCH_PARAM))
                .willReturn(List.of(mutationEntity));
        List<MulBranchParameterDTO> mulBranchParameterDTOList =
                mulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
        assertThat(mulBranchParameterDTOList).isNotNull();
    }

   @Test
    @DisplayName("JUnit for getByCurrencyCodeAndEntityCodeAndEntityType in application service check Parameter not null")
    void getByCurrencyCodeAndEntityCodeAndEntityTypeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        MulBranchParameterDTO mulBranchParameterDTOnull=null;
        MulBranchParameterDTO mulBranchParameterDTOEx=new MulBranchParameterDTO();
        mulBranchParameterDTOEx.setCurrencyCode("INR");
        mulBranchParameterDTOEx.setEntityCode("BR0021");
        mulBranchParameterDTOEx.setEntityType("Branch");
        mulBranchParameterDTOEx.setAuthorized("Y");

        MulBranchParameterEntityKey mulBranchParameterEntityKey = new MulBranchParameterEntityKey();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.setEntityCode("BR001");
        mulBranchParameterEntityKey.setEntityType("Branch");

        given(mulBranchParameterDomainService.getByCurrencyCodeAndEntityCodeAndEntityType(mulBranchParameterDTOEx.getCurrencyCode(),mulBranchParameterDTOEx.getEntityCode(),mulBranchParameterDTOEx.getEntityType()))
                .willReturn(mulBranchParameterEntity);
        given(mulBranchParameterAssembler.convertEntityToDto(mulBranchParameterEntity)).willReturn(mulBranchParameterDTO);
        MulBranchParameterDTO mulBranchParameterDTO1 = mulBranchParameterApplicationService.getByCurrencyCodeAndEntityCodeAndEntityType(sessionContext, mulBranchParameterDTOEx);
        assertThat(mulBranchParameterDTOEx.getCurrencyCode()).isNotBlank();
        assertThat(mulBranchParameterDTOEx.getAuthorized()).isNotBlank();
    }

    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(mulBranchParameterEntity.toString()).isNotNull();
        assertThat(mulBranchParameterDTO.toString()).isNotNull();
        MulBranchParameterDTO mulBranchParameterDTO2=new MulBranchParameterDTO(
                "INR",
                "BR0021",
                "Branch",
                "Level4",
                "Nashik",
                "India",
                1,
                11,
                21);
        MulBranchParameterDTO.builder().currencyCode("INR")
                .entityCode("BR001")
                .entityType("Branch")
                .entityLevel("Level4")
                .entityName("Nashik")
                .currencyName("Indian")
                .spotDays(1)
                .generationOfPaymentMessage(11)
                .generationOfReceiveMessages(21)
                .authorized("N")
                .taskCode("MULBRANCHPARAM")
                .taskIdentifier("INR_BR0021_Branch")
                .build().toString();

        MulBranchParameterEntityKey mulBranchParameterEntityKey=new MulBranchParameterEntityKey("INR","BR0021","Branch");
        assertThat(mulBranchParameterEntityKey.toString()).isNotNull();
        mulBranchParameterEntityKey.setCurrencyCode("INR");
        mulBranchParameterEntityKey.setEntityCode("BR0021");
        mulBranchParameterEntityKey.setEntityType("Branch");
        mulBranchParameterEntityKey.keyAsString();
        mulBranchParameterEntityKey.builder().currencyCode("INR").build();
        assertThat(mulBranchParameterDTO).descriptionText();
    }

   //@Test
    @DisplayName("JUnit for getMulBranchParameters in application service for catch block for checker")
    void getMulBranchParametersCatchBlockForChecker() throws JsonProcessingException, FatalException {

        MutationEntity unauthorizedEntities = getMutationEntity();
        MutationEntity unauthorizedEntities1 = getMutationEntityJsonError();
        sessionContext.setRole(new String[] { "" });
        given(mutationsDomainService.getMutations(
                mulBranchParameterDTO1.getTaskCode()))
                .willReturn(List.of(unauthorizedEntities, unauthorizedEntities1));
        org.junit.jupiter.api.Assertions.assertThrows(FatalException.class,()-> {
            List<MulBranchParameterDTO> mulBranchParameterDTO1 = mulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
            assertThat(mulBranchParameterDTO1).isNotNull();
        });
    }
//    @Test
//    @DisplayName("Should return all getBranchParameters when there are no unauthorized")
//    void getBranchParametersWhenThereAreNoUnauthorized() throws FatalException {
//        given(mutationsDomainService.getMutations("MULBRANCHPARAM"))
//                .willReturn(List.of(mutationEntity));
//        List<MulBranchParameterDTO> mulBranchParameterDTOList =
//                mulBranchParameterApplicationService.getMulBranchParameters(sessionContext);
//        assertThat(mulBranchParameterDTOList).isNotNull();
//    }

    @Test
    @DisplayName("JUnit for proccessMulBranchParameter  in application service for Catch Block")
    void proccessMulBranchParameterForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        org.junit.jupiter.api.Assertions.assertThrows(Exception.class,()-> {
            mulBranchParameterApplicationService.processMulBranchParameter(sessionContext2, mulBranchParameterDTO);
            assertThat(mulBranchParameterDTO).descriptionText();
        });
    }


}

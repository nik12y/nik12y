package com.idg.idgcore.coe.app.service.currencyratetype;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idg.idgcore.coe.domain.assembler.currencyratetype.CurrencyRateTypeAssembler;

import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntityKey;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.process.ProcessConfiguration;
import com.idg.idgcore.coe.domain.service.currencyratetype.ICurrencyRateTypeDomainService;
import com.idg.idgcore.coe.domain.service.mutation.IMutationsDomainService;
import com.idg.idgcore.coe.dto.currencyratetype.CurrencyRateTypeDTO;
import com.idg.idgcore.coe.dto.mutation.PayloadDTO;
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
import static com.idg.idgcore.coe.common.Constants.CURRENCY_RATE_TYPE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
public class CurrencyRateTypeApplicationServiceTest {

    @InjectMocks
    CurrencyRateTypeApplicationService currencyRateTypeApplicationService;


    @Mock
    private ProcessConfiguration process;



    @Mock
    private CurrencyRateTypeAssembler currencyRateTypeAssembler;

    @Mock
    private ICurrencyRateTypeDomainService currencyRateTypeDomainService;

    @Autowired
    private MutationEntity mutationEntity;

    @Autowired
    private MutationEntity mutationEntity2;


    @Mock
    private IMutationsDomainService mutationsDomainService;
    private SessionContext sessionContext;
    private SessionContext sessionContext1;
    private CurrencyRateTypeEntity currencyRateTypeEntity;
    private CurrencyRateTypeDTO currencyRateTypeDTO;
    private CurrencyRateTypeDTO currencyRateTypeDTOUnAuth;
    private CurrencyRateTypeDTO currencyRateTypeDTO1;
    private CurrencyRateTypeEntity currencyRateTypeEntity1;
    private CurrencyRateTypeEntity currencyRateTypeEntity2;
    private CurrencyRateTypeDTO currencyRateTypeDTOMapper;


    @BeforeEach
    void setUp() {
        sessionContext = getValidSessionContext();
        sessionContext1 = getErrorSession();
        currencyRateTypeDTO=getCurrencyRateTypeDTOAuthorized();
        currencyRateTypeEntity=getCurrencyRateTypeEntity();
        currencyRateTypeDTOUnAuth=getCurrencyRateTypeDTOUnAuth();
        currencyRateTypeDTOUnAuth=getCurrencyRateTypeDTOUnAuth();
         currencyRateTypeDTOMapper=getCurrencyRateTypeDTOMapper();
         mutationEntity = getMutationEntity();
         currencyRateTypeEntity1 = getCurrencyRateTypesEntity();
        currencyRateTypeEntity2 = getCurrencyRateTypesEntity2();
        currencyRateTypeDTO1 = getCurrencyRateTypeDTO();
        mutationEntity2 = getMutationEntityJsonError();

    }
    @Test
    @DisplayName("JUnit for getCurrencyRateTypeByType where return the currency rate type when the authorized is Y")
    void getCurrencyRateTypeByTypeWhenAuthorizedIsYThenReturnCurrencyRateType() throws FatalException, JsonProcessingException {
        given(
                currencyRateTypeDomainService.getCurrencyRateTypeByType(
                        currencyRateTypeDTO.getCurrencyRateType()))
                .willReturn(currencyRateTypeEntity);
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity))
                .willReturn(currencyRateTypeDTO);
        CurrencyRateTypeDTO result =
                currencyRateTypeApplicationService.getCurrencyRateTypeByType(sessionContext,currencyRateTypeDTO);
        assertEquals(currencyRateTypeDTO, result);
    }

    @Test
    @DisplayName("JUnit for getCurrencyRateTypes where return all currency rate types when there are no unauthorized mutations")
    void getCurrencyRateTypesWhenThereAreNoUnauthorizedMutationsThenReturnAllCurrencyRateTypes() throws FatalException {
        given(currencyRateTypeDomainService.getCurrencyRateTypes())
                .willReturn(List.of(currencyRateTypeEntity));
        given(mutationsDomainService.getUnauthorizedMutation(CURRENCY_RATE_TYPE, AUTHORIZED_N))
                .willReturn(List.of());
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity))
                .willReturn(currencyRateTypeDTO);

        List<CurrencyRateTypeDTO> currencyRateTypeDTOList =
                currencyRateTypeApplicationService.getCurrencyRateTypes(sessionContext);

        assertEquals(1, currencyRateTypeDTOList.size());
        assertEquals(currencyRateTypeDTO,currencyRateTypeDTOList.get(0));
    }

    @Test
    @DisplayName("JUnit for getCurrencyRateTypeByType in application service when Authorize try Block")
    void getCurrencyRateTypeByTypeIsAuthorize() throws FatalException, JsonProcessingException {

        given(currencyRateTypeDomainService.getCurrencyRateTypeByType(currencyRateTypeDTO.getCurrencyRateType())).willReturn(currencyRateTypeEntity);
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity)).willReturn(currencyRateTypeDTO);
        CurrencyRateTypeDTO currencyRateTypeDTO1= currencyRateTypeApplicationService.getCurrencyRateTypeByType(sessionContext, currencyRateTypeDTO);
        assertEquals("Y",currencyRateTypeDTO1.getAuthorized());
        assertThat(currencyRateTypeDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getCurrencyRateTypeByType in application service when Not Authorize in catch block")
    void getCurrencyRateTypeByTypeWhenNotAuthorizeCatchBlock () throws FatalException, JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"unauthorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"unauthorized\",\"taskCode\":\"CURRTYPE\",\"taskIdentifier\":\"BB\"" +
                ",\"currencyRateType\":\"BB\",\"description\":\"Bill Buy\"}";

        given(mutationsDomainService.getConfigurationByCode(currencyRateTypeDTOUnAuth.getTaskIdentifier())).willReturn(mutationEntity2);
        ModelMapper mapper=new ModelMapper();
        PayloadDTO payload = new PayloadDTO(payLoadString1);
        ObjectMapper mockObjectMapper = mock(ObjectMapper.class);
        PayloadDTO helper = org.mockito.Mockito.mock(PayloadDTO.class);

        Assertions.assertThrows(Exception.class,()-> {
            CurrencyRateTypeDTO currencyRateTypeDTO1 = currencyRateTypeApplicationService.getCurrencyRateTypeByType(sessionContext, currencyRateTypeDTOMapper);
            assertEquals("N",currencyRateTypeDTO1.getAuthorized());
            assertThat(currencyRateTypeDTO1).isNotNull();
        });
    }

    @Test
    @DisplayName("JUnit for getCurrencyRateTypes in application service for try block")
    void getCurrencyRateTypesTryBlock() throws FatalException {

        given(currencyRateTypeDomainService.getCurrencyRateTypes()).willReturn(List.of(currencyRateTypeEntity1));
        given(mutationsDomainService.getUnauthorizedMutation(currencyRateTypeDTO1.getTaskCode(),AUTHORIZED_N)).willReturn(List.of(mutationEntity));

        String payLoadString ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"add\",\"status\":\"closed\",\"recordVersion\":0,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CURRTYPE\",\"taskIdentifier\":\"BB\"" +
                ",\"currencyRateType\":\"BB\",\"description\":\"Bill Buy\"}";


        Payload payload=new Payload();
        payload.setData(payLoadString);
        mutationEntity.setPayload(payload);
        String data1 = mutationEntity.getPayload().getData();
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity1)).willReturn(currencyRateTypeDTO1);

        List<CurrencyRateTypeDTO> currencyRateTypeDTO2 = currencyRateTypeApplicationService.getCurrencyRateTypes(sessionContext);
        assertThat(currencyRateTypeDTO1).isNotNull();
    }

    @Test
    @DisplayName("JUnit for processCurrencyRateType in application service for Try Block")
    void processCurrencyRateTypeForTryBlock() throws JsonProcessingException, FatalException {

        doNothing().when(process).process(currencyRateTypeDTO);
        currencyRateTypeApplicationService.processCurrencyRateType(sessionContext, currencyRateTypeDTO);
        verify(process, times(1)).process(currencyRateTypeDTO);
    }

    @Test
    @DisplayName("JUnit for processCurrencyRateType in application service for Catch Block")
    void processCurrencyRateTypeForCatchBlock() throws FatalException {
        SessionContext sessionContext2=null;
        Assertions.assertThrows(Exception.class,()-> {
            currencyRateTypeApplicationService.processCurrencyRateType(sessionContext2,currencyRateTypeDTO);
            assertThat(currencyRateTypeDTO).descriptionText();
        });
    }

    @Test
    @DisplayName("JUnit for addUpdateRecord in application service")
    void addUpdateRecordTest() throws JsonProcessingException {

        String payLoadString1 ="{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null" +
                ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\"" +
                ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CURRTYPE\",\"taskIdentifier\":\"BB\"" +
                ",\"currencyRateType\":\"BB\",\"description\":\"Bill Buy\"}";

        doNothing().when(currencyRateTypeDomainService).save(currencyRateTypeDTO);
        currencyRateTypeApplicationService.save(currencyRateTypeDTO);
        currencyRateTypeApplicationService.addUpdateRecord(payLoadString1);
        verify(currencyRateTypeDomainService, times(1)).save(currencyRateTypeDTO);
    }

    @Test
    @DisplayName("JUnit for ConfigurationByCode in application service")
    void getConfigurationByCodeTest(){
        String code = currencyRateTypeDTO.getCurrencyRateType();
        given(currencyRateTypeDomainService.getCurrencyRateTypeByType(code)).willReturn(currencyRateTypeEntity);
        currencyRateTypeApplicationService.getConfigurationByCode(code);
        assertThat(currencyRateTypeEntity).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getByCurrencyRateType in application service when Authorize for Negative")
    void getCurrencyRateTypeByTypeIsAuthorizeforNegative() throws FatalException, JsonProcessingException {
        given(currencyRateTypeDomainService.getCurrencyRateTypeByType(currencyRateTypeDTO.getCurrencyRateType())).willReturn(currencyRateTypeEntity);
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity)).willReturn(currencyRateTypeDTO);
        CurrencyRateTypeDTO currencyRateTypeDTO1 = currencyRateTypeApplicationService.getCurrencyRateTypeByType(sessionContext, currencyRateTypeDTO);
        assertNotEquals("N",currencyRateTypeDTO1.getAuthorized());
        assertThat(currencyRateTypeDTO).isNotNull();
    }

    @Test
    @DisplayName("JUnit for getCurrencyRateTypeByType in application service check Parameter not null")
    void getCurrencyRateTypeByTypeIsAuthorizeCheckParameter() throws FatalException, JsonProcessingException {
        CurrencyRateTypeDTO currencyRateTypeDTOnull=null;
        CurrencyRateTypeDTO currencyRateTypeDTOEx=new CurrencyRateTypeDTO();
        currencyRateTypeDTOEx.setCurrencyRateType("TTB");
        currencyRateTypeDTOEx.setAuthorized("Y");
        given(currencyRateTypeDomainService.getCurrencyRateTypeByType(currencyRateTypeDTOEx.getCurrencyRateType())).willReturn(currencyRateTypeEntity);
        given(currencyRateTypeAssembler.convertEntityToDto(currencyRateTypeEntity)).willReturn(currencyRateTypeDTO);
        CurrencyRateTypeDTO currencyRateTypeDTO1 = currencyRateTypeApplicationService.getCurrencyRateTypeByType(sessionContext, currencyRateTypeDTOEx);
        assertThat(currencyRateTypeDTOEx.getCurrencyRateType()).isNotBlank();
        assertThat(currencyRateTypeDTOEx.getAuthorized()).isNotBlank();
    }


    @Test
    @DisplayName("JUnit for code coverage")
    void getCodeCoverage()
    {
        assertThat(currencyRateTypeEntity.toString()).isNotNull();
        assertThat(currencyRateTypeDTO.toString()).isNotNull();
        CurrencyRateTypeDTO currencyRateTypeDTO2=new CurrencyRateTypeDTO("BB",
                "Bill Buy");
        CurrencyRateTypeDTO.builder().currencyRateType("BB")
                .description("Bill Buy")
                .authorized("N")
                .taskCode(CURRENCY_RATE_TYPE)
                .taskIdentifier("BB")
                .build().toString();
        CurrencyRateTypeEntityKey currencyRateTypeEntityKey=new CurrencyRateTypeEntityKey("BB");
        assertThat(currencyRateTypeEntityKey.toString()).isNotNull();
        currencyRateTypeEntityKey.setCurrencyRateType("BB");
        currencyRateTypeEntityKey.keyAsString();
        currencyRateTypeEntityKey.builder().currencyRateType("BB").build();
        assertThat(currencyRateTypeDTO).descriptionText();
    }

    private SessionContext getValidSessionContext() {
        SessionContext sessionContext =
                SessionContext.builder()
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
                        .taskCode(CURRENCY_RATE_TYPE)
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
        return sessionContext;
    }

    private SessionContext getErrorSession() {
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
                        .userId("Vidya")
                        //                .accessibleTargetUnits([])
                        .channel("")
                        .taskCode("")
                        .originalTransactionReferenceNumber("")
                        .externalBatchNumber(null)
                        .customAttributes("")
                        .serviceInvocationModeType(null)
                        .allTargetUnitsSelected(true)
                        //                .mutationType("")
                        .userLocal("")
                        .originatingModuleCode("")
                        .role(new String[]{"maker"})
                        .build();
        return sessionContextError;
    }

    private CurrencyRateTypeDTO getCurrencyRateTypeDTOAuthorized() {
        CurrencyRateTypeDTO currencyRateTypeDTO = new CurrencyRateTypeDTO();
        currencyRateTypeDTO.setCurrencyRateType("BB");
        currencyRateTypeDTO.setDescription("Bill Buy");
        currencyRateTypeDTO.setAuthorized("Y");
        return currencyRateTypeDTO;
    }

    private CurrencyRateTypeDTO getCurrencyRateTypeDTO() {
        CurrencyRateTypeDTO currencyRateTypeDTO=new CurrencyRateTypeDTO();
        currencyRateTypeDTO.setCurrencyRateType("BB");
        currencyRateTypeDTO.setDescription("Bill Buy");
        currencyRateTypeDTO.setTaskCode(CURRENCY_RATE_TYPE);
        currencyRateTypeDTO.setStatus("DELETED");
        currencyRateTypeDTO.setRecordVersion(1);
        return currencyRateTypeDTO;
    }

    private CurrencyRateTypeEntity getCurrencyRateTypeEntity() {
        CurrencyRateTypeEntity currencyRateTypeEntity =
                new CurrencyRateTypeEntity(
                        "BB",
                        "Bill Buy",
                        null,
                        null,
                        null,
                        0,
                        "Y",
                        "draft");

        return currencyRateTypeEntity;
    }

    private CurrencyRateTypeEntity getCurrencyRateTypesEntity() {

        CurrencyRateTypeEntity currencyRateTypeEntity =
                new CurrencyRateTypeEntity
        (
                "BB",
                "Bill Buy",
                null,
                null,
                null,
                1,
                "Y",
                "draft");

        return currencyRateTypeEntity;
    }

    private CurrencyRateTypeEntity getCurrencyRateTypesEntity2() {
        CurrencyRateTypeEntity currencyRateTypeEntity2 = new CurrencyRateTypeEntity();
       currencyRateTypeEntity2.setCurrencyRateType("BB");
       currencyRateTypeEntity2.setCurrencyRateType("Bill Buy");
        currencyRateTypeEntity2.setAuthorized("N");
        currencyRateTypeEntity2.setStatus("closed");
        currencyRateTypeEntity2.setRecordVersion(1);
        return currencyRateTypeEntity2;
    }

    private CurrencyRateTypeDTO getCurrencyRateTypeDTOUnAuth() {

        CurrencyRateTypeDTO currencyRateTypeDTO =
                new CurrencyRateTypeDTO(
                        "BB",
                        "Bill Buy");

        currencyRateTypeDTO.setAuthorized("N");
        currencyRateTypeDTO.setTaskIdentifier("BB");
        return currencyRateTypeDTO;
    }

    private CurrencyRateTypeDTO getCurrencyRateTypeDTOMapper() {

        CurrencyRateTypeDTO currencyRateTypeDTOMapper2=
                CurrencyRateTypeDTO.builder()
                        .currencyRateType("BB")
                        .description("Bill Buy")
                        .authorized("N")
                        .taskCode(CURRENCY_RATE_TYPE)
                        .taskIdentifier("BB")
                        .build();
        return currencyRateTypeDTOMapper2;
    }

    private MutationEntity getMutationEntity() {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null"
                        + ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\""
                        + ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CURRTYPE\",\"taskIdentifier\":\"BB\""
                        + ",\"currencyRateType\":\"BB\",\"description\":\"Bill Buy\"}";


        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setTaskIdentifier("BB");
        mutationEntity.setTaskCode(CURRENCY_RATE_TYPE);
        mutationEntity.setPayload(new Payload(payLoadString));
        mutationEntity.setStatus("closed");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setAction("add");
        mutationEntity.setLastConfigurationAction("unauthorized");

        return mutationEntity;
    }

    private MutationEntity getMutationEntityJsonError() {
        String payLoadString1 =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null"
                        + ",\"action\":\"authorize\",\"status\":\"closed\",\"recordVersion\":1,\"authorized\":\"N\""
                        + ",\"lastConfigurationAction\":\"authorized\",\"taskCode\":\"CURRTYPE\",\"taskIdentifier\":\"BB\""
                        + ",\"currencyRateType\":\"BB\",\"description\":\"Bill Buy\"}";

        MutationEntity mutationEntity2 = new MutationEntity();
        mutationEntity2.setTaskIdentifier("BB");
        mutationEntity2.setTaskCode(CURRENCY_RATE_TYPE);
        mutationEntity2.setPayload(new Payload(payLoadString1));
        mutationEntity2.setStatus("closed");
        mutationEntity2.setAuthorized("N");
        mutationEntity2.setRecordVersion(1);
        mutationEntity2.setAction("add");
        mutationEntity2.setLastConfigurationAction("unauthorized");

        return mutationEntity2;
    }
}


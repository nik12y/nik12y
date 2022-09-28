package com.idg.idgcore.coe.app.service.audit;

import com.idg.idgcore.coe.domain.assembler.audit.AuditHistoryAssembler;
import com.idg.idgcore.coe.domain.entity.audit.AuditHistoryEntity;
import com.idg.idgcore.coe.domain.entity.mutation.Payload;
import com.idg.idgcore.coe.domain.service.audit.IAuditHistoryDomainService;
import com.idg.idgcore.coe.dto.audit.AuditHistoryDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.ServiceInvocationModeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.AUTHORIZED_Y;
import static com.idg.idgcore.coe.common.Constants.INACTIVE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class AuditHistoryApplicationServiceTest {
    @InjectMocks
    AuditHistoryApplicationService auditHistoryApplicationService;
    @Mock
    private IAuditHistoryDomainService auditHistoryDomainService;
@Mock
    AuditHistoryAssembler auditHistoryAssembler;
    private SessionContext sessionContext;
    private AuditHistoryDTO auditHistoryDTO;
    private AuditHistoryEntity auditHistoryEntity;
    private AuditHistoryEntity auditHistoryEntity1;

    @BeforeEach
    void setUp () {
        sessionContext = getValidSessionContext();
        auditHistoryDTO = getValidAuditHistoryDTO();
        auditHistoryEntity = getAuditHistoryEntity();
    }

    @Test
    void getAuditRecordByVersion () throws FatalException {
        given(auditHistoryDomainService.getAuditHistoryByRecordVersion(
                auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(),
                auditHistoryDTO.getRecordVersion(), AUTHORIZED_Y, INACTIVE))
                .willReturn(auditHistoryEntity);
        given(auditHistoryAssembler.setAuditFields(auditHistoryEntity))
                .willReturn(auditHistoryDTO);


        AuditHistoryDTO auditRecordByVersion = auditHistoryApplicationService.getAuditRecordByVersion(
                sessionContext, auditHistoryDTO);
        assertThat(auditRecordByVersion).isNotNull();
    }

    @Test
    void getAuditRecordByVersionException () throws FatalException {
        given(auditHistoryDomainService.getAuditHistoryByRecordVersion(
                auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(),
                auditHistoryDTO.getRecordVersion(), AUTHORIZED_Y, INACTIVE))
                .willReturn(auditHistoryEntity);
        given(auditHistoryAssembler.setAuditFields(auditHistoryEntity))
                .willReturn(auditHistoryDTO);


        AuditHistoryDTO auditRecordByVersion = auditHistoryApplicationService.getAuditRecordByVersion(
                sessionContext, auditHistoryDTO);
        assertThat(auditRecordByVersion).isNotNull();
    }

    @Test
    void getAuditHistory () throws FatalException {
        auditHistoryEntity1 = getAuditHistoryEntity();
        auditHistoryEntity1.setTaskIdentifier("NK");

        given(auditHistoryDomainService.getAuditHistory(
                auditHistoryDTO.getTaskCode(), auditHistoryDTO.getTaskIdentifier(), AUTHORIZED_Y, INACTIVE))
                .willReturn(List.of(auditHistoryEntity, auditHistoryEntity1));
        given(auditHistoryAssembler.setAuditFields(auditHistoryEntity))
                .willReturn(auditHistoryDTO);

        List<AuditHistoryDTO> auditHistoryList = auditHistoryApplicationService.getAuditHistory(
                sessionContext, auditHistoryDTO);
        assertThat(auditHistoryList.size()).isEqualTo(2);

        assertThat(auditHistoryList).isNotNull();
    }

    private SessionContext getValidSessionContext () {
        SessionContext sessionContext = SessionContext.builder().bankCode("").defaultBranchCode("")
                .internalTransactionReferenceNumber("").userTransactionReferenceNumber("")
                .externalTransactionReferenceNumber("").targetUnit("").postingDate(new Date())
                .valueDate(new Date()).transactionBranch("").userId("TestMaker").channel("")
                .taskCode("").originalTransactionReferenceNumber("").externalBatchNumber(1L)
                .customAttributes("").serviceInvocationModeType(ServiceInvocationModeType.Regular)
                .allTargetUnitsSelected(true).userLocal("").originatingModuleCode("")
                .role(new String[] { "maker" }).build();
        return sessionContext;
    }

    private AuditHistoryDTO getValidAuditHistoryDTO () {
        AuditHistoryDTO auditHistoryDTO = new AuditHistoryDTO().builder()
//                .taskCode("CITY")
                .taskIdentifier("MU")
                .recordVersion(1)
                .build();
        return auditHistoryDTO;
    }

    private AuditHistoryEntity getAuditHistoryEntity () {
        String payLoadString =
                getpayloadValidString();
        AuditHistoryEntity auditHistoryEntity = new AuditHistoryEntity();
        auditHistoryEntity.setTaskIdentifier("MU");
        auditHistoryEntity.setTaskCode("CITY");
        auditHistoryEntity.setPayload(new Payload(payLoadString));
        auditHistoryEntity.setStatus("inactive");
        auditHistoryEntity.setAuthorized("N");
        auditHistoryEntity.setRecordVersion(1);
        auditHistoryEntity.setAction("authorize");
        auditHistoryEntity.setLastConfigurationAction("unauthorized");
        return auditHistoryEntity;
    }

    private String getpayloadValidString () {
        String payLoadString =
                "{\"createdBy\":null,\"creationTime\":null,\"lastUpdatedBy\":null,\"lastUpdatedTime\":null,\""
                        + "action\":\"authorize\",\"status\":\"active\",\"recordVersion\":1,\"authorized\":\"N\",\"lastConfigurationAction\":\"authorized\",\"t"
                        + "askCode\":\"CITY\",\"taskIdentifier\":\"MU\",\"cityCode\":\"MU\",\"cityName\":\"MUMBAI\","
                        + "\"countryCode\":\"IN\",\"stateCode\":\"MH\"})";
        return payLoadString;
    }

}
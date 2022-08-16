package com.idg.idgcore.coe.domain.service.reason;

import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.repository.reason.IReasonRepository;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class ReasonDomainServiceTest {

    @Mock
    private IReasonRepository reasonRepository;

    @InjectMocks
    private ReasonDomainService reasonDomainService;
    private ReasonEntity reasonEntity;
    private ReasonDTO reasonDTO;

    @BeforeEach
    void setUp() {
        reasonDTO=getReasonDTO();
        reasonEntity=getReasonEntity();
    }

    @Test
    @DisplayName("Junit test for getReasons method ")
    void getReasonsReturnReasonList() {
        given(reasonRepository.findAll()).willReturn(List.of(reasonEntity));
        List<ReasonEntity> bankParameterEntityList = reasonDomainService.getReasons();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getReasons method for negative scenario")
    void getReasonsEmptyReasonEntityList()
    {
        given(reasonRepository.findAll()).willReturn(Collections.emptyList());
        List<ReasonEntity> reasonEntityList = reasonDomainService.getReasons();

        assertThat(reasonEntityList).isEmpty();
        assertThat(reasonEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getIbanByCodeReturnIbanEntityObject method")
    void getIbanByCodeReturnIbanEntityObject() {
        given(reasonRepository.findByPrimaryReasonCode("AB")).willReturn(reasonEntity);
        ReasonEntity reasonEntity1 =reasonDomainService.getReasonByCode(reasonEntity.getPrimaryReasonCode());
        assertThat(reasonEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getReasonByCode catch block method")
    void getReasonByCodeReturnCatchBlock() {
        ReasonEntity reasonEntity1=null;

        assertThrows(Exception.class,()-> {
            ReasonEntity reasonEntity2 = reasonDomainService.getReasonByCode(reasonEntity1.getPrimaryReasonCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(reasonRepository.findByPrimaryReasonCode("AB")).willReturn(reasonEntity);
        ReasonEntity reasonByCode = reasonDomainService.getConfigurationByCode(reasonDTO);
        assertThat(reasonByCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        ReasonDTO reasonDTO = null;
        assertThrows(BusinessException.class,()-> {
            ReasonEntity ReasonByCode = reasonDomainService.getConfigurationByCode(reasonDTO);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        ReasonDTO reasonDTO = null;
        assertThrows(Exception.class,()-> {
            reasonDomainService.save(reasonDTO);
        });
    }


    /*@Test
    void getConfigurationByCode () {
    }

    @Test
    void getIbans () {
    }

    @Test
    void getIbanByIbanCountryCode () {
    }

    @Test
    void save () {
    }
    */

    private ReasonEntity getReasonEntity()
    {
        ReasonEntity reasonEntity = new ReasonEntity();

        reasonEntity.setPrimaryReasonCode("AB");
        reasonEntity.setPrimaryReasonDesc("PTESTREASON");
        reasonEntity.setIsPriAccountBlock('Y');
        reasonEntity.setIsPriAccountUnblock('Y');
        reasonEntity.setIsPriAccountClosure('Y');
        reasonEntity.setIsPriRequestForAccClosure('Y');
        reasonEntity.setIsPriStopPayment('Y');
        reasonEntity.setIsPriStopPaymentRevoke('Y');
        reasonEntity.setSecondaryReasonCode("AB");
        reasonEntity.setSecondaryReasonDesc("PTESTREASON");
        reasonEntity.setIsSecAccountBlock('Y');
        reasonEntity.setIsSecAccountUnblock('Y');
        reasonEntity.setIsSecAccountClosure('Y');
        reasonEntity.setIsSecRequestForAccClosure('Y');
        reasonEntity.setIsSecStopPayment('Y');
        reasonEntity.setIsSecStopPaymentRevoke('Y');
        reasonEntity.setAuthorized("Y");
        reasonEntity.setApplicableCategories("TEST");
        reasonEntity.setDocumentReqIfAny("TEST");
        reasonEntity.setAuthorized("Y");
        return reasonEntity;
    }

    private ReasonDTO getReasonDTO()
    {
        ReasonDTO reasonDTO = new ReasonDTO();

        reasonDTO.setPrimaryReasonCode("AB");
        reasonDTO.setPrimaryReasonDescription("PTESTREASON");
        reasonDTO.setPrimaryAccountBlock(true);
        reasonDTO.setPrimaryAccountUnblock(true);
        reasonDTO.setPrimaryAccountClosure(true);
        reasonDTO.setPrimaryRequestForAccountClosure(true);
        reasonDTO.setPrimaryStopPayment(true);
        reasonDTO.setPrimaryStopPaymentRevoke(true);
        reasonDTO.setSecondaryReasonCode("AB");
        reasonDTO.setSecondaryReasonDescription("PTESTREASON");
        reasonDTO.setSecondaryAccountBlock(true);
        reasonDTO.setSecondaryAccountUnblock(true);
        reasonDTO.setSecondaryAccountClosure(true);
        reasonDTO.setSecondaryRequestForAccountClosure(true);
        reasonDTO.setSecondaryStopPayment(true);
        reasonDTO.setSecondaryStopPaymentRevoke(true);
        reasonDTO.setApplicableCategories("TEST");
        reasonDTO.setDocumentRequiredIfAny("TEST");

        reasonDTO.setAuthorized("Y");
        reasonDTO.setTaskCode("AB");
        reasonDTO.setStatus("DELETED");
        reasonDTO.setRecordVersion(1);

        return reasonDTO;
    }

}
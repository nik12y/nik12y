package com.idg.idgcore.coe.domain.service.capt;

import com.idg.idgcore.coe.domain.entity.capt.CaptEntity;
import com.idg.idgcore.coe.domain.repository.capt.ICaptRepository;
import com.idg.idgcore.coe.dto.capt.CaptDTO;
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
class CaptDomainServiceTest {

    @Mock
    private ICaptRepository captRepository;

    @InjectMocks
    private CaptDomainService captDomainService;
    private CaptEntity captEntity;
    private CaptDTO captDTO;

    @BeforeEach
    void setUp() {
        captDTO=getCaptDTO();
        captEntity=getCaptEntity();
    }

    @Test
    @DisplayName("Junit test for getCapts method ")
    void getCaptAllReturnCaptList() {
        given(captRepository.findAll()).willReturn(List.of(captEntity));
        List<CaptEntity> bankParameterEntityList = captDomainService.getCaptAll();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getCapts method for negative scenario")
    void getCaptAllEmptyCaptEntityList()
    {
        given(captRepository.findAll()).willReturn(Collections.emptyList());
        List<CaptEntity> captEntityList = captDomainService.getCaptAll();

        assertThat(captEntityList).isEmpty();
        assertThat(captEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getIbanByCodeReturnIbanEntityObject method")
    void getCaptByCodeReturnCaptEntityObject() {
        given(captRepository.findByClearingPaymentTypeCode("ABCD")).willReturn(captEntity);
        CaptEntity captEntity1 =captDomainService.getCaptByCode(captEntity.getClearingPaymentTypeCode());
        assertThat(captEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getCaptByCode catch block method")
    void getCaptByCodeReturnCatchBlock() {
        CaptEntity captEntity1=null;

        assertThrows(Exception.class,()-> {
            CaptEntity captEntity2 = captDomainService.getCaptByCode(captEntity1.getClearingPaymentTypeCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    void getConfigurationByCodeTryBlock() {
        given(captRepository.findByClearingPaymentTypeCode("ABCD")).willReturn(captEntity);
        CaptEntity captByCode = captDomainService.getConfigurationByCode(captDTO);
        assertThat(captByCode).isNotNull();
    }

/*    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getConfigurationByCodeCatchBlock() {
        CaptDTO captDTO = null;
        assertThrows(BusinessException.class,()-> {
            CaptEntity CaptByCode = captDomainService.getConfigurationByCode(captDTO);
        });
    }*/

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        CaptDTO captDTO = null;
        assertThrows(Exception.class,()-> {
            captDomainService.save(captDTO);
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

    private CaptEntity getCaptEntity()
    {
        CaptEntity captEntity = new CaptEntity();

        captEntity.setClearingPaymentTypeCode("ABCD");
        captEntity.setClearingPaymentTypeName("TESTCAPT");
        captEntity.setNetworkType("TESTCAPT");
        captEntity.setIsClearingPaymentCalender('Y');
        captEntity.setWeeklyOff1("SUNDAY");
        captEntity.setWeeklyOff2("MONDAY");
        captEntity.setWeeklyOff3("TUESDAY");
        captEntity.setAuthorized("Y");
        return captEntity;
    }

    private CaptDTO getCaptDTO()
    {
        CaptDTO captDTO = new CaptDTO();

        captDTO.setClearingPaymentTypeCode("ABCD");
        captDTO.setClearingPaymentTypeName("TESTCAPT");
        captDTO.setNetworkType("TESTCAPT");
        captDTO.setCompositeClearingOrPaymentCalendar(true);
        captDTO.setWeeklyOff1("SUNDAY");
        captDTO.setWeeklyOff2("MONDAY");
        captDTO.setWeeklyOff3("TUESDAY");

        captDTO.setAuthorized("Y");
        captDTO.setTaskCode("ABCD");
        captDTO.setStatus("DELETED");
        captDTO.setRecordVersion(1);

        return captDTO;
    }

}
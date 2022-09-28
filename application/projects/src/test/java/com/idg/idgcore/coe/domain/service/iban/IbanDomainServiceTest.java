package com.idg.idgcore.coe.domain.service.iban;

import com.idg.idgcore.coe.domain.entity.iban.IbanBbanEntity;
import com.idg.idgcore.coe.domain.entity.iban.IbanEntity;
import com.idg.idgcore.coe.domain.repository.iban.IIbanRepository;
import com.idg.idgcore.coe.dto.iban.IbanBbanDTO;
import com.idg.idgcore.coe.dto.iban.IbanDTO;
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
class IbanDomainServiceTest {

    @Mock
    private IIbanRepository ibanRepository;

    @InjectMocks
    private IbanDomainService ibanDomainService;
    private IbanEntity ibanEntity;
    private IbanDTO ibanDTO;

    @BeforeEach
    void setUp() {
        ibanDTO=getIbanDTO();
        ibanEntity=getIbanEntity();
    }

    @Test
    @DisplayName("Junit test for getIbans method ")
    void getIbansReturnIbansList() {
        given(ibanRepository.findAll()).willReturn(List.of(ibanEntity));
        List<IbanEntity> bankParameterEntityList = ibanDomainService.getAllEntities();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getIbans method for negative scenario")
    void getIbansEmptyIbanEntityList()
    {
        given(ibanRepository.findAll()).willReturn(Collections.emptyList());
        List<IbanEntity> ibanEntityList = ibanDomainService.getAllEntities();

        assertThat(ibanEntityList).isEmpty();
        assertThat(ibanEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getIbanByIbanCountryCode method")
    void getIbanByCodeReturnIbanEntityObject() {
        given(ibanRepository.findByIbanCountryCode("US")).willReturn(ibanEntity);
        IbanEntity ibanEntity1 =ibanDomainService.getEntityByIdentifier(ibanEntity.getIbanCountryCode());
        assertThat(ibanEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getIbanByIbanCountryCode catch block method")
    void getIbanByCodeReturnCatchBlock() {
        IbanEntity ibanEntity1=null;

        assertThrows(Exception.class,()-> {
            IbanEntity ibanEntity2 = ibanDomainService.getEntityByIdentifier(ibanEntity1.getIbanCountryCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        IbanDTO ibanDTO = null;
        assertThrows(Exception.class,()-> {
            ibanDomainService.save(ibanDTO);
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

    private IbanEntity getIbanEntity()
    {
        IbanEntity ibanEntity = new IbanEntity();
        IbanBbanEntity ibanBbanEntity = new IbanBbanEntity();

        ibanEntity.setIbanCountryCode("US");
        ibanEntity.setIbanCountryPosition(1);
        ibanEntity.setIbanCountryCodeLength(1);
        ibanEntity.setIbanCheckDigitPosition(1);
        ibanEntity.setIbanCheckDigitLength(1);
        ibanEntity.setIbanNationalIdLength(0);
        ibanEntity.setIbanTotalLength(20);

        ibanBbanEntity.setBankIdentifierPosition(1);
        ibanBbanEntity.setBankIdentifierLength(0);
        ibanBbanEntity.setBranchIdentifierPosition(1);
        ibanBbanEntity.setBankIdentifierLength(1);
        ibanBbanEntity.setAccountNumberPosition(5);
        ibanBbanEntity.setAccountNumberLength(4);

        ibanEntity.setIbanBbanEntity(ibanBbanEntity);
        ibanEntity.setAuthorized("Y");
        return ibanEntity;
    }

    private IbanDTO getIbanDTO()
    {
        IbanDTO ibanDTO = new IbanDTO();
        IbanBbanDTO ibanBbanDTO = new IbanBbanDTO();

        ibanDTO.setIbanCountryCode("US");
        ibanDTO.setIbanCountryPosition(1);
        ibanDTO.setIbanCountryCodeLength(1);
        ibanDTO.setIbanCheckDigitPosition(1);
        ibanDTO.setIbanCheckDigitLength(1);
        ibanDTO.setIbanNationalIdLength(0);
        ibanDTO.setIbanTotalLength(20);

        ibanBbanDTO.setBankIdentifierPosition(1);
        ibanBbanDTO.setBankIdentifierLength(0);
        ibanBbanDTO.setBranchIdentifierPosition(1);
        ibanBbanDTO.setBankIdentifierLength(1);
        ibanBbanDTO.setAccountNumberPosition(5);
        ibanBbanDTO.setAccountNumberLength(4);


        ibanDTO.setAuthorized("Y");
        ibanDTO.setTaskCode("US");
        ibanDTO.setStatus("DELETED");
        ibanDTO.setRecordVersion(1);
        ibanDTO.setIbanBban(ibanBbanDTO);

        return ibanDTO;
    }

}
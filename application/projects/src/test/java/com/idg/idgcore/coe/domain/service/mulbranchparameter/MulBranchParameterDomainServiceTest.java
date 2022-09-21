package com.idg.idgcore.coe.domain.service.mulbranchparameter;


import com.idg.idgcore.coe.domain.assembler.mulbranchparameter.MulBranchParameterAssembler;
import com.idg.idgcore.coe.domain.entity.mulbranchparameter.MulBranchParameterEntity;
import com.idg.idgcore.coe.domain.repository.mulbranchparameter.IMulBranchParameterRepository;
import com.idg.idgcore.coe.dto.mulbranchparameter.MulBranchParameterDTO;
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



@ExtendWith(MockitoExtension.class)
public class MulBranchParameterDomainServiceTest {

       @Mock
        private IMulBranchParameterRepository mulBranchParameterRepository;

        @Mock
        private MulBranchParameterAssembler mulBranchParameterAssembler;

        @InjectMocks
        private MulBranchParameterDomainService mulBranchParameterDomainService;
        private MulBranchParameterEntity mulBranchParameterEntity;
        private MulBranchParameterDTO mulBranchParameterDTO;

        @BeforeEach
        void setUp() {
            mulBranchParameterDTO = getMulBranchParameterDTO();
            mulBranchParameterEntity = getMulBranchParameterEntity();
        }

        @Test
        @DisplayName("Junit test for getMulBranchParametersReturnBranchParametersList method ")
        void getMulBranchParametersReturnBranchParametersList() {
            given(mulBranchParameterRepository.findAll()).willReturn(List.of(mulBranchParameterEntity));
            List<MulBranchParameterEntity> mulBranchParameterEntityList = mulBranchParameterDomainService.getMulBranchParameters();
            assertThat(mulBranchParameterEntityList).isNotNull();
            assertThat(mulBranchParameterEntityList.size()).isEqualTo(1);
        }

        @Test
        @DisplayName("JUnit test for getMulBranchParameters method for negative scenario")
        void getMulBranchParametersEmptyBankParameterEntityList() {
            given(mulBranchParameterRepository.findAll()).willReturn(Collections.emptyList());
            List<MulBranchParameterEntity> mulBranchParameterEntityList = mulBranchParameterDomainService.getMulBranchParameters();

            assertThat(mulBranchParameterEntityList).isEmpty();
            assertThat(mulBranchParameterEntityList.size()).isEqualTo(0);

        }


       @Test
        @DisplayName("JUnit test for getBankParameterByCurrencyCodeAndEntityCodeAndEntityType method")
        void getBankParameterByCurrencyandEntityCodeReturnBankParameterEntityObject() {
            given(mulBranchParameterRepository.getByCurrencyCodeAndEntityCodeAndEntityType("INR", "BR0021","Branch")).willReturn(mulBranchParameterEntity);
            MulBranchParameterEntity mulBranchParameterEntity1 = mulBranchParameterDomainService.getByCurrencyCodeAndEntityCodeAndEntityType(mulBranchParameterEntity.getCurrencyCode(), mulBranchParameterEntity.getEntityCode(),mulBranchParameterEntity.getEntityType());
            ;
            assertThat(mulBranchParameterEntity1).isNotNull();
        }


       @Test
        @DisplayName("JUnit test for getBranchParameterByCurrencyCodeAndEntityCodeAndEntityType catch block method")
        void getBankParameterByCurrencyCodeAndEntityCodeAndEntityTypeReturnCatchBlock() {
            MulBranchParameterEntity mulBranchParameterEntity1 = null;

            assertThrows(Exception.class, () -> {
                MulBranchParameterEntity mulBranchParameterEntity2 =
                        mulBranchParameterDomainService.getByCurrencyCodeAndEntityCodeAndEntityType(mulBranchParameterEntity1.getCurrencyCode(), mulBranchParameterEntity1.getEntityCode(),mulBranchParameterEntity1.getEntityType());
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode try block method")
        void getConfigurationByCodeTryBlock() {
            given(mulBranchParameterRepository.getByCurrencyCodeAndEntityCodeAndEntityType("INR", "BR0021","Branch")).willReturn(mulBranchParameterEntity);
            MulBranchParameterEntity branchParameterByCurrencyCodeAndEntityCode = mulBranchParameterDomainService.getConfigurationByCode(mulBranchParameterDTO);
            assertThat(branchParameterByCurrencyCodeAndEntityCode).isNotNull();
        }
    @Test
    @DisplayName("Junit test for Get BranchParameters method ")
    void getMulBranchParametersReturnMulBranchParametersList() {
        given(mulBranchParameterRepository.findAll()).willReturn(List.of(mulBranchParameterEntity));
        List<MulBranchParameterEntity> mulBranchParameterEntityList = mulBranchParameterDomainService.getMulBranchParameters();
        assertThat(mulBranchParameterEntityList).isNotNull();
        assertThat(mulBranchParameterEntityList.size()).isEqualTo(1);
    }


     //  @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getConfigurationByCodeCatchBlock() {
            MulBranchParameterDTO mulBranchParameterDTO = null;
            assertThrows(BusinessException.class, () -> {
                MulBranchParameterEntity branchParameterByCurrencyCodeAndEntityCodeAndEntityType = mulBranchParameterDomainService.getConfigurationByCode(mulBranchParameterDTO);
            });
        }


       //@Test
        @DisplayName("JUnit test for getSaveCode Catch Block method")
        void getSaveCodeCatchBlock() {
            MulBranchParameterDTO mulBranchParameterDTO = null;
            assertThrows(Exception.class, () -> {
                mulBranchParameterDomainService.save(mulBranchParameterDTO);
            });
        }


        private MulBranchParameterEntity getMulBranchParameterEntity() {
            MulBranchParameterEntity mulBranchParameterEntity = new MulBranchParameterEntity();
            mulBranchParameterEntity.setCurrencyCode("INR");
            mulBranchParameterEntity.setEntityCode("BR0021");
            mulBranchParameterEntity.setEntityType("Branch");
            mulBranchParameterEntity.setEntityLevel("Level4");
            mulBranchParameterEntity.setEntityName("Pune");
            mulBranchParameterEntity.setCurrencyName("Indian");
            mulBranchParameterEntity.setSpotDays(1);
            mulBranchParameterEntity.setGenerationOfPaymentMessage(23);
            mulBranchParameterEntity.setGenerationOfReceiveMessages(2);

            return mulBranchParameterEntity;
        }


        private MulBranchParameterDTO getMulBranchParameterDTO() {
            MulBranchParameterDTO mulBranchParameterDTO = new MulBranchParameterDTO();
            mulBranchParameterDTO.setCurrencyCode("INR");
            mulBranchParameterDTO.setEntityCode("BR0021");
            mulBranchParameterDTO.setEntityType("Branch");
            mulBranchParameterDTO.setEntityLevel("Level4");
            mulBranchParameterDTO.setEntityName("Nashik");
            mulBranchParameterDTO.setCurrencyName("Indian");
            mulBranchParameterDTO.setSpotDays(1);
            mulBranchParameterDTO.setGenerationOfPaymentMessage(11);
            mulBranchParameterDTO.setGenerationOfReceiveMessages(21);
            return mulBranchParameterDTO;
        }

    }





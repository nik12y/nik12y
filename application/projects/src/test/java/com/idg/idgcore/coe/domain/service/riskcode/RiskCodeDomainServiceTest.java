package com.idg.idgcore.coe.domain.service.riskcode;

import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.repository.riskcode.IRiskCodeRepository;
import com.idg.idgcore.coe.dto.riskcode.RiskCodeDTO;
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
class RiskCodeDomainServiceTest {
    @Mock
    private IRiskCodeRepository riskCodeRepository;

    @InjectMocks
    private RiskCodeDomainService riskCodeDomainService;
    private RiskCodeEntity riskCodeEntity;
    private RiskCodeDTO riskCodeDTO;

    @BeforeEach
    void setUp() {
        riskCodeDTO=getRiskCodeDTO ();
        riskCodeEntity=getRiskCodeEntity();
    }

    @Test
    @DisplayName("Junit test for getBranches method ")
    public void getRiskCodeReturnRiskCodeList() {
        given(riskCodeRepository.findAll()).willReturn(List.of(riskCodeEntity));
        List<RiskCodeEntity> riskCodeEntityList = riskCodeDomainService.getAllEntities();
        assertThat(riskCodeEntityList).isNotNull();
        assertThat(riskCodeEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getBranches method for negative scenario")
    public void getBranchesEmptyStateEntityList() {
        given(riskCodeRepository.findAll()).willReturn(Collections.emptyList());
        List<RiskCodeEntity> riskCodeEntityList = riskCodeDomainService.getAllEntities();
        assertThat(riskCodeEntityList).isEmpty();
        assertThat(riskCodeEntityList.size()).isEqualTo(0);
    }

    @Test
    @DisplayName("JUnit test for getStateById method")
    public void getBranchTypeByCodeReturnStateEntityObject() {
        given(riskCodeRepository.findByRiskCode("RISK003")).willReturn(riskCodeEntity);
        RiskCodeEntity riskCodeEntity1 =riskCodeDomainService.getEntityByIdentifier(riskCodeEntity.getRiskCode());
        assertThat(riskCodeEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getRiskCodeByCode catch block method")
    public void getRiskCodeByCodeReturnCatchBlock() {
        assertThrows(Exception.class,()-> {
            RiskCodeEntity riskCodeEntity2 = riskCodeDomainService.getEntityByIdentifier(null);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    public void getConfigurationByCodeTryBlock() {
        given(riskCodeRepository.findByRiskCode("RISK003")).willReturn(riskCodeEntity);
        RiskCodeEntity riskCodeByCode = riskCodeDomainService.getEntityByIdentifier("RISK003");
        assertThat(riskCodeByCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        assertThrows(BusinessException.class,()-> {
            riskCodeDomainService.getEntityByIdentifier(null);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        assertThrows(Exception.class, () -> {
            riskCodeDomainService.save(null);
        });
    }

    private RiskCodeEntity getRiskCodeEntity() {
        RiskCodeEntity riskCodeEntity=new RiskCodeEntity();
        riskCodeEntity.setRiskCode("RISK003");
        riskCodeEntity.setRiskCodeDescription("RiskCode003 Category");
        riskCodeEntity.setRiskCodeName("RiskCode Name 003");
        riskCodeEntity.setRiskMode("Internal");
        riskCodeEntity.setIsAllowDetailsModified('Y');
        return riskCodeEntity;
    }

    private RiskCodeDTO getRiskCodeDTO() {
        RiskCodeDTO riskCodeDTO=new RiskCodeDTO();
        riskCodeDTO.setRiskCode("RISK003");
        riskCodeDTO.setRiskCodeDescription("RiskCode Category 003 Description");
        riskCodeDTO.setRiskCodeName("RiskCodeCategory003");
        riskCodeDTO.setRiskMode("Internal");
        riskCodeDTO.setIsAllowDetailsModified(true);
        riskCodeDTO.setAuthorized("Y");
        return riskCodeDTO;
    }
}
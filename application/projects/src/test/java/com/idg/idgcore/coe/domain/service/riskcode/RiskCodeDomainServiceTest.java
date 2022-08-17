package com.idg.idgcore.coe.domain.service.riskcode;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.entity.riskcode.RiskCodeEntity;
import com.idg.idgcore.coe.domain.repository.branchtype.IBranchTypeRepository;
import com.idg.idgcore.coe.domain.repository.riskcode.IRiskCodeRepository;
import com.idg.idgcore.coe.domain.service.branchtype.BranchTypeDomainService;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
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
        riskCodeDTO=getStateDTO ();
        riskCodeEntity=getStateEntity();
    }

    @Test
    @DisplayName("Junit test for getBranches method ")
    public void getStatesReturnStatesList() {
        given(riskCodeRepository.findAll()).willReturn(List.of(riskCodeEntity));
        List<RiskCodeEntity> riskCodeEntityList = riskCodeDomainService.getRiskCodes();
        assertThat(riskCodeEntityList).isNotNull();
        assertThat(riskCodeEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getBranches method for negative scenario")
    public void getBranchesEmptyStateEntityList()
    {
        given(riskCodeRepository.findAll()).willReturn(Collections.emptyList());
        List<RiskCodeEntity> riskCodeEntityList = riskCodeDomainService.getRiskCodes();
        assertThat(riskCodeEntityList).isEmpty();
        assertThat(riskCodeEntityList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("JUnit test for getStateById method")
    public void getBranchTypeByCodeReturnStateEntityObject() {
        given(riskCodeRepository.findByRiskCode("MH")).willReturn(riskCodeEntity);
        RiskCodeEntity riskCodeEntity1 =riskCodeDomainService.getRiskCodeByCode(riskCodeEntity.getRiskCode());
        assertThat(riskCodeEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getStateById catch block method")
    public void getBranchTypeByCodeReturnCatchBolock() {
        RiskCodeEntity riskCodeEntity1=null;

        assertThrows(Exception.class,()-> {
            RiskCodeEntity riskCodeEntity2 = riskCodeDomainService.getRiskCodeByCode(riskCodeEntity1.getRiskCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    public void getConfigurationByCodeTryBlock() {
        given(riskCodeRepository.findByRiskCode("MH")).willReturn(riskCodeEntity);
       RiskCodeEntity riskCodeByCode = riskCodeDomainService.getConfigurationByCode(riskCodeDTO);
        assertThat(riskCodeByCode).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        RiskCodeDTO riskCodeDTO1 = null;
        assertThrows(BusinessException.class,()-> {
            RiskCodeEntity riskCodeByCode = riskCodeDomainService.getConfigurationByCode(riskCodeDTO1);
        });
    }

//    @Test
//    @DisplayName("Should throw an exception when the state code is invalid")
//    void getConfigurationByCodeWhenStateCodeIsInvalidThenThrowException() {
//        StateDTO stateDTO = null;
//        assertThrows(
//                BusinessException.class, () -> {
//                    stateDomainService.getConfigurationByCode(stateDTO);
//                });
//    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        RiskCodeDTO riskCodeDTO2 = null;
        assertThrows(Exception.class,()-> {
            riskCodeDomainService.save(riskCodeDTO2);
        });
    }


    private RiskCodeEntity getStateEntity()
    {
        RiskCodeEntity riskCodeEntity=new RiskCodeEntity();
        riskCodeEntity.setRiskCode("RISK003");
        riskCodeEntity.setRiskCodeDescription("RiskCode003");
        riskCodeEntity.setRiskCodeName("RiskCode003");
        riskCodeEntity.setRiskMode("Internal");
        riskCodeEntity.setIsAllowDetailsModified('Y');
        return riskCodeEntity;
    }

    private RiskCodeDTO getStateDTO()
    {
        RiskCodeDTO riskCodeDTO1=new RiskCodeDTO();
        riskCodeDTO1.setRiskCode("RISK003");
        riskCodeDTO1.setRiskCodeDescription("RiskCode003");
        riskCodeDTO1.setRiskCodeName("RiskCode003");
        riskCodeDTO1.setRiskMode("Internal");
        riskCodeDTO1.setIsAllowDetailsModified(true);
        riskCodeDTO1.setAuthorized("N");
        return riskCodeDTO1;
    }

}
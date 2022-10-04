package com.idg.idgcore.coe.domain.service.riskcategory;

import com.idg.idgcore.coe.domain.entity.riskcategory.RiskCategoryEntity;
import com.idg.idgcore.coe.domain.repository.riskcategory.IRiskCategoryRepository;
import com.idg.idgcore.coe.dto.riskcategory.RiskCategoryDTO;
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
public class RiskCategoryDomainServiceTest {

    @Mock
    private IRiskCategoryRepository iRiskCategoryRepository;

    @InjectMocks
    private RiskCategoryDomainService riskCategoryDomainService;

    private RiskCategoryEntity riskCategoryEntity;

    @BeforeEach
    void setUp() {
        riskCategoryEntity=getThisRiskCategoryEntity();
    }

    @Test
    @DisplayName("Junit test for getRiskCategories method ")
    public void getStatesReturnStatesList() {
        given(iRiskCategoryRepository.findAll()).willReturn(List.of(riskCategoryEntity));
        List<RiskCategoryEntity> riskCategoryEntityList = riskCategoryDomainService.getAllEntities();
        assertThat(riskCategoryEntityList).isNotNull();
        assertThat(riskCategoryEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getRiskCategories method for negative scenario")
    public void getBranchesEmptyStateEntityList() {
        given(iRiskCategoryRepository.findAll()).willReturn(Collections.emptyList());
        List<RiskCategoryEntity> riskCategoryEntityList = riskCategoryDomainService.getAllEntities();
        assertThat(riskCategoryEntityList).isEmpty();
        assertThat(riskCategoryEntityList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("JUnit test for getRiskCategoryById method")
    public void getRiskCategoryByCodeReturnStateEntityObject() {
        given(iRiskCategoryRepository.findByRiskCategoryCode("MH")).willReturn(riskCategoryEntity);
        RiskCategoryEntity riskCategoryEntity1 =riskCategoryDomainService.getEntityByIdentifier(riskCategoryEntity.getRiskCategoryCode());
        assertThat(riskCategoryEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getRiskCategoryById catch block method")
    public void getRiskcategoryByCodeReturnCatchBolock() {
        RiskCategoryEntity riskCategoryEntity1=null;

        assertThrows(Exception.class,()-> {
            RiskCategoryEntity riskcategoryEntity2 = riskCategoryDomainService.getEntityByIdentifier(riskCategoryEntity1.getRiskCategoryCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    public void getConfigurationByCodeTryBlock() {
        given(iRiskCategoryRepository.findByRiskCategoryCode("MH")).willReturn(riskCategoryEntity);
        RiskCategoryEntity riskCategoryByCode = riskCategoryDomainService.getEntityByIdentifier("MH");
        assertThat(riskCategoryByCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        assertThrows(BusinessException.class,()-> {
            RiskCategoryEntity riskCategoryByCode = riskCategoryDomainService.getEntityByIdentifier(null);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        RiskCategoryDTO riskCategoryDTO = null;
        assertThrows(Exception.class,()-> {
            riskCategoryDomainService.save(riskCategoryDTO);
        });
    }

    private RiskCategoryEntity getThisRiskCategoryEntity() {
        RiskCategoryEntity riskCategoryEntity=new RiskCategoryEntity();
        riskCategoryEntity.setRiskCategoryCode("MH");
        riskCategoryEntity.setRiskCategoryName("MAHARASHTRA");
        riskCategoryEntity.setRiskCategoryDescription("New Mumbai");

        return riskCategoryEntity;
    }

    private RiskCategoryDTO getThisRiskCategoryDTO() {
        RiskCategoryDTO riskCategoryDTO=new RiskCategoryDTO();
        riskCategoryDTO.setRiskCategoryCode("MH");
        riskCategoryDTO.setRiskCategoryName("MAHARASHTRA");
        riskCategoryDTO.setRiskCategoryDescription("Navi-Mumbai");

        return riskCategoryDTO;
    }

}

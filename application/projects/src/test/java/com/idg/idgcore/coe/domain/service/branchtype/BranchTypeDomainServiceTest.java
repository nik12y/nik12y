package com.idg.idgcore.coe.domain.service.branchtype;

import com.idg.idgcore.coe.domain.entity.branchtype.BranchTypeEntity;
import com.idg.idgcore.coe.domain.repository.branchtype.IBranchTypeRepository;
import com.idg.idgcore.coe.dto.branchtype.BranchTypeDTO;
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
class BranchTypeDomainServiceTest {
    @Mock
    private IBranchTypeRepository branchTypeRepository;

    @InjectMocks
    private BranchTypeDomainService branchTypeDomainService;

    private BranchTypeEntity branchTypeEntity;
    private BranchTypeDTO branchTypeDTO;


    @BeforeEach
    void setUp() {
        branchTypeDTO=getStateDTO ();
        branchTypeEntity=getStateEntity();
    }



    @Test
    @DisplayName("Junit test for getBranches method ")
    public void getStatesReturnStatesList() {
        given(branchTypeRepository.findAll()).willReturn(List.of(branchTypeEntity));
        List<BranchTypeEntity> branchTypeEntityList = branchTypeDomainService.getAllEntities();
        assertThat(branchTypeEntityList).isNotNull();
        assertThat(branchTypeEntityList.size()).isEqualTo(1);
    }


    @Test
    @DisplayName("JUnit test for getBranches method for negative scenario")
    public void getBranchesEmptyStateEntityList()
    {
        given(branchTypeRepository.findAll()).willReturn(Collections.emptyList());
        List<BranchTypeEntity> branchTypeEntityList = branchTypeDomainService.getAllEntities();
        assertThat(branchTypeEntityList).isEmpty();
        assertThat(branchTypeEntityList.size()).isEqualTo(0);

    }


    @Test
    @DisplayName("JUnit test for getStateById method")
    public void getBranchTypeByCodeReturnStateEntityObject() {
        given(branchTypeRepository.findByBranchTypeCode("MH")).willReturn(branchTypeEntity);
        BranchTypeEntity branchTypeEntity1 =branchTypeDomainService.getEntityByIdentifier(branchTypeEntity.getBranchTypeCode());
        assertThat(branchTypeEntity1).isNotNull();
    }


    @Test
    @DisplayName("JUnit test for getStateById catch block method")
    public void getBranchTypeByCodeReturnCatchBolock() {
        BranchTypeEntity branchTypeEntity1=null;

        assertThrows(Exception.class,()-> {
            BranchTypeEntity branchTypeEntity2 = branchTypeDomainService.getEntityByIdentifier(branchTypeEntity1.getBranchTypeCode());
        });
    }



//    @Test
//    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
//    public void getConfigurationByCodeCatchBlock() {
//        BranchTypeDTO branchTypeDTO = null;
//        assertThrows(BusinessException.class,()-> {
//            BranchTypeEntity branchTypeByCode = branchTypeDomainService.getConfigurationByBranchTypeCode(branchTypeDTO);
//        });
//    }

//    @Test
//    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
//    void getConfigurationByCodeCatchBlock() {
//        BranchTypeDTO branchTypeDTO = null;
//        assertThrows(BusinessException.class,()-> {
//            BranchTypeEntity branchTypeByCode = branchTypeDomainService.getConfigurationByBranchTypeCode(branchTypeDTO);
//        });
//    }
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
        BranchTypeDTO branchTypeDTO = null;
        assertThrows(Exception.class,()-> {
            branchTypeDomainService.save(branchTypeDTO);
        });
    }


    private BranchTypeEntity getStateEntity()
    {
        BranchTypeEntity branchTypeEntity=new BranchTypeEntity();
        branchTypeEntity.setBranchTypeCode("MH");
        branchTypeEntity.setBranchTypeName("MAHARASHTRA");

        return branchTypeEntity;
    }

    private BranchTypeDTO getStateDTO()
    {
        BranchTypeDTO branchTypeDTO=new BranchTypeDTO();
        branchTypeDTO.setBranchTypeCode("MH");
        branchTypeDTO.setBranchTypeName("MAHARASHTRA");

        return branchTypeDTO;
    }

}
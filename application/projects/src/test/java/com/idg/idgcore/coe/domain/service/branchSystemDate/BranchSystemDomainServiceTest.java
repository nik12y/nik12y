package com.idg.idgcore.coe.domain.service.branchSystemDate;

import com.idg.idgcore.coe.domain.entity.branchSystemDate.BranchSystemDateEntity;
import com.idg.idgcore.coe.domain.repository.branchSystemDate.IBranchSystemDateRepository;
import com.idg.idgcore.coe.dto.branchSystemDate.BranchSystemDateDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BranchSystemDomainServiceTest {

    @Mock
    private IBranchSystemDateRepository branchSystemRepository;
    @InjectMocks
    private BranchSystemDateDomainService branchSystemDateDomainService;
    private BranchSystemDateEntity branchSystemDateEntity;
    private BranchSystemDateDTO branchSystemDateDTO;

    @BeforeEach
    void setUp() {
        branchSystemDateDTO=getBranchSystemDTO ();
        branchSystemDateEntity=getBranchSystemEntity();
    }

    @Test
    @DisplayName("Junit test for getBranchSystemAll method ")
    public void getBranchSystemAllReturnBranchSystemAllList() {
        given(branchSystemRepository.findAll()).willReturn(List.of(branchSystemDateEntity));
        List<BranchSystemDateEntity> purgingEntityList = branchSystemDateDomainService.getAllEntities();
        assertThat(purgingEntityList).isNotNull();
        assertThat(purgingEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getBranchSystemAll method for negative scenario")
    public void getBranchSystemAllEmptyBranchSystemEntityList()
    {
        given(branchSystemRepository.findAll()).willReturn(Collections.emptyList());
        List<BranchSystemDateEntity> branchSystemDateEntityList = branchSystemDateDomainService.getAllEntities();
        assertThat(branchSystemDateEntityList).isEmpty();
        assertThat(branchSystemDateEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getBranchCodeById method")
    public void getBranchCodeByCodeReturnBranchSystemDateEntityObject() {
        given(branchSystemRepository.findByBranchCode("BC0002")).willReturn(branchSystemDateEntity);
        BranchSystemDateEntity branchSystemEntity1 = branchSystemDateDomainService.getEntityByIdentifier(branchSystemDateEntity.getBranchCode());
        assertThat(branchSystemEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getBranchCodeById catch block method")
    public void getBranchCodeByCodeReturnCatchBolock() {
        BranchSystemDateEntity branchSystemDateEntity1=null;

        assertThrows(Exception.class,()-> {
            BranchSystemDateEntity branchSystemDateEntity2 = branchSystemDateDomainService.getEntityByIdentifier(branchSystemDateEntity1.getBranchCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        assertThrows(Exception.class,()-> {
            BranchSystemDateEntity branchSystemDateEntity = branchSystemDateDomainService.getEntityByIdentifier(null);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        BranchSystemDateDTO branchSystemDTO = null;
        assertThrows(Exception.class,()-> {
            branchSystemDateDomainService.save(branchSystemDTO);
        });
    }


    private BranchSystemDateEntity getBranchSystemEntity()
    {
        BranchSystemDateEntity branchSystemEntity=new BranchSystemDateEntity();
        branchSystemEntity.setBranchCode("BC0002");
        branchSystemEntity.setCurrentWorkingDate(getDate("07-08-2022"));
        branchSystemEntity.setPreviousWorkingDate(getDate("05-08-2022"));
        branchSystemEntity.setNextWorkingDate(getDate("10-08-2022"));
        return branchSystemEntity;
    }

    private BranchSystemDateDTO getBranchSystemDTO()
    {
        BranchSystemDateDTO branchSystemDateDTO = new BranchSystemDateDTO();
        branchSystemDateDTO.setBranchCode("BC0002");
        branchSystemDateDTO.setCurrentWorkingDate("07-08-2022");
        branchSystemDateDTO.setPreviousWorkingDate("05-08-2022");
        branchSystemDateDTO.setNextWorkingDate("10-08-2022");
        return branchSystemDateDTO;
    }

    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}

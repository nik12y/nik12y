package com.idg.idgcore.coe.domain.service.purging;


import com.idg.idgcore.coe.domain.entity.purging.PurgingEntity;
import com.idg.idgcore.coe.domain.repository.purgingpolicy.IPurgingRepository;
import com.idg.idgcore.coe.domain.service.purgingpolicy.PurgingDomainService;
import com.idg.idgcore.coe.dto.purgingpolicy.PurgingDTO;
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
public class PurgingDomainServiceTest {

    @Mock
    private IPurgingRepository purgingRepository;
    @InjectMocks
    private PurgingDomainService purgingDomainService;
    private PurgingEntity purgingEntity;
    private PurgingDTO purgingDTO;

    @BeforeEach
    void setUp() {
        purgingDTO=getPurgingDTO ();
        purgingEntity=getPurgingEntity();
    }

    @Test
    @DisplayName("Junit test for getPurgingAll method ")
    public void getPurgingAllReturnPurgingAllList() {
        given(purgingRepository.findAll()).willReturn(List.of(purgingEntity));
        List<PurgingEntity> purgingEntityList = purgingDomainService.getAllEntities();
        assertThat(purgingEntityList).isNotNull();
        assertThat(purgingEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getPurgingAll method for negative scenario")
    public void getPurgingAllEmptyPurgingEntityList()
    {
        given(purgingRepository.findAll()).willReturn(Collections.emptyList());
        List<PurgingEntity> purgingEntityList = purgingDomainService.getAllEntities();
        assertThat(purgingEntityList).isEmpty();
        assertThat(purgingEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getPurgingById method")
    public void getPurgingByCodeReturnPurgingEntityObject() {
        given(purgingRepository.findByModuleCode("LN")).willReturn(purgingEntity);
        PurgingEntity purgingEntity1 = purgingDomainService.getEntityByIdentifier(purgingEntity.getModuleCode());
        assertThat(purgingEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getPurgingById catch block method")
    public void getPurgingByCodeReturnCatchBolock() {
        PurgingEntity purgingEntity1=null;

        assertThrows(Exception.class,()-> {
            PurgingEntity purgingEntity2 = purgingDomainService.getEntityByIdentifier(purgingEntity1.getModuleCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        assertThrows(Exception.class,()-> {
            purgingDomainService.save(null);
        });
    }


    private PurgingEntity getPurgingEntity()
    {
        PurgingEntity purgingEntity=new PurgingEntity();
        purgingEntity.setModuleCode("LN");
        purgingEntity.setTranMaintenanceStatus("closed");
        purgingEntity.setRetentionPeriod(5);
        return purgingEntity;
    }

    private PurgingDTO getPurgingDTO()
    {
        PurgingDTO purgingDTO = new PurgingDTO();
        purgingDTO.setModuleCode("LN");
        purgingDTO.setTranMaintenanceStatus("closed");
        purgingDTO.setRetentionPeriod(5);
        return purgingDTO;
    }

}

package com.idg.idgcore.coe.domain.service.mitigant;

import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.repository.mitigant.IMitigantRepository;
import com.idg.idgcore.coe.domain.repository.purpose.IPurposeRepository;
import com.idg.idgcore.coe.domain.service.purpose.PurposeDomainService;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
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
public class MitigantDomainServiceTest {

    @Mock
    private IMitigantRepository mitigantRepository;
    @InjectMocks
    private MitigantDomainService mitigantDomainService;
    private MitigantEntity mitigantEntity;
    private MitigantDTO mitigantDTO;

    @BeforeEach
    void setUp() {
        mitigantDTO=getMitigantDTO ();
        mitigantEntity=getMitigantEntity();
    }

    @Test
    @DisplayName("Junit test for getMitigantAll method ")
    public void getMitigantAllReturnPurposesList() {
        given(mitigantRepository.findAll()).willReturn(List.of(mitigantEntity));
        List<MitigantEntity> mitigantEntityList = mitigantDomainService.getMitigantAll();
        assertThat(mitigantEntityList).isNotNull();
        assertThat(mitigantEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getMitigantAll method for negative scenario")
    public void getMitigantAllEmptyMitigantEntityList()
    {
        given(mitigantRepository.findAll()).willReturn(Collections.emptyList());
        List<MitigantEntity> mitigantEntityList = mitigantDomainService.getMitigantAll();
        assertThat(mitigantEntityList).isEmpty();
        assertThat(mitigantEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getMitigantById method")
    public void getMitigantByCodeReturnMitigantEntityObject() {
        given(mitigantRepository.findByMitigantCode("CR0001")).willReturn(mitigantEntity);
        MitigantEntity mitigantEntity1 = mitigantDomainService.getMitigantByCode(mitigantEntity.getMitigantCode());
        assertThat(mitigantEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getMitigantById catch block method")
    public void getMitigantByCodeReturnCatchBlock() {
        MitigantEntity mitigantEntity1=null;

        assertThrows(Exception.class,()-> {
            MitigantEntity mitigantEntity2 = mitigantDomainService.getMitigantByCode(mitigantEntity1.getMitigantCode());
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode try block method")
    public void getConfigurationByCodeTryBlock() {
        given(mitigantRepository.findByMitigantCode("CR0001")).willReturn(mitigantEntity);
        MitigantEntity mitigantByCode = mitigantDomainService.getConfigurationByCode(mitigantDTO);
        assertThat(mitigantByCode).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        MitigantDTO mitigantDTO = null;
        assertThrows(Exception.class,()-> {
            MitigantEntity mitigantByCode = mitigantDomainService.getConfigurationByCode(mitigantDTO);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        MitigantDTO mitigantDTO = null;
        assertThrows(Exception.class,()-> {
            mitigantDomainService.save(mitigantDTO);
        });
    }

    private MitigantEntity getMitigantEntity()
    {
        MitigantEntity mitigantEntity=new MitigantEntity();
        mitigantEntity.setMitigantCode("CR0001");
        mitigantEntity.setMitigantCodeName("test1");
        mitigantEntity.setMitigantCodeDesc("test1");
        return mitigantEntity;
    }

    private MitigantDTO getMitigantDTO()
    {
        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");
        return mitigantDTO;
    }
}

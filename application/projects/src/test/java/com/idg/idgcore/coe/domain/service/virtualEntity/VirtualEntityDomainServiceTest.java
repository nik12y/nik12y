package com.idg.idgcore.coe.domain.service.virtualEntity;

import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.repository.virtualentity.IVirtualEntityRepository;
import com.idg.idgcore.coe.domain.service.virtualentity.VirtualEntityDomainService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
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
public class VirtualEntityDomainServiceTest {

    @Mock
    private IVirtualEntityRepository virtualEntityRepository;
    @InjectMocks
    private VirtualEntityDomainService virtualEntityDomainService;
    private VirtualEntity virtualEntity;
    private VirtualEntityDTO virtualEntityDTO;

    @BeforeEach
    void setUp() {
        virtualEntityDTO=getVirtualEntityDTO ();
        virtualEntity=getVirtualEntity();
    }

    @Test
    @DisplayName("Junit test for getVirtualEntityAll method ")
    public void getVirtualEntityAllReturnVirtualEntityAllList() {
        given(virtualEntityRepository.findAll()).willReturn(List.of(virtualEntity));
        List<VirtualEntity> virtualEntityList = virtualEntityDomainService.getVirtualEntityAll();
        assertThat(virtualEntityList).isNotNull();
        assertThat(virtualEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getVirtualEntityAll method for negative scenario")
    public void getVirtualEntityAllEmptyVirtualEntityList()
    {
        given(virtualEntityRepository.findAll()).willReturn(Collections.emptyList());
        List<VirtualEntity> virtualEntityList = virtualEntityDomainService.getVirtualEntityAll();
        assertThat(virtualEntityList).isEmpty();
        assertThat(virtualEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getVirtualEntityByEntityCode method")
    public void getVirtualEntityByEntityCodeReturnVirtualEntityObject() {
        given(virtualEntityRepository.findByEntityCode("MKE01")).willReturn(virtualEntity);
        VirtualEntity virtualEntity1 = virtualEntityDomainService.getByVirtualEntityCode(virtualEntity.getEntityCode());
        assertThat(virtualEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getVirtalEntityByEntityCode catch block method")
    public void getVirtalEntityByEntityCodeReturnCatchBolock() {
        VirtualEntity virtualEntity1=null;

        assertThrows(Exception.class,()-> {
            VirtualEntity virtualEntity2 = virtualEntityDomainService.getByVirtualEntityCode(virtualEntity1.getEntityCode());
        });
    }

//    @Test
//    @DisplayName("JUnit test for getConfigurationByCode try block method")
//    public void getConfigurationByCodeTryBlock() {
//        given(virtualEntityRepository.findByEntityCode("MKE01")).willReturn(virtualEntity);
//        VirtualEntity virtualEntityByCode = virtualEntityDomainService.getConfigurationByCode(virtualEntityDTO);
//        assertThat(virtualEntityByCode).isNotNull();
//    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getConfigurationByCodeCatchBlock() {
        VirtualEntityDTO virtualEntityDTO = null;
        assertThrows(Exception.class,()-> {
            VirtualEntity virtualEntityByCode = virtualEntityDomainService.getConfigurationByCode(virtualEntityDTO);
        });
    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    public void getSaveCodeCatchBlock() {
        VirtualEntityDTO virtualEntityDTO = null;
        assertThrows(Exception.class,()-> {
            virtualEntityDomainService.save(virtualEntityDTO);
        });
    }


    private VirtualEntity getVirtualEntity()
    {
        VirtualEntity virtualEntity=new VirtualEntity();
        virtualEntity.setEntityType("MKE");
        virtualEntity.setEntityCode("MKE01");
        virtualEntity.setEntityName("MKE01");
        virtualEntity.setParentEntityType("Legal Entity");
        virtualEntity.setParentEntityCode("LE01");
        virtualEntity.setIsDefault('Y');
        return virtualEntity;
    }

    private VirtualEntityDTO getVirtualEntityDTO()
    {
        VirtualEntityDTO virtualEntityDTO = new VirtualEntityDTO();
        virtualEntityDTO.setEntityType("MKE");
        virtualEntityDTO.setEntityCode("MKE01");
        virtualEntityDTO.setEntityName("MKE01");
        virtualEntityDTO.setParentEntityType("Legal Entity");
        virtualEntityDTO.setParentEntityCode("LE01");
        virtualEntityDTO.setIsDefault(true);
        virtualEntityDTO.setEffectiveDate("2022-08-05");
        return virtualEntityDTO;
    }
}

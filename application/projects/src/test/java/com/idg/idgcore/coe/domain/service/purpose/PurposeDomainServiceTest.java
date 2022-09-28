package com.idg.idgcore.coe.domain.service.purpose;

import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.repository.purpose.IPurposeRepository;
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
class PurposeDomainServiceTest {

    @Mock
    private IPurposeRepository purposeRepository;
    @InjectMocks
    private PurposeDomainService purposeDomainService;
    private PurposeEntity purposeEntity;
    private PurposeDTO purposeDTO;

    @BeforeEach
    void setUp() {
        purposeDTO=getPurposeDTO ();
        purposeEntity=getPurposeEntity();
    }

    @Test
    @DisplayName("Junit test for getPurposes method ")
    public void getPurposesReturnPurposesList() {
        given(purposeRepository.findAll()).willReturn(List.of(purposeEntity));
        List<PurposeEntity> purposeEntityList = purposeDomainService.getAllEntities();
        assertThat(purposeEntityList).isNotNull();
        assertThat(purposeEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getPurposes method for negative scenario")
    public void getPurposesEmptyPurposeEntityList()
    {
        given(purposeRepository.findAll()).willReturn(Collections.emptyList());
        List<PurposeEntity> purposeEntityList = purposeDomainService.getAllEntities();
        assertThat(purposeEntityList).isEmpty();
        assertThat(purposeEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getPurposeById method")
    public void getPurposeByCodeReturnPurposeEntityObject() {
        given(purposeRepository.findByPurposeCode("PC0006")).willReturn(purposeEntity);
        PurposeEntity purposeEntity1 = purposeDomainService.getEntityByIdentifier(purposeEntity.getPurposeCode());
        assertThat(purposeEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getStateById catch block method")
    public void getPurposeByCodeReturnCatchBolock() {
        PurposeEntity purposeEntity1=null;

        assertThrows(Exception.class,()-> {
            PurposeEntity purposeEntity2 = purposeDomainService.getEntityByIdentifier(purposeEntity1.getPurposeCode());
        });
    }

    private PurposeEntity getPurposeEntity()
    {
        PurposeEntity purposeEntity=new PurposeEntity();
        purposeEntity.setPurposeCode("PC0006");
        purposeEntity.setPurposeName("test6");
        purposeEntity.setPurposeType("test6");
        purposeEntity.setPurposeDescription("test6");
        return purposeEntity;
    }

    private PurposeDTO getPurposeDTO()
    {
        PurposeDTO purposeDTO = new PurposeDTO();
        purposeDTO.setPurposeCode("PC0006");
        purposeDTO.setPurposeName("test6");
        purposeDTO.setPurposeType("test6");
        purposeDTO.setPurposeDescription("test6");
        return purposeDTO;
    }

}
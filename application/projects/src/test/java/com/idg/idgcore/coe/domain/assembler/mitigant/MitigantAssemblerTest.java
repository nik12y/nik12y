package com.idg.idgcore.coe.domain.assembler.mitigant;

import com.idg.idgcore.coe.domain.entity.mitigant.MitigantEntity;
import com.idg.idgcore.coe.domain.entity.mitigant.MitigantRiskCodeEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.mitigant.MitigantDTO;
import com.idg.idgcore.coe.dto.mitigant.MitigantRiskCodeDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class MitigantAssemblerTest {

    @InjectMocks
    private MitigantAssembler mitigantAssembler;

    @Test
    @DisplayName("Should set the authorized field in mitigantDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        MitigantDTO mitigantDTO = MitigantDTO.builder().build();
        mitigantAssembler.setAuditFields(mutationEntity, mitigantDTO);
        assertEquals("Y", mitigantDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in mitigantDTO")
    void convertEntityToDTO(){
        MitigantEntity mitigantEntity=new MitigantEntity();
        mitigantEntity.setMitigantCode("CR0001");
        mitigantEntity.setMitigantCodeName("test1");
        mitigantEntity.setMitigantCodeDesc("test1");
        mitigantEntity.setIsAllowModification('Y');
        mitigantEntity.setIsActionable('Y');

        List<MitigantRiskCodeEntity> riskCodeEntity = new ArrayList<>();
        riskCodeEntity.add(new MitigantRiskCodeEntity(1,"RC0001", "market risk1"));
        mitigantEntity.setMitigantRiskCode(riskCodeEntity);
        mitigantEntity.setStatus("draft");
        mitigantEntity.setRecordVersion(0);
        MitigantDTO mitigantDTO=mitigantAssembler.toDTO(mitigantEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in mitigantDTO")
    void convertDTOToEntity(){
        MitigantDTO mitigantDTO = new MitigantDTO();
        mitigantDTO.setMitigantCode("CR0001");
        mitigantDTO.setMitigantCodeName("test1");
        mitigantDTO.setMitigantCodeDesc("test1");
        mitigantDTO.setIsAllowModification(true);
        mitigantDTO.setIsActionable(true);

        List<MitigantRiskCodeDTO> riskCodeDTO = new ArrayList<>();
        riskCodeDTO.add(new MitigantRiskCodeDTO("RC0001", "market risk1"));
        mitigantDTO.setMitigantRiskCode(riskCodeDTO);
        MitigantEntity mitigantEntity=mitigantAssembler.toEntity(mitigantDTO);
    }
}

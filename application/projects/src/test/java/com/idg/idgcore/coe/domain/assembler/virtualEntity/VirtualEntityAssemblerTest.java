package com.idg.idgcore.coe.domain.assembler.virtualEntity;

import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.purpose.PurposeEntity;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.dto.purpose.PurposeDTO;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class VirtualEntityAssemblerTest {

    @InjectMocks
    private VirtualEntityAssembler virtualEntityAssembler;

    @Test
    @DisplayName("Should set the authorized field in virtualEntityDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInStateDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        VirtualEntityDTO virtualEntityDTO = VirtualEntityDTO.builder().build();
        virtualEntityDTO = virtualEntityAssembler.setAuditFields(mutationEntity, virtualEntityDTO);
        assertEquals("Y", virtualEntityDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in virtualEntityDTO")
    void convertEntityToDto() {
        VirtualEntity virtualEntity=new VirtualEntity();
        virtualEntity.setEntityType("LE");
        virtualEntity.setEntityCode("LE01");
        virtualEntity.setEntityName("Legal Entity");
        virtualEntity.setParentEntityType("Group Banking Code");
        virtualEntity.setParentEntityCode("GBC");
        virtualEntity.setIsDefault('Y');
        virtualEntity.setEffectiveDate(getDate("2022-08-08"));
        VirtualEntityDTO virtualEntityDTO = virtualEntityAssembler.convertEntityToDto(virtualEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in virtualEntityDTO")
    void convertDtoToEntity() throws ParseException {
        VirtualEntityDTO virtualEntityDTO=new VirtualEntityDTO();
        virtualEntityDTO.setEntityType("LE");
        virtualEntityDTO.setEntityCode("LE01");
        virtualEntityDTO.setEntityName("Legal Entity");
        virtualEntityDTO.setParentEntityType("Group Banking Code");
        virtualEntityDTO.setParentEntityCode("GBC");
        virtualEntityDTO.setIsDefault(true);
        virtualEntityDTO.setEffectiveDate("2022-08-08");
        VirtualEntity virtualEntity=virtualEntityAssembler.convertDtoToEntity(virtualEntityDTO);
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
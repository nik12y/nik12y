package com.idg.idgcore.coe.domain.assembler.regulatoryRegion;

import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionMappingEntity;
import com.idg.idgcore.coe.dto.questionCategory.QuestionCategoryDetailsDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionMappingDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RegulatoryRegionAssemblerTest {

    @Autowired
    private ModelMapper modelMapper = new ModelMapper();
    @Autowired
    private RegulatoryRegionAssembler regulatoryRegionAssembler = new RegulatoryRegionAssembler();


    @Test
    void setAuditFields() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
       RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = new RegulatoryRegionConfigEntity();
        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntity = regulatoryRegionConfigEntity.getRegulatoryRegionMappingEntity();

        Type listType = new TypeToken<List<QuestionCategoryDetailsDTO>>() {
        }.getType();
        List<RegulatoryRegionMappingDTO> regulatoryRegionDTO = modelMapper.map(regulatoryRegionMappingEntity, listType);
        regulatoryRegionDTO.add(new RegulatoryRegionMappingDTO("REGC002"));

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTO.setRegulatoryRegionCode("REGC002");
        regulatoryRegionConfigDTO.setRegulatoryRegionName("India");
        regulatoryRegionConfigDTO.setRegulatoryRegionDescription("The India");
        regulatoryRegionConfigDTO.setRegionEffectiveDate("2022-08-20");
        regulatoryRegionConfigDTO.setRegulatoryRegionMapping(regulatoryRegionDTO);
        regulatoryRegionConfigDTO.setRegionGroupCode("Country");
        regulatoryRegionConfigDTO.setPurpose("Fees");

        regulatoryRegionAssembler.setAuditFields(mutationEntity, regulatoryRegionConfigDTO);
        assertEquals("Y", regulatoryRegionConfigDTO.getAuthorized());
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
    @Test
    @DisplayName("Junit test for Conversion of Entity To DTO")
    void convertDToToEntity() throws ParseException {
        RegulatoryRegionMappingEntity regulatoryRegionMappingEntity = new RegulatoryRegionMappingEntity(1,"IN","new",1,"N","unauthorized");


        RegulatoryRegionConfigEntity regulatoryRegionConfigEntity = new RegulatoryRegionConfigEntity();
      regulatoryRegionConfigEntity.setRegRegionCode("REGC002");
      regulatoryRegionConfigEntity.setRegionName("India");
      regulatoryRegionConfigEntity.setRegionDescription("The India");
      regulatoryRegionConfigEntity.setRegionEffectiveDate(getDate("2022-08-21"));
      regulatoryRegionConfigEntity.setRegulatoryRegionMappingEntity(List.of(regulatoryRegionMappingEntity));
      regulatoryRegionConfigEntity.setRegionGroupCode("Country");
      regulatoryRegionConfigEntity.setPurpose("Fees");
        regulatoryRegionConfigEntity.setStatus("new");
        regulatoryRegionConfigEntity.setAuthorized("N");
        regulatoryRegionConfigEntity.setRecordVersion(1);
        regulatoryRegionConfigEntity.setLastConfigurationAction("unauthorized");

        RegulatoryRegionMappingDTO regulatoryRegionMappingDTO = new RegulatoryRegionMappingDTO("In");

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO = new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTO.setRegulatoryRegionCode("REGC002");
        regulatoryRegionConfigDTO.setRegulatoryRegionName("India");
        regulatoryRegionConfigDTO.setRegulatoryRegionDescription("The India");
        regulatoryRegionConfigDTO.setRegionEffectiveDate("2022-08-20");
        regulatoryRegionConfigDTO.setRegulatoryRegionMapping(List.of(regulatoryRegionMappingDTO));
        regulatoryRegionConfigDTO.setRegionGroupCode("Country");
        regulatoryRegionConfigDTO.setPurpose("Fees");

        assertEquals(regulatoryRegionConfigEntity, regulatoryRegionAssembler.toEntity(regulatoryRegionConfigDTO));
    }

}

package com.idg.idgcore.coe.domain.assembler.holidaylist;

import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.holidaylist.HolidayDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
class HolidayListAssemblerTest {


    @InjectMocks
    private HolidayListAssembler holidayListAssembler;


    @Test
    @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInHolidayListDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        HolidayListDTO holidayListDTO = HolidayListDTO.builder().build();
        holidayListDTO = holidayListAssembler.setAuditFields(mutationEntity, holidayListDTO);
        assertEquals("Y", holidayListDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in HolidayListDTO")
    void convertEntityToDTO() {
        HolidayListEntity holidayListEntity = new HolidayListEntity();
        holidayListEntity.setHolidayListId("HOL01");
        holidayListEntity.setHolidayListName("Annual");
        holidayListEntity.setIsAdhocHolidays('Y');
        holidayListEntity.setHolidayListDescription("Regular");
        holidayListEntity.setEffectiveDate(new Date());
        List<HolidayEntity> holidayEntity = new ArrayList<>();
        holidayEntity.add(new HolidayEntity(1, "Holi", "Annual", new Date(),"Active",1,"Y","draft"));
        holidayListEntity.setHolidayEntity(holidayEntity);
        holidayListEntity.setStatus("draft");
        holidayListEntity.setRecordVersion(0);

        HolidayListDTO holidayListDTO = holidayListAssembler.convertEntityToDto(holidayListEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in appVerCategoryConfigDTO")
    void convertDtoToEntity() {
        HolidayListDTO holidayListDTO = new HolidayListDTO();
        holidayListDTO.setHolidayListId("HOL01");
        holidayListDTO.setHolidayListDescription("Annual");
        holidayListDTO.setAdhocHolidays(true);
        holidayListDTO.setEffectiveDate("2022-08-22");

        List<HolidayDTO> holidayDTO = new ArrayList<>();
        holidayDTO.add(new HolidayDTO(1,"Holi","Annual" , "2022-08-22"));
        holidayListDTO.setHoliday(holidayDTO);
        HolidayListEntity holidayListEntity = holidayListAssembler.convertDtoToEntity(holidayListDTO);

    }
}
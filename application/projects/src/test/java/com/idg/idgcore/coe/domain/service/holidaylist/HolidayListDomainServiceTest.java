package com.idg.idgcore.coe.domain.service.holidaylist;

import com.idg.idgcore.coe.domain.assembler.holidaylist.HolidayListAssembler;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.repository.holidaylist.IHolidayListRepository;
import com.idg.idgcore.coe.dto.holidaylist.HolidayDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.datatypes.exceptions.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class HolidayListDomainServiceTest {


        @Mock
        private IHolidayListRepository holidayListRepository;

        @InjectMocks
        private HolidayListDomainService holidayListDomainService;

        private HolidayListEntity holidayListEntity;
        private HolidayListAssembler holidayListAssembler;
        private HolidayListDTO holidayListDTO;

        @BeforeEach
        void setUp() {
            holidayListDTO=getHolidayListDTO();
            holidayListEntity=getHolidayListEntity();
        }

        @Test
        @DisplayName("Junit test for HolidayLists method ")
        void getHolidayListsReturnHolidayList() {
            given(holidayListRepository.findAll()).willReturn(List.of(holidayListEntity));
            List<HolidayListEntity> holidayListEntityList = holidayListDomainService.getHolidayLists();
            assertThat(holidayListEntityList).isNotNull();
            assertThat(holidayListEntityList.size()).isEqualTo(1);
        }


        @Test
        @DisplayName("JUnit test for HolidayLists method for negative scenario")
        void getHolidayListsEmptyHolidayEntityList()
        {
            given(holidayListRepository.findAll()).willReturn(Collections.emptyList());
            List<HolidayListEntity> holidayListEntityList = holidayListDomainService.getHolidayLists();
            assertThat(holidayListEntityList).isEmpty();
            assertThat(holidayListEntityList.size()).isEqualTo(0);

        }


        @Test
        @DisplayName("JUnit test for getHolidayListById method")
        void getHolidayListByIdReturnHolidayListEntityObject() {
            given(holidayListRepository.findByHolidayListId("HOL01")).willReturn(holidayListEntity);
            HolidayListEntity holidayListEntity1 =holidayListDomainService.getHolidayListById(holidayListEntity.getHolidayListId());
            assertThat(holidayListEntity1).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getHolidayListById catch block method")
        void getHolidayListByIdReturnCatchBolock() {
            HolidayListEntity holidayListEntity1=null;

            assertThrows(Exception.class,()-> {
                HolidayListEntity holidayListEntity2 = holidayListDomainService.getHolidayListById(holidayListEntity1.getHolidayListId());
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode try block method")
        void getConfigurationByCodeTryBlock() {
            given(holidayListRepository.findByHolidayListId("HOL01")).willReturn(holidayListEntity);
            HolidayListEntity holidayListEntity1 = holidayListDomainService.getConfigurationByCode(holidayListDTO);
            assertThat(holidayListEntity1).isNotNull();
        }


        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getConfigurationByCodeCatchBlock() {
            HolidayListDTO holidayListDTO = null;
            assertThrows(Exception.class,()-> {
                HolidayListEntity holidayListEntity1 = holidayListDomainService.getConfigurationByCode(holidayListDTO);
            });
        }

        @Test
        @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
        void getSaveCodeCatchBlock() {
            HolidayListDTO holidayListDTO = null;
            assertThrows(Exception.class,()-> {
                holidayListDomainService.save(holidayListDTO);
            });
        }


        private HolidayListEntity getHolidayListEntity()
        {
           HolidayListEntity holidayListEntity = new HolidayListEntity();
            holidayListEntity.setHolidayListId("HOL01");
            holidayListEntity.setHolidayListName("Annual");
            holidayListEntity.setHolidayListDescription("Regular");
            holidayListEntity.setIsAdhocHolidays('Y');
            holidayListEntity.setEffectiveDate(new Date());

            List<HolidayEntity> holidayEntity=new ArrayList<>();
            holidayEntity.add(new HolidayEntity(1,"Holi","annual",new Date(),"Active",1,"Y","draft"));
            holidayListEntity.setHolidayEntity(holidayEntity);
            holidayListEntity.setStatus("draft");
            holidayListEntity.setRecordVersion(0);
            return holidayListEntity;
        }

        private HolidayListDTO getHolidayListDTO()
        {
            HolidayListDTO holidayListDTO = new HolidayListDTO();
            holidayListDTO.setHolidayListId("HOL01");
            holidayListDTO.setHolidayListName("Annual");
            holidayListDTO.setHolidayListDescription("Regular");
            holidayListDTO.setAdhocHolidays(true);
            holidayListDTO.setEffectiveDate("2022-08-22");
            List<HolidayDTO> holidayDTO=new ArrayList<>();
            holidayDTO.add(new HolidayDTO(1,"Holi","Annual","2022-08-22"));
            holidayListDTO.setHoliday(holidayDTO);
            holidayListDTO.setStatus("DELETED");
            holidayListDTO.setRecordVersion(1);
            return holidayListDTO;
        }


    }
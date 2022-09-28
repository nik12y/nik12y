package com.idg.idgcore.coe.domain.assembler.holidaylist;

import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayEntity;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.holidaylist.HolidayDTO;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class HolidayListAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public HolidayListEntity convertDtoToEntity(HolidayListDTO holidayListDTO) {
        List<HolidayDTO> holidayDTO = holidayListDTO.getHoliday();
        List<HolidayEntity> holidayEntityList = new ArrayList<>();
        holidayEntityList.addAll(holidayDTO.stream().map(entity->{
            HolidayEntity holidayEntity1=null;
            holidayEntity1 = modelMapper.map(entity, HolidayEntity.class);
            holidayEntity1.setAuthorized(holidayListDTO.getAuthorized());
            holidayEntity1.setRecordVersion(holidayListDTO.getRecordVersion());
            holidayEntity1.setStatus(holidayListDTO.getStatus());
            holidayEntity1.setLastConfigurationAction(holidayListDTO.getLastConfigurationAction());
            return holidayEntity1;
        }).collect(Collectors.toList()));

        HolidayListEntity holidayListEntity = modelMapper.map(holidayListDTO, HolidayListEntity.class);
        holidayListEntity.setIsAdhocHolidays(getCharValueFromBoolean(holidayListDTO.getIsAdhocHolidays()));
        holidayListEntity.setHolidayEntity(holidayEntityList);
        return holidayListEntity;
    }

    public HolidayListDTO convertEntityToDto(HolidayListEntity holidayListEntity){
        List<HolidayDTO> holidayDTOList = new ArrayList<>();
        List<HolidayEntity> holidayEntity = holidayListEntity.getHolidayEntity();
        holidayDTOList.addAll(holidayEntity.stream().map(dto->{
            HolidayDTO holidayDTO=new HolidayDTO();
            holidayDTO=modelMapper.map(dto,HolidayDTO.class);

            return holidayDTO;
        }).collect(Collectors.toList()));

        HolidayListDTO holidayListDTO = modelMapper.map(holidayListEntity, HolidayListDTO.class);
        holidayListDTO.setAdhocHolidays(getBooleanValueFromChar(holidayListEntity.getIsAdhocHolidays()));

        holidayListDTO.setHoliday(holidayDTOList);
        return holidayListDTO;
    }


    public HolidayListDTO setAuditFields (MutationEntity mutationEntity, HolidayListDTO holidayListDTO) {
        holidayListDTO.setAction(mutationEntity.getAction());
        holidayListDTO.setAuthorized(mutationEntity.getAuthorized());
        holidayListDTO.setRecordVersion(mutationEntity.getRecordVersion());
        holidayListDTO.setStatus(mutationEntity.getStatus());
        holidayListDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        holidayListDTO.setCreatedBy(mutationEntity.getCreatedBy());
        holidayListDTO.setCreationTime(mutationEntity.getCreationTime());
        holidayListDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        holidayListDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        holidayListDTO.setTaskCode(mutationEntity.getTaskCode());
        holidayListDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return holidayListDTO;
    }

    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }

}

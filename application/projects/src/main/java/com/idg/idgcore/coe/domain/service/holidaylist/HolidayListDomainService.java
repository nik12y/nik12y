package com.idg.idgcore.coe.domain.service.holidaylist;

import com.idg.idgcore.coe.domain.assembler.holidaylist.HolidayListAssembler;
import com.idg.idgcore.coe.domain.entity.holidaylist.HolidayListEntity;
import com.idg.idgcore.coe.domain.repository.holidaylist.IHolidayListRepository;
import com.idg.idgcore.coe.dto.holidaylist.HolidayListDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;


@Slf4j
@Service
public class HolidayListDomainService implements IHolidayListDomainService {

    @Autowired
    private IHolidayListRepository holidayListRepository;

    @Autowired
    private HolidayListAssembler holidayListAssembler;

    @Override
    public HolidayListEntity getConfigurationByCode(HolidayListDTO holidayListDTO) {
        HolidayListEntity holidayListEntity = null;
        try {
            holidayListEntity = this.holidayListRepository.findByHolidayListId(holidayListDTO.getHolidayListId());
        }
        catch (Exception e){
            if (log.isErrorEnabled())
            {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return holidayListEntity;
    }

    @Override
    public List<HolidayListEntity> getHolidayLists() {
        return this.holidayListRepository.findAll();
    }

    @Override
    public HolidayListEntity getHolidayListById(String holidayListId) {
        HolidayListEntity holidayListEntity = null;
        try {
            holidayListEntity = this.holidayListRepository.findByHolidayListId(holidayListId);
        }
        catch (Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return holidayListEntity;
    }


    public void save(HolidayListDTO holidayListDTO) {
        try{
            HolidayListEntity holidayListEntity=holidayListAssembler.convertDtoToEntity(holidayListDTO);
                this.holidayListRepository.save(holidayListEntity);
    }
        catch(Exception e){
            if (log.isErrorEnabled()){
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }


    }


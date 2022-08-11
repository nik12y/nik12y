package com.idg.idgcore.coe.domain.service.zakat;

import com.idg.idgcore.coe.domain.assembler.zakat.ZakatAssembler;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.domain.repository.zakat.IZakatRepository;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ZakatDomainService implements IZakatDomainService{
    @Autowired
    private IZakatRepository zakatRepository;
    @Autowired
    private ZakatAssembler zakatAssembler;

    @Override
    public ZakatEntity getConfigurationByYear(ZakatDTO zakatDTO) {
        ZakatEntity zakatEntity = null;
        try {
            zakatEntity = this.zakatRepository.findByZakatYear(zakatDTO.getZakatYear());
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return zakatEntity;
    }

    @Override
    public List<ZakatEntity> getZakats() {
        return this.zakatRepository.findAll();
    }

    @Override
    public ZakatEntity getZakatByYear(Integer zakatYear) {
        ZakatEntity zakatEntity = null;
        try {
            zakatEntity = this.zakatRepository.findByZakatYear(zakatYear);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return zakatEntity;
    }

    @Override
    public void save(ZakatDTO zakatDTO) {
        try {
            ZakatEntity zakatEntity = zakatAssembler.convertDtoToEntity(zakatDTO);
            this.zakatRepository.save(zakatEntity);
        }
        catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error(e.getMessage());
            }
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

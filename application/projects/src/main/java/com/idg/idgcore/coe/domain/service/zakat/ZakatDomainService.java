package com.idg.idgcore.coe.domain.service.zakat;

import com.idg.idgcore.coe.domain.assembler.zakat.ZakatAssembler;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.domain.repository.zakat.IZakatRepository;
import com.idg.idgcore.coe.domain.service.generic.DomainService;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import com.idg.idgcore.coe.exception.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.idg.idgcore.coe.exception.Error.DATA_ACCESS_ERROR;

@Slf4j
@Service
public class ZakatDomainService extends DomainService<ZakatDTO, ZakatEntity> {
    @Autowired
    private IZakatRepository zakatRepository;
    @Autowired
    private ZakatAssembler zakatAssembler;

    @Override
    public ZakatEntity getEntityByIdentifier(String zakatYear) {
        ZakatEntity zakatEntity = null;
        try {
            zakatEntity = this.zakatRepository.findByZakatYear(Integer.parseInt(zakatYear));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
        return zakatEntity;
    }

    @Override
    public List<ZakatEntity> getAllEntities() {
        return this.zakatRepository.findAll();
    }

    @Override
    public void save(ZakatDTO zakatDTO) {
        try {
            ZakatEntity zakatEntity = zakatAssembler.toEntity(zakatDTO);
            this.zakatRepository.save(zakatEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            ExceptionUtil.handleException(DATA_ACCESS_ERROR);
        }
    }
}

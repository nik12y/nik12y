package com.idg.idgcore.coe.app.service.zakat;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.domain.assembler.zakat.ZakatAssembler;
import com.idg.idgcore.coe.domain.entity.zakat.ZakatEntity;
import com.idg.idgcore.coe.domain.service.zakat.ZakatDomainService;
import com.idg.idgcore.coe.dto.zakat.ZakatDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.ZAKAT;

@Slf4j
@Service("zakatApplicationService")
public class ZakatApplicationService extends GenericApplicationService<ZakatDTO, ZakatEntity, ZakatDomainService,
        ZakatAssembler> {

    protected ZakatApplicationService() {
        super(ZAKAT);
    }

    public String getTaskCode () {
        return ZakatDTO.builder().build().getTaskCode();
    }
}

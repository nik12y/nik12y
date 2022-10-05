package com.idg.idgcore.coe.app.service.virtualentity;

import com.idg.idgcore.coe.app.service.generic.GenericApplicationService;
import com.idg.idgcore.coe.app.service.virtualRelationship.VirtualEntityRelationshipApplicationService;
import com.idg.idgcore.coe.domain.assembler.virtualentity.VirtualEntityAssembler;
import com.idg.idgcore.coe.domain.entity.virtualentity.VirtualEntity;
import com.idg.idgcore.coe.domain.service.virtualentity.VirtualEntityDomainService;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.idg.idgcore.coe.common.Constants.VIRTUAL_ENTITY;

@Slf4j
@Service("virtualEntityApplicationService")
public class VirtualEntityApplicationService extends GenericApplicationService<VirtualEntityDTO, VirtualEntity,
        VirtualEntityDomainService, VirtualEntityAssembler> {

    @Autowired
    private VirtualEntityRelationshipApplicationService virtualEntityRelationshipApplicationService;
    protected VirtualEntityApplicationService() {
        super(VIRTUAL_ENTITY);
    }

    public String getTaskCode () {
        return VirtualEntityDTO.builder().build().getTaskCode();
    }

    @Override
    public TransactionStatus process(SessionContext sessionContext, VirtualEntityDTO valDTO) throws FatalException {
        try {
            virtualEntityRelationshipApplicationService.processVirtualEntityRelationship(valDTO);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return super.process(sessionContext, valDTO);
    }
}

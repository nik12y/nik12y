package com.idg.idgcore.coe.app.service.regulatoryRegion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.app.service.base.IBaseApplicationService;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.datatypes.core.TransactionStatus;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import com.idg.idgcore.dto.context.SessionContext;

import java.util.List;

public interface IRegulatoryRegionApplicationService  extends IBaseApplicationService {
    TransactionStatus processRegulatoryRegion (SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO)
            throws FatalException, JsonProcessingException;
    void save (RegulatoryRegionConfigDTO regulatoryRegionConfigDTO);
    RegulatoryRegionConfigDTO getRegulatoryRegionByCode (SessionContext sessionContext, RegulatoryRegionConfigDTO regulatoryRegionConfigDTO)
            throws FatalException, JsonProcessingException;
    List<RegulatoryRegionConfigDTO> getRegulatoryRegionCodes (SessionContext sessionContext) throws FatalException;

}

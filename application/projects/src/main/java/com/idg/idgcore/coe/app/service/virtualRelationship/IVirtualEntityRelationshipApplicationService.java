package com.idg.idgcore.coe.app.service.virtualRelationship;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.idg.idgcore.coe.dto.virtualentity.VirtualEntityDTO;
import com.idg.idgcore.datatypes.exceptions.FatalException;

import java.text.ParseException;

public interface IVirtualEntityRelationshipApplicationService {


    void processVirtualEntityRelationship(VirtualEntityDTO virtualEntityDTO)
            throws FatalException, JsonProcessingException, ParseException, org.json.simple.parser.ParseException;

}

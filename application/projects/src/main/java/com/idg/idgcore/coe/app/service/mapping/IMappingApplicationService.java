package com.idg.idgcore.coe.app.service.mapping;

import com.idg.idgcore.coe.dto.mapping.MappingDTO;
import java.util.List;

public interface IMappingApplicationService {
    List<MappingDTO> getMappings ();
    MappingDTO getMappingByCrietria (String action, String role, String status);

}

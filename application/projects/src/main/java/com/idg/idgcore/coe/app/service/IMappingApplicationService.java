package com.idg.idgcore.coe.app.service;

import com.idg.idgcore.coe.dto.MappingDTO;
import java.util.List;

public interface IMappingApplicationService {
    List<MappingDTO> getMappings ();
    MappingDTO getMappingByCrietria (String action, String role, String status);

}

package com.idg.idgcore.app.service;

import com.idg.idgcore.app.dto.MappingDTO;
import java.util.List;

public interface IMappingApplicationService {
    List<MappingDTO> getMappings ();
    MappingDTO getMappingByCrietria (String action, String role, String status);

}

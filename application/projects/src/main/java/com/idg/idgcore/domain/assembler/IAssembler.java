package com.idg.idgcore.domain.assembler;

import com.idg.idgcore.app.dto.*;
import com.idg.idgcore.domain.entity.*;


public interface IAssembler {

    CountryEntity convertDtoToEntity(CountryDTO countryDTO) ;
    CountryDTO convertEntityToDto(CountryEntity countryEntity);
}

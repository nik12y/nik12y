package com.idg.idgcore.coe.domain.assembler;

import com.idg.idgcore.coe.domain.entity.*;
import com.idg.idgcore.coe.dto.*;

public interface IAssembler {

    CountryEntity convertDtoToEntity(CountryDTO countryDTO) ;
    CountryDTO convertEntityToDto(CountryEntity countryEntity);
}

package com.idg.idgcore.coe.domain.assembler.country;

import com.idg.idgcore.coe.domain.entity.country.CountryEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.country.CountryDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class CountryAssemblerTest {


    @InjectMocks
    private CountryAssembler countryAssembler;


    @Test
    @DisplayName("Should set the authorized field in countryDTO")
    void setAuditFieldsShouldSetAuthorizedFieldInCountryDTO() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        CountryDTO countryDTO = CountryDTO.builder().build();
        countryAssembler.setAuditFields(mutationEntity, countryDTO);
        assertEquals("Y", countryDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should set the authorized field in stateDTO")
    void convertEntityToDTO(){
        CountryEntity countryEntity=new CountryEntity();
        countryEntity.setIsIban('N');
        countryEntity.setIsEuMember('N');
        countryEntity.setIsClearingNetwork('N');
        countryEntity.setIsMtGenerate('N');
        CountryDTO countryDTO=countryAssembler.toDTO(countryEntity);
    }

    @Test
    @DisplayName("Should set the authorized field in stateDTO")
    void convertDTOToEntity(){
        CountryDTO countryDTO=new CountryDTO();
        countryDTO.setIbanRequired(false);
        countryDTO.setEuMember(false);
        countryDTO.setDefaultClearingNetwork(false);
        countryDTO.setGenerateMt205(false);
        CountryEntity countryEntity=countryAssembler.toEntity(countryDTO);
    }
}
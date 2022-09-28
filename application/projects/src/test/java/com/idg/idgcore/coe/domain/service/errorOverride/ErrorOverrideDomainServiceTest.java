package com.idg.idgcore.coe.domain.service.errorOverride;

import com.idg.idgcore.coe.domain.assembler.errorOverride.ErrorOverrideAssembler;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideConversionsEntity;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideEntity;
import com.idg.idgcore.coe.domain.entity.errorOverride.ErrorOverrideLanguageDetailsEntity;
import com.idg.idgcore.coe.domain.repository.errorOverride.IErrorOverrideRepository;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideConversionsDTO;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideDTO;
import com.idg.idgcore.coe.dto.errorOverride.ErrorOverrideLanguageDetailsDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class ErrorOverrideDomainServiceTest {
    @InjectMocks
    private ErrorOverrideDomainService domainService;
    @Mock private ErrorOverrideAssembler assembler;
    @Mock private IErrorOverrideRepository repository;

    @Test
    void getConfigurationByCode () {
        ErrorOverrideDTO dto = getErrorOverrideDTONew();
        ErrorOverrideEntity errorOverrideEntity = getErrorOverrideEntityNew();
        given(repository.findByErrorCode("ERR-CHD-02")).willReturn(errorOverrideEntity);
        ErrorOverrideEntity configurationByCode = domainService.getEntityByIdentifier(dto.getTaskIdentifier());
        assertThat(configurationByCode).isNotNull();
    }

    @Test
    void getErrorCodes () {
        ErrorOverrideEntity errorOverrideEntity = getErrorOverrideEntityNew();
        given(repository.findAll()).willReturn(
                List.of(errorOverrideEntity));
        List<ErrorOverrideEntity> errorOverrideEntityList = domainService.getAllEntities();
        assertThat(errorOverrideEntityList).isNotNull();
    }

    private ErrorOverrideEntity getErrorOverrideEntityNew () {
        ErrorOverrideLanguageDetailsEntity detailEntity = new ErrorOverrideLanguageDetailsEntity();
        detailEntity.setLanguageCode("ENG");
        detailEntity.setLanguageName("English ");
        detailEntity.setLanguageCode("EN");
        detailEntity.setLanguageName("New Error Cod");
        ErrorOverrideConversionsEntity conversionsEntity = new ErrorOverrideConversionsEntity();
        conversionsEntity.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsEntity.setNewErrorCode("1");
        ErrorOverrideEntity entity = new ErrorOverrideEntity();
        entity.setErrorCode("ERR-CHD-02");
        entity.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        entity.setBranchCode("ALL");
        entity.setTypeOfMessage("Ignore");
        entity.setIsConfirmationRequired('Y');
        entity.setFunctionCode("Cash Deposit/Withdrawal");
        entity.setBatchType("Error");
        entity.setErrorOverrideConversionsEntity(conversionsEntity);
        entity.setErrorOverrideLanguageDetailsEntity(detailEntity);
        entity.setStatus("new");
        entity.setRecordVersion(1);
        return entity;
    }

    private ErrorOverrideDTO getErrorOverrideDTONew () {
        ErrorOverrideLanguageDetailsDTO detailDto = new ErrorOverrideLanguageDetailsDTO();
        detailDto.setLanguageCode("ENG");
        detailDto.setLanguageName("English ");
        detailDto.setLanguageCode("EN");
        detailDto.setLanguageName("New Error Cod");
        ErrorOverrideConversionsDTO conversionsDTO = new ErrorOverrideConversionsDTO();
        conversionsDTO.setFunctionCodeOverride("Cash Deposit/Withdrawal");
        conversionsDTO.setNewErrorCode("1");
        ErrorOverrideDTO dto = new ErrorOverrideDTO();
        dto.setErrorCode("ERR-CHD-02");
        dto.setErrorMessage("PAN input is mandatory for the cash transaction above INR 50K");
        dto.setBranchCode("ALL");
        dto.setTypeOfMessage("Ignore");
        dto.setIsConfirmationRequired(true);
        dto.setFunctionCode("Cash Deposit/Withdrawal");
        dto.setBatchType("Error");
        dto.setErrorOverrideConversions(conversionsDTO);
        dto.setErrorOverrideLanguageDetails(detailDto);
        dto.setStatus("new");
        dto.setTaskCode("ERROR-OVERRIDE");
        dto.setTaskIdentifier("ERR-CHD-01");
        dto.setRecordVersion(1);
        return dto;
    }

}
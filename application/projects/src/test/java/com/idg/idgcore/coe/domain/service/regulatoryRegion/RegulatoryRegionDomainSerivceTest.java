package com.idg.idgcore.coe.domain.service.regulatoryRegion;

import com.idg.idgcore.coe.app.service.regulatoryRegion.RegulatoryRegionApplicationService;
import com.idg.idgcore.coe.domain.assembler.regulatoryRegion.RegulatoryRegionAssembler;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionConfigEntity;
import com.idg.idgcore.coe.domain.entity.regulatoryRegion.RegulatoryRegionMappingEntity;
import com.idg.idgcore.coe.domain.repository.regulatoryRegion.IRegulatoryRegionRepository;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionConfigDTO;
import com.idg.idgcore.coe.dto.regulatoryRegion.RegulatoryRegionMappingDTO;
import com.idg.idgcore.dto.context.SessionContext;
import com.idg.idgcore.enumerations.core.MutationType;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static com.idg.idgcore.coe.common.Constants.REGULATORY_SERVICE;
import static com.idg.idgcore.enumerations.core.ServiceInvocationModeType.Regular;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegulatoryRegionDomainSerivceTest {

    @Mock
    private IRegulatoryRegionRepository iRegulatoryRegionRepository;

    @InjectMocks
    private RegulatoryRegionDomainService regulatoryRegionDomainService;

    @Mock
    private RegulatoryRegionAssembler regulatoryRegionAssembler;

    @InjectMocks
    private RegulatoryRegionApplicationService regulatoryRegionApplicationService;

    @Mock
    RegulatoryRegionConfigEntity regulatoryRegionConfigEntity;
    SessionContext sessionContext;
    RegulatoryRegionConfigDTO regulatoryRegionConfigDTOUnAuth;
    MutationEntity mutationEntity;


    @BeforeEach
    void setUp(){
        sessionContext= getValidSessionContext();
        regulatoryRegionConfigEntity=getRegulatoryRegionConfigEntityUnAuth();
        regulatoryRegionConfigDTOUnAuth =getRegulatoryRegionConfigDTOUnAuth();
        mutationEntity=getMutationEntity();
    }


    @Test
    @DisplayName("JUnit test for getConfigurationByCode")
    void getConfigurationByCodeReturnEntity(){
        List<RegulatoryRegionMappingEntity> regulatoryRegionMappingEntities = new ArrayList<>();
        regulatoryRegionMappingEntities.add(new RegulatoryRegionMappingEntity(1,"IN","add",
                1,
                "Y",
                "authorized"));

       RegulatoryRegionConfigEntity regulatoryRegionConfigEntity= new RegulatoryRegionConfigEntity("REGC002", "India",
                "The India", getDate("2022-08-21"), "Country",
                "Fees",regulatoryRegionMappingEntities,
                null,
                null,
                "add",
                1,
                "Y",
                "authorized");
        List<RegulatoryRegionMappingDTO> regulatoryRegionMappingDTOList = new ArrayList<>();
        regulatoryRegionMappingDTOList.add(new RegulatoryRegionMappingDTO("IN"));

        RegulatoryRegionConfigDTO regulatoryRegionConfigDTO= new RegulatoryRegionConfigDTO("REGC002","India","The India","2022-08-21","Country","Fees",
                regulatoryRegionMappingDTOList);
        given(iRegulatoryRegionRepository.findByRegRegionCode(regulatoryRegionConfigDTO.getRegulatoryRegionCode())).willReturn(regulatoryRegionConfigEntity);
        RegulatoryRegionConfigEntity questionCategoryEntity = regulatoryRegionDomainService.getConfigurationByCode(regulatoryRegionConfigDTO);
        assertThat(questionCategoryEntity).isNotNull();
    }

    @Test
    @DisplayName("Junit Test for getRegulatoryRegionByCode")
    void getRegulatoryRegionByCode(){

        given(iRegulatoryRegionRepository.findByRegRegionCode(regulatoryRegionConfigDTOUnAuth.getRegulatoryRegionCode())).willReturn(regulatoryRegionConfigEntity);
        RegulatoryRegionConfigEntity regulatoryRegionByCode = regulatoryRegionDomainService.getRegulatoryRegionByCode(regulatoryRegionConfigEntity.getRegRegionCode());
        assertThat(regulatoryRegionByCode).isNotNull();
    }

    @Test
    @DisplayName("Junit Test for getRegulatoryRegionCodes empty list")
    void getRegulatoryRegionCodes(){
        given(iRegulatoryRegionRepository.findAll()).willReturn(Collections.emptyList());
        List<RegulatoryRegionConfigEntity> regulatoryRegionCodes = regulatoryRegionDomainService.getRegulatoryRegionCodes();
        assertThat(regulatoryRegionCodes).isEmpty();
    }

    @Test
    @DisplayName("Junit test for getRegulatoryRegionCodes with list of Data")
    void getRegulatoryRegionCodesWithList(){

        given(iRegulatoryRegionRepository.findAll()).willReturn(List.of(regulatoryRegionConfigEntity));
        List<RegulatoryRegionConfigEntity> regulatoryRegionCodes = regulatoryRegionDomainService.getRegulatoryRegionCodes();
        assertThat(regulatoryRegionCodes).isNotNull().hasSize(1);
    }

    @Test
    @DisplayName("Junit test for save all QuestionCategories")
    void saveRegulatoryRegion() throws ParseException {

        given(regulatoryRegionAssembler.convertDtoToEntity(regulatoryRegionConfigDTOUnAuth)).willReturn(regulatoryRegionConfigEntity);
        when(iRegulatoryRegionRepository.save(any(RegulatoryRegionConfigEntity.class))).thenReturn(regulatoryRegionConfigEntity);
        regulatoryRegionDomainService.save(regulatoryRegionConfigDTOUnAuth);
        assertThat(regulatoryRegionConfigEntity).isNotNull();

    }

    private MutationEntity getMutationEntity(){
        MutationEntity mutationEntity=new MutationEntity();
        mutationEntity.setTaskCode(REGULATORY_SERVICE);
        mutationEntity.setTaskIdentifier("REGC002");
        mutationEntity.setLastConfigurationAction("unauthorized");
        mutationEntity.setAuthorized("N");
        mutationEntity.setRecordVersion(1);
        mutationEntity.setStatus("new");
        mutationEntity.setAction("add");
        return  mutationEntity;
    }
    private Date getDate (String s) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(s);
        }
        catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    @NotNull
    private RegulatoryRegionConfigEntity getRegulatoryRegionConfigEntityUnAuth(){
        RegulatoryRegionMappingEntity regulatoryRegionMappingEntity=new RegulatoryRegionMappingEntity();
        regulatoryRegionMappingEntity.setDemographicMappingCode("IN");
        RegulatoryRegionConfigEntity regulatoryRegionConfigEntityUnAuth=new RegulatoryRegionConfigEntity();
        regulatoryRegionConfigEntityUnAuth.setRegRegionCode("REGC002");
        regulatoryRegionConfigEntityUnAuth.setRegionName("India");
        regulatoryRegionConfigEntityUnAuth.setRegionDescription("The India");
        regulatoryRegionConfigEntityUnAuth.setRegionEffectiveDate(getDate("2022-08-21"));
        regulatoryRegionConfigEntityUnAuth.setRegionGroupCode("Country");
        regulatoryRegionConfigEntityUnAuth.setPurpose("Fees");
        regulatoryRegionConfigEntityUnAuth.setRegulatoryRegionMappingEntity(List.of(regulatoryRegionMappingEntity));
        regulatoryRegionConfigEntityUnAuth.setStatus("new");
        regulatoryRegionConfigEntityUnAuth.setAuthorized("N");
        regulatoryRegionConfigEntityUnAuth.setLastConfigurationAction("unauthorized");
        regulatoryRegionConfigEntityUnAuth.setRecordVersion(0);
        return regulatoryRegionConfigEntityUnAuth;
    }

    @NotNull
    private RegulatoryRegionConfigDTO getRegulatoryRegionConfigDTOUnAuth(){
        RegulatoryRegionMappingDTO regulatoryRegionMappingDTOAuth=new RegulatoryRegionMappingDTO();
        regulatoryRegionMappingDTOAuth.setDemographicMappingCode("UK");
        RegulatoryRegionConfigDTO regulatoryRegionConfigDTOUnAuth=new RegulatoryRegionConfigDTO();
        regulatoryRegionConfigDTOUnAuth.setRegulatoryRegionCode("REGC002");
        regulatoryRegionConfigDTOUnAuth.setRegulatoryRegionName("United Kingdom");
        regulatoryRegionConfigDTOUnAuth.setRegulatoryRegionDescription("The USA ()");
        regulatoryRegionConfigDTOUnAuth.setRegionEffectiveDate("2022-08-21");
        regulatoryRegionConfigDTOUnAuth.setRegionGroupCode("Country");
        regulatoryRegionConfigDTOUnAuth.setPurpose("Tax");
        regulatoryRegionConfigDTOUnAuth.setRegulatoryRegionMapping(List.of(regulatoryRegionMappingDTOAuth));
        regulatoryRegionConfigDTOUnAuth.setStatus("new");
        regulatoryRegionConfigDTOUnAuth.setAuthorized("N");
        regulatoryRegionConfigDTOUnAuth.setLastConfigurationAction("unauthorized");
        regulatoryRegionConfigDTOUnAuth.setRecordVersion(0);
        regulatoryRegionConfigDTOUnAuth.setAction("unauthorized");
        return regulatoryRegionConfigDTOUnAuth;
    }


    @NotNull
    private SessionContext getValidSessionContext(){
        SessionContext sessionContext=new SessionContext();
        sessionContext.setBankCode("");
        //   sessionContext.setAccessibleTargetUnits();
        sessionContext.setChannel("");                           sessionContext.setDefaultBranchCode("");
        sessionContext.setCustomAttributes("");                  sessionContext.setAllTargetUnitsSelected(false);
        // sessionContext.setExternalBatchNumber();
        sessionContext.setExternalTransactionReferenceNumber(""); sessionContext.setInternalTransactionReferenceNumber("");
        sessionContext.setLocalDateTime(new Date());              sessionContext.setMutationType(MutationType.ADDITION);
        //  sessionContext.setAccessibleTargetUnits("");
        sessionContext.setDefaultBranchCode("");                sessionContext.getOriginalTransactionReferenceNumber();
        sessionContext.setOriginatingModuleCode("");            sessionContext.setRole(new String[] {"maker"});
        sessionContext.setServiceInvocationModeType(Regular);   sessionContext.setPostingDate(new Date());
        sessionContext.setTargetUnit("");                       sessionContext.setTaskCode(REGULATORY_SERVICE);
        sessionContext.setTransactionBranch("");                sessionContext.setUserId("");
        sessionContext.setUserTransactionReferenceNumber("");   sessionContext.setValueDate(new Date());
        return  sessionContext;
    }
}

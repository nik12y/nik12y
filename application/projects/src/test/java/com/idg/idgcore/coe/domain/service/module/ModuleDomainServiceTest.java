package com.idg.idgcore.coe.domain.service.module;

import com.idg.idgcore.coe.domain.entity.module.ModuleEntity;
import com.idg.idgcore.coe.domain.repository.module.IModuleRepository;
import com.idg.idgcore.coe.dto.module.ModuleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

@ExtendWith (MockitoExtension.class)
class ModuleDomainServiceTest {

    @Mock
    private IModuleRepository moduleRepository;

    @InjectMocks
    private ModuleDomainService moduleDomainService;
    private ModuleEntity moduleEntity;
    private ModuleDTO moduleDTO;

    @BeforeEach
    void setUp() {
        moduleDTO=getModuleDTO();
        moduleEntity=getModuleEntity();
    }

    @Test
    @DisplayName("Junit test for getModules method ")
    void getModulesReturnModuleList() {
        given(moduleRepository.findAll()).willReturn(List.of(moduleEntity));
        List<ModuleEntity> bankParameterEntityList = moduleDomainService.getAllEntities();
        assertThat(bankParameterEntityList).isNotNull();
        assertThat(bankParameterEntityList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("JUnit test for getModules method for negative scenario")
    void getModulesEmptyModuleEntityList()
    {
        given(moduleRepository.findAll()).willReturn(Collections.emptyList());
        List<ModuleEntity> moduleEntityList = moduleDomainService.getAllEntities();

        assertThat(moduleEntityList).isEmpty();
        assertThat(moduleEntityList.size()).isEqualTo(0);

    }

    @Test
    @DisplayName("JUnit test for getIbanByCodeReturnIbanEntityObject method")
    void getIbanByCodeReturnIbanEntityObject() {
        given(moduleRepository.findByModuleCode("AB")).willReturn(moduleEntity);
        ModuleEntity moduleEntity1 =moduleDomainService.getEntityByIdentifier(moduleEntity.getModuleCode());
        assertThat(moduleEntity1).isNotNull();
    }

    @Test
    @DisplayName("JUnit test for getModuleByCode catch block method")
    void getModuleByCodeReturnCatchBlock() {
        ModuleEntity moduleEntity1=null;

        assertThrows(Exception.class,()-> {
            ModuleEntity moduleEntity2 = moduleDomainService.getEntityByIdentifier(moduleEntity1.getModuleCode());
        });
    }


//    @Test
//    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
//    void getConfigurationByCodeCatchBlock() {
//        ModuleDTO moduleDTO = null;
//        assertThrows(BusinessException.class,()-> {
//            ModuleEntity ModuleByCode = moduleDomainService.getConfigurationByCode(moduleDTO);
//        });
//    }

    @Test
    @DisplayName("JUnit test for getConfigurationByCode for Catch Block method")
    void getSaveCodeCatchBlock() {
        ModuleDTO moduleDTO = null;
        assertThrows(Exception.class,()-> {
            moduleDomainService.save(moduleDTO);
        });
    }


    /*@Test
    void getConfigurationByCode () {
    }

    @Test
    void getIbans () {
    }

    @Test
    void getIbanByIbanCountryCode () {
    }

    @Test
    void save () {
    }
    */

    private ModuleEntity getModuleEntity()
    {
        ModuleEntity moduleEntity = new ModuleEntity();

        moduleEntity.setModuleCode("AB");
        moduleEntity.setModuleName("TESTMODULE");
        moduleEntity.setBankCode("SBI");
        moduleEntity.setModuleUsers(12);
        moduleEntity.setModuleCurrentUser(12);
        moduleEntity.setIsLicensed('Y');
        moduleEntity.setIsPurgeAvailable('Y');
        moduleEntity.setIsUdf('Y');
        moduleEntity.setAuthorized("Y");
        return moduleEntity;
    }

    private ModuleDTO getModuleDTO()
    {
        ModuleDTO moduleDTO = new ModuleDTO();

        moduleDTO.setModuleCode("AB");
        moduleDTO.setModuleName("TESTMODULE");
        moduleDTO.setBankCode("SBI");
        moduleDTO.setModuleUsers(12);
        moduleDTO.setModuleCurrentUsers(12);
        moduleDTO.setLicensed(true);
        moduleDTO.setPurgeAvailable(true);
        moduleDTO.setUserDefinedFields(true);
        moduleDTO.setInstalled(true);

        moduleDTO.setAuthorized("Y");
        moduleDTO.setTaskCode("AB");
        moduleDTO.setStatus("DELETED");
        moduleDTO.setRecordVersion(1);

        return moduleDTO;
    }

}
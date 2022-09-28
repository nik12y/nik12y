package com.idg.idgcore.coe.domain.assembler.aml;

import com.idg.idgcore.coe.domain.entity.aml.AmlEntity;
import com.idg.idgcore.coe.domain.entity.aml.LimitEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.aml.AmlDTO;
import com.idg.idgcore.coe.dto.aml.LimitDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AmlAssemblerTest {

    @Test
    @DisplayName(
            "Should set the action field in amlDTO when the action field in mutationEntity is not null")
    void setAuditFieldsWhenActionIsNotNull() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAction("CREATE");
        AmlDTO amlDTO = AmlDTO.builder().build();

        AmlAssembler amlAssembler = new AmlAssembler();
        amlAssembler.setAuditFields(mutationEntity, amlDTO);

        assertEquals("CREATE", amlDTO.getAction());
    }

    @Test
    @DisplayName(
            "Should set the authorized field in amlDTO when the authorized field in mutationEntity is not null")
    void setAuditFieldsWhenAuthorizedIsNotNull() {
        MutationEntity mutationEntity = new MutationEntity();
        mutationEntity.setAuthorized("Y");
        AmlDTO amlDTO = AmlDTO.builder().build();

        AmlAssembler amlAssembler = new AmlAssembler();
        amlAssembler.setAuditFields(mutationEntity, amlDTO);

        assertEquals("Y", amlDTO.getAuthorized());
    }

    @Test
    @DisplayName("Should return dto when amlEntity is not null")
    void convertEntityToDtoWhenAmlEntityIsNotNullThenReturnResult() {
        AmlEntity amlEntity = new AmlEntity();

        amlEntity.setProductCategory("Lending");
        amlEntity.setProductDescription("Secured Loan");
        amlEntity.setIsProductType('Y');
        amlEntity.setProductLimitCurrency("INR");
        amlEntity.setExchangeRateCode("ER002");
        amlEntity.setExchangeRateType("Mid-Rate");
        amlEntity.setDebitCreditIndicator("Debit Indicator");
        amlEntity.setLimitEntity(new LimitEntity("LM01", 100000.00f, "INR"));
        amlEntity.setLifeCycleId(null);
        amlEntity.setReferenceNo(null);
        amlEntity.setRecordVersion(1);
        amlEntity.setStatus("authorize");
        amlEntity.setAuthorized("Y");
        amlEntity.setLastConfigurationAction("authorize");

        AmlAssembler amlAssembler = new AmlAssembler();

        AmlDTO amlDTO = amlAssembler.toDTO(amlEntity);

        assertNotNull(amlDTO);
        assertEquals(amlEntity.getProductCategory(), amlDTO.getProductCategory());
        assertEquals(amlEntity.getProductDescription(), amlDTO.getProductDescription());
        assertTrue(amlDTO.getIsProductType());
        assertEquals(amlEntity.getProductLimitCurrency(), amlDTO.getProductLimitCurrency());
        assertEquals(amlEntity.getExchangeRateCode(), amlDTO.getExchangeRateCode());
        assertEquals(amlEntity.getExchangeRateType(), amlDTO.getExchangeRateType());
        assertEquals(amlEntity.getDebitCreditIndicator(), amlDTO.getDebitCreditIndicator());

        LimitDTO limit = amlDTO.getLimit();

        assertNotNull(limit);

        assertEquals(amlEntity.getLimitEntity().getLimitCode(), limit.getLimitCode());
        assertEquals(amlEntity.getLimitEntity().getLimitAmount(), limit.getLimitAmount());
        assertEquals(amlEntity.getLimitEntity().getLimitCurrency(), limit.getLimitCurrency());
    }
}
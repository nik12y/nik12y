package com.idg.idgcore.coe.domain.assembler.reason;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import org.springframework.stereotype.Component;


@Component
public class ReasonAssembler extends Assembler<ReasonDTO, ReasonEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return ReasonDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return ReasonEntity.class;
    }

    @Override
    public ReasonEntity toEntity(ReasonDTO reasonDTO) {
        ReasonEntity reasonEntity = super.toEntity(reasonDTO);
        reasonEntity.setPrimaryReasonCode(reasonDTO.getPrimaryReasonCode());
        reasonEntity.setPrimaryReasonDesc(reasonDTO.getPrimaryReasonDescription());
        reasonEntity.setIsPriAccountBlock(getCharValueFromBoolean(reasonDTO.isPrimaryAccountBlock()));
        reasonEntity.setIsPriAccountUnblock(getCharValueFromBoolean(reasonDTO.isPrimaryAccountUnblock()));
        reasonEntity.setIsPriAccountClosure(getCharValueFromBoolean(reasonDTO.isPrimaryAccountClosure()));
        reasonEntity.setIsPriRequestForAccClosure(getCharValueFromBoolean(reasonDTO.isPrimaryRequestForAccountClosure()));
        reasonEntity.setIsPriStopPayment(getCharValueFromBoolean(reasonDTO.isPrimaryStopPayment()));
        reasonEntity.setIsPriStopPaymentRevoke(getCharValueFromBoolean(reasonDTO.isPrimaryStopPaymentRevoke()));
        reasonEntity.setSecondaryReasonCode(reasonDTO.getSecondaryReasonCode());
        reasonEntity.setSecondaryReasonDesc(reasonDTO.getSecondaryReasonDescription());
        reasonEntity.setIsSecAccountBlock(getCharValueFromBoolean(reasonDTO.isSecondaryAccountBlock()));
        reasonEntity.setIsSecAccountUnblock(getCharValueFromBoolean(reasonDTO.isSecondaryAccountUnblock()));
        reasonEntity.setIsSecAccountClosure(getCharValueFromBoolean(reasonDTO.isSecondaryAccountClosure()));
        reasonEntity.setIsSecRequestForAccClosure(getCharValueFromBoolean(reasonDTO.isSecondaryRequestForAccountClosure()));
        reasonEntity.setIsSecStopPayment(getCharValueFromBoolean(reasonDTO.isSecondaryStopPayment()));
        reasonEntity.setIsSecStopPaymentRevoke(getCharValueFromBoolean(reasonDTO.isSecondaryStopPaymentRevoke()));
        reasonEntity.setApplicableCategories(reasonDTO.getApplicableCategories());
        reasonEntity.setDocumentReqIfAny(reasonDTO.getDocumentRequiredIfAny());
        return reasonEntity;
    }

    @Override
    public ReasonDTO toDTO(ReasonEntity reasonEntity) {
        ReasonDTO reasonDTO = super.toDTO(reasonEntity);
        reasonDTO.setPrimaryReasonCode(reasonEntity.getPrimaryReasonCode());
        reasonDTO.setPrimaryReasonDescription(reasonEntity.getPrimaryReasonDesc());
        reasonDTO.setPrimaryAccountBlock(getBooleanValueFromChar(reasonEntity.getIsPriAccountBlock()));
        reasonDTO.setPrimaryAccountUnblock(getBooleanValueFromChar(reasonEntity.getIsPriAccountUnblock()));
        reasonDTO.setPrimaryAccountClosure(getBooleanValueFromChar(reasonEntity.getIsPriAccountClosure()));
        reasonDTO.setPrimaryRequestForAccountClosure(getBooleanValueFromChar(reasonEntity.getIsPriRequestForAccClosure()));
        reasonDTO.setPrimaryStopPayment(getBooleanValueFromChar(reasonEntity.getIsPriStopPayment()));
        reasonDTO.setPrimaryStopPaymentRevoke(getBooleanValueFromChar(reasonEntity.getIsPriStopPaymentRevoke()));
        reasonDTO.setSecondaryReasonCode(reasonEntity.getSecondaryReasonCode());
        reasonDTO.setSecondaryReasonDescription(reasonEntity.getSecondaryReasonDesc());
        reasonDTO.setSecondaryAccountBlock(getBooleanValueFromChar(reasonEntity.getIsSecAccountBlock()));
        reasonDTO.setSecondaryAccountUnblock(getBooleanValueFromChar(reasonEntity.getIsSecAccountUnblock()));
        reasonDTO.setSecondaryAccountClosure(getBooleanValueFromChar(reasonEntity.getIsSecAccountClosure()));
        reasonDTO.setSecondaryRequestForAccountClosure(getBooleanValueFromChar(reasonEntity.getIsSecRequestForAccClosure()));
        reasonDTO.setSecondaryStopPayment(getBooleanValueFromChar(reasonEntity.getIsSecStopPayment()));
        reasonDTO.setSecondaryStopPaymentRevoke(getBooleanValueFromChar(reasonEntity.getIsSecStopPaymentRevoke()));
        reasonDTO.setApplicableCategories(reasonEntity.getApplicableCategories());
        reasonDTO.setDocumentRequiredIfAny(reasonEntity.getDocumentReqIfAny());
        return reasonDTO;
    }

}

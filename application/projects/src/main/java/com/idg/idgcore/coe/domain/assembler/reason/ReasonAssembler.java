package com.idg.idgcore.coe.domain.assembler.reason;

import com.idg.idgcore.coe.domain.entity.reason.ReasonEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.reason.ReasonDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;


@Component
public class ReasonAssembler {

    private ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig() {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public ReasonEntity convertDtoToEntity(ReasonDTO reasonDTO) {
        ReasonEntity reasonEntity = modelMapper.map(reasonDTO, ReasonEntity.class);
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

    public ReasonDTO convertEntityToDto(ReasonEntity reasonEntity) {
        ReasonDTO reasonDTO = modelMapper.map(reasonEntity, ReasonDTO.class);
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

    public ReasonDTO setAuditFields (MutationEntity mutationEntity, ReasonDTO reasonDTO) {
        reasonDTO.setAction(mutationEntity.getAction());
        reasonDTO.setAuthorized(mutationEntity.getAuthorized());
        reasonDTO.setRecordVersion(mutationEntity.getRecordVersion());
        reasonDTO.setStatus(mutationEntity.getStatus());
        reasonDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        reasonDTO.setCreatedBy(mutationEntity.getCreatedBy());
        reasonDTO.setCreationTime(mutationEntity.getCreationTime());
        reasonDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        reasonDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        reasonDTO.setTaskCode(mutationEntity.getTaskCode());
        reasonDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return reasonDTO;
    }


    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);
    }
}

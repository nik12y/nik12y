package com.idg.idgcore.coe.domain.assembler.transaction;

import com.idg.idgcore.coe.domain.entity.transaction.TransactionEntity;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.transaction.TransactionDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class TransactionAssembler {
    private final ModelMapper modelMapper = new ModelMapper();

    @PostConstruct
    private void setMapperConfig () {
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
    }

    public TransactionEntity convertDtoToEntity (TransactionDTO transactionDTO) {
        TransactionEntity transactionEntity = modelMapper.map(transactionDTO, TransactionEntity.class);
        transactionEntity.setTransactionCode(transactionDTO.getTransactionCode());
        transactionEntity.setTransactionDesc(transactionDTO.getTransactionDescription());
        transactionEntity.setTransactionSwiftCode(transactionDTO.getTransactionSwiftCode());
        transactionEntity.setMisHead(transactionDTO.getMisHead());
        transactionEntity.setIsImmediate(getCharValueFromBoolean(transactionDTO.getImmediate()));
        transactionEntity.setIsOnValueDate(getCharValueFromBoolean(transactionDTO.getOnValueDate()));
        transactionEntity.setIsAfterDaysValueDate(getCharValueFromBoolean(transactionDTO.getAfterXDaysValueDate()));
        transactionEntity.setIsAfterDays(getCharValueFromBoolean(transactionDTO.getAfterXDays()));
        transactionEntity.setNoOfDays(transactionDTO.getNoOfDays());
        transactionEntity.setIsIntradayManualRel(getCharValueFromBoolean(transactionDTO.getIntradayManualRelease()));
        transactionEntity.setStatementDateDerivation(transactionDTO.getStatementDateDerivation());
        transactionEntity.setIsAmlTracking(getCharValueFromBoolean(transactionDTO.getAmlTracking()));
        transactionEntity.setProductGroup(transactionDTO.getProductGroup());
        transactionEntity.setIsBalChkAppliBatch(getCharValueFromBoolean(transactionDTO.getAvailableBalanceCheckApplicableBatch()));
        transactionEntity.setIsBalChkAppliOnline(getCharValueFromBoolean(transactionDTO.getAvailableBalanceCheckApplicableOnline()));
        transactionEntity.setIsPricingOnTranCount(getCharValueFromBoolean(transactionDTO.getPricingOnTransactionCount()));
        transactionEntity.setIsPricingTurnoverIncl(getCharValueFromBoolean(transactionDTO.getPricingTurnoverInclusion()));
        transactionEntity.setIsPricingBalanceIncl(getCharValueFromBoolean(transactionDTO.getPricingBalanceInclusive()));
        transactionEntity.setIsTurnoverLimit(getCharValueFromBoolean(transactionDTO.getTurnoverLimit()));
        transactionEntity.setIsCoverAccount(getCharValueFromBoolean(transactionDTO.getCoverAccount()));
        transactionEntity.setIsPricingPenaltyIncl(getCharValueFromBoolean(transactionDTO.getPricingPenaltyInclusive()));
        transactionEntity.setIsConsiderAcStatusChg(getCharValueFromBoolean(transactionDTO.getConsiderForAccountStatusChange()));
        transactionEntity.setIsChequeMandatory(getCharValueFromBoolean(transactionDTO.getChequeMandatory()));
        transactionEntity.setIsInterBranchInLcy(getCharValueFromBoolean(transactionDTO.getInterBranchInLcy()));
        transactionEntity.setIsThirdPartyTranCode(getCharValueFromBoolean(transactionDTO.getThirdPartyDealingSystemTransactionCode()));
        return transactionEntity;
    }

    public TransactionDTO convertEntityToDto (TransactionEntity transactionEntity) {
        TransactionDTO transactionDTO = modelMapper.map(transactionEntity, TransactionDTO.class);
        transactionDTO.setTransactionCode(transactionEntity.getTransactionCode());
        transactionDTO.setTransactionDescription(transactionEntity.getTransactionDesc());
        transactionDTO.setTransactionSwiftCode(transactionEntity.getTransactionSwiftCode());
        transactionDTO.setExternalTxnCode(transactionEntity.getExternalTxnCode());
        transactionDTO.setMisHead(transactionEntity.getMisHead());
        transactionDTO.setImmediate(getBooleanValueFromChar(transactionEntity.getIsImmediate()));
        transactionDTO.setOnValueDate(getBooleanValueFromChar(transactionEntity.getIsOnValueDate()));
        transactionDTO.setAfterXDaysValueDate(getBooleanValueFromChar(transactionEntity.getIsAfterDaysValueDate()));
        transactionDTO.setAfterXDays(getBooleanValueFromChar(transactionEntity.getIsAfterDays()));
        transactionDTO.setNoOfDays(transactionEntity.getNoOfDays());
        transactionDTO.setIntradayManualRelease(getBooleanValueFromChar(transactionEntity.getIsIntradayManualRel()));
        transactionDTO.setStatementDateDerivation(transactionEntity.getStatementDateDerivation());
        transactionDTO.setAmlTracking(getBooleanValueFromChar(transactionEntity.getIsAmlTracking()));
        transactionDTO.setProductGroup(transactionEntity.getProductGroup());
        transactionDTO.setAvailableBalanceCheckApplicableBatch(getBooleanValueFromChar(transactionEntity.getIsBalChkAppliBatch()));
        transactionDTO.setAvailableBalanceCheckApplicableOnline(getBooleanValueFromChar(transactionEntity.getIsBalChkAppliOnline()));
        transactionDTO.setPricingOnTransactionCount(getBooleanValueFromChar(transactionEntity.getIsPricingOnTranCount()));
        transactionDTO.setPricingTurnoverInclusion(getBooleanValueFromChar(transactionEntity.getIsPricingTurnoverIncl()));
        transactionDTO.setPricingBalanceInclusive(getBooleanValueFromChar(transactionEntity.getIsPricingBalanceIncl()));
        transactionDTO.setTurnoverLimit(getBooleanValueFromChar(transactionEntity.getIsTurnoverLimit()));
        transactionDTO.setCoverAccount(getBooleanValueFromChar(transactionEntity.getIsCoverAccount()));
        transactionDTO.setPricingPenaltyInclusive(getBooleanValueFromChar(transactionEntity.getIsPricingPenaltyIncl()));
        transactionDTO.setConsiderForAccountStatusChange(getBooleanValueFromChar(transactionEntity.getIsConsiderAcStatusChg()));
        transactionDTO.setChequeMandatory(getBooleanValueFromChar(transactionEntity.getIsChequeMandatory()));
        transactionDTO.setInterBranchInLcy(getBooleanValueFromChar(transactionEntity.getIsInterBranchInLcy()));
        transactionDTO.setThirdPartyDealingSystemTransactionCode(getBooleanValueFromChar(transactionEntity.getIsThirdPartyTranCode()));
        return transactionDTO;
    }

    public TransactionDTO setAuditFields (MutationEntity  mutationEntity, TransactionDTO transactionDTO) {
        transactionDTO.setAction(mutationEntity.getAction());
        transactionDTO.setAuthorized(mutationEntity.getAuthorized());
        transactionDTO.setRecordVersion(mutationEntity.getRecordVersion());
        transactionDTO.setStatus(mutationEntity.getStatus());
        transactionDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        transactionDTO.setCreatedBy(mutationEntity.getCreatedBy());
        transactionDTO.setCreationTime(mutationEntity.getCreationTime());
        transactionDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        transactionDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        transactionDTO.setTaskCode(mutationEntity.getTaskCode());
        transactionDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return transactionDTO;
    }

    public char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}

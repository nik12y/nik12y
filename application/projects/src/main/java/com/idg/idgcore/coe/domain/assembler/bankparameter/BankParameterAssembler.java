package com.idg.idgcore.coe.domain.assembler.bankparameter;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.bankparameter.*;
import com.idg.idgcore.coe.domain.entity.mutation.MutationEntity;
import com.idg.idgcore.coe.dto.bankparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.BranchParameterDTO;
import org.springframework.stereotype.Component;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Component
public class BankParameterAssembler extends Assembler<BankParameterDTO, BankParameterEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return BankParameterDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BankParameterEntity.class;
    }

    @Override
    public BankParameterEntity toEntity(BankParameterDTO bankParameterDTO) {
        /**
         * For Bank Parameter Address
         */
        //BankParameterEntity bankParameterEntity = modelMapper.map(bankParameterDTO,BankParameterEntity.class);

        BankParameterAddressDTO bankParameterAddressDTO= bankParameterDTO.getBankParameterAddress();
        BankParameterAddressEntity bankParameterAddressEntity = modelMapper.map(bankParameterAddressDTO, BankParameterAddressEntity.class);
        bankParameterAddressEntity.setBankAddress1(bankParameterAddressDTO.getBankAddress1());
        bankParameterAddressEntity.setBankAddress2(bankParameterAddressDTO.getBankAddress2());
        bankParameterAddressEntity.setBankAddress3(bankParameterAddressDTO.getBankAddress3());
        bankParameterAddressEntity.setBankAddress4(bankParameterAddressDTO.getBankAddress4());
        bankParameterAddressEntity.setCountryCode(bankParameterAddressDTO.getCountry());
        bankParameterAddressEntity.setStateCode(bankParameterAddressDTO.getState());
        bankParameterAddressEntity.setCityCode(bankParameterAddressDTO.getCity());

        /**
         * For Bank Parameter Contact Information
         */
        BankParameterContactInfoDTO bankParameterContactInfoDTO= bankParameterDTO.getBankParameterContactInfo();
        BankParameterContactInfoEntity bankParameterContactInfoEntity = modelMapper.map(bankParameterContactInfoDTO, BankParameterContactInfoEntity.class);
        bankParameterContactInfoEntity.setTelephoneNo(bankParameterContactInfoDTO.getTelephoneNo());
        bankParameterContactInfoEntity.setFaxNo(bankParameterContactInfoDTO.getFaxNo());
        bankParameterContactInfoEntity.setEmailId(bankParameterContactInfoDTO.getEmailId());
        bankParameterContactInfoEntity.setBankWebsite(bankParameterContactInfoDTO.getBankWebsite());
        /**
         * For Bank Parameter Currency
         */
        BankParameterCurrencyDTO bankParameterCurrencyDTO= bankParameterDTO.getBankParameterCurrency();
        BankParameterCurrencyEntity bankParameterCurrencyEntity = modelMapper.map(bankParameterCurrencyDTO, BankParameterCurrencyEntity.class);
        bankParameterCurrencyEntity.setCurrencyCode(bankParameterCurrencyDTO.getCurrencyCode());
        bankParameterCurrencyEntity.setCurrencyName(bankParameterCurrencyDTO.getCurrencyName());
        bankParameterCurrencyEntity.setIsDenominationTrackingRequired(getCharValueFromBoolean(bankParameterCurrencyDTO.getIsDenominationTrackingRequired()));
        bankParameterCurrencyEntity.setCurrencyDenomination(bankParameterCurrencyDTO.getCurrencyOfDenomination());
        bankParameterCurrencyEntity.setCurrencyDenominationTracking(bankParameterCurrencyDTO.getDenominationTrackingCurrency());
        /**
         * For Bank Parameter Preferences
         */
        BankParameterPreferencesDTO bankParameterPreferencesDTO= bankParameterDTO.getBankParameterPreferences();
        BankParameterPreferencesEntity bankParameterPreferencesEntity = modelMapper.map(bankParameterPreferencesDTO, BankParameterPreferencesEntity.class);
        bankParameterPreferencesEntity.setWeekBeginDay(bankParameterPreferencesDTO.getWeekBeginDay());
        bankParameterPreferencesEntity.setWeeklyOff1(bankParameterPreferencesDTO.getWeeklyOff1());
        bankParameterPreferencesEntity.setWeeklyOff2(bankParameterPreferencesDTO.getWeeklyOff2());
      bankParameterPreferencesEntity.setWeeklyOff3(bankParameterPreferencesDTO.getWeeklyOff3());
        bankParameterPreferencesEntity.setFinancialYearBeginMonth(bankParameterPreferencesDTO.getFinancialYearBeginMonth());
        /**
         * For Bank Parameter For OD And Loan Decision
         */
        BankParameterForOdLoanDTO bankParameterForOdLoanDTO= bankParameterDTO.getBankParameterForOdLoan();
        BankParameterForOdLoanEntity bankParameterForOdLoanEntity = modelMapper.map(bankParameterForOdLoanDTO, BankParameterForOdLoanEntity.class);
        bankParameterForOdLoanEntity.setRuleIdOd(bankParameterForOdLoanDTO.getRuleIdForOd());
        bankParameterForOdLoanEntity.setRuleNameOd(bankParameterForOdLoanDTO.getRuleNameForOd());
        bankParameterForOdLoanEntity.setRuleIdLoan(bankParameterForOdLoanDTO.getRuleIdForLoan());
        bankParameterForOdLoanEntity.setRuleNameLoan(bankParameterForOdLoanDTO.getRuleNameForLoan());
        /**
         * Set Address/Currency/Preferences/ODLoanDecision Entity values
         */
        BankParameterEntity bankParameterEntity = modelMapper.map(bankParameterDTO, BankParameterEntity.class);
        bankParameterEntity.setBankParameterAddressEntity(bankParameterAddressEntity);
        bankParameterEntity.setBankParameterContactInfoEntity(bankParameterContactInfoEntity);
        bankParameterEntity.setBankParameterCurrencyEntity(bankParameterCurrencyEntity);
        bankParameterEntity.setBankParameterPreferencesEntity(bankParameterPreferencesEntity);
        bankParameterEntity.setBankParameterForOdLoanEntity(bankParameterForOdLoanEntity);
        /**
         * Set Bank Parameter
         */
        bankParameterEntity.setBankCode(bankParameterDTO.getBankCode());
        bankParameterEntity.setBankName(bankParameterDTO.getBankName());
        bankParameterEntity.setBankCodeRegulatory(bankParameterDTO.getRegulatoryBankCode());
        bankParameterEntity.setBankConciseName(bankParameterDTO.getBankConciseName());
        bankParameterEntity.setBankGroupCode(bankParameterDTO.getGroupBankingCode());
        //Check why audit values are not updating
        return bankParameterEntity;
    }

    @Override
    public BankParameterDTO toDTO(BankParameterEntity bankParameterEntity) {

        /**
         * For Bank Parameter Address
         */
        BankParameterAddressEntity bankParameterAddressEntity = bankParameterEntity.getBankParameterAddressEntity();
        BankParameterAddressDTO bankParameterAddressDTO = modelMapper.map(bankParameterAddressEntity, BankParameterAddressDTO.class);
        bankParameterAddressDTO.setBankAddress1(bankParameterAddressEntity.getBankAddress1());
        bankParameterAddressDTO.setBankAddress2(bankParameterAddressEntity.getBankAddress2());
        bankParameterAddressDTO.setBankAddress3(bankParameterAddressEntity.getBankAddress3());
        bankParameterAddressDTO.setBankAddress4(bankParameterAddressEntity.getBankAddress4());
        bankParameterAddressDTO.setCountry(bankParameterAddressEntity.getCountryCode());
        bankParameterAddressDTO.setState(bankParameterAddressEntity.getStateCode());
        bankParameterAddressDTO.setCity(bankParameterAddressEntity.getCityCode());

        /**
         * For Bank Parameter Contact Information
         */
        BankParameterContactInfoEntity bankParameterContactInfoEntity = bankParameterEntity.getBankParameterContactInfoEntity();
        BankParameterContactInfoDTO bankParameterContactInfoDTO = modelMapper.map(bankParameterContactInfoEntity, BankParameterContactInfoDTO.class);
        bankParameterContactInfoDTO.setTelephoneNo(bankParameterContactInfoEntity.getTelephoneNo());
        bankParameterContactInfoDTO.setFaxNo(bankParameterContactInfoEntity.getFaxNo());
        bankParameterContactInfoDTO.setEmailId(bankParameterContactInfoEntity.getEmailId());
        bankParameterContactInfoDTO.setBankWebsite(bankParameterContactInfoEntity.getBankWebsite());
        /**
         * For Bank Parameter Currency
         */
        BankParameterCurrencyEntity bankParameterCurrencyEntity = bankParameterEntity.getBankParameterCurrencyEntity();
        BankParameterCurrencyDTO bankParameterCurrencyDTO = modelMapper.map(bankParameterCurrencyEntity, BankParameterCurrencyDTO.class);
        bankParameterCurrencyDTO.setCurrencyCode(bankParameterCurrencyEntity.getCurrencyCode());
        bankParameterCurrencyDTO.setCurrencyName(bankParameterCurrencyEntity.getCurrencyName());
        bankParameterCurrencyDTO.setIsDenominationTrackingRequired(getBooleanValueFromChar(bankParameterCurrencyEntity.getIsDenominationTrackingRequired()));
        bankParameterCurrencyDTO.setCurrencyOfDenomination(bankParameterCurrencyEntity.getCurrencyDenomination());
        bankParameterCurrencyDTO.setDenominationTrackingCurrency(bankParameterCurrencyEntity.getCurrencyDenominationTracking());
        /**
         * For Bank Parameter Preferences
         */
        BankParameterPreferencesEntity bankParameterPreferencesEntity = bankParameterEntity.getBankParameterPreferencesEntity();
        BankParameterPreferencesDTO bankParameterPreferencesDTO = modelMapper.map(bankParameterPreferencesEntity, BankParameterPreferencesDTO.class);
        bankParameterPreferencesDTO.setWeekBeginDay(bankParameterPreferencesEntity.getWeekBeginDay());
        bankParameterPreferencesDTO.setWeeklyOff1(bankParameterPreferencesEntity.getWeeklyOff1());
        bankParameterPreferencesDTO.setWeeklyOff2(bankParameterPreferencesEntity.getWeeklyOff2());
        bankParameterPreferencesDTO.setWeeklyOff3(bankParameterPreferencesEntity.getWeeklyOff3());
        bankParameterPreferencesDTO.setFinancialYearBeginMonth(bankParameterPreferencesEntity.getFinancialYearBeginMonth());
        /**
         * Set Address/Currency/Preferences/ODLoanDecision
         */
        BankParameterForOdLoanEntity bankParameterForOdLoanEntity = bankParameterEntity.getBankParameterForOdLoanEntity();
        BankParameterForOdLoanDTO bankParameterForOdLoanDTO = modelMapper.map(bankParameterForOdLoanEntity, BankParameterForOdLoanDTO.class);
        bankParameterForOdLoanDTO.setRuleIdForOd(bankParameterForOdLoanEntity.getRuleIdOd());
        bankParameterForOdLoanDTO.setRuleNameForOd(bankParameterForOdLoanEntity.getRuleNameOd());
        bankParameterForOdLoanDTO.setRuleIdForLoan(bankParameterForOdLoanEntity.getRuleIdLoan());
        bankParameterForOdLoanDTO.setRuleNameForLoan(bankParameterForOdLoanEntity.getRuleNameLoan());

        /**
         * Set Address/Currency/Preferences/ODLoanDecision Entity values
         */
        /**
         * Set Bank Parameter
         */
        BankParameterDTO bankParameterDTO = modelMapper.map(bankParameterEntity, BankParameterDTO.class);
        bankParameterDTO.setBankParameterAddress(bankParameterAddressDTO);
        bankParameterDTO.setBankParameterContactInfo(bankParameterContactInfoDTO);
        bankParameterDTO.setBankParameterCurrency(bankParameterCurrencyDTO);
        bankParameterDTO.setBankParameterPreferences(bankParameterPreferencesDTO);
        bankParameterDTO.setBankParameterForOdLoan(bankParameterForOdLoanDTO);

        bankParameterDTO.setBankCode(bankParameterEntity.getBankCode());
        bankParameterDTO.setBankName(bankParameterEntity.getBankName());
        bankParameterDTO.setRegulatoryBankCode(bankParameterEntity.getBankCodeRegulatory());
        bankParameterDTO.setBankConciseName(bankParameterEntity.getBankConciseName());
        bankParameterDTO.setGroupBankingCode(bankParameterEntity.getBankGroupCode());
        return bankParameterDTO;
    }

    public BankParameterDTO setAuditFields (MutationEntity mutationEntity, BankParameterDTO bankParameterDTO) {
        bankParameterDTO.setAction(mutationEntity.getAction());
        bankParameterDTO.setAuthorized(mutationEntity.getAuthorized());
        bankParameterDTO.setRecordVersion(mutationEntity.getRecordVersion());
        bankParameterDTO.setStatus(mutationEntity.getStatus());
        bankParameterDTO.setLastConfigurationAction(mutationEntity.getLastConfigurationAction());
        bankParameterDTO.setCreatedBy(mutationEntity.getCreatedBy());
        bankParameterDTO.setCreationTime(mutationEntity.getCreationTime());
        bankParameterDTO.setLastUpdatedBy(mutationEntity.getLastUpdatedBy());
        bankParameterDTO.setLastUpdatedTime(mutationEntity.getLastUpdatedTime());
        bankParameterDTO.setTaskCode(mutationEntity.getTaskCode());
        bankParameterDTO.setTaskIdentifier(mutationEntity.getTaskIdentifier());
        return bankParameterDTO;
    }

    public char getCharValueFromBoolean(boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public boolean getBooleanValueFromChar(Character value) {
        return value.equals(CHAR_Y);

    }
}

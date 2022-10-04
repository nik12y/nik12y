package com.idg.idgcore.coe.domain.assembler.branchparameter;

import com.idg.idgcore.coe.domain.assembler.generic.Assembler;
import com.idg.idgcore.coe.domain.entity.branchparameter.*;
import com.idg.idgcore.coe.dto.branchparameter.*;
import org.springframework.stereotype.Component;

@Component
public class BranchParameterAssembler extends Assembler<BranchParameterDTO, BranchParameterEntity> {

    @Override
    public Class getSpecificDTOClass() {
        return BranchParameterDTO.class;
    }

    @Override
    public Class getSpecificEntityClass() {
        return BranchParameterEntity.class;
    }

    @Override
    public BranchParameterEntity toEntity(BranchParameterDTO branchParameterDTO) {
        BranchParameterEntity branchParameterEntity = super.toEntity(branchParameterDTO);

        BranchParameterAddressDTO branchParameterAddressDTO= branchParameterDTO.getBranchParameterAddress();
        BranchParameterAddressEntity branchParameterAddressEntity = modelMapper.map(branchParameterAddressDTO, BranchParameterAddressEntity.class);
        branchParameterAddressEntity.setCountryCode(branchParameterAddressDTO.getCountry());
        branchParameterAddressEntity.setStateCode(branchParameterAddressDTO.getState());
        branchParameterAddressEntity.setCityCode(branchParameterAddressDTO.getCity());
        /**
         * For Branch Parameter Contact Information
         */
        BranchParameterContactInfoDTO branchParameterContactInfoDTO= branchParameterDTO.getBranchParameterContactInfo();
        BranchParameterContactInfoEntity branchParameterContactInfoEntity = modelMapper.map(branchParameterContactInfoDTO, BranchParameterContactInfoEntity.class);
        /**
         * For Branch Parameter General
         */
        BranchParameterGeneralDTO branchParameterGeneralDTO= branchParameterDTO.getBranchParameterGeneral();
        BranchParameterGeneralEntity branchParameterGeneralEntity = modelMapper.map(branchParameterGeneralDTO, BranchParameterGeneralEntity.class);
        branchParameterGeneralEntity.setGeneralCurrencyCode(branchParameterGeneralDTO.getGeneralCurrencyCode());
        branchParameterGeneralEntity.setIsHeadZoneRegionalOffice(branchParameterGeneralDTO.getIsHeadZoneRegionalOffice());

        /**
         *  For Branch Parameter Clearing
         */
        BranchParameterClearingDTO branchParameterClearingDTO = branchParameterDTO.getBranchParameterClearing();
        BranchParameterClearingEntity branchParameterClearingEntity = modelMapper.map(branchParameterClearingDTO, BranchParameterClearingEntity.class);
        branchParameterClearingEntity.setClearingLocalPaymentBranch(branchParameterClearingDTO.getClearingLocalPaymentBranch());
        branchParameterClearingEntity.setClearingCurrencyCode(branchParameterClearingDTO.getClearingCurrencyCode());
        /**
         * For Branch Parameter ATM
         */
        BranchParameterAtmDTO branchParameterAtmDTO = branchParameterDTO.getBranchParameterAtm();
        BranchParameterAtmEntity branchParameterAtmEntity = modelMapper.map(branchParameterClearingDTO,BranchParameterAtmEntity.class);
        branchParameterAtmEntity.setAtmBranch(branchParameterAtmDTO.getAtmBranch());
        branchParameterAtmEntity.setAtmInstitutionIdentity(branchParameterAtmDTO.getAtmInstitutionIdentification());
        branchParameterAtmEntity.setAtmInterBranchTranCode(branchParameterAtmDTO.getAtmInterBranchTransactionCode());
        branchParameterAtmEntity.setAtmIsCustomerFundTrf(getCharValueFromBoolean(branchParameterAtmDTO.getAtmIsCustomerFundTransfer()));
        /**
         * For Branch Parameter Timezone
         */
        BranchParameterTimezoneDTO branchParameterTimezoneDTO = branchParameterDTO.getBranchParameterTimezone();
        BranchParameterTimezoneEntity branchParameterTimezoneEntity = modelMapper.map(branchParameterTimezoneDTO,BranchParameterTimezoneEntity.class );
        branchParameterTimezoneEntity.setTimeZoneIsAhead(getCharValueFromBoolean(branchParameterTimezoneDTO.getTimeZoneIsAhead()));
        /**
         *  For Branch Parameter Global interdict
         */
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictDTO = branchParameterDTO.getBranchParameterGlobalInterdict();
        BranchParameterGlobalInterdictEntity branchParameterGlobalInterdictEntity = modelMapper.map(branchParameterGlobalInterdictDTO, BranchParameterGlobalInterdictEntity.class);
        /**
         *  For Branch Parameter Transaction
         */
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateDTO = branchParameterDTO.getBranchParameterTranDuplicate();
        BranchParameterTranDuplicateEntity branchParameterTranDuplicateEntity = modelMapper.map(branchParameterTranDuplicateDTO, BranchParameterTranDuplicateEntity.class);
        branchParameterTranDuplicateEntity.setTranDupModuleCode(branchParameterTranDuplicateDTO.getTranModuleCode());
        branchParameterTranDuplicateEntity.setTranDupIsCheckReq(getCharValueFromBoolean(branchParameterTranDuplicateDTO.getTranDuplicationCheckRequired()));
        branchParameterTranDuplicateEntity.setTranDupNoOfDaysForCheck(branchParameterTranDuplicateDTO.getTranNoOfDayForDuplicationCheck());

        /**
         * For Branch Parameter Local Currency
         */
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyDTO= branchParameterDTO.getBranchParameterLocalCurrency();
        BranchParameterLocalCurrencyEntity branchParameterLocalCurrencyEntity= modelMapper.map(branchParameterLocalCurrencyDTO, BranchParameterLocalCurrencyEntity.class);

        /**
         * For Bank Parameter Misc
         */
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousDTO= branchParameterDTO.getBranchParameterMiscellaneous();
        BranchParameterMiscellaneousEntity branchParameterMiscellaneousEntity = modelMapper.map(branchParameterMiscellaneousDTO, BranchParameterMiscellaneousEntity.class);
        branchParameterMiscellaneousEntity.setMiscBranchIsDenoReq(getCharValueFromBoolean(branchParameterMiscellaneousDTO.getMiscDenominationTrackingRequired()));
        branchParameterMiscellaneousEntity.setMiscBranchCurrencyDeno(branchParameterMiscellaneousDTO.getMiscCurrencyOfDenomination());
        branchParameterMiscellaneousEntity.setMiscBranchWeekBeginDay(branchParameterMiscellaneousDTO.getMiscWeekBeginDay());
        branchParameterMiscellaneousEntity.setMiscBranchWeeklyOff1(branchParameterMiscellaneousDTO.getMiscWeeklyOff1());
        branchParameterMiscellaneousEntity.setMiscBranchWeeklyOff2(branchParameterMiscellaneousDTO.getMiscWeeklyOff2());
        branchParameterMiscellaneousEntity.setMiscBranchFinYrBeginMth(branchParameterMiscellaneousDTO.getMiscFinancialYearBeginMonth());

        /**
         * Set Address/Currency/Preferences/ODLoanDecision Entity values
         */

        branchParameterEntity.setBranchParameterAddressEntity(branchParameterAddressEntity);
        branchParameterEntity.setBranchParameterContactInfoEntity(branchParameterContactInfoEntity);
        branchParameterEntity.setBranchParameterGeneralEntity(branchParameterGeneralEntity);
        branchParameterEntity.setBranchParameterClearingEntity(branchParameterClearingEntity);
        branchParameterEntity.setBranchParameterAtmEntity(branchParameterAtmEntity);
        branchParameterEntity.setBranchParameterTimezoneEntity(branchParameterTimezoneEntity);
        branchParameterEntity.setBranchParameterGlobalInterdictEntity(branchParameterGlobalInterdictEntity);
        branchParameterEntity.setBranchParameterTranDuplicateEntity(branchParameterTranDuplicateEntity);
        branchParameterEntity.setBranchParameterLocalCurrencyEntity(branchParameterLocalCurrencyEntity);
        branchParameterEntity.setBranchParameterMiscellaneousEntity(branchParameterMiscellaneousEntity);
        /**
         * Set Bank Parameter
         */
        branchParameterEntity.setBranchCode(branchParameterDTO.getBranchCode());
        branchParameterEntity.setBranchName(branchParameterDTO.getBranchName());
        branchParameterEntity.setBankCode(branchParameterDTO.getBankCode());
        branchParameterEntity.setBranchConciseName(branchParameterDTO.getBranchConciseName());
        branchParameterEntity.setBranchConciseName(branchParameterDTO.getBranchConciseName());
        branchParameterEntity.setIsBranchAvailableStatus(getCharValueFromBoolean(branchParameterDTO.getIsBranchAvailableStatus()));
        return branchParameterEntity;
    }

    @Override
    public BranchParameterDTO toDTO(BranchParameterEntity branchParameterEntity) {

        /**
         * For Branch Parameter Address
         */
        BranchParameterAddressEntity branchParameterAddressEntity= branchParameterEntity.getBranchParameterAddressEntity();
        BranchParameterAddressDTO branchParameterAddressDTO = modelMapper.map(branchParameterAddressEntity, BranchParameterAddressDTO.class);
        branchParameterAddressDTO.setCountry(branchParameterAddressEntity.getCountryCode());
        branchParameterAddressDTO.setState(branchParameterAddressEntity.getStateCode());
        branchParameterAddressDTO.setCity(branchParameterAddressEntity.getCityCode());
        /**
         * For Branch Parameter Contact Information
         */
        BranchParameterContactInfoEntity branchParameterContactInfoEntity= branchParameterEntity.getBranchParameterContactInfoEntity();
        BranchParameterContactInfoDTO branchParameterContactInfoDTO = modelMapper.map(branchParameterContactInfoEntity, BranchParameterContactInfoDTO.class);
        /**
         * For Branch Parameter General
         */
        BranchParameterGeneralEntity branchParameterGeneralEntity= branchParameterEntity.getBranchParameterGeneralEntity();
        BranchParameterGeneralDTO branchParameterGeneralDTO = modelMapper.map(branchParameterGeneralEntity, BranchParameterGeneralDTO.class);
        branchParameterGeneralDTO.setGeneralCurrencyCode(branchParameterGeneralEntity.getGeneralCurrencyCode());
        branchParameterGeneralDTO.setIsHeadZoneRegionalOffice(branchParameterGeneralEntity.getIsHeadZoneRegionalOffice());

        /**
         *  For Branch Parameter Clearing
         */
        BranchParameterClearingEntity branchParameterClearingEntity = branchParameterEntity.getBranchParameterClearingEntity();
        BranchParameterClearingDTO branchParameterClearingDTO = modelMapper.map(branchParameterClearingEntity, BranchParameterClearingDTO.class);
        branchParameterClearingDTO.setClearingLocalPaymentBranch(branchParameterClearingEntity.getClearingLocalPaymentBranch());
        branchParameterClearingDTO.setClearingCurrencyCode(branchParameterClearingEntity.getClearingCurrencyCode());
        /**
         * For Branch Parameter ATM
         */
        BranchParameterAtmEntity branchParameterAtmEntity = branchParameterEntity.getBranchParameterAtmEntity();
        BranchParameterAtmDTO branchParameterAtmDTO = modelMapper.map(branchParameterAtmEntity,BranchParameterAtmDTO.class);
        branchParameterAtmDTO.setAtmBranch(branchParameterAtmEntity.getAtmBranch());
        branchParameterAtmDTO.setAtmInstitutionIdentification(branchParameterAtmEntity.getAtmInstitutionIdentity());
        branchParameterAtmDTO.setAtmInterBranchTransactionCode(branchParameterAtmEntity.getAtmInterBranchTranCode());
        branchParameterAtmDTO.setAtmIsCustomerFundTransfer(getBooleanValueFromChar(branchParameterAtmEntity.getAtmIsCustomerFundTrf()));
        /**
         * For Branch Parameter Timezone
         */
        BranchParameterTimezoneEntity branchParameterTimezoneEntity = branchParameterEntity.getBranchParameterTimezoneEntity();
        BranchParameterTimezoneDTO branchParameterTimezoneDTO = modelMapper.map(branchParameterTimezoneEntity,BranchParameterTimezoneDTO.class );
        branchParameterTimezoneDTO.setTimeZoneIsAhead(getBooleanValueFromChar(branchParameterTimezoneEntity.getTimeZoneIsAhead()));
        /**
         *  For Branch Parameter Global interdict
         */
        BranchParameterGlobalInterdictEntity branchParameterGlobalInterdictEntity = branchParameterEntity.getBranchParameterGlobalInterdictEntity();
        BranchParameterGlobalInterdictDTO branchParameterGlobalInterdictDTO = modelMapper.map(branchParameterGlobalInterdictEntity, BranchParameterGlobalInterdictDTO.class);
        /**
         *  For Branch Parameter Transaction Duplicate
         */
        BranchParameterTranDuplicateEntity branchParameterTranDuplicateEntity = branchParameterEntity.getBranchParameterTranDuplicateEntity();
        BranchParameterTranDuplicateDTO branchParameterTranDuplicateDTO = modelMapper.map(branchParameterTranDuplicateEntity, BranchParameterTranDuplicateDTO.class);
        branchParameterTranDuplicateDTO.setTranModuleCode(branchParameterTranDuplicateEntity.getTranDupModuleCode());
        branchParameterTranDuplicateDTO.setTranNoOfDayForDuplicationCheck(branchParameterTranDuplicateDTO.getTranNoOfDayForDuplicationCheck());
        branchParameterTranDuplicateDTO.setTranDuplicationCheckRequired(getBooleanValueFromChar(branchParameterTranDuplicateEntity.getTranDupIsCheckReq()));

        /**
         * For Branch Parameter Local Currency
         */
        BranchParameterLocalCurrencyEntity branchParameterLocalCurrencyEntity= branchParameterEntity.getBranchParameterLocalCurrencyEntity();
        BranchParameterLocalCurrencyDTO branchParameterLocalCurrencyDTO= modelMapper.map(branchParameterLocalCurrencyEntity, BranchParameterLocalCurrencyDTO.class);

        /**
         * For Bank Parameter Misc
         */
        BranchParameterMiscellaneousEntity branchParameterMiscellaneousEntity= branchParameterEntity.getBranchParameterMiscellaneousEntity();
        BranchParameterMiscellaneousDTO branchParameterMiscellaneousDTO = modelMapper.map(branchParameterMiscellaneousEntity, BranchParameterMiscellaneousDTO.class);
        branchParameterMiscellaneousDTO.setMiscDenominationTrackingRequired(getBooleanValueFromChar(branchParameterMiscellaneousEntity.getMiscBranchIsDenoReq()));
        branchParameterMiscellaneousDTO.setMiscCurrencyOfDenomination(branchParameterMiscellaneousEntity.getMiscBranchCurrencyDeno());
        branchParameterMiscellaneousDTO.setMiscWeekBeginDay(branchParameterMiscellaneousEntity.getMiscBranchWeekBeginDay());
        branchParameterMiscellaneousDTO.setMiscWeeklyOff1(branchParameterMiscellaneousEntity.getMiscBranchWeeklyOff1());
        branchParameterMiscellaneousDTO.setMiscWeeklyOff2(branchParameterMiscellaneousEntity.getMiscBranchWeeklyOff2());
        branchParameterMiscellaneousDTO.setMiscFinancialYearBeginMonth(branchParameterMiscellaneousEntity.getMiscBranchFinYrBeginMth());

        /**
         * Set Entity values
         */
        BranchParameterDTO branchParameterDTO = modelMapper.map(branchParameterEntity, BranchParameterDTO.class);
        branchParameterDTO.setBranchParameterAddress(branchParameterAddressDTO);
        branchParameterDTO.setBranchParameterContactInfo(branchParameterContactInfoDTO);
        branchParameterDTO.setBranchParameterGeneral(branchParameterGeneralDTO);
        branchParameterDTO.setBranchParameterClearing(branchParameterClearingDTO);
        branchParameterDTO.setBranchParameterAtm(branchParameterAtmDTO);
        branchParameterDTO.setBranchParameterTimezone(branchParameterTimezoneDTO);
        branchParameterDTO.setBranchParameterGlobalInterdict(branchParameterGlobalInterdictDTO);
        branchParameterDTO.setBranchParameterTranDuplicate(branchParameterTranDuplicateDTO);
        branchParameterDTO.setBranchParameterLocalCurrency(branchParameterLocalCurrencyDTO);
        branchParameterDTO.setBranchParameterMiscellaneous(branchParameterMiscellaneousDTO);
        /**
         * Set Bank Parameter
         */
        branchParameterDTO.setBranchCode(branchParameterDTO.getBranchCode());
        branchParameterDTO.setBranchName(branchParameterDTO.getBranchName());
        branchParameterDTO.setBankCode(branchParameterDTO.getBankCode());
        branchParameterDTO.setBranchConciseName(branchParameterDTO.getBranchConciseName());
        branchParameterDTO.setIsBranchAvailableStatus(getBooleanValueFromChar(branchParameterEntity.getIsBranchAvailableStatus()));
        return branchParameterDTO;
    }
}

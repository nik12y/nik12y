package com.idg.idgcore.coe.dto.transaction;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idg.idgcore.coe.dto.base.CoreEngineBaseDTO;
import lombok.*;
import lombok.experimental.SuperBuilder;

import static com.idg.idgcore.coe.common.Constants.TRANSACTION;

@ToString (callSuper = true)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@JsonInclude (JsonInclude.Include.NON_NULL)
public class TransactionDTO extends CoreEngineBaseDTO {
    private String transactionCode;
    private String transactionDescription;
    private String transactionSwiftCode;
    private String externalTxnCode;
    private String misHead;
    private Boolean immediate;
    private Boolean onValueDate;
    private Boolean afterXDaysValueDate;
    private Boolean afterXDays;
    private Integer noOfDays;
    private Boolean intradayManualRelease;
    private String statementDateDerivation;
    private Boolean amlTracking;
    private String productGroup;
    private Boolean availableBalanceCheckApplicableBatch;
    private Boolean availableBalanceCheckApplicableOnline;
    private Boolean pricingOnTransactionCount;
    private Boolean pricingTurnoverInclusion;
    private Boolean pricingBalanceInclusive;
    private Boolean turnoverLimit;
    private Boolean coverAccount;
    private Boolean pricingPenaltyInclusive;
    private Boolean considerForAccountStatusChange;
    private Boolean chequeMandatory;
    private Boolean interBranchInLcy;
    private Boolean thirdPartyDealingSystemTransactionCode;

    @Override
    public String getTaskCode () {
        return TRANSACTION;
    }

    @Override
    public void setTaskCode (String taskCode) {
        super.setTaskCode(TRANSACTION);
    }

    @Override
    public String getTaskIdentifier () {
        return this.getTransactionCode();
    }
}

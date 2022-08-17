package com.idg.idgcore.coe.domain.entity.transaction;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_TRAN_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (TransactionEntityKey.class)
public class TransactionEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="transaction_code")
    private String transactionCode;
    @Column (name="transaction_desc")
    private String transactionDesc;
    @Column (name="transaction_swift_code")
    private String transactionSwiftCode;
    @Column (name="external_txn_code")
    private String externalTxnCode;
    @Column (name="mis_head")
    private String misHead;
    @Column (name="is_immediate")
    private char isImmediate;
    @Column (name="is_on_value_date")
    private char isOnValueDate;
    @Column (name="is_after_days_value_date")
    private char isAfterDaysValueDate;
    @Column (name="is_after_days")
    private char isAfterDays;
    @Column (name="no_of_days")
    private Integer noOfDays;
    @Column (name="is_intraday_manual_rel")
    private char isIntradayManualRel;
    @Column (name="statement_date_derivation")
    private String statementDateDerivation;
    @Column (name="is_aml_tracking")
    private char isAmlTracking;
    @Column (name="product_group")
    private String productGroup;
    @Column (name="is_bal_chk_appli_batch")
    private char isBalChkAppliBatch;
    @Column (name="is_bal_chk_appli_online")
    private char isBalChkAppliOnline;
    @Column (name="is_pricing_on_tran_count")
    private char isPricingOnTranCount;
    @Column (name="is_pricing_turnover_incl")
    private char isPricingTurnoverIncl;
    @Column (name="is_pricing_balance_incl")
    private char isPricingBalanceIncl;
    @Column (name="is_turnover_limit")
    private char isTurnoverLimit;
    @Column (name="is_cover_account")
    private char isCoverAccount;
    @Column (name="is_pricing_penalty_incl")
    private char isPricingPenaltyIncl;
    @Column (name="is_consider_ac_status_chg")
    private char isConsiderAcStatusChg;
    @Column (name="is_cheque_mandatory")
    private char isChequeMandatory;
    @Column (name="is_inter_branch_in_lcy")
    private char isInterBranchInLcy;
    @Column (name="is_third_party_tran_code")
    private char isThirdPartyTranCode;
    @Column (name="life_cycle_id")
    private String lifeCycleId;
    @Column (name="reference_no")
    private String referenceNo;
    @Column (name="record_status")
    private String status;
    @Column (name="record_version")
    private Integer recordVersion;
    @Column (name="is_authorized")
    private String authorized;
    @Column (name="last_configuration_action")
    private String lastConfigurationAction;

}

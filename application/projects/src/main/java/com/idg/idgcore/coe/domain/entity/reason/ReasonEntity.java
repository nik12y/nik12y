package com.idg.idgcore.coe.domain.entity.reason;

import com.idg.idgcore.domain.AbstractAuditableDomainEntity;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "IDGC_COE_REASON_CODE_CNFG")
@Inheritance (strategy = InheritanceType.JOINED)
@ToString
@IdClass (ReasonEntityKey.class)
public class ReasonEntity extends AbstractAuditableDomainEntity implements Serializable {
    @Id
    @Column (name="primary_reason_code")
    private String primaryReasonCode;
    @Column (name="primary_reason_desc")
    private String primaryReasonDesc;
    @Column (name="is_pri_account_block")
    private char isPriAccountBlock;
    @Column (name="is_pri_account_unblock")
    private char isPriAccountUnblock;
    @Column (name="is_pri_account_closure")
    private char isPriAccountClosure;
    @Column (name="is_pri_request_for_acc_closure")
    private char isPriRequestForAccClosure;
    @Column (name="is_pri_stop_payment")
    private char isPriStopPayment;
    @Column (name="is_pri_stop_payment_revoke")
    private char isPriStopPaymentRevoke;
    @Column (name="secondary_reason_code")
    private String secondaryReasonCode;
    @Column (name="secondary_reason_desc")
    private String secondaryReasonDesc;
    @Column (name="is_sec_account_block")
    private char isSecAccountBlock;
    @Column (name="is_sec_account_unblock")
    private char isSecAccountUnblock;
    @Column (name="is_sec_account_closure")
    private char isSecAccountClosure;
    @Column (name="is_sec_request_for_acc_closure")
    private char isSecRequestForAccClosure;
    @Column (name="is_sec_stop_payment")
    private char isSecStopPayment;
    @Column (name="is_sec_stop_payment_revoke")
    private char isSecStopPaymentRevoke;
    @Column (name="applicable_categories")
    private String applicableCategories;
    @Column (name="document_req_if_any")
    private String documentReqIfAny;
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

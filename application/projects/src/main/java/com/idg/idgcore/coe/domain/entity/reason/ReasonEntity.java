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
    private String primaryReasonCode;
    private String primaryReasonDesc;
    private char isPriAccountBlock;
    private char isPriAccountUnblock;
    private char isPriAccountClosure;
    private char isPriRequestForAccClosure;
    private char isPriStopPayment;
    private char isPriStopPaymentRevoke;
    private String secondaryReasonCode;
    private String secondaryReasonDesc;
    private char isSecAccountBlock;
    private char isSecAccountUnblock;
    private char isSecAccountClosure;
    private char isSecRequestForAccClosure;
    private char isSecStopPayment;
    private char isSecStopPaymentRevoke;
    private String applicableCategories;
    private String documentReqIfAny;
    private String status;
    private Integer recordVersion;
    private String authorized;
    private String lastConfigurationAction;

}

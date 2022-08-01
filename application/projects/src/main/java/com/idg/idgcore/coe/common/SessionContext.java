package com.idg.idgcore.coe.common;

import lombok.*;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionContext implements Serializable, Cloneable {
    /**
     * bank code of  the user
     */
    private String bankCode;
    /**
     * engine code of  the user
     */
    private String engineCode;
    /**
     * screen code of  the user
     */
    private String screenCode;
    /**
     * default branch code of the user.
     */
    private String defaultBranchCode;
    /**
     * System generated internal reference number
     */
    private String internalTransactionReferenceNumber;
    /**
     * user reference provided.
     */
    private String userTransactionReferenceNumber;
    /**
     * External transaction reference number
     */
    private String externalTransactionReferenceNumber;
    /**
     * default target unit of the user.
     */
    private String targetUnit;
    /**
     * Current posting date
     */
    private Date postingDate;
    /**
     * Value Date
     */
    private Date valueDate;
    /**
     * transaction branch for current operation.
     */
    private String transactionBranch;
    /**
     * default user id
     */
    private String userId;
    /**
     * Accessible target units.
     */
    private String[] accessibleTargetUnits;
    /**
     * Channel from which request is coming.
     */
    private String channel;
    /**
     * Task code is unique to each functional use case
     * For E.g Cash deposit will have separate task code, Cash withdraw will have separate task code.
     */
    private String taskCode;
    private Date localDateTime = new Date();
    /**
     * Used in case of reversals.
     */
    private String originalTransactionReferenceNumber;
    private Long externalBatchNumber;
    private String customAttributes;
    private ServiceInvocationModeType serviceInvocationModeType;
    private boolean allTargetUnitsSelected;
    private MutationType mutationType;
    private String userLocal;
    private String originatingModuleCode;

    private String[] role;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

package com.idg.idgcore.coe.exception;

import com.idg.idgcore.datatypes.core.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.enumerations.core.*;
import com.idg.idgcore.infra.*;

import java.util.Objects;

public class ExceptionUtil {
    private ExceptionUtil () {
    }

    public static void handleException (Error errorCode, Throwable t)  throws BusinessException {
        if (Objects.nonNull(t)) {
            BusinessException businessException = new BusinessException(errorCode.getCode(), errorCode.getMessage(), t);
            logException(businessException);
            setErrorCode(businessException);
            throw businessException;
        }
        else {
            handleException(errorCode);
        }
    }

    public static void handleException (Error errorCode) throws BusinessException{
        BusinessException businessException = new BusinessException(errorCode.getCode(), errorCode.getMessage());
        logException(businessException);
        setErrorCode(businessException);
        throw businessException;
    }

    public static void logException (BusinessException businessException){
        businessException.log();
    }

    public static void setErrorCode (BusinessException businessException){
        TransactionStatus transactionStatus = (TransactionStatus) ThreadAttribute.get(ThreadAttribute.TRANSACTION_STATUS);
        setBusinessValidations(businessException);
        businessException.log();
    }

    public static void setBusinessValidations(BusinessException businessException){
        TransactionStatus transactionStatus = (TransactionStatus) ThreadAttribute.get(ThreadAttribute.TRANSACTION_STATUS);
        BusinessValidationError[] errors = getValidationErrors();
        errors[0]=getValidationError(businessException);
        transactionStatus.setValidationErrors(errors);
    }

    private static BusinessValidationError[] getValidationErrors(){
        TransactionStatus transactionStatus = (TransactionStatus) ThreadAttribute.get(ThreadAttribute.TRANSACTION_STATUS);
        int size = transactionStatus.getValidationErrors() == null ? 1 : transactionStatus.getValidationErrors().length+1;
        return new BusinessValidationError[size];
    }

    public static BusinessValidationError getValidationError(BusinessException businessException){
        BusinessValidationError validationError = new BusinessValidationError();
        validationError.setErrorCode(businessException.getErrorCode());
        validationError.setErrorMessage(businessException.getMessage());
        return validationError;
    }
}

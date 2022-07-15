package com.idg.idgcore.coe.exception;

import com.idg.idgcore.datatypes.exceptions.BusinessException;

import java.util.Objects;

public class ExceptionUtil {
    private ExceptionUtil () {
    }

    public static void handleException (Error errorCode, Throwable t)  throws BusinessException {
        if (Objects.nonNull(t)) {
            throw new BusinessException(errorCode.getCode(), errorCode.getMessage(), t);
        }
        else {
            handleException(errorCode);
        }
    }

    public static void handleException (Error errorCode) throws BusinessException{
        throw new BusinessException(errorCode.getCode(), errorCode.getMessage());
    }

}

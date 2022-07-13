package com.idg.idgcore.coe.exception;

import com.idg.idgcore.datatypes.exceptions.*;

import java.util.*;

public class ExceptionUtil {
    private ExceptionUtil () {
    }

    public static void handleException (Error errorCode, Throwable t) {
        if (Objects.nonNull(t)) {
            throw new BusinessException(errorCode.getCode(), errorCode.getMessage(), t);
        }
        else {
            handleException(errorCode);
        }
    }

    public static void handleException (Error errorCode) {
        throw new BusinessException(errorCode.getCode(), errorCode.getMessage());
    }

}

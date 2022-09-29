package com.idg.idgcore.coe.domain.assembler.common;

import lombok.extern.slf4j.*;

import static com.idg.idgcore.coe.common.Constants.CHAR_N;
import static com.idg.idgcore.coe.common.Constants.CHAR_Y;

@Slf4j
public class AssemblerUtils {

    private AssemblerUtils(){

    }

    public static char getCharValueFromBoolean (boolean value) {
        return value ? CHAR_Y : CHAR_N;
    }

    public static boolean getBooleanValueFromChar (Character value) {
        return value.equals(CHAR_Y);
    }

}

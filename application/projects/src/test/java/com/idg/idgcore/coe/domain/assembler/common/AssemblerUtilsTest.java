package com.idg.idgcore.coe.domain.assembler.common;

import com.idg.idgcore.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class AssemblerUtilsTest {

    @Test
    void getCharValueFromBooleanTrue () {
        assertEquals(AssemblerUtils.getCharValueFromBoolean(true),'Y');

    }

    @Test
    void getCharValueFromBooleanFalse () {
        assertEquals(AssemblerUtils.getCharValueFromBoolean(false),'N');

    }

    @Test
    void getBooleanValueFromCharY () {
        assertEquals(AssemblerUtils.getBooleanValueFromChar('Y'),true);

    }

    @Test
    void getBooleanValueFromCharN () {
        assertEquals(AssemblerUtils.getBooleanValueFromChar('N'),false);

    }

}
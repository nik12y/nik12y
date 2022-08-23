package com.idg.idgcore.coe.app.config.urn;

import com.idg.idgcore.*;
import com.idg.idgcore.dto.context.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class TransactionReferenceNumberGeneratorTest {

    @InjectMocks
    private TransactionReferenceNumberGenerator transactionReferenceNumberGenerator;


    @Test
    void generateTransactionReferenceId () {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("0001");
        sessionContext.setEngineCode("TESENG");
        sessionContext.setScreenCode("TESCR");
        assertThat(transactionReferenceNumberGenerator.generateTransactionReferenceId(sessionContext),
                containsStringIgnoringCase("0001TESENGTESCR"));

    }

    @Test
    void generateTransactionReferenceIdWithoutEngScr () {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("0001");
        sessionContext.setEngineCode("");
        sessionContext.setScreenCode("");
        assertThat(transactionReferenceNumberGenerator.generateTransactionReferenceId(sessionContext),
                containsStringIgnoringCase("0001IDGCORE"));

    }

    @Test
    void generateTransactionReferenceIdExceedBankCode () {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("00000000001");
        sessionContext.setEngineCode("");
        sessionContext.setScreenCode("");
        assertThat(transactionReferenceNumberGenerator.generateTransactionReferenceId(sessionContext),
                containsString("Bank Code should be of length upto 10"));

    }

    @Test
    void generateTransactionReferenceIdExceedEngineCode () {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("0001");
        sessionContext.setEngineCode("ENGINECODEWIDTH");
        sessionContext.setScreenCode("");
        assertThat(transactionReferenceNumberGenerator.generateTransactionReferenceId(sessionContext),
                containsString("Engine Code should be of length upto 10"));

    }

    @Test
    void generateTransactionReferenceIdExceedScreenCode () {
        SessionContext sessionContext = new SessionContext();
        sessionContext.setBankCode("0001");
        sessionContext.setEngineCode("");
        sessionContext.setScreenCode("SCREENCODEWIDTH");
        assertThat(transactionReferenceNumberGenerator.generateTransactionReferenceId(sessionContext),
                containsString("Screen Code should be of length upto 10"));

    }

}
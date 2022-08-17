package com.idg.idgcore.coe.endpoint.graphql.resolver.transaction;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.FileReaderUtil;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class TransactionQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName ("test to get transaction by code ")
    @Test
    void getTransactionByCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Transaction/query-transactions.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citiess.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }


    @DisplayName ("test to get all transactionss ")
    @Test
    void getTransactions () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Transaction/query-transactionbycode.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-transactionbycode.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }

    @DisplayName ("test to get all transactions ")
    @Test
    void getTransactionsWrong () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Transaction/query-transactionbycode.graphqls");
        //        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertNotEquals(FileReaderUtil.read("response/success-response-transaction.json"),
                graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
    }

}
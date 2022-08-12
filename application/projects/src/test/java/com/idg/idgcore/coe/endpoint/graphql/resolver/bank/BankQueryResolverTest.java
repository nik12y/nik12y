package com.idg.idgcore.coe.endpoint.graphql.resolver.bank;

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
import static org.junit.jupiter.api.Assertions.*;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class BankQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for Get the record by code")
//    //@Test
    void getBankByCodeQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bank/query-bank-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
       // assertEquals(FileReaderUtil.read("response/get-bank-by-code-query.json"),graphQLResponse.getRawResponse().getBody(), true);

    }

    @DisplayName("JUnit test for Get all the records")
//    //@Test
    void getBanksQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bank/query-banks.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
     //   assertEquals(FileReaderUtil.read("response/get-banks-query.json"),graphQLResponse.getRawResponse().getBody(), true);


    }

}
package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcode;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class RiskCodeTypeQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for Get the record by code")
    @Test
    void getRiskCodeTypeByCodeQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/riskcode/query-riskcode-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
       // assertEquals(FileReaderUtil.read("response/get-bank-by-code-query.json"),graphQLResponse.getRawResponse().getBody(), true);

    }

    @DisplayName("JUnit test for Get all the records")
    @Test
    void getBranchesQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/riskcode/query-riskcodes.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
     //   assertEquals(FileReaderUtil.read("response/get-banks-query.json"),graphQLResponse.getRawResponse().getBody(), true);


    }

}
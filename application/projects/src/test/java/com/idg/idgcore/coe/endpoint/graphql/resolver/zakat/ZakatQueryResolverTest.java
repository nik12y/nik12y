package com.idg.idgcore.coe.endpoint.graphql.resolver.zakat;

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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class ZakatQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for Get the record by code")
    @Test
    void getZakatByYearQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/zakat/query-zakat-byyear.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @DisplayName("JUnit test for Get all the records")
    @Test
    void getZakatsQuery() throws IOException, JSONException {

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/zakat/query-zakats.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }
}
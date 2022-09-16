package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyAmountInWord;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CurrencyAmountInWordMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void addNewAmountInWords () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/add-maker-new-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void addNewAmountInWordsAuth () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/authorize-checker-new-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void addNewAmountInWordsTwo () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/add-maker-new-currency-two.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}
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
class CurrencyAmountInWordQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;



    @Test
    void getLanguageLov () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/lov-language.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getLocaleLov () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/lov-locale.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getAmountInWordsDetailsAuth () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/query-currency-amountIn-words-byCode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getAmountInWordsDetailsUnAuth () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/query-currency-amountIn-words-byCode-unauth.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getAmountInWordsList () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/currencyAmountInWords/query-currency-amountIn-words-all.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }


}
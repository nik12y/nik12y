package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyConfiguration;

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
class CurrencyConfigurationQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getCountryLocaleLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/lov-country-locale.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getWeekDaysLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/lov-week-days.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getRoundingRuleLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/lov-rounding-rule.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getDayDivisorLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/lov-day-divisor.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getCurrenciesList () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/query-currencyconfig.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getCurrencyDetailsWithoutAuthorized () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/query-currencyconfig-bycode-auth.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getCurrencyDetailsAuthorized () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/query-currencyconfig-bycode-unAuth.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}
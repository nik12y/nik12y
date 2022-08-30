package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyconfiguration;

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
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/lov-country-locale.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getWeekDaysLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/lov-week-days.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getRoundigRuleLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/lov-rounding-rule.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getDayDivisorLov () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/lov-day-divisor.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getCurrencyDetails () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/query-currencyconfig.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getCurrenciesList () throws IOException, JSONException{
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/CurrencyConfig/query-currencyconfig-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }
}
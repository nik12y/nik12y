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
class CurrencyConfigurationMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void processCurrencyConfigurationAdd () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/add-maker-new-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAddAuth () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/authorize-checker-new-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAddTwo () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/add-maker-new-currency-two.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAddAuthTwo () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/authorize-checker-new-currency-two.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAuthClose () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/authorize-checker-closed-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAuthCheckReopen () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/authorize-checker-reopened-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationAuthCheckUpdate () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/authorize-checker-updated-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationClose () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/close-maker-closed-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationDelete () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/delete-maker-deleted-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationNewDraft () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/draft-maker-draft-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationDraftUpdate () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/draft-maker-updated-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationModify () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/modify-maker-updated-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationReject () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/reject-checker-rejected-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processCurrencyConfigurationReopen () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/reopen-maker-reopened-currency.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processAmountFormatting () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/mutation-processamount-formatting.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @Test
    void processAmountRoundingTruncate () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/mutation-processamount-rounding-truncate.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void processAmountRoundingRoundNear () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/currencyConfiguration/mutation-processamount-rounding-roundnear.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}
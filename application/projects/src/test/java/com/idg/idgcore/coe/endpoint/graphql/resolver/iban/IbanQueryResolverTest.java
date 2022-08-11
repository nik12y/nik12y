package com.idg.idgcore.coe.endpoint.graphql.resolver.iban;

import com.fasterxml.jackson.core.*;
import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import com.idg.idgcore.coe.dto.iban.*;
import com.idg.idgcore.datatypes.exceptions.*;
import com.idg.idgcore.dto.context.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import com.idg.idgcore.coe.app.service.iban.*;

import java.io.*;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class IbanQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;
    @Test
    void getIbanByIbanCountryCode () throws IOException, JSONException {
        System.out.println(" Iban getIbanByIbanCountryCode ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/iban/iban-query-ibanbyibancountrycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read(
                "response/iban/iban-query-ibanbyibancountrycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" Iban getIbanByIbanCountryCode is DONE");
    }

    @Test
    void getIbans () throws IOException, JSONException {
        System.out.println(" Iban getIbans ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/iban/iban-query-ibans.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read(
                "response/iban/iban-query-ibans.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" Iban getIbans is DONE");

    }


   /* @DisplayName ("test to get iban by ibans ")
    @Test
    void getIbanByIbanCountryCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/iban/query-iban.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citiess.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }

    @DisplayName ("test to get all ibanbyibanCountryCode ")
    @Test
    void getCities () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/iban/query-ibanbyibancountrycode.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citybycode.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }

    @DisplayName ("test to get all ibans ")
    @Test
    void getCitiesWrong () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/iban/query-ibanbyibancountrycode.graphqls");
        //        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertNotEquals(FileReaderUtil.read("response/iban/success-response-iban.json"),
                graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
    }*/

    /*@Test
    void getIbanByIbanCountryCode () {
    }

    @Test
    void getIban () {
    }*/

}
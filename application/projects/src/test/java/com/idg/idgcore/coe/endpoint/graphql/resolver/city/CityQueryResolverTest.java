package com.idg.idgcore.coe.endpoint.graphql.resolver.city;

import static org.junit.jupiter.api.Assertions.*;

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
class CityQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName ("test to get city by code ")
    @Test
    void getCityByCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/City/query-cities.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citiess.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }


    @DisplayName ("test to get all cities ")
    @Test
    void getCities () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/City/query-citybycode.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citybycode.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }

    @DisplayName ("test to get all cities ")
    @Test
    void getCitiesWrong () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/City/query-citybycode.graphqls");
        //        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertNotEquals(FileReaderUtil.read("response/city/success-response-city.json"),
                graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
    }

}
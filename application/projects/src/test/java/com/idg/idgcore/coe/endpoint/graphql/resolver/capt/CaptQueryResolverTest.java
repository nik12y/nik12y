package com.idg.idgcore.coe.endpoint.graphql.resolver.capt;

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
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CaptQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName ("test to get capt by code ")
    @Test
    void getCaptByCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Capt/query-captAll.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-citiess.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }


    @DisplayName ("test to get all captss ")
    @Test
    void getCapts () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Capt/query-captbycode.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponse.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/query-captbycode.json"),
        //                graphQLResponse.getRawResponse().getBody(), true);
    }

    @DisplayName ("test to get all capts ")
    @Test
    void getCaptsWrong () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/Capt/query-captbycode.graphqls");
        //        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertNotEquals(FileReaderUtil.read("response/success-response-capt.json"),
                graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
    }

}
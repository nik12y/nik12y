package com.idg.idgcore.coe.endpoint.graphql.resolver.country;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CountryMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processCountrySaveDraft() throws IOException {
        String srcDraft = "request/country/draft-maker-draft-country.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processCountrySaveDraftToNew() throws IOException {
        String srcDraft = "request/country/add-maker-new-country.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }


//    authorize-checker-new-city


    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processCountrySaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/country/authorize-checker-new-country.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

}
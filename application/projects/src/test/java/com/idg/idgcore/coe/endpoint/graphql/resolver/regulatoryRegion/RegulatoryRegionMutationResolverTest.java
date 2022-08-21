package com.idg.idgcore.coe.endpoint.graphql.resolver.regulatoryRegion;


import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class RegulatoryRegionMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processRegulatoryRegionSaveDraft() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/draft-maker-draft-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processRegulatoryRegionSaveDraftToNew() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/add-maker-new-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processRegulatoryRegionSaveDraftToNewAuth() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/authorize-checker-new-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processRegulatoryRegionSaveDraftToUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/draft-maker-updated-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processRegulatoryRegionModifyBankGroup() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/modify-maker-updated-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processRegulatoryRegionAuthCheckerModifyUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/authorize-checker-updated-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processRegulatoryRegionRejectChecker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/reject-checker-rejected-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processRegulatoryRegionCloseMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/close-maker-closed-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processRegulatoryRegionAuthCheckerClosed() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/authorize-checker-closed-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processRegulatoryRegionReopenMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/reopen-maker-reopened-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processRegulatoryRegionAuthByCheckerToReopen() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/authorize-checker-reopened-regulatoryRegion.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processRegulatoryRegionDeleted() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/delete-maker-deleted-regulatoryRegion.graphqls");
        System.out.println("delete-maker-deleted-questionCategory");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}
















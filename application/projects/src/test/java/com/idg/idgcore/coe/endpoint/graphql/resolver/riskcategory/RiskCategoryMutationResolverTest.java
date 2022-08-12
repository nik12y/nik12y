package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcategory;


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
class RiskCategoryMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processRiskCategorySaveDraft() throws IOException {
        String srcDraft = "request/riskcategory/draft-maker-draft-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processRiskCategorySaveDraftToNew() throws IOException {
        String srcDraft = "request/riskcategory/add-maker-new-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


//    authorize-checker-new-city


    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processRiskCategorySaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/riskcategory/authorize-checker-new-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processRiskCategorySaveDraftToAuthChkClosed() throws IOException {
        String srcDraft = "request/riskcategory/authorize-checker-closed-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopened  ")
    @Test
    void processRiskCategorySaveDraftToAuthChkReopen() throws IOException {
        String srcDraft = "request/riskcategory/authorize-checker-reopened-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processRiskCategorySaveDraftToCloseMakerClosed() throws IOException {
        String srcDraft = "request/riskcategory/close-maker-closed-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



    @DisplayName(" JUnit test for Auth the Draft-Delete-Maker-Deleted")
    @Test
    void processBranchTypeSaveDraftToDeleteMakerDeleted() throws IOException {
        String srcDraft = "request/riskcategory/delete-maker-deleted-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCategory")
    @Test
    void processRiskCategorySaveDraftToMakerUpdated() throws IOException {
        String srcDraft = "request/riskcategory/draft-maker-updated-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCategory")
    @Test
    void processRiskCategorySaveDraftToModifyMakerUpdate() throws IOException {
        String srcDraft = "request/riskcategory/modify-maker-updated-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reject-Checker-Rejected")
    @Test
    void processRiskCategorySaveDraftToRejectCheckerRejected() throws IOException {
        String srcDraft = "request/riskcategory/reject-checker-rejected-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reject-checker-rejected-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopen-Maker-Reopened")
    @Test
    void processRiskCategorySaveDraftToReopenMakerReopened() throws IOException {
        String srcDraft = "request/riskcategory/reopen-maker-reopened-riskcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-riskcategory ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



}
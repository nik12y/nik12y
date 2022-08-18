package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcode;


import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class RiskCodeTypeMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processRiskCodeSaveDraft() throws IOException {
        String srcDraft = "request/riskcode/draft-maker-draft-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processRiskCodeSaveDraftToNew() throws IOException {
        String srcDraft = "request/riskcode/add-maker-new-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


//    authorize-checker-new-city


    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processRiskCodeSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/riskcode/authorize-checker-new-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processRiskCodeSaveDraftToAuthChkClosed() throws IOException {
        String srcDraft = "request/riskcode/authorize-checker-closed-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopened  ")
    @Test
    void processRiskCodeSaveDraftToAuthChkReopen() throws IOException {
        String srcDraft = "request/riskcode/authorize-checker-reopened-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Updated  ")
    @Test
    void processRiskCodeSaveDraftToAuthChkUpdated() throws IOException {
        String srcDraft = "request/riskcode/authorize-checker-updated-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-riskcode ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processRiskCodeSaveDraftToCloseMakerClosed() throws IOException {
        String srcDraft = "request/riskcode/close-maker-closed-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New-RiskCodeType-Mutation  ")
    @Test
    void processRiskCodeSaveDraftToNewBranchTypeMutation() throws IOException {
        String srcDraft = "request/riskcode/close-maker-closed-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-new-riskcodetype-mutation_maker ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Delete-Maker-Deleted")
    @Test
    void processRiskCodeSaveDraftToDeleteMakerDeleted() throws IOException {
        String srcDraft = "request/riskcode/delete-maker-deleted-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCodeType")
    @Test
    void processRiskCodeSaveDraftToMakerUpdated() throws IOException {
        String srcDraft = "request/riskcode/draft-maker-updated-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-riskcode ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCodeType")
    @Test
    void processRiskCodeSaveDraftToModifyMakerUpdate() throws IOException {
        String srcDraft = "request/riskcode/modify-maker-updated-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-riskcode ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reject-Checker-Rejected")
    @Test
    void processRiskCodeSaveDraftToRejectCheckerRejected() throws IOException {
        String srcDraft = "request/riskcode/reject-checker-rejected-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reject-checker-rejected-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @Disabled
    @DisplayName(" JUnit test for Auth the Draft-Reopen-Maker-Reopened")
    @Test
    void processRiskCodeSaveDraftToReopenMakerReopened() throws IOException {
//        String srcDraft = "request/riskcode/reopen-maker-reopened-riskcode.graphqls";
//        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
//        System.out.println(" reopen-maker-reopened-riskcode ");
//        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
//        System.out.println(" DONE ");

        String srcDraft = "request/riskcode/reopen-maker-reopened-riskcode.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reject-checker-rejected-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



}
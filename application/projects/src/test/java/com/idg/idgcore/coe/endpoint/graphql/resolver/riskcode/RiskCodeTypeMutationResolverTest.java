package com.idg.idgcore.coe.endpoint.graphql.resolver.riskcode;


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
class RiskCodeTypeMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processbranchTypeSaveDraft() throws IOException {
        String srcDraft = "request/riskcodetyperequest/draft-maker-draft-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processBranchTypeSaveDraftToNew() throws IOException {
        String srcDraft = "request/riskcodetyperequest/add-maker-new-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


//    authorize-checker-new-city


    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processBranchTypeSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/riskcodetyperequest/authorize-checker-new-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkClosed() throws IOException {
        String srcDraft = "request/riskcodetyperequest/authorize-checker-closed-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopened  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkReopen() throws IOException {
        String srcDraft = "request/riskcodetyperequest/authorize-checker-reopened-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Updated  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkUpdated() throws IOException {
        String srcDraft = "request/riskcodetyperequest/authorize-checker-updated-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processBranchTypeSaveDraftToCloseMakerClosed() throws IOException {
        String srcDraft = "request/riskcodetyperequest/close-maker-closed-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New-RiskCodeType-Mutation  ")
    @Test
    void processBranchTypeSaveDraftToNewBranchTypeMutation() throws IOException {
        String srcDraft = "request/riskcodetyperequest/close-new-riskcodetype-mutation_maker.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-new-riskcodetype-mutation_maker ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Delete-Maker-Deleted")
    @Test
    void processBranchTypeSaveDraftToDeleteMakerDeleted() throws IOException {
        String srcDraft = "request/riskcodetyperequest/delete-maker-deleted-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCodeType")
    @Test
    void processBranchTypeSaveDraftToMakerUpdated() throws IOException {
        String srcDraft = "request/riskcodetyperequest/draft-maker-updated-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-RiskCodeType")
    @Test
    void processBranchTypeSaveDraftToModifyMakerUpdate() throws IOException {
        String srcDraft = "request/riskcodetyperequest/modify-maker-updated-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reject-Checker-Rejected")
    @Test
    void processBranchTypeSaveDraftToRejectCheckerRejected() throws IOException {
        String srcDraft = "request/riskcodetyperequest/reject-checker-rejected-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reject-checker-rejected-riscodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopen-Maker-Reopened")
    @Test
    void processBranchTypeSaveDraftToReopenMakerReopened() throws IOException {
        String srcDraft = "request/riskcodetyperequest/reopen-maker-reopened-riskcodetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-riskcodetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



}
package com.idg.idgcore.graphqlsdl.resolver;


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
class BranchTypeMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processbranchTypeSaveDraft() throws IOException {
        String srcDraft = "request/branchtyperequest/draft-maker-draft-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processBranchTypeSaveDraftToNew() throws IOException {
        String srcDraft = "request/branchtyperequest/add-maker-new-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


//    authorize-checker-new-city


    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processBranchTypeSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/branchtyperequest/authorize-checker-new-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkClosed() throws IOException {
        String srcDraft = "request/branchtyperequest/authorize-checker-closed-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopened  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkReopen() throws IOException {
        String srcDraft = "request/branchtyperequest/authorize-checker-reopened-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Updated  ")
    @Test
    void processBranchTypeSaveDraftToAuthChkUpdated() throws IOException {
        String srcDraft = "request/branchtyperequest/authorize-checker-updated-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Closed  ")
    @Test
    void processBranchTypeSaveDraftToCloseMakerClosed() throws IOException {
        String srcDraft = "request/branchtyperequest/close-maker-closed-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

//    @DisplayName(" JUnit test for Auth the Draft-New-BranchType-Mutation  ")
//    @Test
//    void processBranchTypeSaveDraftToNewBranchTypeMutation() throws IOException {
//        String srcDraft = "request/branchtyperequest/close-new-branchtype-mutation_maker.graphqls";
//
//        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
//        System.out.println(" close-new-branchtype-mutation_maker ");
//        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
//        System.out.println(" DONE ");
//
//    }

    @DisplayName(" JUnit test for Auth the Draft-Delete-Maker-Deleted")
    @Test
    void processBranchTypeSaveDraftToDeleteMakerDeleted() throws IOException {
        String srcDraft = "request/branchtyperequest/delete-maker-deleted-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-BranchType")
    @Test
    void processBranchTypeSaveDraftToMakerUpdated() throws IOException {
        String srcDraft = "request/branchtyperequest/draft-maker-updated-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Maker-Updated-BranchType")
    @Test
    void processBranchTypeSaveDraftToModifyMakerUpdate() throws IOException {
        String srcDraft = "request/branchtyperequest/modify-maker-updated-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reject-Checker-Rejected")
    @Test
    void processBranchTypeSaveDraftToRejectCheckerRejected() throws IOException {
        String srcDraft = "request/branchtyperequest/reject-checker-rejected-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reject-checker-rejected-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-Reopen-Maker-Reopened")
    @Test
    void processBranchTypeSaveDraftToReopenMakerReopened() throws IOException {
        String srcDraft = "request/branchtyperequest/reopen-maker-reopened-branchtype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-branchtype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }



}
package com.idg.idgcore.coe.endpoint.graphql.resolver.branchSystemDate;

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
public class BranchSystemMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processBranchSystemDateSaveToDraft() throws IOException {
        String srcDraft = "request/branchSystemDate/draft-maker-draft-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processBranchSystemDateSaveDraftToNew() throws IOException {
        String srcDraft = "request/branchSystemDate/add-maker-new-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New Record ")
    @Test
    void processBranchSystemDateSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/branchSystemDate/authorize-checker-new-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as draft as updated ")
    @Test
    void processBranchSystemDateSaveDraftToUpdated() throws IOException {
        String srcDraft = "request/branchSystemDate/draft-maker-updated-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save draft as updated Auth")
    @Test
    void processBranchSystemDateSaveDraftToUpdatedAuth() throws IOException {
        String srcDraft = "request/branchSystemDate/authorize-checker-updated-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processBranchSystemDateSaveDraftToModify() throws IOException {
        String srcDraft = "request/branchSystemDate/modify-maker-updated-branchsystem.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processBranchSystemDateSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/branchSystemDate/authorize-checker-updated-branchsystem.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for close draft as closed ")
    @Test
    void processBranchSystemDateCloseDraftToClosed() throws IOException {
        String srcDraft = "request/branchSystemDate/close-maker-closed-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save auth draft as closed ")
    @Test
    void processBranchSystemDateAuthDraftToClosed() throws IOException {
        String srcDraft = "request/branchSystemDate/authorize-checker-closed-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for reopen as draft as Reopened ")
    @Test
    void processBranchSystemDateReopenDraftToReopened() throws IOException {
        String srcDraft = "request/branchSystemDate/reopen-maker-reopened-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Auth draft as reopened ")
    @Test
    void processBranchSystemDateAuthDraftToReopened() throws IOException {
        String srcDraft = "request/branchSystemDate/authorize-checker-reopened-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for delete as draft as deleted ")
    @Test
    void processBranchSystemDateDeleteDraftToDeleted() throws IOException {
        String srcDraft = "request/branchSystemDate/delete-maker-deleted-branchsystem.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}

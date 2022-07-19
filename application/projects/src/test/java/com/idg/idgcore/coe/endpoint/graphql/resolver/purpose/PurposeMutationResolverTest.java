package com.idg.idgcore.coe.endpoint.graphql.resolver.purpose;

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
class PurposeMutationResolverTest
{

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processPurposeSaveToDraft() throws IOException {
        String srcDraft = "request/purpose/draft-maker-draft-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processPurposeSaveDraftToNew() throws IOException {
        String srcDraft = "request/purpose/add-maker-new-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New Record ")
    @Test
    void processPurposeSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/purpose/authorize-checker-new-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as draft as updated ")
    @Test
    void processPurposeSaveDraftToUpdated() throws IOException {
        String srcDraft = "request/purpose/draft-maker-updated-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save draft as updated Auth")
    @Test
    void processPurposeSaveDraftToUpdatedAuth() throws IOException {
        String srcDraft = "request/purpose/authorize-checker-updated-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processPurposeSaveDraftToModify() throws IOException {
        String srcDraft = "request/purpose/modify-maker-updated-purpose.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processPurposeSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/purpose/authorize-checker-updated-purpose.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for close draft as closed ")
    @Test
    void processPurposeCloseDraftToClosed() throws IOException {
        String srcDraft = "request/purpose/close-maker-closed-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save auth draft as closed ")
    @Test
    void processPurposeAuthDraftToClosed() throws IOException {
        String srcDraft = "request/purpose/authorize-checker-closed-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for reopen as draft as Reopened ")
    @Test
    void processPurposeReopenDraftToReopened() throws IOException {
        String srcDraft = "request/purpose/reopen-maker-reopened-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Auth draft as reopened ")
    @Test
    void processPurposeAuthDraftToReopened() throws IOException {
        String srcDraft = "request/purpose/authorize-checker-reopened-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for delete as draft as deleted ")
    @Test
    void processPurposeDeleteDraftToDeleted() throws IOException {
        String srcDraft = "request/purpose/delete-maker-deleted-purpose.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-purpose ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}
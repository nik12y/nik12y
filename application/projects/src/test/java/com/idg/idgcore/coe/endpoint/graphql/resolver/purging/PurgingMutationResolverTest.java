package com.idg.idgcore.coe.endpoint.graphql.resolver.purging;

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
public class PurgingMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processPurgingSaveToDraft() throws IOException {
        String srcDraft = "request/purging/draft-maker-draft-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processPurgingSaveDraftToNew() throws IOException {
        String srcDraft = "request/purging/add-maker-new-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New Record ")
    @Test
    void processPurgingSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/purging/authorize-checker-new-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as draft as updated ")
    @Test
    void processPurgingSaveDraftToUpdated() throws IOException {
        String srcDraft = "request/purging/draft-maker-updated-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save draft as updated Auth")
    @Test
    void processPurgingSaveDraftToUpdatedAuth() throws IOException {
        String srcDraft = "request/purging/authorize-checker-updated-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processPurgingSaveDraftToModify() throws IOException {
        String srcDraft = "request/purging/modify-maker-updated-purging.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processPurgingSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/purging/authorize-checker-updated-purging.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for close draft as closed ")
    @Test
    void processPurgingCloseDraftToClosed() throws IOException {
        String srcDraft = "request/purging/close-maker-closed-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save auth draft as closed ")
    @Test
    void processPurgingAuthDraftToClosed() throws IOException {
        String srcDraft = "request/purging/authorize-checker-closed-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for reopen as draft as Reopened ")
    @Test
    void processPurgingReopenDraftToReopened() throws IOException {
        String srcDraft = "request/purging/reopen-maker-reopened-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Auth draft as reopened ")
    @Test
    void processPurgingAuthDraftToReopened() throws IOException {
        String srcDraft = "request/purging/authorize-checker-reopened-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for delete as draft as deleted ")
    @Test
    void processPurgingDeleteDraftToDeleted() throws IOException {
        String srcDraft = "request/purging/delete-maker-deleted-purging.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-purging ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}

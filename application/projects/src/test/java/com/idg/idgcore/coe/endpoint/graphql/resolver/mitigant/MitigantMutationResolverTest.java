package com.idg.idgcore.coe.endpoint.graphql.resolver.mitigant;

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
public class MitigantMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processMitigantSaveToDraft() throws IOException {
        String srcDraft = "request/mitigant/draft-maker-draft-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New Record ")
    @Test
    void processMitigantSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/mitigant/authorize-checker-new-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as draft as updated ")
    @Test
    void processMitigantSaveDraftToUpdated() throws IOException {
        String srcDraft = "request/mitigant/draft-maker-updated-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save draft as updated Auth")
    @Test
    void processMitigantSaveDraftToUpdatedAuth() throws IOException {
        String srcDraft = "request/mitigant/authorize-checker-updated-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processMitigantSaveDraftToModify() throws IOException {
        String srcDraft = "request/mitigant/modify-maker-updated-mitigant.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processMitigantSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/mitigant/authorize-checker-updated-mitigant.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for close draft as closed ")
    @Test
    void processMitigantCloseDraftToClosed() throws IOException {
        String srcDraft = "request/mitigant/close-maker-closed-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save auth draft as closed ")
    @Test
    void processMitigantAuthDraftToClosed() throws IOException {
        String srcDraft = "request/mitigant/authorize-checker-closed-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for reopen as draft as Reopened ")
    @Test
    void processMitigantReopenDraftToReopened() throws IOException {
        String srcDraft = "request/mitigant/reopen-maker-reopened-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Auth draft as reopened ")
    @Test
    void processMitigantAuthDraftToReopened() throws IOException {
        String srcDraft = "request/mitigant/authorize-checker-reopened-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for delete as draft as deleted ")
    @Test
    void processPurposeDeleteDraftToDeleted() throws IOException {
        String srcDraft = "request/mitigant/delete-maker-deleted-mitigant.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-mitigant ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

}

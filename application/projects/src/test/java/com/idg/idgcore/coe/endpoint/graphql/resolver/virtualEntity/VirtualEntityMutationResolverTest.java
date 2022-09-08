package com.idg.idgcore.coe.endpoint.graphql.resolver.virtualEntity;

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
class VirtualEntityMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processVirtualEntitySaveToDraft()  throws IOException {
        String srcDraft = "request/virtualEntity/draft-maker-draft-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processVirtualEntitySaveDraftToNew() throws IOException {
        String srcDraft = "request/virtualEntity/add-maker-new-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New Record ")
    @Test
    void processVirtualEntitySaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/virtualEntity/authorize-checker-new-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as draft as updated ")
    @Test
    void processVirtualEntitySaveDraftToUpdated() throws IOException {
        String srcDraft = "request/virtualEntity/draft-maker-updated-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save draft as updated Auth")
    @Test
    void processVirtualEntitySaveDraftToUpdatedAuth() throws IOException {
        String srcDraft = "request/virtualEntity/authorize-checker-updated-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processVirtualEntitySaveDraftToModify() throws IOException {
        String srcDraft = "request/virtualEntity/modify-maker-updated-virtualEntity.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processVirtualEntitySaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/virtualEntity/authorize-checker-updated-virtualEntity.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for close draft as closed ")
    @Test
    void processVirtualEntityCloseDraftToClosed() throws IOException {
        String srcDraft = "request/virtualEntity/close-maker-closed-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save auth draft as closed ")
    @Test
    void processVirtualEntityAuthDraftToClosed() throws IOException {
        String srcDraft = "request/virtualEntity/authorize-checker-closed-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for reopen as draft as Reopened ")
    @Test
    void processVirtualEntityReopenDraftToReopened() throws IOException {
        String srcDraft = "request/virtualEntity/reopen-maker-reopened-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Auth draft as reopened ")
    @Test
    void processVirtualEntityAuthDraftToReopened() throws IOException {
        String srcDraft = "request/virtualEntity/authorize-checker-reopened-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for delete as draft as deleted ")
    @Test
    void processVirtualEntityDeleteDraftToDeleted() throws IOException {
        String srcDraft = "request/virtualEntity/delete-maker-deleted-virtualEntity.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-virtualEntity ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

}
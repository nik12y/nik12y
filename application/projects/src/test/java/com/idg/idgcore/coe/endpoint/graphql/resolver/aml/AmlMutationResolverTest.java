package com.idg.idgcore.coe.endpoint.graphql.resolver.aml;

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
class AmlMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
//    @Test
    void processAmlSaveToDraft() throws IOException {
        String srcDraft = "request/aml/draft-maker-draft-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-draft-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for save as Draft to New ")
//    @Test
    void processAmlSaveDraftToNew() throws IOException {
        String srcDraft = "request/aml/add-maker-new-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" add-maker-new-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
//    @Test
    void processAmlSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/aml/authorize-checker-new-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName("JUnit test for Draft Update")
//    @Test
    void processAmlSaveDrafttoUpdate() throws IOException {
        String srcDraft = "request/aml/draft-maker-updated-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit test for Auth the Draft-Update record")
//    @Test
    void processAmlSaveDrafttoUpdateAuth() throws IOException {
        String srcDraft = "request/aml/authorize-checker-updated-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
//    @Test
    void processAmlSaveDraftToModify() throws IOException {
        String srcDraft = "request/aml/modify-maker-updated-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
//    @Test
    void processAmlSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/aml/authorize-checker-updated-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Save as Close")
//    @Test
    void processAmlSaveClose() throws IOException {
        String srcDraft = "request/aml/close-maker-closed-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Close Record")
//    @Test
    void processAmlSaveCloseToAuth() throws IOException {
        String srcDraft = "request/aml/authorize-checker-closed-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Reopen a record")
//    @Test
    void processAmlReopenRecord() throws IOException {
        String srcDraft = "request/aml/reopen-maker-reopened-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Reopen Request")
//    @Test
    void processAmlAuthReopenRecord() throws IOException {
        String srcDraft = "request/aml/authorize-checker-reopened-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Delete")
//    @Test
    void processAmlDeleteDraft() throws IOException {
        String srcDraft = "request/aml/delete-maker-deleted-aml.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-aml ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}
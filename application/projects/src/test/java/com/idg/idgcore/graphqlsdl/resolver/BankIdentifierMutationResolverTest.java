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
public class BankIdentifierMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processBankIdentifierSaveToDraft() throws IOException {
        String srcDraft = "request/bankidentifier/draft-maker-draft-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-draft-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processBankIdentifierSaveDraftToNew() throws IOException {
        String srcDraft = "request/bankidentifier/add-maker-new-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" add-maker-new-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processBankIdentifierSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/bankidentifier/authorize-checker-new-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName("JUnit test for Draft Update")
    @Test
    void processBankIdentifierSaveDrafttoUpdate() throws IOException {
        String srcDraft = "request/bankidentifier/draft-maker-updated-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit test for Auth the Draft-Update record")
    @Test
    void processBankIdentifierSaveDrafttoUpdateAuth() throws IOException {
        String srcDraft = "request/bankidentifier/authorize-checker-updated-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processBankIdentifierSaveDraftToModify() throws IOException {
        String srcDraft = "request/bankidentifier/modify-maker-updated-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processBankIdentifierSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/bankidentifier/authorize-checker-updated-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Save as Close")
    @Test
    void processBankIdentifierSaveClose() throws IOException {
        String srcDraft = "request/bankidentifier/close-maker-closed-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Close Record")
    @Test
    void processBankIdentifierSaveCloseToAuth() throws IOException {
        String srcDraft = "request/bankidentifier/authorize-checker-closed-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Reopen a record")
    @Test
    void processBankIdentifierReopenRecord() throws IOException {
        String srcDraft = "request/bankidentifier/reopen-maker-reopened-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Reopen Request")
    @Test
    void processBankIdentifierAuthReopenRecord() throws IOException {
        String srcDraft = "request/bankidentifier/authorize-checker-reopened-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Delete")
    @Test
    void processBankIdentifierDeleteDraft() throws IOException {
        String srcDraft = "request/bankidentifier/delete-maker-deleted-bankidentifier.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-bankidentifier ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}

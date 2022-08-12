package com.idg.idgcore.coe.endpoint.graphql.resolver.zakat;

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
class ZakatMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;


    @DisplayName("JUnit test for save as Draft")
    @Test
    void processZakatSaveToDraft() throws IOException {
        String srcDraft = "request/zakat/draft-maker-draft-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-draft-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processZakatSaveDraftToNew() throws IOException {
        String srcDraft = "request/zakat/add-maker-new-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" add-maker-new-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processZakatSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/zakat/authorize-checker-new-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName("JUnit test for Draft Update")
    @Test
    void processZakatSaveDraftToUpdate() throws IOException {
        String srcDraft = "request/zakat/draft-maker-updated-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-updated-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit test for Auth the Draft-Update record")
    @Test
    void processZakatSaveDraftToUpdateAuth() throws IOException {
        String srcDraft = "request/zakat/authorize-checker-updated-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void processZakatSaveDraftToModify() throws IOException {
        String srcDraft = "request/zakat/modify-maker-updated-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void processZakatSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/zakat/authorize-checker-updated-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-modified-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Save as Close")
    @Test
    void processZakatSaveClose() throws IOException {
        String srcDraft = "request/zakat/close-maker-closed-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Close Record")
    @Test
    void processZakatSaveCloseToAuth() throws IOException {
        String srcDraft = "request/zakat/authorize-checker-closed-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Reopen a record")
    @Test
    void processZakatReopenRecord() throws IOException {
        String srcDraft = "request/zakat/reopen-maker-reopened-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Reopen Request")
    @Test
    void processZakatAuthReopenRecord() throws IOException {
        String srcDraft = "request/zakat/authorize-checker-reopened-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Delete")
    @Test
    void processZakatDeleteDraft() throws IOException {
        String srcDraft = "request/zakat/delete-maker-deleted-zakat.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-zakat ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}
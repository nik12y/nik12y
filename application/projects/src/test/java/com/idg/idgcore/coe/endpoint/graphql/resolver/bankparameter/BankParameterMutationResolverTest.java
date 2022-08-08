package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class BankParameterMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;



    @DisplayName("JUnit Bank Parameter test for save as Draft")
    @Test
    void processBankParameterSaveDraft() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-draft-maker-draft.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for save as Draft to New ")
    @Test
    void processBankParameterSaveDraftToNew() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-add-maker-new.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for Authorize the Draft-New record  ")
    @Test
    void processBankParameterSaveDraftToNewAuthorize() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-authorize-checker-new.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for Active Close record  ")
    @Test
    void processBankParameterSaveClose() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-close-maker-closed.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for Authorize Close record  ")
    @Test
    void processBankParameterSaveAuthorizeClose() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-authorize-checker-closed.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for Reopen record  ")
    @Test
    void processBankParameterSaveReopen() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-reopen-maker-reopened.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for Authorize Reopen record  ")
    @Test
    void processBankParameterSaveAuthorizeReopen() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-authorize-checker-reopened.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for draft-maker-draft-update record  ")
    @Test
    void processBankParameterSaveDraftForUpdate() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-draft-maker-draft-update.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for modify-maker-updated. record  ")
    @Test
    void processBankParameterSaveUpdateDraft() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-modify-maker-updated.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for authorize-checker-updated record  ")
    @Test
    void processBankParameterSaveAuthorizeUpdateDraft() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-authorize-checker-updated.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for draft-maker-draft-delete record  ")
    @Test
    void processBankParameterSaveDeleteDraft() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-draft-maker-draft-delete.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for delete-maker-deleted record  ")
    @Test
    void processBankParameterSaveDelete() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-delete-maker-deleted-new.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for add-maker-new-delete record  ")
    @Test
    void processBankParameterSaveDeleteNew() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-add-maker-new-delete.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @DisplayName(" JUnit Bank Parameter test for add-maker-new-delete record  ")
    @Test
    void processBankParameterSaveDeleteNewAdd() throws IOException {
        String srcDraft = "request/bankparameter/bankparameter-delete-maker-deleted-new.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }


}
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
class GroupBankingMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processGroupBankingSaveDraft() throws IOException {
        String srcDraft = "request/bankgroup/draft-maker-draft-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processGroupBankingSaveDraftToNew() throws IOException {
        String srcDraft = "request/bankgroup/add-maker-new-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

//    authorize-checker-new-city
    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processGroupBankingSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/bankgroup/authorize-checker-new-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-bankgroup ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }
    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processGroupBankingSaveDraftToUpdate() throws IOException {
        String srcDraft = "request/bankgroup/draft-maker-updated-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("draft-maker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processGroupBankingModifyBankGroup() throws IOException {
        String srcDraft = "request/bankgroup/modify-maker-updated-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("modify-maker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processGroupBankingAuthCheckerModifyUpdate() throws IOException {
        String srcDraft = "request/bankgroup/authorize-checker-updated-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processGroupBankingRejectChecker() throws IOException {
        String srcDraft = "request/bankgroup/reject-checker-rejected-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reject-checker-rejected-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processGroupBankingCloseMaker() throws IOException {
        String srcDraft = "request/bankgroup/close-maker-closed-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("close-maker-closed-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processGroupBankingAuthCheckerClosed() throws IOException {
        String srcDraft = "request/bankgroup/authorize-checker-closed-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-closed-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processGroupBankingReopenMaker() throws IOException {
        String srcDraft = "request/bankgroup/reopen-maker-reopened-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reopen-maker-reopened-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processGroupBankingAuthByCheckerToReopen() throws IOException {
        String srcDraft = "request/bankgroup/authorize-checker-reopened-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-reopened-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processGroupBankingDeleted() throws IOException {
        String srcDraft = "request/bankgroup/delete-maker-deleted-bankgroup.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("delete-maker-deleted-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }


}
















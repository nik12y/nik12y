package com.idg.idgcore.coe.endpoint.graphql.resolver.groupBanking;


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
        String srcDraft = "request/groupBanking/draft-maker-draft-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processGroupBankingSaveDraftToNew() throws IOException {
        String srcDraft = "request/groupBanking/add-maker-new-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

//    authorize-checker-new-city
    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processGroupBankingSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/groupBanking/authorize-checker-new-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-bankgroup ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }
    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processGroupBankingSaveDraftToUpdate() throws IOException {
        String srcDraft = "request/groupBanking/draft-maker-updated-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("draft-maker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processGroupBankingModifyBankGroup() throws IOException {
        String srcDraft = "request/groupBanking/modify-maker-updated-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("modify-maker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processGroupBankingAuthCheckerModifyUpdate() throws IOException {
        String srcDraft = "request/groupBanking/authorize-checker-updated-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-updated-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processGroupBankingRejectChecker() throws IOException {
        String srcDraft = "request/groupBanking/reject-checker-rejected-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reject-checker-rejected-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processGroupBankingCloseMaker() throws IOException {
        String srcDraft = "request/groupBanking/close-maker-closed-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("close-maker-closed-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processGroupBankingAuthCheckerClosed() throws IOException {
        String srcDraft = "request/groupBanking/authorize-checker-closed-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-closed-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processGroupBankingReopenMaker() throws IOException {
        String srcDraft = "request/groupBanking/reopen-maker-reopened-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reopen-maker-reopened-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processGroupBankingAuthByCheckerToReopen() throws IOException {
        String srcDraft = "request/groupBanking/authorize-checker-reopened-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-reopened-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processGroupBankingDeleted() throws IOException {
        String srcDraft = "request/groupBanking/delete-maker-deleted-groupBanking.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("delete-maker-deleted-groupBanking");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }


}
















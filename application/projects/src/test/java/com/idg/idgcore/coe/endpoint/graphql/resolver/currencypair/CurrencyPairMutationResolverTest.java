package com.idg.idgcore.coe.endpoint.graphql.resolver.currencypair;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import com.idg.idgcore.coe.app.service.currencypair.ICurrencyPairApplicationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CurrencyPairMutationResolverTest {

    @InjectMocks
    CurrencyPairMutationResolver currencyPairMutationResolver;

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    private ICurrencyPairApplicationService currencyPairApplicationService;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processCurrencyPairSaveToDraft() throws IOException {
        String srcDraft = "request/currencypair/draft-maker-draft-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processCurrencyPairSaveDraftToNew() throws IOException {
        String srcDraft = "request/currencypair/add-maker-new-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processCurrencyPairSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/currencypair/authorize-checker-new-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processCurrencyPairSaveDraftToUpdate() throws IOException {
        String srcDraft = "request/currencypair/draft-maker-updated-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processCurrencyPairModify() throws IOException {
        String srcDraft = "request/currencypair/modify-maker-updated-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processCurrencyPairAuthCheckerModifyUpdate() throws IOException {
        String srcDraft = "request/currencypair/authorize-checker-updated-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processCurrencyPairRejectChecker() throws IOException {
        String srcDraft = "request/currencypair/reject-checker-rejected-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processCurrencyPairCloseMaker() throws IOException {
        String srcDraft = "request/currencypair/close-maker-closed-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processCurrencyPairAuthCheckerClosed() throws IOException {
        String srcDraft = "request/currencypair/authorize-checker-closed-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processCurrencyPairReopenMaker() throws IOException {
        String srcDraft = "request/currencypair/reopen-maker-reopened-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processCurrencyPairAuthByCheckerToReopen() throws IOException {
        String srcDraft = "request/currencypair/authorize-checker-reopened-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processCurrencyPairDeleted() throws IOException {
        String srcDraft = "request/currencypair/delete-maker-deleted-currencypair.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}
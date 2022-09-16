package com.idg.idgcore.coe.endpoint.graphql.resolver.mulbranchparameter;


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
public class MulBranchParameterMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processMulBranchParameterSaveDraft() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/draft-maker-draft-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processMulBranchParameterSaveDraftToNew() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/mulBranchParameter-add-maker-new.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processMulBranchParameterSaveDraftToNewAuth() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/authorize-checker-new-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processMulBranchParameterSaveDraftToUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/draft-maker-updated-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processMulBranchParameterModifyBankGroup() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/mulbranchparameter/modify-maker-updated-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processMulBranchParameterAuthCheckerModifyUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/authorize-checker-updated-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processMulBranchParameterRejectChecker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/reject-checker-rejected-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processMulBranchParameterCloseMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/reject-checker-rejected-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processMulBranchParameterAuthCheckerClosed() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/authorize-checker-closed-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processMulBranchParameterReopenMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/mulbranchparameter/reopen-maker-reopened-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processMulBranchParameterAuthByCheckerToReopen() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/authorize-checker-reopened-mulBranchParameter.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processMulBranchParameterDeleted() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mulbranchparameter/delete-maker-deleted-mulBranchParameter.graphqls");
        System.out.println("delete-maker-deleted-questionCategory");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

}



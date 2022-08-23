package com.idg.idgcore.coe.endpoint.graphql.resolver.questionCategory;


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
class QuestionCategoryMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processQuestionCategorySaveDraft() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/draft-maker-draft-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processQuestionCategorySaveDraftToNew() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/add-maker-new-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processQuestionCategorySaveDraftToNewAuth() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/authorize-checker-new-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processQuestionCategorySaveDraftToUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/draft-maker-updated-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the bankGroup ")
    @Test
    void processQuestionCategoryModifyBankGroup() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/questionCategory/modify-maker-updated-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processQuestionCategoryAuthCheckerModifyUpdate() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/authorize-checker-updated-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processQuestionCategoryRejectChecker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/reject-checker-rejected-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processQuestionCategoryCloseMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/close-maker-closed-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processQuestionCategoryAuthCheckerClosed() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/authorize-checker-closed-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processQuestionCategoryReopenMaker() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/questionCategory/reopen-maker-reopened-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processQuestionCategoryAuthByCheckerToReopen() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/authorize-checker-reopened-questionCategory.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processQuestionCategoryDeleted() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/delete-maker-deleted-questionCategory.graphqls");
        System.out.println("delete-maker-deleted-questionCategory");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }


}
















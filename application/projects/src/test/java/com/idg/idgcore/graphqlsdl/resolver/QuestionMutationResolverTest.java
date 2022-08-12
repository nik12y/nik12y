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
class QuestionMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processQuestionSaveDraft() throws IOException {
        String srcDraft = "request/question/draft-maker-draft-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processQuestionSaveDraftToNew() throws IOException {
        String srcDraft = "request/question/add-maker-new-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void processQuestionSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/question/authorize-checker-new-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-new-question ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }
    @DisplayName(" JUnit test for save as Draft To Update ")
    @Test
    void processQuestionSaveDraftToUpdate() throws IOException {
        String srcDraft = "request/question/draft-maker-updated-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("draft-maker-updated-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for modify the Question ")
    @Test
    void processQuestionModifyBankGroup() throws IOException {
        String srcDraft = "request/question/modify-maker-updated-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("modify-maker-updated-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth checker update ")
    @Test
    void processQuestionAuthCheckerModifyUpdate() throws IOException {
        String srcDraft = "request/question/authorize-checker-updated-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-updated-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Checker Rejected  ")
    @Test
    void processQuestionRejectChecker() throws IOException {
        String srcDraft = "request/question/reject-checker-rejected-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reject-checker-rejected-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Maker Close ")
    @Test
    void processQuestionCloseMaker() throws IOException {
        String srcDraft = "request/question/close-maker-closed-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("close-maker-closed-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
    @DisplayName(" JUnit test for Auth Checker closed  ")
    @Test
    void processQuestionAuthCheckerClosed() throws IOException {
        String srcDraft = "request/question/authorize-checker-closed-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-closed-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Re-Open Maker")
    @Test
    void processQuestionReopenMaker() throws IOException {
        String srcDraft = "request/question/reopen-maker-reopened-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("reopen-maker-reopened-bankgroup");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Auth By Checker to Re-Open ")
    @Test
    void processQuestionAuthByCheckerToReopen() throws IOException {
        String srcDraft = "request/question/authorize-checker-reopened-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-reopened-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName(" JUnit test for Deleted ")
    @Test
    void processQuestionDeleted() throws IOException {
        String srcDraft = "request/question/delete-maker-deleted-question.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("delete-maker-deleted-question");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }


}
















package com.idg.idgcore.coe.endpoint.graphql.resolver.verificationcategory;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import com.idg.idgcore.coe.app.service.verificationcategory.AppVerCategoryApplicationService;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class AppVerCategoryMutationResolverTest {

    @InjectMocks
    AppVerCategoryMutationResolver appVerCategoryMutationResolver;

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    private AppVerCategoryApplicationService appVerCategoryApplicationService;

    @DisplayName("JUnit test for save as draft")
    @Test
    void processAppVerCategoryConfigSaveToDraft() throws IOException {
        String srcDraft = "request/verificationCategory/draft-maker-draft-verificationcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for add To new")
    void processAppVerCategoryConfigAddToNew() throws IOException {
        String srcDraft = "request/verificationCategory/add-maker-new-verificationcategory.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for authorized To new")
    void processAppVerCategoryConfigAuthorizedToNew() throws IOException {
        String srcDraft = "request/verificationCategory/authorize-checker-new-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to New ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }


    @Test
    @DisplayName("JUnit test for authorized To Closed")
    void processAppVerCategoryConfigAuthorizedToClosed() throws IOException {
        String srcDraft = "request/verificationCategory/authorize-checker-closed-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to closed ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for authorized To Reopened")
    void processAppVerCategoryConfigAuthorizedToReopened() throws IOException {
        String srcDraft = "request/verificationCategory/authorize-checker-reopened-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to closed ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for authorized To Updated")
    void processAppVerCategoryConfigAuthorizedToUpdated() throws IOException {
        String srcDraft = "request/verificationCategory/authorize-checker-updated-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to closed ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for close To closed")
    void processAppVerCategoryConfigCloseToClosed() throws IOException {
        String srcDraft = "request/verificationCategory/close-maker-closed-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to closed ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for delete To deleted")
    void processAppVerCategoryConfigDeleteToDeleted() throws IOException {
        String srcDraft = "request/verificationCategory/delete-maker-deleted-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to deleted ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for draft To updated")
    void processAppVerCategoryConfigDraftToUpdated() throws IOException {
        String srcDraft = "request/verificationCategory/draft-maker-updated-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to updated ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for modify To updated")
    void processAppVerCategoryConfigModifyToUpdated() throws IOException {
        String srcDraft = "request/verificationCategory/modify-maker-update-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to Modify ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for reject To rejected")
    void processAppVerCategoryConfigRejectToRejected() throws IOException {
        String srcDraft = "request/verificationCategory/reject-checker-rejected-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to Rejected ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for reopen To reopened")
    void processAppVerCategoryConfigRopenToReopened() throws IOException, FatalException {
        String srcDraft = "request/verificationCategory/reopen-maker-reopened-verificationcategory.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft to Rejected ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }


}
package com.idg.idgcore.coe.endpoint.graphql.resolver.categorychecklist;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import com.idg.idgcore.coe.app.service.categorychecklist.AppVerCatChecklistPolicyApplicationService;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class AppVerCatChecklistPolicyMutationResolverTest {

        @InjectMocks
        AppVerCatChecklistPolicyMutationResolver appVerCatChecklistPolicyMutationResolver;

        @Autowired
        GraphQLTestTemplate graphQLTestTemplate;

        private AppVerCatChecklistPolicyApplicationService appVerCatChecklistPolicyApplicationService;

        @DisplayName("JUnit test for save as draft")
        @Test
        void processAppVerCatChecklistPolicySaveToDraft() throws IOException {
            String srcDraft = "request/categorychecklist/draft-maker-draft-checklistpolicy.graphqls";

            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

        @Test
        @DisplayName("JUnit test for add To new")
        void processAppVerCategoryConfigAddToNew() throws IOException {
            String srcDraft = "request/categorychecklist/add-maker-new-checklistpolicy.graphqls";

            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

        @Test
        @DisplayName("JUnit test for authorized To new")
        void processAppVerCategoryConfigAuthorizedToNew() throws IOException {
            String srcDraft = "request/categorychecklist/authorize-checker-new-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }


        @Test
        @DisplayName("JUnit test for authorized To Closed")
        void processAppVerCategoryConfigAuthorizedToClosed() throws IOException {
            String srcDraft = "request/categorychecklist/authorize-checker-closed-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for authorized To Reopened")
        void processAppVerCategoryConfigAuthorizedToReopened() throws IOException {
            String srcDraft = "request/categorychecklist/authorize-checker-reopened-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for authorized To Updated")
        void processAppVerCategoryConfigAuthorizedToUpdated() throws IOException {
            String srcDraft = "request/categorychecklist/authorize-checker-updated-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for close To closed")
        void processAppVerCategoryConfigCloseToClosed() throws IOException {
            String srcDraft = "request/categorychecklist/close-maker-closed-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for delete To deleted")
        void processAppVerCategoryConfigDeleteToDeleted() throws IOException {
            String srcDraft = "request/categorychecklist/delete-maker-deleted-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to deleted ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for draft To updated")
        void processAppVerCategoryConfigDraftToUpdated() throws IOException {
            String srcDraft = "request/categorychecklist/draft-maker-updated-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to updated ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for modify To updated")
        void processAppVerCategoryConfigModifyToUpdated() throws IOException {
            String srcDraft = "request/categorychecklist/modify-maker-update-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Modify ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for reject To rejected")
        void processAppVerCategoryConfigRejectToRejected() throws   IOException {
            String srcDraft = "request/categorychecklist/reject-checker-rejected-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Rejected ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for reopen To reopened")
        void processAppVerCategoryConfigRopenToReopened() throws IOException, FatalException {
            String srcDraft = "request/categorychecklist/reopen-maker-reopened-checklistpolicy.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Rejected ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");

        }

}
package com.idg.idgcore.coe.endpoint.graphql.resolver.holidaylist;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import com.idg.idgcore.coe.app.service.holidaylist.IHolidayListApplicationService;
import com.idg.idgcore.datatypes.exceptions.FatalException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class HolidayListMutationResolverTest {


        @InjectMocks
        HolidayListMutationResolver holidayListMutationResolver;

        @Autowired
        GraphQLTestTemplate graphQLTestTemplate;

        private IHolidayListApplicationService holidayListApplicationService;

        @DisplayName("JUnit test for save as draft")
        @Test
        void processHolidayListSaveToDraft() throws IOException {
            String srcDraft = "request/holidaylist/draft-maker-draft-holidaylist.graphqls";

            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

        @Test
        @DisplayName("JUnit test for add To new")
        void processHolidayListAddToNew() throws IOException {
            String srcDraft = "request/holidaylist/add-maker-new-holidaylist.graphqls";

            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

        @Test
        @DisplayName("JUnit test for authorized To new")
        void processHolidayListAuthorizedToNew() throws IOException {
            String srcDraft = "request/holidaylist/authorize-checker-new-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }


        @Test
        @DisplayName("JUnit test for authorized To Closed")
        void processHolidayListAuthorizedToClosed() throws IOException {
            String srcDraft = "request/holidaylist/authorize-checker-closed-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for authorized To Reopened")
        void processHolidayListAuthorizedToReopened() throws IOException {
            String srcDraft = "request/holidaylist/authorize-checker-reopened-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for authorized To Updated")
        void processHolidayListAuthorizedToUpdated() throws IOException {
            String srcDraft = "request/holidaylist/authorize-checker-updated-holiday.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for close To closed")
        void processHolidayListCloseToClosed() throws IOException {
            String srcDraft = "request/holidaylist/close-maker-closed-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to closed ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for delete To deleted")
        void processHolidayListDeleteToDeleted() throws IOException {
            String srcDraft = "request/holidaylist/delete-maker-deleted-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to deleted ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for draft To updated")
        void processHolidayListDraftToUpdated() throws IOException {
            String srcDraft = "request/holidaylist/draft-maker-updated-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to updated ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for modify To updated")
        void processHolidayListModifyToUpdated() throws IOException {
            String srcDraft = "request/holidaylist/modify-maker-updated-holidaylis.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Modify ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for reject To rejected")
        void processHolidayListRejectToRejected() throws   IOException {
            String srcDraft = "request/holidaylist/reject-checker-rejected-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Rejected ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");
        }

        @Test
        @DisplayName("JUnit test for reopen To reopened")
        void processHolidayListRopenToReopened() throws IOException, FatalException {
            String srcDraft = "request/holidaylist/reopen-maker-reopened-holidaylist.graphqls";
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
            System.out.println(" srcDraft to Rejected ");
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
            System.out.println(" DONE ");

        }

}
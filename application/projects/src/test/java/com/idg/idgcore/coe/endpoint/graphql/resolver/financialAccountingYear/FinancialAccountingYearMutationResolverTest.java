package com.idg.idgcore.coe.endpoint.graphql.resolver.financialAccountingYear;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class FinancialAccountingYearMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processFinancialAccountingYearSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/financialAccountingYear/1/draft-maker-draft-finAccYear.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/financialAccountingYear/1/delete-maker-deleted-finAccYear.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //                assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read(
                        "response/financialAccountingYear/success-response-finAccYear.json"),
                graphQLResponseDraftDlt.getRawResponse().getBody());
    }

    @DisplayName ("draft- new -auth  ")
    @Test
    void processFinancialAccountingYearSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/financialAccountingYear/2/draft-maker-draft-finAccYear.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/financialAccountingYear/2/add-maker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/financialAccountingYear/2/authorize-checker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
//                assertThat(graphQLResponseAuth.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read(
//                        "response/financialAccountingYear/success-response-finAccYear.json"),
//                graphQLResponseAuth.getRawResponse().getBody(),
//                String.valueOf(true));
    }

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processFinancialAccountingYearSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/financialAccountingYear/3/add-maker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/financialAccountingYear/3/reject-checker-rejected-finAccYear.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/financialAccountingYear/3/modify-maker-update-finAccYear.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/financialAccountingYear/3/authorize-checker-updated-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
                assertEquals(FileReaderUtil.read("response/financialAccountingYear/success-response-finAccYear.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(),
                        String.valueOf(true));
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processFinancialAccountingYearSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/financialAccountingYear/4/draft-maker-draft-finAccYear.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftUpdate = "request/financialAccountingYear/4/draft-maker-updated-finAccYear.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);
        String srcNew = "request/financialAccountingYear/4/add-maker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/financialAccountingYear/4/authorize-checker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/financialAccountingYear/success-response-finAccYear.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processFinancialAccountingYearSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/financialAccountingYear/5/add-maker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/financialAccountingYear/5/authorize-checker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/financialAccountingYear/5/modify-maker-update-finAccYear.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/financialAccountingYear/5/authorize-checker-updated-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/financialAccountingYear/success-response-finAccYear.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processFinancialAccountingYearSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/financialAccountingYear/6/add-maker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/financialAccountingYear/6/authorize-checker-new-finAccYear.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/financialAccountingYear/6/close-maker-closed-finAccYear.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/financialAccountingYear/6/authorize-checker-closed-finAccYear.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/financialAccountingYear/6/reopen-maker-reopened-finAccYear.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/financialAccountingYear/6/authorize-checker-reopened-finAccYear.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
                assertEquals(FileReaderUtil.read("response/financialAccountingYear/success-response-finAccYear.json"),graphQLResponseCkReopen.getRawResponse().getBody(),
                        String.valueOf(true));
    }

}


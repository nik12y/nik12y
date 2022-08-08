package com.idg.idgcore.coe.endpoint.graphql.resolver.reason;


import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.FileReaderUtil;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class ReasonMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processReasonSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/Reason/1/draft-maker-draft-reason.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/Reason/1/delete-maker-deleted-reason.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

/*    @DisplayName ("draft- new -auth  ")
    @Test
    void processReasonSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/Reason/2/draft-maker-draft-reason.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/Reason/2/add-maker-new-reason.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Reason/2/authorize-checker-new-reason.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        //        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));
    }*/

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processReasonSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/Reason/3/add-maker-new-reason.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/Reason/3/reject-checker-rejected-reason.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/Reason/3/modify-maker-update-reason.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Reason/3/authorize-checker-updated-reason.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processReasonSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/Reason/4/draft-maker-draft-reason.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftUpdate = "request/Reason/4/draft-maker-updated-reason.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);
        String srcNew = "request/Reason/4/add-maker-new-reason.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Reason/4/authorize-checker-new-reason.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processReasonSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/Reason/5/add-maker-new-reason.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Reason/5/authorize-checker-new-reason.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/Reason/5/modify-maker-update-reason.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Reason/5/authorize-checker-updated-reason.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processReasonSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/Reason/6/add-maker-new-reason.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Reason/6/authorize-checker-new-reason.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/Reason/6/close-maker-closed-reason.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/Reason/6/authorize-checker-closed-reason.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/Reason/6/reopen-maker-reopened-reason.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/Reason/6/authorize-checker-reopened-reason.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-reason.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }
    @DisplayName (" Wrong-mapping ")
    @Test
    void processReasonWrongMapping () throws IOException, JSONException {
        String srcWrong = "request/Reason/WrongMapping-reason.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcWrong);
        //        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/wrong-mapping-response-reason.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
                String.valueOf(true));
    }



}
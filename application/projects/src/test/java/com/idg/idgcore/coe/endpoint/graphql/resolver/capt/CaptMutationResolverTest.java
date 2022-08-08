package com.idg.idgcore.coe.endpoint.graphql.resolver.capt;


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
class CaptMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processCaptSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/Capt/1/draft-maker-draft-capt.graphqls";

        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/Capt/1/delete-maker-deleted-draft-capt.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

/*    @DisplayName ("draft- new -auth  ")
    @Test
    void processCaptSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/Capt/2/draft-maker-draft-capt.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/Capt/2/add-maker-new-capt.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Capt/2/authorize-checker-new-capt.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        //        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));
    }*/

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processCaptSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/Capt/3/add-maker-new-capt.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/Capt/3/reject-checker-rejected-capt.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/Capt/3/modify-maker-updated-capt.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Capt/3/authorize-checker-updated-capt.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processCaptSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/Capt/4/draft-maker-draft-capt.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
       /* String srcDraftUpdate = "request/Capt/4/draft-maker-updated-capt.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);*/
        String srcNew = "request/Capt/4/add-maker-new-capt.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Capt/4/authorize-checker-new-capt.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processCaptSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/Capt/5/add-maker-new-capt.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Capt/5/authorize-checker-new-capt.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/Capt/5/modify-maker-updated-capt.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Capt/5/authorize-checker-updated-capt.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processCaptSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/Capt/6/add-maker-new-capt.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Capt/6/authorize-checker-new-capt.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/Capt/6/close-maker-closed-capt.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/Capt/6/authorize-checker-closed-capt.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/Capt/6/reopen-maker-reopened-capt.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/Capt/6/authorize-checker-reopened-capt.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-capt.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }
    @DisplayName (" Wrong-mapping ")
    @Test
    void processCaptWrongMapping () throws IOException, JSONException {
        String srcWrong = "request/Capt/WrongMapping-capt.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcWrong);
        //        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/wrong-mapping-response-capt.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
                String.valueOf(true));
    }


}
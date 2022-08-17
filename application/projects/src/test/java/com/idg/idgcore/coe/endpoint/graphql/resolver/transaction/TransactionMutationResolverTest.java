package com.idg.idgcore.coe.endpoint.graphql.resolver.transaction;


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
class TransactionMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processTransactionSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/Transaction/1/draft-maker-draft-transaction.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/Transaction/1/delete-maker-deleted-transaction.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

/*    @DisplayName ("draft- new -auth  ")
    @Test
    void processTransactionSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/Transaction/2/draft-maker-draft-transaction.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/Transaction/2/add-maker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Transaction/2/authorize-checker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        //        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));
    }*/

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processTransactionSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/Transaction/3/add-maker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/Transaction/3/reject-checker-rejected-transaction.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/Transaction/3/modify-maker-update-transaction.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Transaction/3/authorize-checker-updated-transaction.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processTransactionSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/Transaction/4/draft-maker-draft-transaction.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftUpdate = "request/Transaction/4/draft-maker-updated-transaction.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);
        String srcNew = "request/Transaction/4/add-maker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Transaction/4/authorize-checker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processTransactionSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/Transaction/5/add-maker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Transaction/5/authorize-checker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/Transaction/5/modify-maker-update-transaction.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Transaction/5/authorize-checker-updated-transaction.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processTransactionSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/Transaction/6/add-maker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Transaction/6/authorize-checker-new-transaction.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/Transaction/6/close-maker-closed-transaction.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/Transaction/6/authorize-checker-closed-transaction.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/Transaction/6/reopen-maker-reopened-transaction.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/Transaction/6/authorize-checker-reopened-transaction.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-transaction.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }
    @DisplayName (" Wrong-mapping ")
    @Test
    void processTransactionWrongMapping () throws IOException, JSONException {
        String srcWrong = "request/Transaction/WrongMapping-transaction.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcWrong);
        //        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/wrong-mapping-response-transaction.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
                String.valueOf(true));
    }



}
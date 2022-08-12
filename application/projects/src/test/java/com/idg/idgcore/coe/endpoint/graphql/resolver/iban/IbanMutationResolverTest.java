package com.idg.idgcore.coe.endpoint.graphql.resolver.iban;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class IbanMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processIbanSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/iban/1/iban-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/iban/1/iban-delete-maker-deleted.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

    @DisplayName ("draft- new -auth  ")
    @Test
    void processIbanSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/iban/2/iban-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/iban/2/iban-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/iban/2/iban-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        /*assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/iban/iban-success-response.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));*/
    }

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processIbanSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/iban/3/iban-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/iban/3/iban-reject-checker-rejected.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/iban/3/iban-modify-maker-update.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/iban/3/iban-authorize-checker-updated.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processIbanSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/iban/4/iban-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftUpdate = "request/iban/4/iban-draft-maker-updated.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);
        String srcNew = "request/iban/4/iban-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/iban/4/iban-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processIbanSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/iban/5/iban-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/iban/5/iban-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/iban/5/iban-modify-maker-update.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/iban/5/iban-authorize-checker-updated.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processIbanSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/iban/6/iban-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/iban/6/iban-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/iban/6/iban-close-maker-closed.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/iban/6/iban-authorize-checker-closed.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/iban/6/iban-reopen-maker-reopened.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/iban/6/iban-authorize-checker-reopened.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }

    @DisplayName (" Wrong-mapping ")
    @Test
    void processIbanWrongMapping () throws IOException, JSONException {
        String srcWrong = "request/iban/WrongMapping-iban.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcWrong);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        /*assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/iban/wrong-mapping-iban-response.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
                String.valueOf(true));*/
    }

    /*@Test
    void processIban () {
    }*/

}
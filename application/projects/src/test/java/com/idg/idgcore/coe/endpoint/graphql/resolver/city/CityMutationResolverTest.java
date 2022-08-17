package com.idg.idgcore.coe.endpoint.graphql.resolver.city;

import static org.junit.jupiter.api.Assertions.*;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

import java.io.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CityMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processCitySaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/City/1/draft-maker-draft-city.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/City/1/delete-maker-deleted-city.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

    @DisplayName ("draft- new -auth  ")
    @Test
    void processCitySaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/City/2/draft-maker-draft-city.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/City/2/add-maker-new-city.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/City/2/authorize-checker-new-city.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
                assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        /*assertEquals(FileReaderUtil.read("response/city/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));*/
    }

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processCitySaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/City/3/add-maker-new-city.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/City/3/reject-checker-rejected-city.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/City/3/modify-maker-update-city.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/City/3/authorize-checker-updated-city.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processCitySaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/City/4/draft-maker-draft-city.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftUpdate = "request/City/4/draft-maker-updated-city.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);
        String srcNew = "request/City/4/add-maker-new-city.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/City/4/authorize-checker-new-city.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processCitySaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/City/5/add-maker-new-city.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/City/5/authorize-checker-new-city.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/City/5/modify-maker-update-city.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/City/5/authorize-checker-updated-city.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processCitySaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/City/6/add-maker-new-city.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/City/6/authorize-checker-new-city.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/City/6/close-maker-closed-city.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/City/6/authorize-checker-closed-city.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/City/6/reopen-maker-reopened-city.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/City/6/authorize-checker-reopened-city.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }
    @DisplayName (" Wrong-mapping ")
    @Test
    void processCityWrongMapping () throws IOException, JSONException {
        String srcWrong = "request/City/WrongMapping-city.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcWrong);
        //        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/city/wrong-mapping-response-city.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
                String.valueOf(true));
    }



}
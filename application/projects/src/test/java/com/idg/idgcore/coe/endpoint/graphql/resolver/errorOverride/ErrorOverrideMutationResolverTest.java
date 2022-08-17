package com.idg.idgcore.coe.endpoint.graphql.resolver.errorOverride;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class ErrorOverrideMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;


    @DisplayName(" draft-delete ")
    @Test
    void processErrorOverrideSaveDraftDelete() throws IOException, JSONException {
        String srcDraft = "request/errorOverride/1/draft-maker-draft-errorOverride.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcDraftDelete = "request/errorOverride/1/delete-maker-deleted-errorOverride.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(srcDraftDelete);

        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);


    }

    @DisplayName("draft- new -auth  ")
    @Test
    void processErrorOverrideSaveDraftToNewAuth() throws IOException, JSONException {
        String srcDraft = "request/errorOverride/2/draft-maker-draft-errorOverride.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcNew = "request/errorOverride/2/add-maker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/errorOverride/2/authorize-checker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

    }

    @DisplayName(" new - reject -modify- auth")
    @Test
    void processErrorOverrideSaveDraftToNewReject() throws IOException, JSONException {

        String srcNew = "request/errorOverride/3/add-maker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftRej = "request/errorOverride/3/reject-checker-rejected-errorOverride.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);

        String srcModify = "request/errorOverride/3/modify-maker-update-errorOverride.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/errorOverride/3/authorize-checker-updated-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);

    }
    @DisplayName(" draft-updateDraft-New-Auth  ")
    @Test
    void processErrorOverrideSaveDraftToUpdated() throws IOException, JSONException {
        String srcDraft = "request/errorOverride/4/draft-maker-draft-errorOverride.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcDraftUpdate = "request/errorOverride/4/draft-maker-updated-errorOverride.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);

        String srcNew = "request/errorOverride/4/add-maker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/errorOverride/4/authorize-checker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

    }

    @DisplayName(" new-auth-modify-auth  ")
    @Test
    void processErrorOverrideSaveDraftToUpdatedAuth() throws IOException, JSONException {
        String srcNew = "request/errorOverride/5/add-maker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/errorOverride/5/authorize-checker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcModify = "request/errorOverride/5/modify-maker-update-errorOverride.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/errorOverride/5/authorize-checker-updated-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);

        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);


    }

    @DisplayName("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processErrorOverrideSaveCloseToClosed() throws IOException, JSONException {
        String srcNew = "request/errorOverride/6/add-maker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/errorOverride/6/authorize-checker-new-errorOverride.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcMakerClose = "request/errorOverride/6/close-maker-closed-errorOverride.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);

        String srcCheckerClosed = "request/errorOverride/6/authorize-checker-closed-errorOverride.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(srcCheckerClosed);

        String srcReopenMaker = "request/errorOverride/6/reopen-maker-reopened-errorOverride.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(srcReopenMaker);

        String srcCheckerReopned = "request/errorOverride/6/authorize-checker-reopened-errorOverride.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(srcCheckerReopned);


        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-errorOverride-response.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);

    }

}
package com.idg.idgcore.coe.endpoint.graphql.resolver.state;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class StateMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;


    @DisplayName(" draft-delete ")
    @Test
    void processStateSaveDraftDelete() throws IOException, JSONException {
        String srcDraft = "request/state/1/draft-maker-draft-state.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcDraftDelete = "request/state/1/delete-maker-deleted-state.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(srcDraftDelete);

        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);


    }

    @DisplayName("draft- new -auth  ")
    @Test
    void processStateSaveDraftToNewAuth() throws IOException, JSONException {
        String srcDraft = "request/state/2/draft-maker-draft-state.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcNew = "request/state/2/add-maker-new-state.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/state/2/authorize-checker-new-state.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

    }

    @DisplayName(" new - reject -modify- auth")
    @Test
    void processStateSaveDraftToNewReject() throws IOException, JSONException {

        String srcNew = "request/state/3/add-maker-new-state.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftRej = "request/state/3/reject-checker-rejected-state.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);

        String srcModify = "request/state/3/modify-maker-update-state.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/state/3/authorize-checker-updated-state.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);

    }
    @DisplayName(" draft-updateDraft-New-Auth  ")
    @Test
    void processStateSaveDraftToUpdated() throws IOException, JSONException {
        String srcDraft = "request/state/4/draft-maker-draft-state.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcDraftUpdate = "request/state/4/draft-maker-updated-state.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);

        String srcNew = "request/state/4/add-maker-new-state.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/state/4/authorize-checker-new-state.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

            }

    @DisplayName(" new-auth-modify-auth  ")
    @Test
    void processStateSaveDraftToUpdatedAuth() throws IOException, JSONException {
        String srcNew = "request/state/5/add-maker-new-state.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/state/5/authorize-checker-new-state.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcModify = "request/state/5/modify-maker-update-state.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/state/5/authorize-checker-updated-state.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);

        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);


    }

    @DisplayName("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processStateSaveCloseToClosed() throws IOException, JSONException {
        String srcNew = "request/state/6/add-maker-new-state.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/state/6/authorize-checker-new-state.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcMakerClose = "request/state/6/close-maker-closed-state.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);

        String srcCheckerClosed = "request/state/6/authorize-checker-closed-state.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(srcCheckerClosed);

        String srcReopenMaker = "request/state/6/reopen-maker-reopened-state.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(srcReopenMaker);

        String srcCheckerReopned = "request/state/6/authorize-checker-reopened-state.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(srcCheckerReopned);


        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
//        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/authorize-checker-new-state-response.json"),graphQLResponseCkReopen.getRawResponse().getBody(),
                String.valueOf(true));

    }

}
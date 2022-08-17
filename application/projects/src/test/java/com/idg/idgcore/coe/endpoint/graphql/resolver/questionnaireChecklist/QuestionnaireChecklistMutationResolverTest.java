package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;

import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;



@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class QuestionnaireChecklistMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;


    @DisplayName(" draft-delete ")
    @Test
    void processQuestionnaireChecklistSaveDraftDelete() throws IOException, JSONException {
        String srcDraft = "request/questionnaireChecklist/1/draft-maker-draft-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("1-------------------");



        String srcDraftDelete = "request/questionnaireChecklist/1/delete-maker-deleted-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(srcDraftDelete);

        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);


    }

    @DisplayName("draft- new -auth  ")
    @Test
    void processQuestionnaireChecklistSaveDraftToNewAuth() throws IOException, JSONException {
        String srcDraft = "request/questionnaireChecklist/2/draft-maker-draft-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcNew = "request/questionnaireChecklist/2/add-maker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/questionnaireChecklist/2/authorize-checker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

    }

    @DisplayName(" new - reject -modify- auth")
    @Test
    void processQuestionnaireChecklistSaveDraftToNewReject() throws IOException, JSONException {

        String srcNew = "request/questionnaireChecklist/3/add-maker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftRej = "request/questionnaireChecklist/3/reject-checker-rejected-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);

        String srcModify = "request/questionnaireChecklist/3/modify-maker-update-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/questionnaireChecklist/3/authorize-checker-updated-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);

    }
    @DisplayName(" draft-updateDraft-New-Auth  ")
    @Test
    void processQuestionnaireChecklistSaveDraftToUpdated() throws IOException, JSONException {
        String srcDraft = "request/questionnaireChecklist/4/draft-maker-draft-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);

        String srcDraftUpdate = "request/questionnaireChecklist/4/draft-maker-updated-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);

        String srcNew = "request/questionnaireChecklist/4/add-maker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/questionnaireChecklist/4/authorize-checker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);

    }

    @DisplayName(" new-auth-modify-auth  ")
    @Test
    void processQuestionnaireChecklistSaveDraftToUpdatedAuth() throws IOException, JSONException {
        String srcNew = "request/questionnaireChecklist/5/add-maker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/questionnaireChecklist/5/authorize-checker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcModify = "request/questionnaireChecklist/5/modify-maker-update-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);

        String srcAuthUpdated = "request/questionnaireChecklist/5/authorize-checker-updated-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(srcAuthUpdated);

        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseAuth.getRawResponse().getBody(), true);


    }

    @DisplayName("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processQuestionnaireChecklistSaveCloseToClosed() throws IOException, JSONException {
        String srcNew = "request/questionnaireChecklist/6/add-maker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);

        String srcDraftAut = "request/questionnaireChecklist/6/authorize-checker-new-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);

        String srcMakerClose = "request/questionnaireChecklist/6/close-maker-closed-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);

        String srcCheckerClosed = "request/questionnaireChecklist/6/authorize-checker-closed-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(srcCheckerClosed);

        String srcReopenMaker = "request/questionnaireChecklist/6/reopen-maker-reopened-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(srcReopenMaker);

        String srcCheckerReOpen = "request/questionnaireChecklist/6/authorize-checker-reopened-questionnaireChecklist.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(srcCheckerReOpen);


        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/authorize-checker-new-questionnaireChecklist-response.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);

    }

}
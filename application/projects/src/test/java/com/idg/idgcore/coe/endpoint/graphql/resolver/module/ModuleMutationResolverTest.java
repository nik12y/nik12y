package com.idg.idgcore.coe.endpoint.graphql.resolver.module;


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
class ModuleMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    @Test
    void processModuleSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/Module/1/draft-maker-draft-module.graphqls";

        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/Module/1/delete-maker-deleted-draft-module.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

    @DisplayName ("draft- new -auth  ")
    @Test
    void processModuleSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/Module/2/draft-maker-draft-module.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/Module/2/add-maker-new-module.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Module/2/authorize-checker-new-module.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        //        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseAuth.getRawResponse().getBody(),
//                String.valueOf(true));
    }

    @DisplayName (" new - reject -modify- auth")
    @Test
    void processModuleSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/Module/3/add-maker-new-module.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/Module/3/reject-checker-rejected-module.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/Module/3/modify-maker-updated-module.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Module/3/authorize-checker-updated-module.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    @Test
    void processModuleSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/Module/4/draft-maker-draft-module.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
       /* String srcDraftUpdate = "request/Module/4/draft-maker-updated-module.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);*/
        String srcNew = "request/Module/4/add-maker-new-module.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Module/4/authorize-checker-new-module.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    @Test
    void processModuleSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/Module/5/add-maker-new-module.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Module/5/authorize-checker-new-module.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/Module/5/modify-maker-updated-module.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/Module/5/authorize-checker-updated-module.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName ("new-auth-close-authClose -reopen -Auth  ")
    @Test
    void processModuleSaveCloseToClosed () throws IOException, JSONException {
        String srcNew = "request/Module/6/add-maker-new-module.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/Module/6/authorize-checker-new-module.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcMakerClose = "request/Module/6/close-maker-closed-module.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcMakerClose);
        String srcCheckerClosed = "request/Module/6/authorize-checker-closed-module.graphqls";
        GraphQLResponse graphQLResponseCheckerClosed = graphQLTestTemplate.postForResource(
                srcCheckerClosed);
        String srcReopenMaker = "request/Module/6/reopen-maker-reopened-module.graphqls";
        GraphQLResponse graphQLResponseReopenMaker = graphQLTestTemplate.postForResource(
                srcReopenMaker);
        String srcCheckerReopned = "request/Module/6/authorize-checker-reopened-module.graphqls";
        GraphQLResponse graphQLResponseCkReopen = graphQLTestTemplate.postForResource(
                srcCheckerReopned);
        assertTrue(graphQLResponseCkReopen.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseCkReopen.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-module.json"),graphQLResponseCkReopen.getRawResponse().getBody(), true);
    }
//    @DisplayName (" Wrong-mapping ")
//    @Test
//    void processModuleWrongMapping () throws IOException, JSONException {
//        String srcWrong = "request/Module/WrongMapping-module.graphqls";
//        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
//                srcWrong);
//        //        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
//        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/wrong-mapping-response-module.json"),graphQLResponseDraftDlt.getRawResponse().getBody(),
//                String.valueOf(true));
//    }


}
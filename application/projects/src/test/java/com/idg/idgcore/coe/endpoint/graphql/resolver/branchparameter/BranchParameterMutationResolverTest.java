package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class BranchParameterMutationResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName (" draft-delete ")
    //@Test
    void processBranchParameterSaveDraftDelete () throws IOException, JSONException {
        String srcDraft = "request/branchparameter/1/branchparameter-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcDraftDelete = "request/branchparameter/1/branchparameter-delete-maker-deleted.graphqls";
        GraphQLResponse graphQLResponseDraftDlt = graphQLTestTemplate.postForResource(
                srcDraftDelete);
        assertTrue(graphQLResponseDraftDlt.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseDraftDlt.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseDraftDlt.getRawResponse().getBody(), true);
    }

    @DisplayName ("draft- new -auth  ")
    //@Test
    void processBranchParameterSaveDraftToNewAuth () throws IOException, JSONException {
        String srcDraft = "request/branchparameter/2/branchparameter-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        String srcNew = "request/branchparameter/2/branchparameter-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/branchparameter/2/branchparameter-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        /*assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/iban/iban-success-response.json"),graphQLResponseAuth.getRawResponse().getBody(),
                String.valueOf(true));*/
    }

    @DisplayName (" new - reject -modify- auth")
    //@Test
    void processIbanSaveDraftToNewReject () throws IOException, JSONException {
        String srcNew = "request/branchparameter/3/branchparameter-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftRej = "request/branchparameter/3/branchparameter-reject-checker-rejected.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftRej);
        String srcModify = "request/branchparameter/3/branchparameter-modify-maker-update.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/branchparameter/3/branchparameter-authorize-checker-updated.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuthUpdated.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuthUpdated.getRawResponse().getBody(), true);
    }

    @DisplayName (" draft-updateDraft-New-Auth  ")
    //@Test
    void processIbanSaveDraftToUpdated () throws IOException, JSONException {
        String srcDraft = "request/branchparameter/4/branchparameter-draft-maker-draft.graphqls";
        GraphQLResponse graphQLResponseDraft = graphQLTestTemplate.postForResource(srcDraft);
        /*String srcDraftUpdate = "request/branchparameter/4/branchparameter-draft-maker-updated.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraftUpdate);*/
        String srcNew = "request/branchparameter/4/branchparameter-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/branchparameter/4/branchparameter-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        assertTrue(graphQLResponseAuth.getStatusCode().is2xxSuccessful());
        //        assertThat(graphQLResponseAuth.isOk(), equalTo(true));
        //        assertEquals(FileReaderUtil.read("response/success-response-city.json"),graphQLResponseAuth.getRawResponse().getBody(), true);
    }

    @DisplayName (" new-auth-modify-auth  ")
    //@Test
    void processIbanSaveDraftToUpdatedAuth () throws IOException, JSONException {
        String srcNew = "request/branchparameter/5/branchparameter-add-maker-new.graphqls";
        GraphQLResponse graphQLResponseNew = graphQLTestTemplate.postForResource(srcNew);
        String srcDraftAut = "request/branchparameter/5/branchparameter-authorize-checker-new.graphqls";
        GraphQLResponse graphQLResponseAuth = graphQLTestTemplate.postForResource(srcDraftAut);
        String srcModify = "request/branchparameter/5/branchparameter-modify-maker-update.graphqls";
        GraphQLResponse graphQLResponseModify = graphQLTestTemplate.postForResource(srcModify);
        String srcAuthUpdated = "request/branchparameter/5/branchparameter-authorize-checker-updated.graphqls";
        GraphQLResponse graphQLResponseAuthUpdated = graphQLTestTemplate.postForResource(
                srcAuthUpdated);
        assertTrue(graphQLResponseAuthUpdated.getStatusCode().is2xxSuccessful());
    }

}
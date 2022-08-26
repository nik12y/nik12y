package com.idg.idgcore.coe.endpoint.graphql.resolver.currencyratetype;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import com.idg.idgcore.coe.domain.entity.currencyratetype.CurrencyRateTypeEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class CurrencyRateTypeMutationResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for save as Draft")
    @Test
    void processCurrencyRateTypeSaveToDraft() throws IOException {
        String srcDraft ="request/currencyratetype/draft-maker-draft-currencyratetype.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" draft-maker-draft-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for save as Draft to New ")
    @Test
    void processCurrencyRateTypeSaveDraftToNew() throws IOException {
        String srcDraft = "request/currencyratetype/add-maker-new-currencyratetype.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" add-maker-new-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName(" JUnit test for Auth the Draft-New record  ")
    @Test
    void  processCurrencyRateTypeSaveDraftToNewAuth() throws IOException {
        String srcDraft = "request/currencyratetype/authorize-checker-new-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("authorize-checker-new-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");

    }

    @DisplayName("JUnit test for Draft Update")
    @Test
    void  processCurrencyRateTypeSaveDrafttoUpdate() throws IOException {
        String srcDraft = "request/currencyratetype/draft-maker-updated-currencyratetype.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println("draft-maker-updated-currencyratetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit test for Auth the Draft-Update record")
    @Test
    void  processCurrencyRateTypeSaveDrafttoUpdateAuth() throws IOException {
        String srcDraft = "request/currencyratetype/authorize-checker-updated-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-currencyratetype ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Modify")
    @Test
    void  processCurrencyRateTypeSaveDraftToModify() throws IOException {
        String srcDraft = "request/currencyratetype/modify-maker-updated-currencyratetype.graphqls";
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" modify-maker-updated-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Draft-Modify Record")
    @Test
    void  processCurrencyRateTypeSaveDraftToModifyAuth() throws IOException {
        String srcDraft = "request/currencyratetype/authorize-checker-updated-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-updated-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Save as Close")
    @Test
    void  processCurrencyRateTypeSaveClose() throws IOException {
        String srcDraft = "request/currencyratetype/close-maker-closed-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" close-maker-closed-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Close Record")
    @Test
    void  processCurrencyRateTypeSaveCloseToAuth() throws IOException {
        String srcDraft = "request/currencyratetype/authorize-checker-closed-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-closed-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Reopen a record")
    @Test
    void  processCurrencyRateTypeReopenRecord() throws IOException {
        String srcDraft = "request/currencyratetype/reopen-maker-reopened-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" reopen-maker-reopened-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Auth the Reopen Request")
    @Test
    void  processCurrencyRateTypeAuthReopenRecord() throws IOException {
        String srcDraft = "request/currencyratetype/authorize-checker-reopened-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" authorize-checker-reopened-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @DisplayName("JUnit Test for Draft Delete")
    @Test
    void  processCurrencyRateTypeDeleteDraft() throws IOException {
        String srcDraft = "request/currencyratetype/delete-maker-deleted-currencyratetype.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" delete-maker-deleted-currencyratetype");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }
}

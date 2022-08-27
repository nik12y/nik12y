package com.idg.idgcore.coe.endpoint.graphql.resolver.groupBanking;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class GroupBankingQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for list of GroupBanks")
    @Test
    void getAllGroupBanks() throws IOException {
        String srcDraft = "request/groupBanking/query-groupBankings.graphqls";

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(srcDraft);
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for Get groupBanking By Code")
    void getGroupBankByCode() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/groupBanking/query-groupBanking-bycode.graphqls");
        System.out.println(" srcDraft ");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println("Done");
    }
}

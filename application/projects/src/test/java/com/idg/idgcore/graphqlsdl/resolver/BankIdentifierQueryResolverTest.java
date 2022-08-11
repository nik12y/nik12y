package com.idg.idgcore.graphqlsdl.resolver;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class BankIdentifierQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void shouldAbleToGetBankIdentifiers() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bankidentifier/query-bankidentifiers.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void shouldAbleToGetBankIdentifierByCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bankidentifier/query-bankidentifier-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }
}

package com.idg.idgcore.coe.endpoint.graphql.resolver.mitigant;

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
public class MitigantQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void getMitigantByMitigantCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mitigant/query-mitigant-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void getMitigantAll() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/mitigant/query-mitigant-all.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}

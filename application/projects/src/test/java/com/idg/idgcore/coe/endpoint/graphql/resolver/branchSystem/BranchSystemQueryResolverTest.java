package com.idg.idgcore.coe.endpoint.graphql.resolver.branchSystem;

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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class BranchSystemQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getBranchSystemDateByCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/branchSystem/query-branchSystem-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
    }

    @Test
    void getBranchSystemDateAll() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/branchSystem/query-branchSystem-All.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
    }
}

package com.idg.idgcore.coe.endpoint.graphql.resolver.virtualEntity;

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
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class VirtualEntityQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getVirtualEntityByEntityCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/virtualEntity/query-virtualEntity-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
    }

    @Test
    void getVirtualEntityAll() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/virtualEntity/query-virtualEntity-All.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
    }
}
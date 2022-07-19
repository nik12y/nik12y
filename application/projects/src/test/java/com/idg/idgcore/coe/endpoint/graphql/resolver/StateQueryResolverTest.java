package com.idg.idgcore.coe.endpoint.graphql.resolver;

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
class StateQueryResolverTest {

        @Autowired
        GraphQLTestTemplate graphQLTestTemplate;

        @Test
        public void getStateByCode() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-statebycode.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/query-statebycode.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

        }

        @Test
        public void getStates() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-states.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/query-states.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

    }

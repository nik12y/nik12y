package com.idg.idgcore.coe.endpoint.graphql.resolver.state;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.FileReaderUtil;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
//import com.social.graphqlsdl.FileReaderUtil;


import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.skyscreamer.jsonassert.JSONAssert.assertNotEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class StateQueryResolverTest {

        @Autowired
        GraphQLTestTemplate graphQLTestTemplate;

        @Test
         void getStateByCode() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-statebycode.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/state/query-statebycode.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

        @Test
        void getStates() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-states.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
            System.out.println(graphQLResponse.getRawResponse().getBody());
//            assertEquals(FileReaderUtil.read("response/state/query-states.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

    @Test
    void getStateByCodeNegative() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-statebycode.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertNotEquals(FileReaderUtil.read("response/state/query-statebycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }
    @Test
    void getStatesNegative() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/state/query-states.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertNotEquals(FileReaderUtil.read("response/state/query-states.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }


    }

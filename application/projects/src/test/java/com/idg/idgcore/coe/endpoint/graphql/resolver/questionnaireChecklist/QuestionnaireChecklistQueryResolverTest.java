package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

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
class QuestionnaireChecklistQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void testGetQuestionnaireChecklistByCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionnaireChecklist/query-questionnaireChecklistbycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklistbycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetQuestionnaireChecklists() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionnaireChecklist/query-questionnaireChecklists.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        System.out.println(graphQLResponse.getRawResponse().getBody());
        //            assertEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklists.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetQuestionnaireChecklistByCodeNegative() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionnaireChecklist/query-questionnaireChecklistbycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertNotEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklistbycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }
    @Test
    void getQuestionnaireChecklistsNegative() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionnaireChecklist/query-questionnaireChecklists.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertNotEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklists.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }


}

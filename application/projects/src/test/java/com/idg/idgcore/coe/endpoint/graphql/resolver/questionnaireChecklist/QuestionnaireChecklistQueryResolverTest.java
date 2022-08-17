package com.idg.idgcore.coe.endpoint.graphql.resolver.questionnaireChecklist;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.IsEqual.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class QuestionnaireChecklistQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void testGetQuestionnaireChecklistByCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/questionnaireChecklist/query-questionnaireChecklistById.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklistbycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetQuestionnaireChecklists () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/questionnaireChecklist/query-questionnaireChecklists.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        System.out.println(graphQLResponse.getRawResponse().getBody());
        //            assertEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklists.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void testGetQuestionnaireChecklistByCodeNegative () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/questionnaireChecklist/query-questionnaireChecklistById.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertNotEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklistbycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    void getQuestionnaireChecklistsNegative () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/questionnaireChecklist/query-questionnaireChecklists.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //            assertNotEquals(FileReaderUtil.read("response/questionnaireChecklist/query-questionnaireChecklists.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}

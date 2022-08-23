package com.idg.idgcore.coe.endpoint.graphql.resolver.questionCategory;

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
class QuestionCategoryQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for list of QuestionCategories")
    @Test
    void getQuestionCategories() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/questionCategory/query-questionsCategories.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for Get QuestionCategory By Id")
    void getQuestionCategoryById() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/questionCategory/query-questionCategory-byId.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println("Done");
    }
}

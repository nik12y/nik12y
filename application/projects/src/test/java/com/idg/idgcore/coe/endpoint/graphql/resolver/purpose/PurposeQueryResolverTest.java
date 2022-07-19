package com.idg.idgcore.coe.endpoint.graphql.resolver.purpose;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class PurposeQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

//    @Test
//    void getPurposeByCode() throws IOException, JSONException {
//        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/purpose/query-purpose-bycode.graphqls");
//        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/query-purpose-bycode.json"),graphQLResponse.getRawResponse().getBody(), true);
//    }

//    @Test
//    void getPurposes() throws IOException, JSONException {
//        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/purpose/query-purposes.graphqls");
//        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/query-purposes.json"),graphQLResponse.getRawResponse().getBody(), true);
//    }
}
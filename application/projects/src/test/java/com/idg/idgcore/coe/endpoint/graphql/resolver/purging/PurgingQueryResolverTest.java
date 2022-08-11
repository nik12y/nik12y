package com.idg.idgcore.coe.endpoint.graphql.resolver.purging;

import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.idg.idgcore.FileReaderUtil;
import org.json.JSONException;

import java.io.IOException;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
public class PurgingQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    void getPurgingByCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/purging/query-purging-bycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/purging/query-purging-bycode.json"),graphQLResponse.getRawResponse().getBody(), true);
    }

    @Test
    void getPurgingAll() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/purging/query-purgingAll.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/purging/query-purgingAll.json"),graphQLResponse.getRawResponse().getBody(), true);
    }

}

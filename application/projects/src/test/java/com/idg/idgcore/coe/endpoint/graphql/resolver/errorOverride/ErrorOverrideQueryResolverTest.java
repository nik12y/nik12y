package com.idg.idgcore.coe.endpoint.graphql.resolver.errorOverride;

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
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class ErrorOverrideQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void getErrorOverrideByCode () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/errorOverride/query-errorOverrideByCode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(
//                FileReaderUtil.read("response/errorOverride/response-errorOverrideByCode.json"),
//                graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

    @Test
    public void getErrorOverrides () throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource(
                "request/errorOverride/query-errorCodesAll.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/errorOverride/response-errorCodesAll.json"),
//                graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}

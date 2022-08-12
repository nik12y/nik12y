package com.idg.idgcore.coe.endpoint.graphql.resolver.branchparameter;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.skyscreamer.jsonassert.JSONAssert.assertEquals;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class BranchParameterQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;
    //@Test
    void getBranchParameterByBranchCode () throws IOException, JSONException {
        System.out.println(" BranchParameter getBranchParameterByBranchCode ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/branchparameter/branchparameter-query-branchbycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read(
//                "response/branchparameter/branchparameter-query-bybranchcode.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" BranchParameter getBranchParameterByBranchCode is DONE");
    }

    //@Test
    void getBranchParameters () throws IOException, JSONException {
        System.out.println(" BranchParameter getBranchParameters ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/branchparameter/branchparameter-query-branchparameters.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read(
//                "response/branchparameter/branchparameter-query-branchparameter.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" BranchParameter getBranchParameters is DONE");

    }


    /*//@Test
    void getBranchParameterByBranchCode () {
    }

    //@Test
    void getBranchParameters () {
    }*/

}
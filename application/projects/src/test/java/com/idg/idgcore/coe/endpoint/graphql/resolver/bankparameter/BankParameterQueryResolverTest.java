package com.idg.idgcore.coe.endpoint.graphql.resolver.bankparameter;

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
class BankParameterQueryResolverTest {
    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

   ////@Test
    void getBankParameterByBankCode () throws IOException, JSONException {
        System.out.println(" Bankparameter getBankParameterByBankCode ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bankparameter/bankparameter-query-bankbycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//        assertEquals(FileReaderUtil.read("response/bankparameter/bankparameter-query-bankbycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" Bankparameter getBankParameterByBankCode is DONE");
    }

    ////@Test
    void getBankParameters () throws IOException, JSONException {
        System.out.println(" Bankparameter getBankParameters ");
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/bankparameter/bankparameter-query-bankparameters.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        //assertEquals(FileReaderUtil.read("response/bankparameter/bankparameter-query-bankparameters.json"),graphQLResponse.getRawResponse().getBody(), true);
        System.out.println(" Bankparameter getBankParameters is DONE");

    }

}
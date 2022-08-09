package com.idg.idgcore.coe.endpoint.graphql.resolver.financialAccountingYear;

import com.graphql.spring.boot.test.*;
import com.idg.idgcore.*;
import org.json.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class FinancialAccountingYearQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void getFinancialAccountingYearByCode() throws IOException, JSONException {
       /* graphQLTestTemplate.postForResource("request/financialAccountingYear/6/add-maker-new-finAccYear.graphqls");
        graphQLTestTemplate.postForResource("request/financialAccountingYear/6/authorize-checker-new-finAccYear.graphqls");
        graphQLTestTemplate.postForResource("request/financialAccountingYear/2/add-maker-new-finAccYear.graphqls");
        graphQLTestTemplate.postForResource("request/financialAccountingYear/2/authorize-checker-new-finAccYear.graphqls");
        */

        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/financialAccountingYear/getByCode_finAccYear.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/financialAccountingYear/response_getByCode_finAccYear.json"),graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void getFinancialAccountingYears() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/financialAccountingYear/getAll_finAccYear.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
        assertEquals(FileReaderUtil.read("response/financialAccountingYear/response_getAll_finAccYear.json"),graphQLResponse.getRawResponse().getBody(),
                String.valueOf(true));
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}
package com.idg.idgcore.coe.endpoint.graphql.resolver.country;

import com.graphql.spring.boot.test.GraphQLResponse;
import com.graphql.spring.boot.test.GraphQLTestTemplate;
import com.idg.idgcore.TestApplication;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = TestApplication.class)
class CountryQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @Test
    public void getCountryByCode() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/country/query-countrybycode.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/country/query-countrybycode.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

    }

    @Test
    public void getCountries() throws IOException, JSONException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/country/query-countries.graphqls");
        assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/country/query-countries.json"),graphQLResponse.getRawResponse().getBody(), true);
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
    }

}
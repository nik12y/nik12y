package com.idg.idgcore.coe.endpoint.graphql.resolver.regulatoryRegion;

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
class RegulatoryRegionQueryResolverTest {

    @Autowired
    GraphQLTestTemplate graphQLTestTemplate;

    @DisplayName("JUnit test for list of RegulatoryRegion")
    @Test
    void getRegulatoryRegion() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource( "request/regulatoryRegion/query-regulatoryRegions.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println(" DONE ");
    }

    @Test
    @DisplayName("JUnit test for Get regulatoryRegion By Code")
    void getRegulatoryRegionByCode() throws IOException {
        GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/regulatoryRegion/query-regulatoryRegion-byRegionCode.graphqls");
        assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        System.out.println("Done");
    }
}

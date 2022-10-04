package com.idg.idgcore.coe.endpoint.graphql.resolver.holidaylist;

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
class HolidayListQueryResolverTest {


        @Autowired
        GraphQLTestTemplate graphQLTestTemplate;

        @Test
        public void getHolidayListById() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/holidaylist/query-holidaylistById.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/verificationcategory/query-verificationcategorybyid.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());

        }

        @Test
        public void getAppVerCatChecklistPolicies() throws IOException, JSONException {
            GraphQLResponse graphQLResponse = graphQLTestTemplate.postForResource("request/holidaylist/query-holidayLists.graphqls");
            assertThat(graphQLResponse.isOk(), equalTo(true));
//            assertEquals(FileReaderUtil.read("response/verificationcategory/query-verificationcategoryconfigs.json"),graphQLResponse.getRawResponse().getBody(), true);
            assertTrue(graphQLResponse.getStatusCode().is2xxSuccessful());
        }

}
package org.acme;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class GreetingResourceTest {

    @RepeatedTest(20)
    public void testInstanceCount(RepetitionInfo repetitionInfo) {
        final int testN = repetitionInfo.getCurrentRepetition();
        if (testN==1) {
            expectEndpointMatch("/reset", "OK");
        }
        expectEndpointMatch("/currentBeanCount", "count: 1");
        expectEndpointMatch("/producerCallsCount", testN);
        expectEndpointMatch("/instanceDestroyedCount", testN);
    }

    private void expectEndpointMatch(String endpoint, int expected) {
        expectEndpointMatch( endpoint, String.valueOf( expected ) );
    }

    private static void expectEndpointMatch(String endpoint, String expected) {
        given()
                .when().get( endpoint )
                .then()
                .statusCode( 200 )
                .body( is( expected ) );
    }

}
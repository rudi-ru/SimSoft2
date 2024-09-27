package helpers;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static io.restassured.RestAssured.given;

public class BaseRequests {

    public static RequestSpecification initRequestSpecification() throws IOException{
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder
                .setContentType(ContentType.JSON)
                .setBaseUri(ParametersProvider.getProperty("apiUrl"))
                .setAccept(ContentType.JSON);
       // RequestSpecification requestSpecification = requestSpecBuilder.build();
        return requestSpecBuilder.build(); //
    }

    public static void deleteUserById(String userId) {

        given()
                .when()
                .delete("/api/delete/" + userId)
                .then()
                .statusCode(204);

    }
}

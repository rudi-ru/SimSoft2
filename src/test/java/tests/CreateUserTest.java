package tests;

import helpers.BaseRequests;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pojo.Response;
import pojo.User;

import java.io.File;
import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateUserTest extends BaseRequests {
    private RequestSpecification requestSpecification;
    private String userId;

    @BeforeClass
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
    }

    @Test
    public void testCreateUserWithSerialization() {
        User userPojo = User.builder().build();

        userId = given()
                .spec(requestSpecification)
                //.body(new File("src/test/resources/json/userCreationBody.json"))
                .body(userPojo)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                //.body("Доп сведения", equalTo(userPojo), "title", equalTo(userPojo))
                .extract().path("id");
        System.out.println(userId);
    }
    @Test
    public void testCreateUserWithSerializationAndDeserialization() {
        User userPojo = User.builder().title("123456789").build();

        Response response = given()
                .spec(requestSpecification)
                //.body(new File("src/test/resources/json/userCreationBody.json"))
                .body(userPojo)
                .when()
                .post("/api/create")
                .then()
                .statusCode(200)
                //.body("Доп сведения", equalTo(userPojo), "title", equalTo(userPojo))
                .extract().as(Response.class, ObjectMapperType.GSON);
        //userId = response.;
        //System.out.println(userId);
    }

}

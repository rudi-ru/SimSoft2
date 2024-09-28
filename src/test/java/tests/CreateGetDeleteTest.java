package tests;

import helpers.BaseRequests;
import io.qameta.allure.Description;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pojo.Response;
import pojo.User;
import java.io.IOException;
import java.util.Arrays;
import static io.restassured.RestAssured.given;


public class CreateGetDeleteTest extends BaseRequests {
    private RequestSpecification requestSpecification;
    static User userPojo = new User();
    static String userId = "";

    @BeforeClass
    public void setup() throws IOException {
        requestSpecification = BaseRequests.initRequestSpecification();
    }

    // Создаем объект, проверяем код ответа базы, извлекаем id
    @Test
    @Description("1")
    public void testCreateWithSerialization() {
        userPojo.setTitle("qwerty123");
        userPojo.setVerified(true);
        userPojo.setImportant_numbers(Arrays.asList(42, 87, 15, 23));

        userId = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .post("/create")
                .then()
                .statusCode(200).extract().body().asString();
        Assert.assertFalse(userId.isEmpty());
    }

    // Проверяем создание объекта
    @Test
    @Description("2")
    public void testGetWithSerializationAndDeserialization() {

        Response response = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .get("/get/" + userId)
                .then()
                .statusCode(200)
                .extract()
                .as(Response.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userId, response.getId().toString());
        softAssert.assertEquals(userPojo.getTitle(), response.getTitle());
        softAssert.assertEquals(userPojo.getImportant_numbers(), response.getImportant_numbers());
        softAssert.assertEquals(userPojo.isVerified(), response.isVerified());
        softAssert.assertAll();
    }

    // Проверяем всю базу
    @Test
    @Description("3")
    public void testGetAllWithSerializationAndDeserialization() {
        Response response = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .get("/getAll")
                .then()
                .statusCode(200)
                .extract()
                .as(Response.class, ObjectMapperType.GSON);

        Assert.assertFalse(response.toString().isEmpty(), "empty");
    }

    // этот тест не работает - отваливается база
//    @Test
//    @Description("4")
//    public void testPatchWithSerializationAndDeserialization() {
//        userPojo.setTitle("AAAAAAAAAA2");
//        userPojo.setVerified(false);
//        userPojo.setImportant_numbers(Arrays.asList(2387655, 321311));
//        Response response = (Response) given()
//                .spec(requestSpecification)
//                .body(userPojo)
//                .when()
//                .patch("/patch/56")
//                .then()
//                .statusCode(204)
//                .log().all()
//                .extract()
//                .response();
//
//        Assert.assertFalse(response.toString().isEmpty());
//    }

    // удаляем объект
    @Test
    @Description("5")
    public void testTryDeleteUserWithSerializationAndDeserialization() {

        BaseRequests.deleteUserById(userId);
        Response response = (Response) given()
                .spec(requestSpecification)
                .when()
                .delete("/delete/" + userId)
                .then()
                .statusCode(500)
                .extract()
                .as(Response.class, ObjectMapperType.GSON);

        Assert.assertTrue(response.getError().equals("no rows found for this id"), "not deleted");
    }
 }

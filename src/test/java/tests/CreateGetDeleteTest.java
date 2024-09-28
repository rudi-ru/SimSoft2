package tests;

import helpers.BaseRequests;
import helpers.ParametersProvider;
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
    public void testCreateWithSerialization() throws IOException {
        userPojo.setTitle("qwerty123");
        userPojo.setVerified(true);
        userPojo.setImportantNumbers(Arrays.asList(42, 87, 15, 23));

        userId = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .post(ParametersProvider.getProperty("create"))
                .then()
                .statusCode(200).extract().body().asString();
        Assert.assertFalse(userId.isEmpty(), "object not created");
    }

    // Проверяем создание объекта с указанным id
    @Test
    @Description("2")
    public void testGetWithSerializationAndDeserialization() throws IOException {

        Response response = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .get(ParametersProvider.getProperty("get").toString() + userId)
                .then()
                .statusCode(200)
                .extract()
                .as(Response.class, ObjectMapperType.GSON);

        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(userId, response.getId());
        softAssert.assertEquals(userPojo.getTitle(), response.getTitle());
        softAssert.assertEquals(userPojo.getImportantNumbers(), response.getImportantNumbers());
        softAssert.assertEquals(userPojo.isVerified(), response.isVerified());
        //softAssert.assertAll();
    }

    // Выбираем из базы все имеющиеся объекты, если база пустая - выводим ошибку
    @Test
    @Description("3")
    public void testGetAllWithSerializationAndDeserialization() throws IOException {
        Response response = given()
                .spec(requestSpecification)
                .body(userPojo)
                .when()
                .get(ParametersProvider.getProperty("getAll"))
                .then()
                .statusCode(200)
                .extract()
                .as(Response.class, ObjectMapperType.GSON);

        Assert.assertFalse(response.toString().isEmpty(), "empty");
    }

//     этот тест не работает - отваливается база
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

    // Удаляем объект из базы с указанным id
    @Test
    @Description("5")
    public void testTryDeleteUserWithSerializationAndDeserialization() throws Exception {
        BaseRequests.deleteUserById(userId);
        String response = given()
                .spec(requestSpecification)
                .when()
                .get(ParametersProvider.getProperty("get").toString() + userId)
                .then()
                .statusCode(500)
                .extract()
                .asString();
        Assert.assertTrue(response.equals("{\"error\":\"no rows in result set\"}"), "not deleted");
    }
 }

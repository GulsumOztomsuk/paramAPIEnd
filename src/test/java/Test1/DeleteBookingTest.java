package Test1;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class DeleteBookingTest {

    private static String authToken = "";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


    }


   @Test
    public void deleteBookingTest() {
        Response createResponse = createBooking();
        createResponse.then().statusCode(200);
        int bookingId = createResponse.jsonPath().getInt("bookingid");

       String token = RestAssured
               .given()
               .contentType(ContentType.JSON)
               .body("{\"username\":\"admin\",\"password\":\"password123\"}")
               .post("/auth")
               .then()
               .statusCode(200)
               .extract()
               .path("token");

       System.out.println("Token: " + token);

       Response response = RestAssured
               .given()
               .contentType(ContentType.JSON)
               .header("Cookie", "token=" + token)
               .delete("/booking/" + bookingId)
               .then()
               .statusCode(201)
               .extract()
               .response();

       System.out.println("Response: " + response.asString());

    }

    private Response createBooking() {

        String requestBody = "{\n" +
                "  \"firstname\": \"Param\",\n" +
                "  \"lastname\": \"Tech\",\n" +
                "  \"totalprice\": 200,\n" +
                "  \"depositpaid\": true,\n" +
                "  \"bookingdates\": {\n" +
                "    \"checkin\": \"2023-08-01\",\n" +
                "    \"checkout\": \"2024-07-05\"\n" +
                "  },\n" +
                "  \"additionalneeds\": \"Breakfast\"\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/booking")
                .then()
                .extract()
                .response();

        return response;
    }


}
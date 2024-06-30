package Test1;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.codehaus.groovy.util.ListHashMap;
import org.json.JSONObject;
import org.junit.jupiter.api.DisplayName;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.testng.Assert.assertEquals;

public class BookingTest {

    @BeforeClass
    public void setup() {
        // Base URL setup
        RestAssured.baseURI = "https://restful-booker.herokuapp.com/";

    }


    @Test
    public void createBookingTest() {
        // Request body as JSON object
        //Map<String,Object> requestBody=new ListHashMap<>();
        JSONObject requestBody = new JSONObject()
                .put("firstname", "Param")
                .put("lastname", "Tech")
                .put("totalprice", 200)
                .put("depositpaid", true);

        JSONObject bookingDates = new JSONObject()
                .put("checkin", "2023-01-01")
                .put("checkout", "2024-01-01");

        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", "Breakfast");

        // Send POST request and validate
        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody.toString())
                .when()
                .post("/booking")
                .then()
                .assertThat()
                .statusCode(200)
                .body("booking.firstname",equalTo("Param"),
                        "booking.lastname",equalTo("Tech"),
                        "booking.totalprice",equalTo(200),
                        "booking.depositpaid",equalTo(true),
                        "booking.additionalneeds",equalTo("Breakfast"))
                .extract()
                .response();

        // Assert firstname from the response


        int bookingId = response.jsonPath().getInt("bookingid");

        String responseBody = response.getBody().asString();
        System.out.println("Response body: " + responseBody);

        System.out.println("Created booking with ID: " + bookingId);


    }

}
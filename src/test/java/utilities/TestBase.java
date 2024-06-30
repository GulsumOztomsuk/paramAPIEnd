package utilities;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    @BeforeAll
    public static void init(){

            //save baseurl inside this variable so that we do not need to type each http method
            RestAssured.baseURI="https://restful-booker.herokuapp.com";

        }



}


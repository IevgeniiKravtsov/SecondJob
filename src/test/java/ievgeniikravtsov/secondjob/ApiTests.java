package ievgeniikravtsov.secondjob;

import com.epam.reportportal.service.ReportPortal;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import java.io.File;
import java.util.Calendar;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class ApiTests {

    private static final Logger LOGGER = LogManager.getLogger(ApiTests.class);
        String endpoint = "https://jsonplaceholder.typicode.com/users";
        public static final String screenshot_file_path = "demoScreenshoot.png";

        //1.Create a test to verify a https status code:
//	Send the http request by using the GET method.
//	The URL is https://jsonplaceholder.typicode.com/users
//	Validation: status code of the obtained response is 200  O  K
        @Test(groups={"smoke"})
        public void getStatusCode()  {

            LOGGER.info("Test started GET method___");
            given()
                    .when()
                    .get(endpoint)
                    .then()
                    .assertThat().statusCode(200);
            LOGGER.info("Test finished GET method");

        }

        //2.Create a test to verify a http response header:
//	Send the http request by using the GET method.
//	The URL is https://jsonplaceholder.typicode.com/users
//	Validation: - the content-type header exists in the obtained response
//              - the value of the content-type header is application/json; charset=utf-8
        @Test(groups={"regression"})
        public void getHeader() {

            File file = new File(screenshot_file_path);
            ReportPortal.emitLog("My screenShot", "INFO", Calendar.getInstance().getTime(), file);

            LOGGER.info("Test started GET Header");
            String contentType = "application/json; charset=utf-8";
            given()
                    .when()
                    .get(endpoint)
                    .then()
                    .log()
                    .headers()
                    .assertThat()
                    .header("Content-Type", notNullValue())
                    .header("Content-Type", equalTo(contentType));
            LOGGER.info("Test finished GET Header");
        }

 //3.Create a test to verify a http response body:
//	Send the http request by using the GET method:
//	The URL is https://jsonplaceholder.typicode.com/users
//	Validation: the content of the response body is the array of 10 users
        @Test(groups={"regression"})
        public void getBodyContent() {
            LOGGER.info("Test started GET Body");
            given()
                    .when()
                    .get(endpoint)
                    .then()
                    .log()
                    .body()
                    .assertThat()
                    .body("id", everyItem(notNullValue()))
                    .body("id[9]", equalTo(10));
            LOGGER.info("Test finished GET Body");
        }

}


package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static helpers.CustomAllureListener.withCustomTemplates;
import static tests.TestBase.allureTestOpsSession;
import static tests.TestBase.token;


public class Specification {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .header("X-XSRF-TOKEN", token)
            .cookies("XSRF-TOKEN", token,
                    "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
            .contentType(JSON)
            .log().all();



    public static ResponseSpecification responseSpec = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .expectStatusCode(200)
            .build();

}

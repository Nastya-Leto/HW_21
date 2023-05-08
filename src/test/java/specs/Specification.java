package specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;
import static helpers.CustomAllureListener.withCustomTemplates;


public class Specification {

    public static RequestSpecification requestSpec = with()
            .filter(withCustomTemplates())
            .log().all()
            .baseUri("https://allure.autotests.cloud")
            .basePath("/api/rs");


    public static ResponseSpecification responseSpec = new ResponseSpecBuilder().
            log(STATUS).
            log(BODY).
            build();

}

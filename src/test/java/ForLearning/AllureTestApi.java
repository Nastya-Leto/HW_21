package ForLearning;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import models.CreateTestCaseBodyModel;
import models.CreateTestCaseResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AllureTestApi {
    static String projectId = "2251",
            alluresession = "72370f56-8442-41d6-8a8c-fc4c1bbb1168",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";


    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @Test
    void createWitApiOnlyTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();

        step("Authorize");


        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("CreateTestCase", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", token)
                        .cookies("XSRF-TOKEN", token,
                                "ALLURE_TESTOPS_SESSION", alluresession)
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .log().status()
                        .log().body()
                        .statusCode(200)
                        .extract().as(CreateTestCaseResponseModel.class)

        );

        step("Verify testcase name", () ->
                assertThat(createTestCaseResponse.getName()).isEqualTo(testCaseName));

    }
}
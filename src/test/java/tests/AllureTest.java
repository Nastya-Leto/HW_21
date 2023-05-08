package tests;

import models.CreateTestCaseBodyModel;
import models.CreateTestCaseResponseModel;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static java.lang.String.format;


import org.openqa.selenium.Cookie;


public class AllureTest extends TestBase {

    @Test
    void createTest() {


        step("Авторизация");


        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("Создание тест кейса", () ->
                given()
                        .log().all()
                        .header("X-XSRF-TOKEN", token)
                        .cookies("XSRF-TOKEN", token,
                                "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
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

        step("Проверка имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            testCasesId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }

    @Test
    void editingTest() {

        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);
        String editTestCaseUrl = format("api/rs/testcase/%s", "18497"); //сюда если поставить переменную projectId, то будет нулл передаваться

        step("Проверка имени тест кейса", () -> {

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", token)
                    .cookies("XSRF-TOKEN", token,
                            "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    .contentType("application/json;charset=UTF-8")
                    .body(testCaseBody + "1") // Если записать вот так, то будет Body:CreateTestCaseBodyModel(name = null) 1
                    .when()
                    .patch(editTestCaseUrl)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

    }
}
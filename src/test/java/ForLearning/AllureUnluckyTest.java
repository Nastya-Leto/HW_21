/*package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import models.CreateTestCaseBodyModel;
import models.CreateTestCaseResponseModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Cookie;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.Specification.requestSpec;
import static specs.Specification.responseSpec;


public class AllureUnluckyTest {
    static String projectId = "2251",
            alluresession = "a848e97d-62ae-4cb4-acb5-218e66636402",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";



    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;

        RestAssured.baseURI = "https://allure.autotests.cloud";
    }

    @Test
    void createTest() {
        Faker faker = new Faker();
        String testCaseName = faker.name().fullName();

        step("Авторизация");


        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("Создание тест кейса", () ->
                given(requestSpec)
                        .header("X-XSRF-TOKEN", token)
                        .cookies("XSRF-TOKEN", token,
                                "ALLURE_TESTOPS_SESSION", alluresession)
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .spec(responseSpec)
                        .extract().as(CreateTestCaseResponseModel.class)

        );

        step("Проверка имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", alluresession);
            getWebDriver().manage().addCookie(autorizationCookie);

            Integer testCaseeId = createTestCaseResponse.getId();
            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCaseeId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseName));
        });
    }
}*/
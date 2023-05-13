package tests;


import models.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.Specification.requestSpec;
import static specs.Specification.responseSpec;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;


import org.openqa.selenium.Cookie;

import java.util.ArrayList;
import java.util.List;

@DisplayName("Тестирование тест-кейсов в Allure")
@TestMethodOrder(OrderAnnotation.class)
public class AllureTest extends TestBase {


    @Test
    @Order(1)
    @DisplayName("Создать новый тест-кейс")
    void CreateTest() {


        step("Авторизация");


        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("Создание тест кейса", () ->

                given(requestSpec)
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .spec(responseSpec)
                        .extract().as(CreateTestCaseResponseModel.class)

        );

        step("Проверка имени созданного тест кейса", () -> {

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
    @Order(2)
    @DisplayName("Изменить имя тест-кейса")
    void editingTest() {

        step("Авторизация");


        RenameTestCaseBodyModel testCaseBodyNew = new RenameTestCaseBodyModel();
        testCaseBodyNew.setName(testCaseNameNew);

        String formTestCaseUrl = format("api/rs/testcase/%s", testCasesId);


        step("Изменение имени тест-кейсика", () -> {

            given(requestSpec)
                    .body(testCaseBodyNew)
                    .when()
                    .patch(formTestCaseUrl)
                    .then()
                    .spec(responseSpec)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверка нового имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseNameNew));

        });

    }

    @Test
    @Order(3)
    @DisplayName("Добавление шагов к тест-кейсу")
    void AddingStepsTest() {

        step("Авторизация");

        AddingStepsRequestModel.ListUsersData step = new AddingStepsRequestModel.ListUsersData();
        step.setName("Открыть страницу сайта");
        AddingStepsRequestModel.ListUsersData step1 = new AddingStepsRequestModel.ListUsersData();
        step1.setName("Авторизоваться");

        AddingStepsRequestModel addingTestCaseBody = new AddingStepsRequestModel();
        List<AddingStepsRequestModel.ListUsersData> steps = new ArrayList<>();
        steps.add(step);
        steps.add(step1);
        addingTestCaseBody.setSteps(steps);


        String addingTestCaseUrl = format("api/rs/testcase/%s/scenario", testCasesId);


        step("Добавляем шаги к тест-кейсу", () -> {

            given(requestSpec)
                    .body(addingTestCaseBody)
                    .when()
                    .post(addingTestCaseUrl)
                    .then()
                    .spec(responseSpec)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверяем успешно созданные шаги в тест-кейсе", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            $$(".Editable").shouldHave(sizeGreaterThanOrEqual(1));

        });

    }

    @Test
    @Order(4)
    @DisplayName("Изменение статуса тест-кейса")
    void ChangeStatusTest() {

        step("Авторизация");

        String testCaseId = "{ \"statusId\": \"-3\"}";
        String changeStatus = format("api/rs/testcase/%s", testCasesId);

        step("Меняем статус на Active", () -> {

            given(requestSpec)
                    .body(testCaseId)
                    .when()
                    .patch(changeStatus)
                    .then()
                    .spec(responseSpec)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверка cтатуса кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            $(".Label_clickable").shouldHave(text("Active"));


        });
    }

    @Test
    @Order(5)
    @DisplayName("Добавление тега")
    void AddingTagTest() {

        step("Авторизация");

        AddingTegRequestModel tag = new AddingTegRequestModel();
        tag.setName("Smouk");
        tag.setId(1041);
        List<AddingTegRequestModel> tags = new ArrayList<>();
        tags.add(tag);

        name = "smouk";
        id = "1041";

        String addingTegCaseUrl = format("api/rs/testcase/%s/tag", testCasesId);


        step("Добавление тег Smouk", () -> {

            given(requestSpec)
                    .body(tags)
                    .when()
                    .post(addingTegCaseUrl)
                    .then()
                    .spec(responseSpec)
                    .extract().as(AddingTegResponseModel[].class);

        });


        step("Проверка наличия тега", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);

            open(testCaseUrl);
            $(".TestCaseOverview__secondary").$(byText("smouk")).shouldBe(exist);

        });
    }
}
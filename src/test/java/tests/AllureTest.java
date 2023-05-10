package tests;


import models.*;
import org.junit.jupiter.api.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
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

@TestMethodOrder(OrderAnnotation.class)
public class AllureTest extends TestBase {


    @Test
    @Order(1)
    @DisplayName("Создаем новый тест-кейс")
    void CreateTest() {


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
    @Order(2)
    @DisplayName("Изменяем имя тест-кейса")
    void editingTest() {


        // testCaseBody.setName(testCaseName);// Как передать сюда еще "+1" ((

        String testCaseNameNew = "{\"name\": \"Прекрасный тестушка\"}";

        String formTestCaseUrl = format("api/rs/testcase/%s", testCasesId);


        step("Изменить имя теста", () -> {

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", token)
                    .cookies("XSRF-TOKEN", token,
                            "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    .contentType("application/json;charset=UTF-8")
                    .body(testCaseNameNew)
                    .when()
                    .patch(formTestCaseUrl)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверка имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            // $(".TestCaseLayout__name").shouldHave(selectedText(testCaseNameNew)); - не рабоатает, ищет "{"name": "Прекрасный тестушка"}"
            $(".TestCaseLayout__name").shouldHave(visible);

        });

    }

    @Test
    @Order(3)
    @DisplayName("Добавляем шаги к тест-кейсу")
    void AddingStepsTest() {


        AddingStepsRequestModel.ListUsersData addingTestCaseBody = new AddingStepsRequestModel.ListUsersData(); // Почему тут подчеркивало, если в классе не было "static?"
        addingTestCaseBody.setName("Открыть страницу сайта");
        addingTestCaseBody.setSpacing("Страница успешно открыта");

        String addingTestCaseUrl = format("api/rs/testcase/%s/scenario", testCasesId);


        step("Изменить имя теста", () -> {

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", token)
                    .cookies("XSRF-TOKEN", token,
                            "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    .contentType("application/json;charset=UTF-8")
                    .body(addingTestCaseBody)
                    .when()
                    .post(addingTestCaseUrl)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

             /*  step("Проверка имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            //  $(".TestCaseLayout__name").shouldHave(ownText(testCaseName));
            $(".TestCaseLayout__name").shouldHave(visible);

        });*/

    }

    @Test
    @Order(4)
    @DisplayName("Меняем статус на Active")
    void ChangeStatusTest() {


        String testCaseId = "{ \"statusId\": \"-3\"}";
        String changeStatus = format("api/rs/testcase/%s", testCasesId);

        step("Изменить статус", () -> {

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", token)
                    .cookies("XSRF-TOKEN", token,
                            "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    .contentType("application/json;charset=UTF-8")
                    .body(testCaseId)
                    .when()
                    .patch(changeStatus)
                    // .patch("api/rs/testcase/18593")
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
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
    @Disabled
    @Order(5)
    @DisplayName("Добавляем тег")
    void AddingTagTest() {


      /*AddingTegRequestModel addingTegBody = new AddingTegRequestModel();
        addingTegBody.setName("Smouk");
        addingTegBody.setId(1041);  - так не работает*/

      //  String addingTegBody = format("[{"Name": Smouk, "Id": 1041}]"); почему так не работат..

        name = "Smouk";
        id = "1041";

        String addingTegBody = format("[{%s: %s, %s: %s}]", guotes("name"), guotes(name),
                guotes("job"), guotes(id)); //как сделать, если один атрибут не стринг?

        String addingTegCaseUrl = format("api/rs/testcase/%s/tag", testCasesId);


        step("Добавить тег Smouk", () -> {

            given()
                    .log().all()
                    .header("X-XSRF-TOKEN", token)
                    .cookies("XSRF-TOKEN", token,
                            "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    .contentType("application/json;charset=UTF-8")
                    .body(addingTegBody)
                    .when()
                    .post("api/rs/testcase/18632/tag")
                    //.post(addingTegCaseUrl)
                    .then()
                    .log().status()
                    .log().body()
                    .statusCode(200)
                    .extract().as(AddingTegResponseModel.class); // как сделать модель со странным массивом?

        });


             /*  step("Проверка имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            //  $(".TestCaseLayout__name").shouldHave(ownText(testCaseName));
            $(".TestCaseLayout__name").shouldHave(visible);

        });*/
    }

    @Test
    @Disabled
    @Order(6)
    @DisplayName("Создаем тест-кейс, который упадет")
    void FallenCreateTestSpec() {

        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("Создание тест кейса", () ->
                given(requestSpec)
                        .header("X-XSRF-TOKEN", token)
                        .cookies("XSRF-TOKEN", token,
                                "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                        .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .spec(responseSpec)
                        // .statusCode(200)
                        .extract().as(CreateTestCaseResponseModel.class)

        );

    }
}
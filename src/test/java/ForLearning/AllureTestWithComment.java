/*package ForLearning;


import models.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Cookie;
import tests.TestBase;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.CollectionCondition.sizeGreaterThanOrEqual;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static java.lang.String.format;
import static specs.Specification.requestSpec;
import static specs.Specification.responseSpec;

@TestMethodOrder(OrderAnnotation.class)
public class AllureTestWithComment extends TestBase {


    @Test
    @Order(1)
    @DisplayName("Создать новый тест-кейс")
    void CreateTest() {


        step("Авторизация");


        CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
        testCaseBody.setName(testCaseName);

        CreateTestCaseResponseModel createTestCaseResponse = step("Создание тест кейса", () ->
                //given()
                given(requestSpec)
                        //.log().all()
                       // .header("X-XSRF-TOKEN", token)
                       // .cookies("XSRF-TOKEN", token,
                         //       "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                       // .contentType("application/json;charset=UTF-8")
                        .body(testCaseBody)
                        .queryParam("projectId", projectId)
                        .when()
                        .post("/api/rs/testcasetree/leaf")
                        .then()
                        .spec(responseSpec)
                       // .log().status()
                       // .log().body()
                      // .statusCode(200)
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

        // testCaseBody.setName(testCaseName);// Как передать сюда еще "+1" ((

        RenameTestCaseBodyModel testCaseBodyNew = new RenameTestCaseBodyModel();
        testCaseBodyNew.setName(testCaseNameNew);

        // String testCaseNameNew = "{\"name\": \"Прекрасный тестушка\"}"; // Изменять имя по другому

        String formTestCaseUrl = format("api/rs/testcase/%s", testCasesId);


        step("Изменение имени тест-кейса", () -> {

            given(requestSpec)
                    //.log().all()
                    //.header("X-XSRF-TOKEN", token)
                    //.cookies("XSRF-TOKEN", token,
                        //    "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    //.contentType("application/json;charset=UTF-8")
                    .body(testCaseBodyNew)
                    .when()
                    .patch(formTestCaseUrl)
                    .then()
                    .spec(responseSpec)
                    //.log().status()
                    //.log().body()
                    //.statusCode(200)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверка нового имени тест кейса", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            $(".TestCaseLayout__name").shouldHave(text(testCaseNameNew));
            ;

        });

    }

    @Test
    @Order(3)
    @DisplayName("Добавление шагов к тест-кейсу")
    void AddingStepsTest() {

        step("Авторизация");

        AddingStepsRequestModel.ListUsersData step = new AddingStepsRequestModel.ListUsersData();
        step.setName("Открыть страницу сайта");
        //step.setSpacing("Страница успешно открыта");
        AddingStepsRequestModel.ListUsersData step1 = new AddingStepsRequestModel.ListUsersData();
        step.setName("Авторизоваться");// передается как null

        AddingStepsRequestModel addingTestCaseBody = new AddingStepsRequestModel();
        List<AddingStepsRequestModel.ListUsersData> steps = new ArrayList<>();
        steps.add(step);
        steps.add(step1);
        addingTestCaseBody.setSteps(steps);


        String addingTestCaseUrl = format("api/rs/testcase/%s/scenario", testCasesId);


        step("Добавляем шаги к тест-кейсу", () -> {

            given(requestSpec)
                   // .log().all()
                   // .header("X-XSRF-TOKEN", token)
                   // .cookies("XSRF-TOKEN", token,
                     //       "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    //.contentType("application/json;charset=UTF-8")
                    .body(addingTestCaseBody)
                    .when()
                    .post(addingTestCaseUrl)
                    .then()
                    .spec(responseSpec)
                    //.log().status()
                    //.log().body()
                   // .statusCode(200)
                    .extract().as(CreateTestCaseResponseModel.class);

        });

        step("Проверяем успешно созданные шаги в тест-кейсе", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);

            //  $(".TestCaseLayout__name").shouldHave(ownText(testCaseName));
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
                    //.log().all()
                    //.header("X-XSRF-TOKEN", token)
                    //.cookies("XSRF-TOKEN", token,
                      //      "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    //.contentType("application/json;charset=UTF-8")
                    .body(testCaseId)
                    .when()
                    .patch(changeStatus)
                    // .patch("api/rs/testcase/18593")
                    .then()
                    .spec(responseSpec)
                    //.log().status()
                    //.log().body()
                    //.statusCode(200)
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

        //  String addingTegBody = format("[{"Name": Smouk, "Id": 1041}]"); почему так не работат..

        name = "Smouk";
        id = "1041";

        /*String addingTegBody = format("[{%s: %s, %s: %s}]", guotes("name"), guotes(name),
                guotes("job"), guotes(id));  //как сделать, если один атрибут не стринг?*/

       /* String addingTegCaseUrl = format("api/rs/testcase/%s/tag", testCasesId);


        step("Добавление тег Smouk", () -> {

            given(requestSpec)
                   // .log().all()
                   // .header("X-XSRF-TOKEN", token)
                   // .cookies("XSRF-TOKEN", token,
                     //       "ALLURE_TESTOPS_SESSION", allureTestOpsSession)
                    //.contentType("application/json;charset=UTF-8")
                    .body(tags)
                    .when()
                    //.post("api/rs/testcase/18780/tag")
                    .post(addingTegCaseUrl)
                    .then()
                    .spec(responseSpec)
                    //.log().status()
                    //.log().body()
                    //.statusCode(200)
                    .extract().as(AddingTegResponseModel[].class); // как сделать модель со странным массивом?

        });


        step("Проверка наличия тега", () -> {

            open("/favicon.ico");
            Cookie autorizationCookie = new Cookie("ALLURE_TESTOPS_SESSION", allureTestOpsSession);
            getWebDriver().manage().addCookie(autorizationCookie);

            String testCaseUrl = format("/project/%s/test-cases/%s", projectId, testCasesId);
            open(testCaseUrl);
            //open("https://allure.autotests.cloud/project/2251/test-cases/18781?treeId=0");

            //  $(".TestCaseLayout__name").shouldHave(ownText(testCaseName));
            $(".Label_status_transparent ").$(byText("Smouk"));

        });
    }
}*/

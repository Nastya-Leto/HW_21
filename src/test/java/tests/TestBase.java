package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import models.CreateTestCaseBodyModel;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public static String projectId = "2251",
            allureTestOpsSession = "27f59929-9be2-4471-a95d-7b80bbe58405",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";
    public static Integer testCasesId;
    public static String formTestCaseUrl; // почему без этого ошибка при обращении к перемнным?

    Faker faker = new Faker();
    String testCaseName = faker.name().fullName();
    public static CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();
    public static String guotes (String s) {
        return "\"" + s + "\"";
    }

    public static String name, id;

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;

        RestAssured.baseURI = "https://allure.autotests.cloud";

    }
}

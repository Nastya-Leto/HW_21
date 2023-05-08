package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import models.CreateTestCaseBodyModel;
import org.junit.jupiter.api.BeforeAll;

public class TestBase {

    public static String projectId = "2251",
            allureTestOpsSession = "a212d4fd-ea85-4311-9911-a88f7f6a39e7",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";
    public static Integer testCasesId;

    Faker faker = new Faker();
    String testCaseName = faker.name().fullName();
    public static CreateTestCaseBodyModel testCaseBody = new CreateTestCaseBodyModel();


    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;

        RestAssured.baseURI = "https://allure.autotests.cloud";

    }
}

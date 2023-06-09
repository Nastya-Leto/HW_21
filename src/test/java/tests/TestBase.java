package tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import com.github.javafaker.Faker;
import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    public static String projectId = "2251",
            allureTestOpsSession = "b9c1e2b2-2fbb-4035-ad2b-240527446e12",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";
    public static Integer testCasesId;

    Faker faker = new Faker();
    String testCaseName = faker.name().fullName();
    String testCaseNameNew = faker.name().fullName();

    public static String name, id;

    @BeforeEach
    void addListener() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
    }

    @AfterEach
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }

    @BeforeAll
    static void setUp() {
        Configuration.baseUrl = "https://allure.autotests.cloud";
        Configuration.holdBrowserOpen = true;
        Configuration.browser = "chrome";
        Configuration.browserVersion = "100.0";
        Configuration.browserSize = "1920x1080";
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub";
        RestAssured.baseURI = "https://allure.autotests.cloud";

        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of(
                "enableVNC", true,
                "enableVideo", true

        ));
        Configuration.browserCapabilities = capabilities;



    }
}

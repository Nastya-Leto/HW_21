package tests;

import com.codeborne.selenide.Configuration;
import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Map;

public class TestBase {

    public static String projectId = "2251",
            allureTestOpsSession = "87676eba-d68b-4aaa-9f46-e1f9cf5d6511",
            token = "c4e031d1-3399-4597-b35a-36215173d8ce";
    public static Integer testCasesId;

    Faker faker = new Faker();
    String testCaseName = faker.name().fullName();
    String testCaseNameNew = faker.name().fullName();

    public static String name, id;

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

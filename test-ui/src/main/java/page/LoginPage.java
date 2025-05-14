package page;

import com.codeborne.selenide.SelenideElement;
import config.Config;
import config.UiConfig;
import io.qameta.allure.Allure;
import org.openqa.selenium.Alert;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class LoginPage {
    private static final UiConfig env = Config.getInstance().getEnv().getUiConfig();

    private final static SelenideElement USERNAME_FIELD = $x("//input[@name='email']");
    private final static SelenideElement PASSWORD_FIELD = $x("//input[@name='password']");
    private final static SelenideElement LOGIN_BUTTON = $x("//button[contains(text(),\" GO\")] ");
    private final static SelenideElement LOGOUT_BUTTON = $x("//button[contains(text(),\" LOGOUT\")] ");
    private final static String USERNAME = env.getUsername();
    private final static String PASSWORD = env.getPassword();

    public LoginPage openLoginPage() {
        Allure.step("Open login page", () -> open(env.getBaseUrl()));
        return this;
    }

    public LoginPage isPageOpen() {
        Allure.step("Verify that login page is open", () -> {
            LOGOUT_BUTTON.shouldBe(visible);
        });
        return this;
    }

    public LoginPage loginWithDefaultCredentials() {
        return login(USERNAME, PASSWORD);
    }

    public LoginPage login(String username, String password) {
        Allure.step("Log in with username: " + username + " and password: " + password, () -> {
            USERNAME_FIELD.shouldBe(visible).setValue(username);
            PASSWORD_FIELD.shouldBe(visible).setValue(password);
            LOGIN_BUTTON.shouldBe(enabled).click();
        });
        return this;
    }

    public void confirmAlert() {
        Allure.step("Confirm browser alert", () -> {
            Alert alert = switchTo().alert();
            alert.accept();
        });
    }
}

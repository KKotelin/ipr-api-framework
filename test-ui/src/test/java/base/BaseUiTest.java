package base;

import client.auth.AuthClient;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import page.LoginPage;

public abstract class BaseUiTest {

    protected String token;

    @BeforeEach
    public void setUpBrowser() {
        driver.BrowserManager.setup();
        token = new AuthClient().loginDefault();
        new LoginPage()
                .openLoginPage()
                .loginWithDefaultCredentials()
                .confirmAlert();
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}

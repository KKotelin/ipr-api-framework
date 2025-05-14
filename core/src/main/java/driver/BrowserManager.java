package driver;

import com.codeborne.selenide.Configuration;
import config.Config;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BrowserManager {

    public static void setup() {
        var uiConfig = Config.getInstance().getEnv().getUiConfig();

        String browser = System.getProperty("browser", uiConfig.getBrowser());
        boolean headless = Boolean.parseBoolean(
                System.getProperty("headless", String.valueOf(uiConfig.getHeadless()))
        );

        switch (browser.toLowerCase()) {
            case "firefox" -> WebDriverManager.firefoxdriver().setup();
            case "edge"    -> WebDriverManager.edgedriver().setup();
            default        -> WebDriverManager.chromedriver().setup();
        }

        Configuration.browser = browser;
        Configuration.headless = headless;
        Configuration.baseUrl = uiConfig.getBaseUrl();
        Configuration.timeout = uiConfig.getTimeout();
        Configuration.browserSize = "1920x1080";
        Configuration.headless = true;
    }
}

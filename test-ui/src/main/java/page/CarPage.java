package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import java.math.BigDecimal;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;
import static org.openqa.selenium.By.id;

public class CarPage {
    private static final SelenideElement ENGINE_TYPE_FIELD = $(id("car_engine_type_send"));
    private static final SelenideElement MARK_FIELD = $(id("car_mark_send"));
    private static final SelenideElement MODEL_FIELD = $(id("car_model_send"));
    private static final SelenideElement PRICE_FIELD = $(id("car_price_send"));
    private static final SelenideElement PUSH_TO_API_BUTTON = $x("//*[@id=\"root\"]/div/section/div/div/button[1]");
    private static final SelenideElement STATUS_FIELD = $x("//*[@id=\"root\"]/div/section/div/div/button[2]");
    private static final SelenideElement NEW_ID_FIELD = $x("//*[@id=\"root\"]/div/section/div/div/button[3]");

    public void isPageOpen() {
        Allure.step("Verify that car creation page is open", () -> {
            PUSH_TO_API_BUTTON.shouldBe(visible);
        });
    }

    public CarPage fillEngineType(String engineType) {
        Allure.step("Enter engine type: " + engineType, () -> ENGINE_TYPE_FIELD.sendKeys(engineType));
        return this;
    }

    public CarPage fillMark(String mark) {
        Allure.step("Enter mark: " + mark, () -> MARK_FIELD.sendKeys(mark));
        return this;
    }

    public CarPage fillModel(String model) {
        Allure.step("Enter model: " + model, () -> MODEL_FIELD.sendKeys(model));
        return this;
    }

    public CarPage fillPrice(BigDecimal price) {
        Allure.step("Enter price: " + price, () -> PRICE_FIELD.sendKeys(String.valueOf(price)));
        return this;
    }

    public CarPage clickPushButton() {
        Allure.step("Click PUSH button (create car)", () -> {
            PUSH_TO_API_BUTTON.shouldBe(visible);
            PUSH_TO_API_BUTTON.click();
        });
        return this;
    }

    public void isStatusMessageCorrect(String expectedMessage) {
        Allure.step("Check status message: expected '" + expectedMessage + "'", () -> {
            STATUS_FIELD.shouldHave(text(expectedMessage), Duration.ofSeconds(10));
        });
    }

    public String getNewCarId() {
        return Allure.step("Get ID of created car", () -> {
            NEW_ID_FIELD.shouldBe(visible);
            return NEW_ID_FIELD.getText().replaceAll("^.*?(\\d+)$", "$1");
        });
    }
}

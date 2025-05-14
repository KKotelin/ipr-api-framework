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
        Allure.step("Проверка открытия страницы создания автомобиля", () -> {
            PUSH_TO_API_BUTTON.shouldBe(visible);
        });
    }

    public CarPage fillEngineType(String engineType) {
        Allure.step("Ввод типа двигателя: " + engineType, () -> {
            ENGINE_TYPE_FIELD.sendKeys(engineType);
        });
        return this;
    }

    public CarPage fillMark(String mark) {
        Allure.step("Ввод марки автомобиля: " + mark, () -> {
            MARK_FIELD.sendKeys(mark);
        });
        return this;
    }

    public CarPage fillModel(String model) {
        Allure.step("Ввод модели автомобиля: " + model, () -> {
            MODEL_FIELD.sendKeys(model);
        });
        return this;
    }

    public CarPage fillPrice(BigDecimal price) {
        Allure.step("Ввод цены автомобиля: " + price, () -> {
            PRICE_FIELD.sendKeys(String.valueOf(price));
        });
        return this;
    }

    public CarPage clickPushButton() {
        Allure.step("Нажатие кнопки PUSH (создание машины)", () -> {
            PUSH_TO_API_BUTTON.shouldBe(visible);
            PUSH_TO_API_BUTTON.click();
        });
        return this;
    }

    public void isStatusMessageCorrect(String expectedMessage) {
        Allure.step("Проверка сообщения статуса: ожидается '" + expectedMessage + "'", () -> {
            STATUS_FIELD.shouldHave(text(expectedMessage), Duration.ofSeconds(10));
        });
    }

    public String getNewCarId() {
        return Allure.step("Получение ID созданного автомобиля", () -> {
            NEW_ID_FIELD.shouldBe(visible);
            return NEW_ID_FIELD.getText().replaceAll("^.*?(\\d+)$", "$1");
        });
    }
}

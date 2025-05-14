package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import java.math.BigDecimal;
import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;

public class HousePage {
    private final static SelenideElement FLOORS_FIELD = $x("//input[@id='floor_send']");
    private final static SelenideElement PRICE_FIELD = $x("//input[@id='price_send']");
    private final static SelenideElement WARM_AND_COVERED_FIELD = $x("//input[@id='parking_first_send']");
    private final static SelenideElement WARM_AND_NO_COVERED_FIELD = $x("//input[@id='parking_second_send']");
    private final static SelenideElement NO_WARM_AND_COVERED_FIELD = $x("//input[@id='parking_third_send']");
    private final static SelenideElement NO_WARM_AND_NO_COVERED_FIELD = $x("//input[@id='parking_fourth_send']");
    private final static SelenideElement PUSH_TO_API_BUTTON = $x("//button[@class='tableButton btn btn-primary']");
    private final static SelenideElement STATUS_FIELD = $x("//button[contains(text(), 'Successfully')]");
    private final static SelenideElement NEW_ID_FIELD = $x("//button[@class='newId btn btn-secondary']");

    public void isPageOpen() {
        Allure.step("Проверка открытия страницы создания дома", () -> {
            PUSH_TO_API_BUTTON.shouldBe(visible);
        });
    }

    public HousePage fillFloors(int floors) {
        Allure.step("Ввод количества этажей: " + floors, () -> {
            FLOORS_FIELD.setValue(String.valueOf(floors));
        });
        return this;
    }

    public HousePage fillPrice(BigDecimal price) {
        Allure.step("Ввод цены дома: " + price, () -> {
            PRICE_FIELD.setValue(price.toPlainString());
        });
        return this;
    }

    public HousePage fillWarmCoveredPlaces(int count) {
        Allure.step("Парковка: тепло и крыто — " + count + " мест", () -> {
            WARM_AND_COVERED_FIELD.setValue(String.valueOf(count));
        });
        return this;
    }

    public HousePage fillWarmUncoveredPlaces(int count) {
        Allure.step("Парковка: тепло и без крыши — " + count + " мест", () -> {
            WARM_AND_NO_COVERED_FIELD.setValue(String.valueOf(count));
        });
        return this;
    }

    public HousePage fillColdCoveredPlaces(int count) {
        Allure.step("Парковка: холодно и крыто — " + count + " мест", () -> {
            NO_WARM_AND_COVERED_FIELD.setValue(String.valueOf(count));
        });
        return this;
    }

    public HousePage fillColdUncoveredPlaces(int count) {
        Allure.step("Парковка: холодно и без крыши — " + count + " мест", () -> {
            NO_WARM_AND_NO_COVERED_FIELD.setValue(String.valueOf(count));
        });
        return this;
    }

    public HousePage clickPushButton() {
        Allure.step("Нажатие кнопки PUSH (создание дома)", () -> {
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

    public String getNewHouseId() {
        return Allure.step("Получение ID созданного дома", () -> {
            NEW_ID_FIELD.shouldBe(visible);
            return NEW_ID_FIELD.getText().replaceAll("^.*?(\\d+)$", "$1");
        });
    }
}

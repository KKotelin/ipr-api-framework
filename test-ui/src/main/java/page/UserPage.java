package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import java.math.BigDecimal;
import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class UserPage {
    private final static SelenideElement FIRST_NAME_FIELD = $("#first_name_send");
    private final static SelenideElement SECOND_NAME_FIELD = $("#last_name_send");
    private final static SelenideElement AGE_FIELD = $("#age_send");
    private final static SelenideElement MONEY_FIELD = $("#money_send");
    private final static SelenideElement SEX_MALE_INPUT = $x("//input[@id='sex_send' and @value='MALE']");
    private final static SelenideElement SEX_FEMALE_INPUT = $x("//input[@id='sex_send' and @value='FEMALE']");
    private final static SelenideElement PUSH_BUTTON = $x("//button[contains(text(), \"PUSH\")] ");
    private final static SelenideElement STATUS_FIELD = $x("//button[contains(text(), \"Status\")] ");
    private final static SelenideElement NEW_ID_FIELD = $x("//button[contains(@class,'newId')]");

    public void isPageOpen() {
        Allure.step("Verify that user creation page is open", () -> {
            PUSH_BUTTON.shouldBe(visible);
        });
    }

    public UserPage fillFirstName(String firstName) {
        Allure.step("Enter first name: " + firstName, () -> {
            FIRST_NAME_FIELD.setValue(firstName);
        });
        return this;
    }

    public UserPage fillSecondName(String secondName) {
        Allure.step("Enter last name: " + secondName, () -> {
            SECOND_NAME_FIELD.setValue(secondName);
        });
        return this;
    }

    public UserPage fillAge(int age) {
        Allure.step("Enter age: " + age, () -> {
            AGE_FIELD.sendKeys(String.valueOf(age));
        });
        return this;
    }

    public UserPage fillMoney(BigDecimal money) {
        Allure.step("Enter balance: " + money, () -> {
            MONEY_FIELD.sendKeys(String.valueOf(money));
        });
        return this;
    }

    public UserPage selectSex(boolean isMale) {
        Allure.step("Select gender: " + (isMale ? "MALE" : "FEMALE"), () -> {
            if (isMale) {
                SEX_MALE_INPUT.click();
                SEX_MALE_INPUT.shouldBe(selected);
            } else {
                SEX_FEMALE_INPUT.click();
                SEX_FEMALE_INPUT.shouldBe(selected);
            }
        });
        return this;
    }

    public UserPage clickPushButton() {
        Allure.step("Click PUSH button to create user", () -> {
            PUSH_BUTTON.shouldBe(visible);
            PUSH_BUTTON.click();
        });
        return this;
    }

    public void isStatusMessageCorrect(String expectedMessage) {
        Allure.step("Check status message: expected '" + expectedMessage + "'", () -> {
            STATUS_FIELD.shouldHave(text(expectedMessage), Duration.ofSeconds(10));
        });
    }

    public String getNewUserId() {
        return Allure.step("Get ID of the created user", () -> {
            NEW_ID_FIELD.shouldBe(visible);
            return NEW_ID_FIELD.getText().replaceAll("^.*?(\\d+)$", "$1");
        });
    }
}

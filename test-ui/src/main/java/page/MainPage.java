package page;

import com.codeborne.selenide.SelenideElement;
import io.qameta.allure.Allure;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$x;
import static com.codeborne.selenide.Selenide.actions;

public class MainPage {
    private final static SelenideElement USERS_LINK = $x("//a[text()='Users']");
    private final static SelenideElement CREATE_NEW_USER_LINK = $x("//a[@href='#/create/user']");

    private final static SelenideElement HOUSES_MENU = $x("//a[contains(text(), 'Houses')]");
    private final static SelenideElement HOUSES_MENU_CREATE_NEW = $x("//a[contains(text(), 'Create new')]");

    private static final SelenideElement CARS_DROPDOWN = $x("//a[text() = 'Cars']");
    private static final SelenideElement CARS_CREATE_NEW = $x("//a[text() = 'Create new']");

    public void openCreateNewUserPage() {
        Allure.step("Open user creation page", () -> {
            USERS_LINK.click();
            CREATE_NEW_USER_LINK.shouldBe(visible);
            actions().moveToElement(CREATE_NEW_USER_LINK).click().perform();
        });
    }

    public void openCreateHousePage() {
        Allure.step("Open house creation page", () -> {
            HOUSES_MENU.click();
            HOUSES_MENU_CREATE_NEW.click();
        });
    }

    public void openCreateCarPage() {
        Allure.step("Open car creation page", () -> {
            CARS_DROPDOWN.shouldBe(visible).click();
            CARS_CREATE_NEW.shouldBe(visible).click();
        });
    }
}

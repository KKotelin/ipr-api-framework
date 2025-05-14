package user;

import assertion.UserAssertions;
import base.BaseUiTest;
import client.api.UserClient;
import db.DbUser;
import io.qameta.allure.Epic;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import page.MainPage;
import page.UserPage;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Пользователи")
@DisplayName("Создание пользователя")
public class CreateUserTest extends BaseUiTest {
    private final MainPage mainPage = new MainPage();
    private final UserPage userPage = new UserPage();
    private UserClient userClient;

    private static final Faker faker = new Faker();
    private Integer newUserId;

    @BeforeEach
    public void login() {
        mainPage.openCreateNewUserPage();
        userPage.isPageOpen();
        userClient = new UserClient(token);
    }

    @AfterEach
    public void after() {
        userClient.deleteUser(newUserId);
    }

    @Tag("ui")
    @Tag("user")
    @DisplayName("Создание пользователя через UI")
    @Test
    public void createUser() {
        String firstName = faker.name().firstName();
        String secondName = faker.name().lastName();
        int age = faker.number().numberBetween(18, 95);
        BigDecimal money = BigDecimal.valueOf(100000);
        boolean isMale = faker.bool().bool();
        userPage.fillFirstName(firstName)
                .fillSecondName(secondName)
                .fillAge(age)
                .fillMoney(money)
                .selectSex(isMale)
                .clickPushButton()
                .isStatusMessageCorrect("Status: Successfully pushed, code: 201");

        newUserId = Integer.parseInt(userPage.getNewUserId());
        var dbUser = DbUser.getUserById(newUserId);

        assertAll("created user",
                () -> UserAssertions.assertUserId(newUserId, dbUser.getId()),
                () -> UserAssertions.assertFirstName(firstName, dbUser.getFirstName()),
                () -> UserAssertions.assertSecondName(secondName, dbUser.getSecondName()),
                () -> UserAssertions.assertAge(age, dbUser.getAge()),
                () -> UserAssertions.assertMoney(money, dbUser.getMoney()),
                () -> UserAssertions.assertSex(isMale, dbUser.getSex())
        );
    }
}

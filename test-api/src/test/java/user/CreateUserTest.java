package user;

import assertion.UserAssertions;
import base.BaseApiTest;
import client.api.UserClient;
import db.DbUser;
import io.qameta.allure.Epic;
import model.api.UserDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Пользователи")
@DisplayName("Создание пользователя через API")
public class CreateUserTest extends BaseApiTest {
    UserClient userClient;
    private UserDto initialUser;
    private UserDto createdUser;

    @BeforeEach
    void before() {
        initialUser = DataGenerator.generateUser(BigDecimal.valueOf(10000));
        userClient = new UserClient(token);

    }

    @AfterEach
    void after() {
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("user")
    @DisplayName("Создание пользователя. POST /user")
    @Test
    public void createUserTest() {
        createdUser = userClient.createUser(initialUser);

        var dbUser = DbUser.getUserById(createdUser.getId());

        assertAll("Проверка созданного пользователя",
                () -> UserAssertions.assertUserId(createdUser.getId(), dbUser.getId()),
                () -> UserAssertions.assertFirstName(createdUser.getFirstName(), dbUser.getFirstName()),
                () -> UserAssertions.assertSecondName(createdUser.getSecondName(), dbUser.getSecondName()),
                () -> UserAssertions.assertAge(createdUser.getAge(), dbUser.getAge()),
                () -> UserAssertions.assertMoney(createdUser.getMoney(), dbUser.getMoney()),
                () -> UserAssertions.assertSex(createdUser.getSex(), dbUser.getSex())
        );
    }
}

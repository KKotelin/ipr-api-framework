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
@DisplayName("Обновление пользователя через API")
public class UpdateUserTest extends BaseApiTest {
    UserClient userClient;
    private UserDto createdUser;
    private UserDto userUpdate;

    @BeforeEach
    void before() {
        var initialUser = DataGenerator.generateUser(BigDecimal.valueOf(10000));
        userClient = new UserClient(token);
        createdUser = userClient.createUser(initialUser);
        userUpdate = DataGenerator.generateUser(BigDecimal.valueOf(50000));
    }

    @AfterEach
    void after() {
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("user")
    @DisplayName("Обновление пользователя. PUT /user/{userId}")
    @Test
    void updateUser_shouldPersistChanges() {
        var updatedUser = userClient.updateUser(createdUser.getId(), userUpdate);

        var dbUserAfterUpdate = DbUser.getUserById(createdUser.getId());

        assertAll("Проверка обновленного пользователя",
                () -> UserAssertions.assertUserId(updatedUser.getId(), dbUserAfterUpdate.getId()),
                () -> UserAssertions.assertFirstName(updatedUser.getFirstName(), dbUserAfterUpdate.getFirstName()),
                () -> UserAssertions.assertSecondName(updatedUser.getSecondName(), dbUserAfterUpdate.getSecondName()),
                () -> UserAssertions.assertAge(updatedUser.getAge(), dbUserAfterUpdate.getAge()),
                () -> UserAssertions.assertMoney(updatedUser.getMoney(), dbUserAfterUpdate.getMoney()),
                () -> UserAssertions.assertSex(updatedUser.getSex(), dbUserAfterUpdate.getSex())
        );
    }
}

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

@Epic("Пользователи")
@DisplayName("Добавление денег пользователю через API")
public class AddMoneyToUserTest extends BaseApiTest {
    UserClient userClient;
    private UserDto createdUser;

    @BeforeEach
    public void before() {
        var initialUser = DataGenerator.generateUser(BigDecimal.valueOf(5000));
        userClient = new UserClient(token);
        createdUser = userClient.createUser(initialUser);
    }

    @AfterEach
    public void after() {
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("user")
    @DisplayName("Добавление денег пользователю. POST /user/{userId}/money/{amount}")
    @Test
    public void addMoneyToUser() {
        var userAfterAddMoney = userClient.addMoneyToUser(createdUser.getId(), 10000);

        var dbUserAfterAddMoney = DbUser.getUserById(createdUser.getId());

        UserAssertions.assertMoney(dbUserAfterAddMoney.getMoney(), userAfterAddMoney.getMoney());
    }
}

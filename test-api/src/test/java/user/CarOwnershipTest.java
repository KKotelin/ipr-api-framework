package user;

import assertion.CarAssertions;
import assertion.UserAssertions;
import base.BaseApiTest;
import client.api.CarClient;
import client.api.UserClient;
import db.DbCar;
import db.DbUser;
import io.qameta.allure.Epic;
import model.api.CarDto;
import model.api.UserDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;

import java.math.BigDecimal;

@Epic("Пользователи")
@DisplayName("Покупка и продажа автомобиля пользователем через API")
public class CarOwnershipTest extends BaseApiTest {
    UserClient userClient;
    CarClient carClient;
    private UserDto createdUser;
    private CarDto createdCar;

    @BeforeEach
    public void before() {
        var initialUser = DataGenerator.generateUser(BigDecimal.valueOf(10000));
        var initialCar = DataGenerator.generateCar(1, BigDecimal.valueOf(5000));
        userClient = new UserClient(token);
        carClient = new CarClient(token);
        createdUser = userClient.createUser(initialUser);
        createdCar = carClient.createCar(initialCar);
    }

    @AfterEach
    public void after() {
        carClient.deleteCar(createdCar.getId());
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("user")
    @DisplayName("Покупка и продажа автомобиля пользователем. POST /user/{userId}/buyCar/{carId}. POST /user/{userId}/sellCar/{carId}")
    @Test
    public void testBuyCarToUser() {
        userClient.buyCarToUser(createdUser.getId(), createdCar.getId());

        var dbCarAfterBuy = DbCar.getCarById(createdCar.getId());
        var dbUserAfterBuy = DbUser.getUserById(createdUser.getId());

        CarAssertions.assertOwnerId(dbCarAfterBuy.getUserId(), dbUserAfterBuy.getId());
        UserAssertions.assertMoney(dbUserAfterBuy.getMoney(), createdUser.getMoney().subtract(createdCar.getPrice()));

        userClient.sellCarToUser(createdUser.getId(), createdCar.getId());

        var dbCarAfterSell = DbCar.getCarById(createdCar.getId());
        var dbUserAfterSell = DbUser.getUserById(createdUser.getId());

        CarAssertions.assertOwnerId(0, dbCarAfterSell.getUserId());
        UserAssertions.assertMoney(createdUser.getMoney(), dbUserAfterSell.getMoney());
    }
}
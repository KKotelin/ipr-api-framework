package e2e;

import assertion.CarAssertions;
import assertion.HouseAssertions;
import assertion.UserAssertions;
import base.BaseApiTest;
import client.api.CarClient;
import client.api.HouseClient;
import client.api.UserClient;
import db.DbCar;
import db.DbHouse;
import db.DbUser;
import io.qameta.allure.Epic;
import model.api.CarDto;
import model.api.HouseDto;
import model.api.UserDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;
import utils.EngineTypeUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@Epic("E2E сценарии")
@DisplayName("E2E сценарии")
public class E2ETest extends BaseApiTest {
    UserClient userClient;
    CarClient carClient;
    HouseClient houseClient;
    private UserDto initialUser;
    private CarDto initialCar;
    private HouseDto initialHouse;

    private UserDto createdUser;
    private CarDto createdCar;
    private HouseDto createdHouse;

    @BeforeEach
    public void before() {
        initialUser = DataGenerator.generateUser(BigDecimal.valueOf(1000000));
        initialCar = DataGenerator.generateCar(1, BigDecimal.valueOf(10000));
        initialHouse = DataGenerator.generateHouse(BigDecimal.valueOf(200000));
        userClient = new UserClient(token);
        carClient = new CarClient(token);
        houseClient = new HouseClient(token);
        createdHouse = houseClient.createHouse(initialHouse);
    }

    @AfterEach
    public void after() {
        carClient.deleteCar(createdCar.getId());
        houseClient.deleteHouse(createdHouse.getId());
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("e2e")
    @DisplayName("Полный сценарий работы пользователя")
    @Test
    public void e2eTest() {
        createdUser = userClient.createUser(initialUser);
        var dbUserInitial = DbUser.getUserById(createdUser.getId());

        assertAll("Проверка созданного пользователя",
                () -> UserAssertions.assertUserId(createdUser.getId(), dbUserInitial.getId()),
                () -> UserAssertions.assertFirstName(createdUser.getFirstName(), dbUserInitial.getFirstName()),
                () -> UserAssertions.assertSecondName(createdUser.getSecondName(), dbUserInitial.getSecondName()),
                () -> UserAssertions.assertAge(createdUser.getAge(), dbUserInitial.getAge()),
                () -> UserAssertions.assertMoney(createdUser.getMoney(), dbUserInitial.getMoney()),
                () -> UserAssertions.assertSex(createdUser.getSex(), dbUserInitial.getSex())
        );

        createdCar = carClient.createCar(initialCar);
        var dbCarInitial = DbCar.getCarById(createdCar.getId());

        assertAll("Проверка созданного автомобиля",
                () -> CarAssertions.assertCarId(createdCar.getId(), dbCarInitial.getId()),
                () -> CarAssertions.assertEngineTypeId(EngineTypeUtils.getIdByName(createdCar.getEngineType()),
                        dbCarInitial.getEngineTypeId()),
                () -> CarAssertions.assertCarMark(createdCar.getMark(), dbCarInitial.getMark()),
                () -> CarAssertions.assertCarModel(createdCar.getModel(), dbCarInitial.getModel()),
                () -> CarAssertions.assertCarPrice(createdCar.getPrice(), dbCarInitial.getPrice())
        );

        createdHouse = houseClient.createHouse(initialHouse);
        var dbHouseInitial = DbHouse.getHouseById(createdHouse.getId());
        var parkingPlaceInitial = createdHouse.getParkingPlaces().getFirst();
        var dbParkingPlaceInitial = DbHouse.getParkingPlaceByHouseId(createdHouse.getId());

        assertAll("Проверка созданного дома",
                () -> HouseAssertions.assertHouseId(createdHouse.getId(), dbHouseInitial.getId()),
                () -> HouseAssertions.assertHousePrice(createdHouse.getPrice(), dbHouseInitial.getPrice()),
                () -> HouseAssertions.assertFloorCount(createdHouse.getFloorCount(), dbHouseInitial.getFloorCount()),

                () -> HouseAssertions.assertParkingPlaceId(parkingPlaceInitial.getId(), dbParkingPlaceInitial.getId()),
                () -> HouseAssertions.assertIsCovered(parkingPlaceInitial.getIsCovered(), dbParkingPlaceInitial.getIsCovered()),
                () -> HouseAssertions.assertIsWarm(parkingPlaceInitial.getIsWarm(), dbParkingPlaceInitial.getIsWarm()),
                () -> HouseAssertions.assertPlaceCount(parkingPlaceInitial.getPlacesCount(), dbParkingPlaceInitial.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(parkingPlaceInitial.getId(), dbParkingPlaceInitial.getId())
        );

        userClient.buyCarToUser(createdUser.getId(), createdCar.getId());

        var dbCarAfterBuy = DbCar.getCarById(createdCar.getId());
        var dbUserAfterCarBuy = DbUser.getUserById(createdUser.getId());

        CarAssertions.assertOwnerId(dbCarAfterBuy.getUserId(), dbUserAfterCarBuy.getId());
        UserAssertions.assertMoney(dbUserAfterCarBuy.getMoney(),
                initialUser.getMoney().subtract(initialCar.getPrice()));

        var settledHouse = houseClient.settleHouse(createdHouse.getId(), createdUser.getId());
        var lodger = settledHouse.getLodgers().getFirst();
        var dbUserAfterHouseBuy = DbUser.getUserById(createdUser.getId());

        assertAll("Проверка покупки дома пользователем",
                () -> UserAssertions.assertUserId(lodger.getId(), dbUserInitial.getId()),
                () -> UserAssertions.assertFirstName(lodger.getFirstName(), dbUserInitial.getFirstName()),
                () -> UserAssertions.assertSecondName(lodger.getSecondName(), dbUserInitial.getSecondName()),
                () -> UserAssertions.assertAge(lodger.getAge(), dbUserInitial.getAge()),
                () -> UserAssertions.assertSex(lodger.getSex(), dbUserInitial.getSex()),

                () -> UserAssertions.assertHouseId(settledHouse.getId(), dbUserAfterHouseBuy.getHouseId())
        );

        UserAssertions.assertMoney(dbUserAfterHouseBuy.getMoney(),
                initialUser.getMoney().subtract(initialCar.getPrice()).subtract(initialHouse.getPrice()));

        userClient.sellCarToUser(createdUser.getId(), createdCar.getId());
        var dbCarAfterSell = DbCar.getCarById(createdCar.getId());
        var dbUserAfterCarSell = DbUser.getUserById(createdUser.getId());

        assertEquals(0, dbCarAfterSell.getUserId(),
                "После продажи автомобиль не должен иметь владельца");
        UserAssertions.assertMoney(dbUserAfterCarSell.getMoney(),
                initialUser.getMoney().subtract(initialHouse.getPrice()));

        houseClient.evictHouse(settledHouse.getId(), createdUser.getId());
        var dbUserAfterHouseSell = DbUser.getUserById(createdUser.getId());

        assertNotEquals(settledHouse.getId(), dbUserAfterHouseSell.getHouseId(),
                "Пользователь не должен быть привязан к дому после выселения");
        UserAssertions.assertMoney(dbUserAfterHouseSell.getMoney(), initialUser.getMoney());
    }
}

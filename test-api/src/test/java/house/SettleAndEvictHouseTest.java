package house;

import assertion.HouseAssertions;
import assertion.UserAssertions;
import base.BaseApiTest;
import client.api.HouseClient;
import client.api.UserClient;
import db.DbHouse;
import db.DbUser;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import model.api.HouseDto;
import model.api.UserDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Дома")
@DisplayName("Покупка и продажа дома через API")
public class SettleAndEvictHouseTest extends BaseApiTest {
    UserClient userClient;
    HouseClient houseClient;
    private UserDto initialUser;
    private HouseDto initialHouse;
    private UserDto createdUser;
    private HouseDto createdHouse;

    @BeforeEach
    public void before() {
        initialUser = DataGenerator.generateUser(BigDecimal.valueOf(1000000));
        initialHouse = DataGenerator.generateHouse(BigDecimal.valueOf(1000));
        userClient = new UserClient(token);
        houseClient = new HouseClient(token);
        createdUser = userClient.createUser(initialUser);
        createdHouse = houseClient.createHouse(initialHouse);
    }

    @AfterEach
    public void after() {
        houseClient.deleteHouse(createdHouse.getId());
        userClient.deleteUser(createdUser.getId());
    }

    @Tag("api")
    @Tag("house")
    @DisplayName("Покупка и продажа дома. POST /house/{houseId}/settle/{userId}. POST /house/{houseId}/evict/{userId}")
    @Test
    public void settleAndEvictHouseTest() {
        var settledHouse = houseClient.settleHouse(createdHouse.getId(), createdUser.getId());

        var parkingPlace = createdHouse.getParkingPlaces().getFirst();
        var lodger = settledHouse.getLodgers().getFirst();

        var dbHouseAfterSettle = DbHouse.getHouseById(settledHouse.getId());
        var dbParkingAfterSettle = DbHouse.getParkingPlaceByHouseId(createdHouse.getId());
        var dbUserAfterSettle = DbUser.getUserById(createdUser.getId());

        assertAll("Проверка покупки дома",
                () -> HouseAssertions.assertHouseId(settledHouse.getId(), dbHouseAfterSettle.getId()),
                () -> HouseAssertions.assertHousePrice(settledHouse.getPrice(), dbHouseAfterSettle.getPrice()),
                () -> HouseAssertions.assertFloorCount(settledHouse.getFloorCount(), dbHouseAfterSettle.getFloorCount()),

                () -> HouseAssertions.assertParkingPlaceId(parkingPlace.getId(), dbParkingAfterSettle.getId()),
                () -> HouseAssertions.assertIsCovered(parkingPlace.getIsCovered(), dbParkingAfterSettle.getIsCovered()),
                () -> HouseAssertions.assertIsWarm(parkingPlace.getIsWarm(), dbParkingAfterSettle.getIsWarm()),
                () -> HouseAssertions.assertPlaceCount(parkingPlace.getPlacesCount(), dbParkingAfterSettle.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(settledHouse.getId(), dbParkingAfterSettle.getHouseId())
        );

        assertAll("Проверка продажи дома",
                () -> UserAssertions.assertUserId(lodger.getId(), dbUserAfterSettle.getId()),
                () -> UserAssertions.assertFirstName(lodger.getFirstName(), dbUserAfterSettle.getFirstName()),
                () -> UserAssertions.assertSecondName(lodger.getSecondName(), dbUserAfterSettle.getSecondName()),
                () -> UserAssertions.assertAge(lodger.getAge(), dbUserAfterSettle.getAge()),
                () -> UserAssertions.assertSex(lodger.getSex(), dbUserAfterSettle.getSex()),

                () -> UserAssertions.assertHouseId(settledHouse.getId(), dbUserAfterSettle.getHouseId()),
                () -> UserAssertions.assertMoney(
                        dbUserAfterSettle.getMoney(),
                        initialUser.getMoney().subtract(initialHouse.getPrice()))
        );

        var evictedHouse = houseClient.evictHouse(settledHouse.getId(), createdUser.getId());

        var dbUserAfterEvict = DbUser.getUserById(createdUser.getId());

        assertAll("house after evict",
                () -> HouseAssertions.assertHouseId(evictedHouse.getId(), dbHouseAfterSettle.getId()),
                () -> HouseAssertions.assertHousePrice(evictedHouse.getPrice(), dbHouseAfterSettle.getPrice()),
                () -> HouseAssertions.assertFloorCount(evictedHouse.getFloorCount(), dbHouseAfterSettle.getFloorCount()),

                () -> HouseAssertions.assertParkingPlaceId(parkingPlace.getId(), dbParkingAfterSettle.getId()),
                () -> HouseAssertions.assertIsCovered(parkingPlace.getIsCovered(), dbParkingAfterSettle.getIsCovered()),
                () -> HouseAssertions.assertIsWarm(parkingPlace.getIsWarm(), dbParkingAfterSettle.getIsWarm()),
                () -> HouseAssertions.assertPlaceCount(parkingPlace.getPlacesCount(), dbParkingAfterSettle.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(evictedHouse.getId(), dbParkingAfterSettle.getHouseId())
        );

        assertAll("lodger after evict",
                () -> Assertions.assertNotEquals(evictedHouse.getId(), dbUserAfterEvict.getHouseId(),
                        "Пользователь не должен быть привязан к дому после выселения"),
                () -> UserAssertions.assertMoney(dbUserAfterEvict.getMoney(), initialUser.getMoney())
        );
    }
}
//todo Возможно стоит сделать ассерты по типу альфовских. На статус код и непустой массив в ответе

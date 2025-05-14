package house;

import assertion.HouseAssertions;
import base.BaseApiTest;
import client.api.HouseClient;
import db.DbHouse;
import io.qameta.allure.Epic;
import model.api.HouseDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Дома")
@DisplayName("Создание дома через API")
public class CreateHouseTest extends BaseApiTest {
    HouseClient houseClient;
    private HouseDto initialHouse;
    private HouseDto createdHouse;

    @BeforeEach
    public void setUp() {
        initialHouse = DataGenerator.generateHouse(BigDecimal.valueOf(1000000));
        houseClient = new HouseClient(token);
    }

    @AfterEach
    public void tearDown() {
        houseClient.deleteHouse(createdHouse.getId());
    }

    @Tag("api")
    @Tag("house")
    @DisplayName("Создание дома. POST /house")
    @Test
    public void createHouseWithoutLodgers() {
        createdHouse = houseClient.createHouse(initialHouse);

        var createdParkingPlaces = createdHouse.getParkingPlaces().getFirst();
        var dbHouse = DbHouse.getHouseById(createdHouse.getId());
        var dbParkingPlaceFromHouse = DbHouse.getParkingPlaceByHouseId(createdHouse.getId());

        assertAll("Проверка созданного дома",
                () -> HouseAssertions.assertHouseId(createdHouse.getId(), dbHouse.getId()),
                () -> HouseAssertions.assertHousePrice(createdHouse.getPrice(), dbHouse.getPrice()),
                () -> HouseAssertions.assertFloorCount(createdHouse.getFloorCount(), dbHouse.getFloorCount()),

                () -> HouseAssertions.assertParkingPlaceId(createdParkingPlaces.getId(), dbParkingPlaceFromHouse.getId()),
                () -> HouseAssertions.assertIsCovered(createdParkingPlaces.getIsCovered(), dbParkingPlaceFromHouse.getIsCovered()),
                () -> HouseAssertions.assertIsWarm(createdParkingPlaces.getIsWarm(), dbParkingPlaceFromHouse.getIsWarm()),
                () -> HouseAssertions.assertPlaceCount(createdParkingPlaces.getPlacesCount(), dbParkingPlaceFromHouse.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(createdHouse.getId(), dbParkingPlaceFromHouse.getHouseId())
        );
    }
}

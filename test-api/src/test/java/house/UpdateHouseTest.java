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
@DisplayName("Обновление дома через API")
public class UpdateHouseTest extends BaseApiTest {
    HouseClient houseClient;
    private HouseDto houseUpdate;
    private HouseDto createdHouse;

    @BeforeEach
    public void setUp() {
        var initialHouse = DataGenerator.generateHouse(BigDecimal.valueOf(1000000));
        houseUpdate = DataGenerator.generateHouse(BigDecimal.valueOf(2000000));
        houseClient = new HouseClient(token);
        createdHouse = houseClient.createHouse(initialHouse);
    }

    @AfterEach
    public void tearDown() {
        houseClient.deleteHouse(createdHouse.getId());
    }

    @Tag("api")
    @Tag("house")
    @DisplayName("Обновление дома. PUT /house/{houseId}")
    @Test
    public void testUpdateHouse() {
        var updatedHouse = houseClient.updateHouse(createdHouse.getId(), houseUpdate);

        var updatedParkingPlaces = updatedHouse.getParkingPlaces().getFirst();
        var dbUpdateHouse = DbHouse.getHouseById(updatedHouse.getId());
        var dbUpdateParkingPlace = DbHouse.getParkingPlaceByHouseId(updatedHouse.getId());

        assertAll("Проверка обновленного дома",
                () -> HouseAssertions.assertHouseId(updatedHouse.getId(), dbUpdateHouse.getId()),
                () -> HouseAssertions.assertHousePrice(updatedHouse.getPrice(), dbUpdateHouse.getPrice()),
                () -> HouseAssertions.assertFloorCount(updatedHouse.getFloorCount(), dbUpdateHouse.getFloorCount()),

                () -> HouseAssertions.assertParkingPlaceId(updatedParkingPlaces.getId(), dbUpdateParkingPlace.getId()),
                () -> HouseAssertions.assertIsCovered(updatedParkingPlaces.getIsCovered(), dbUpdateParkingPlace.getIsCovered()),
                () -> HouseAssertions.assertIsWarm(updatedParkingPlaces.getIsWarm(), dbUpdateParkingPlace.getIsWarm()),
                () -> HouseAssertions.assertPlaceCount(updatedParkingPlaces.getPlacesCount(), dbUpdateParkingPlace.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(updatedParkingPlaces.getId(), dbUpdateParkingPlace.getId())
        );
    }
}

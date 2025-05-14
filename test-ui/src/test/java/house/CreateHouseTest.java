package house;

import assertion.HouseAssertions;
import base.BaseUiTest;
import client.api.HouseClient;
import db.DbHouse;
import io.qameta.allure.Epic;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import page.HousePage;
import page.MainPage;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Дома")
@DisplayName("Создание дома")
public class CreateHouseTest extends BaseUiTest {

    private final MainPage mainPage = new MainPage();
    private final HousePage housePage = new HousePage();
    private HouseClient houseClient;

    private static final Faker faker = new Faker();
    private Integer newHouseId;

    @BeforeEach
    public void login() {
        houseClient = new HouseClient(token);
        mainPage.openCreateHousePage();
        housePage.isPageOpen();
    }

    @AfterEach
    public void after() {
        houseClient.deleteHouse(newHouseId);
    }

    @Tag("ui")
    @Tag("house")
    @DisplayName("Создание дома через UI")
    @Test
    public void createHouse() {
        int floors = faker.number().numberBetween(1, 5);
        BigDecimal price = BigDecimal.valueOf(faker.number().randomDouble(2, 100000, 1000000));

        int warmCovered = faker.number().numberBetween(1, 3);

        housePage.fillFloors(floors)
                .fillPrice(price)
                .fillWarmCoveredPlaces(warmCovered)
                .clickPushButton()
                .isStatusMessageCorrect("Status: Successfully pushed, code: 201");

        newHouseId = Integer.parseInt(housePage.getNewHouseId());
        var dbHouse = DbHouse.getHouseById(newHouseId);
        var dbParkingPlaces = DbHouse.getParkingPlaceByHouseId(newHouseId);

        assertAll("house settled",
                () -> HouseAssertions.assertHouseId(newHouseId, dbHouse.getId()),
                () -> HouseAssertions.assertHousePrice(price, dbHouse.getPrice()),
                () -> HouseAssertions.assertFloorCount(floors, dbHouse.getFloorCount()),

                () -> HouseAssertions.assertPlaceCount(warmCovered, dbParkingPlaces.getPlacesCount()),
                () -> HouseAssertions.assertParkingByHouseId(newHouseId, dbParkingPlaces.getHouseId())
        );
    }
}

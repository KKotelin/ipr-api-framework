package car;

import assertion.CarAssertions;
import base.BaseUiTest;
import client.api.CarClient;
import db.DbCar;
import io.qameta.allure.Epic;
import net.datafaker.Faker;
import org.junit.jupiter.api.*;
import page.CarPage;
import page.MainPage;
import utils.EngineTypeUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Автомобили")
@DisplayName("Создание автомобиля")
public class CreateCarTest extends BaseUiTest {

    private final MainPage mainPage = new MainPage();
    private final CarPage carPage = new CarPage();
    private CarClient carClient;

    private static final Faker faker = new Faker();
    private Integer newCarId;

    @BeforeEach
    public void login() {
        carClient = new CarClient(token);
        mainPage.openCreateCarPage();
        carPage.isPageOpen();
    }

    @AfterEach
    public void after() {
        carClient.deleteCar(newCarId);
    }

    @Tag("ui")
    @Tag("car")
    @DisplayName("Создание автомобиля через UI")
    @Test
    public void createCar() {
        String engineType = EngineTypeUtils.getNameById(faker.number().numberBetween(1, 6));
        String mark = faker.vehicle().make();
        String model = faker.vehicle().model();
        BigDecimal price = BigDecimal.valueOf(1000);
        carPage.fillEngineType(engineType)
                .fillMark(mark)
                .fillModel(model)
                .fillPrice(price)
                .clickPushButton()
                .isStatusMessageCorrect("Status: Successfully pushed, code: 201");
        newCarId = Integer.parseInt(carPage.getNewCarId());
        var dbCar = DbCar.getCarById(newCarId);

        assertAll("checks create car",
                () -> CarAssertions.assertCarId(newCarId, dbCar.getId()),
                () -> CarAssertions.assertEngineTypeId(EngineTypeUtils.getIdByName(engineType), dbCar.getEngineTypeId()),
                () -> CarAssertions.assertCarMark(mark, dbCar.getMark()),
                () -> CarAssertions.assertCarModel(model, dbCar.getModel()),
                () -> CarAssertions.assertCarPrice(price, dbCar.getPrice())
        );
    }
}

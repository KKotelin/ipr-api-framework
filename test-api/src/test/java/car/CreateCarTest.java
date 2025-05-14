package car;

import assertion.CarAssertions;
import base.BaseApiTest;
import client.api.CarClient;
import db.DbCar;
import io.qameta.allure.Epic;
import model.api.CarDto;
import org.junit.jupiter.api.*;
import utils.DataGenerator;
import utils.EngineTypeUtils;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;

@Epic("Автомобили")
@DisplayName("Создание нового автомобиля через API")
public class CreateCarTest extends BaseApiTest {
    private CarClient carClient;
    private CarDto initialCar;
    private CarDto createdCar;

    @BeforeEach
    public void before() {
        initialCar = DataGenerator.generateCar(1, BigDecimal.valueOf(10000));
        carClient = new CarClient(token);
    }

    @AfterEach
    public void after() {
        carClient.deleteCar(createdCar.getId());
    }

    @Tag("api")
    @Tag("car")
    @DisplayName("Создание автомобиля. POST /car")
    @Test
    public void testCreateCar() {
        createdCar = carClient.createCar(initialCar);

        var dbCar = DbCar.getCarById(createdCar.getId());

        assertAll("Проверка созданного автомобиля",
                () -> CarAssertions.assertCarId(createdCar.getId(), dbCar.getId()),
                () -> CarAssertions.assertEngineTypeId(EngineTypeUtils.getIdByName(createdCar.getEngineType()), dbCar.getEngineTypeId()),
                () -> CarAssertions.assertCarMark(createdCar.getMark(), dbCar.getMark()),
                () -> CarAssertions.assertCarModel(createdCar.getModel(), dbCar.getModel()),
                () -> CarAssertions.assertCarPrice(createdCar.getPrice(), dbCar.getPrice())
        );
    }
}

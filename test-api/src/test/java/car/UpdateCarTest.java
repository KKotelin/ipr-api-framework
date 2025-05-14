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
@DisplayName("Обновление автомобиля через API")
public class UpdateCarTest extends BaseApiTest {
    CarClient carClient;
    private CarDto carUpdate;
    private CarDto createdCar;

    @BeforeEach
    public void before() {
        var initialCar = DataGenerator.generateCar(1, BigDecimal.valueOf(10000));
        carClient = new CarClient(token);
        carUpdate = DataGenerator.generateCar(2, BigDecimal.valueOf(6000));
        createdCar = carClient.createCar(initialCar);

    }

    @AfterEach
    public void after() {
        carClient.deleteCar(createdCar.getId());
    }

    @Tag("api")
    @Tag("car")
    @DisplayName("Обновление автомобиля. PUT /car/{carId}")
    @Test
    public void updateUser() {
        var updatedCar = carClient.updateCar(createdCar.getId(), carUpdate);

        var dbCarAfterUpdate = DbCar.getCarById(createdCar.getId());

        assertAll("Проверка обновленного автомобиля",
                () -> CarAssertions.assertCarId(updatedCar.getId(), dbCarAfterUpdate.getId()),
                () -> CarAssertions.assertEngineTypeId(EngineTypeUtils.getIdByName(updatedCar.getEngineType()), dbCarAfterUpdate.getEngineTypeId()),
                () -> CarAssertions.assertCarMark(updatedCar.getMark(), dbCarAfterUpdate.getMark()),
                () -> CarAssertions.assertCarModel(updatedCar.getModel(), dbCarAfterUpdate.getModel()),
                () -> CarAssertions.assertCarPrice(updatedCar.getPrice(), dbCarAfterUpdate.getPrice())
        );
    }
}

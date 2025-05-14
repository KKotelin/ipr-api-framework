package client.api;

import client.BaseApiClient;
import client.endpoint.CarEndpoints;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import model.api.CarDto;
import utils.ConversionUtils;

public class CarClient extends BaseApiClient {

    public CarClient(String token) {
        super(token);
    }
    public CarDto createCar(CarDto car) {
        return Allure.step("Create a new car", () -> {
            CarDto createdCar = request()
                    .body(car)
                    .when()
                    .post(CarEndpoints.CREATE_CAR.getValue())
                    .then()
                    .log().all()
                    .statusCode(201)
                    .extract().as(CarDto.class);

            Allure.addAttachment("Created car (server response)", "application/json",
                    ConversionUtils.toPrettyJson(createdCar), ".json");

            return createdCar;
        });
    }

    public CarDto updateCar(Integer carId, CarDto car) {
        return Allure.step("Update car with ID = " + carId, () -> {
            CarDto updatedCar = request()
                    .body(car)
                    .when()
                    .put(CarEndpoints.UPDATE_CAR.getValue(), carId)
                    .then()
                    .log().all()
                    .statusCode(202)
                    .extract().as(CarDto.class);

            Allure.addAttachment("Updated car", "application/json",
                    ConversionUtils.toPrettyJson(updatedCar), ".json");

            return updatedCar;
        });
    }

    public Response deleteCar(Integer carId) {
        return Allure.step("Delete car with ID = " + carId, () -> {
            Response response = request()
                    .when()
                    .delete(CarEndpoints.DELETE_CAR.getValue(), carId)
                    .then()
                    .log().all()
                    .statusCode(204)
                    .extract().response();

            Allure.addAttachment("Car deletion — status code", String.valueOf(response.statusCode()));
            return response;
        });
    }

}

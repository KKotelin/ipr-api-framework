package client.api;

import client.BaseApiClient;
import client.endpoint.UserEndpoints;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import model.api.UserDto;
import utils.ConversionUtils;

public class UserClient extends BaseApiClient {

    public UserClient(String token) {
        super(token);
    }

    public UserDto createUser(UserDto user) {
        return Allure.step("Create a new user", () -> {
            UserDto createdUser = request()
                    .body(user)
                    .when()
                    .post(UserEndpoints.CREATE_USER.getValue())
                    .then()
                    .log().all()
                    .statusCode(201)
                    .extract().as(UserDto.class);

            Allure.addAttachment("Created user (server response)", "application/json",
                    ConversionUtils.toPrettyJson(createdUser), ".json");

            return createdUser;
        });
    }

    public UserDto updateUser(int userId, UserDto user) {
        return Allure.step("Update user with ID = " + userId, () -> {
            UserDto updatedUser = request()
                    .body(user)
                    .when()
                    .put(UserEndpoints.UPDATE_USER.getValue(), userId)
                    .then()
                    .log().all()
                    .statusCode(202)
                    .extract().as(UserDto.class);

            Allure.addAttachment("Updated user", "application/json",
                    ConversionUtils.toPrettyJson(updatedUser), ".json");

            return updatedUser;
        });
    }

    public Response deleteUser(Integer userId) {
        return Allure.step("Delete user with ID = " + userId, () -> {
            Response response = request()
                    .when()
                    .delete(UserEndpoints.DELETE_USER.getValue(), userId)
                    .then()
                    .log().all()
                    .statusCode(204)
                    .extract().response();

            Allure.addAttachment("User deletion — status code", String.valueOf(response.statusCode()));
            return response;
        });
    }

    public UserDto sellCarToUser(Integer userId, Integer carId) {
        return Allure.step("Sell car to user (userId = " + userId + ", carId = " + carId + ")", () -> {
            UserDto result = request()
                    .when()
                    .post(UserEndpoints.SELL_CAR_USER.getValue(), userId, carId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(UserDto.class);

            Allure.addAttachment("User after car sale", "application/json",
                    ConversionUtils.toPrettyJson(result), ".json");

            return result;
        });
    }

    public UserDto buyCarToUser(Integer userId, Integer carId) {
        return Allure.step("User buys car (userId = " + userId + ", carId = " + carId + ")", () -> {
            UserDto result = request()
                    .when()
                    .post(UserEndpoints.BUY_CAR_USER.getValue(), userId, carId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(UserDto.class);

            Allure.addAttachment("User after car purchase", "application/json",
                    ConversionUtils.toPrettyJson(result), ".json");

            return result;
        });
    }

    public UserDto addMoneyToUser(Integer userId, Integer amount) {
        return Allure.step("Add money to user (userId = " + userId + ", amount = " + amount + ")", () -> {
            UserDto result = request()
                    .when()
                    .post(UserEndpoints.ADD_MONEY.getValue(), userId, amount)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(UserDto.class);

            Allure.addAttachment("User after balance top-up", "application/json",
                    ConversionUtils.toPrettyJson(result), ".json");

            return result;
        });
    }
}

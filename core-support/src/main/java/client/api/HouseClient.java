package client.api;

import client.BaseApiClient;
import client.endpoint.HouseEndpoints;
import io.qameta.allure.Allure;
import io.restassured.response.Response;
import model.api.HouseDto;
import utils.ConversionUtils;

public class HouseClient extends BaseApiClient {

    public HouseClient(String token) {
        super(token);
    }

    public HouseDto createHouse(HouseDto house) {
        return Allure.step("Create a new house", () -> {
            HouseDto createdHouse = request()
                    .body(house)
                    .when()
                    .post(HouseEndpoints.CREATE_HOUSE.getValue())
                    .then()
                    .log().all()
                    .statusCode(201)
                    .extract().as(HouseDto.class);

            Allure.addAttachment("Created house (server response)", "application/json",
                    ConversionUtils.toPrettyJson(createdHouse), ".json");

            return createdHouse;
        });
    }

    public HouseDto updateHouse(Integer houseId, HouseDto house) {
        return Allure.step("Update house with ID = " + houseId, () -> {
            HouseDto updatedHouse = request()
                    .body(house)
                    .when()
                    .put(HouseEndpoints.UPDATE_HOUSE.getValue(), houseId)
                    .then()
                    .log().all()
                    .statusCode(202)
                    .extract().as(HouseDto.class);

            Allure.addAttachment("Updated house", "application/json",
                    ConversionUtils.toPrettyJson(updatedHouse), ".json");

            return updatedHouse;
        });
    }

    public Response deleteHouse(Integer houseId) {
        return Allure.step("Delete house with ID = " + houseId, () -> {
            Response response = request()
                    .when()
                    .delete(HouseEndpoints.DELETE_HOUSE.getValue(), houseId)
                    .then()
                    .log().all()
                    .statusCode(204)
                    .extract().response();

            Allure.addAttachment("House deletion — status code", String.valueOf(response.statusCode()));
            return response;
        });
    }

    public HouseDto settleHouse(Integer houseId, Integer userId) {
        return Allure.step("Settle user (userId = " + userId + ") into house (houseId = " + houseId + ")", () -> {
            HouseDto result = request()
                    .when()
                    .post(HouseEndpoints.SETTLE_HOUSE.getValue(), houseId, userId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(HouseDto.class);

            Allure.addAttachment("House after settling", "application/json",
                    ConversionUtils.toPrettyJson(result), ".json");

            return result;
        });
    }

    public HouseDto evictHouse(Integer houseId, Integer userId) {
        return Allure.step("Evict user (userId = " + userId + ") from house (houseId = " + houseId + ")", () -> {
            HouseDto result = request()
                    .when()
                    .post(HouseEndpoints.EVICT_HOUSE.getValue(), houseId, userId)
                    .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(HouseDto.class);

            Allure.addAttachment("House after eviction", "application/json",
                    ConversionUtils.toPrettyJson(result), ".json");

            return result;
        });
    }
}

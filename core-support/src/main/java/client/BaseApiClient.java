package client;

import config.Config;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public abstract class BaseApiClient {
    protected final String token;

    public BaseApiClient(String token) {
        this.token = token;
    }

    protected RequestSpecification request() {
        return given()
                .filter(new AllureRestAssured())
                .log().all()
                .spec(new RequestSpecBuilder()
                        .setBaseUri(Config.getInstance().getEnv().getApiConfig().getBaseUrl())
                        .setContentType(ContentType.JSON)
                        .addHeader("Authorization", "Bearer " + token)
                        .build());
    }
}
package client.auth;

import client.api.CarClient;
import config.Config;
import io.qameta.allure.Allure;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class AuthClient {

    private static final Logger logger = LoggerFactory.getLogger(CarClient.class);

    public String login(String username, String password) {
        return Allure.step("User authorization: " + username, () -> {
            try {
                Map<String, Object> body = Map.of(
                        "username", username,
                        "password", password
                );

                String token = RestAssured
                        .given()
                        .baseUri(Config.getInstance().getEnv().getApiConfig().getBaseUrl())
                        .contentType(ContentType.JSON)
                        .body(body)
                        .log().all()
                        .when()
                        .post("/login")
                        .then()
                        .log().all()
                        .statusCode(202)
                        .extract()
                        .path("access_token");

                Allure.addAttachment("Access Token", token);
                return token;

            } catch (Exception e) {
                logger.error("Login failed for user={}", username, e);
                throw e;
            }
        });
    }

    public String loginDefault() {
        return login(
                Config.getInstance().getEnv().getApiConfig().getUsername(),
                Config.getInstance().getEnv().getApiConfig().getPassword()
        );
    }
}

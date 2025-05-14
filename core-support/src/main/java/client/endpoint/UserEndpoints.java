package client.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserEndpoints {

    CREATE_USER("/user"),
    UPDATE_USER("/user/{userId}"),
    DELETE_USER("/user/{userId}"),
    SELL_CAR_USER("/user/{userId}/sellCar/{carId}"),
    BUY_CAR_USER("/user/{userId}/buyCar/{carId}"),
    ADD_MONEY("/user/{userId}/money/{amount}");

    private final String value;
}



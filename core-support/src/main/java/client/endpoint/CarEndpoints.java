package client.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CarEndpoints {

    CREATE_CAR("/car"),
    UPDATE_CAR("/car/{carId}"),
    DELETE_CAR("/car/{carId}"),
    USERS_CAR_LIST("/user/{userId}/cars");

    private final String value;
}

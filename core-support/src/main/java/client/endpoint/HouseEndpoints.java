package client.endpoint;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HouseEndpoints {

    CREATE_HOUSE("/house"),
    UPDATE_HOUSE("/house/{houseId}"),
    DELETE_HOUSE("/house/{houseId}"),
    SETTLE_HOUSE("/house/{houseId}/settle/{userId}"),
    EVICT_HOUSE("/house/{houseId}/evict/{userId}");

    private final String value;
}

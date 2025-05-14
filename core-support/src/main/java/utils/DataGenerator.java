package utils;

import model.api.CarDto;
import model.api.HouseDto;
import model.api.ParkingPlaceDto;
import model.api.UserDto;
import net.datafaker.Faker;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

public class DataGenerator {
    private static final Faker faker = new Faker();

    public static UserDto generateUser(BigDecimal money) {
        return UserDto.builder()
                .firstName(faker.name().firstName())
                .secondName(faker.name().lastName())
                .age(faker.number().numberBetween(18, 60))
                .sex(faker.options().option("MALE", "FEMALE"))
                .money(money)
                .build();
    }

    public static CarDto generateCar(Integer engineTypeId, BigDecimal price) {
        return CarDto.builder()
                .engineType(EngineTypeUtils.getNameById(engineTypeId))
                .mark(faker.vehicle().make())
                .model(faker.vehicle().model())
                .price(price)
                .build();
    }

    public static HouseDto generateHouse(BigDecimal priceHouse) {
        List<ParkingPlaceDto> parkingPlaces = List.of(
                new ParkingPlaceDto(
                        0,
                        faker.bool().bool(),
                        faker.bool().bool(),
                        faker.number().numberBetween(1, 10)
                )
        );

        List<UserDto> lodgers = Collections.emptyList();

        return new HouseDto(
                0,
                faker.number().numberBetween(1, 5),
                priceHouse,
                parkingPlaces,
                lodgers
        );
    }

    public static ParkingPlaceDto generateParkingPlace(Boolean isWarm, Boolean isCovered, Integer placesCount) {
        return ParkingPlaceDto.builder()
                .isWarm(isWarm)
                .isCovered(isCovered)
                .placesCount(placesCount)
                .build();
    }
}

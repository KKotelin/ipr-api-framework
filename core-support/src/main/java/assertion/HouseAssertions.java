package assertion;

import io.qameta.allure.Allure;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HouseAssertions {

    public static void assertHouseId(Integer expected, Integer actual) {
        Allure.step("Verify house ID", () ->
                assertEquals(expected, actual, "House ID does not match"));
    }

    public static void assertFloorCount(Integer expected, Integer actual) {
        Allure.step("Verify number of floors", () ->
                assertEquals(expected, actual, "Floor count does not match"));
    }

    public static void assertHousePrice(BigDecimal expected, BigDecimal actual) {
        Allure.step("Verify house price", () ->
                assertEquals(0, expected.compareTo(actual), "House price does not match"));
    }

    public static void assertParkingPlaceId(Integer expected, Integer actual) {
        Allure.step("Verify parking place ID", () ->
                assertEquals(expected, actual, "Parking place ID does not match"));
    }

    public static void assertIsCovered(Boolean expected, Boolean actual) {
        Allure.step("Verify isCovered parameter", () ->
                assertEquals(expected, actual, "isCovered value does not match"));
    }

    public static void assertIsWarm(Boolean expected, Boolean actual) {
        Allure.step("Verify isWarm parameter", () ->
                assertEquals(expected, actual, "isWarm value does not match"));
    }

    public static void assertPlaceCount(Integer expected, Integer actual) {
        Allure.step("Verify parking place count", () ->
                assertEquals(expected, actual, "Place count in parking does not match"));
    }

    public static void assertParkingByHouseId(Integer expected, Integer actual) {
        Allure.step("Verify parking-house link", () ->
                assertEquals(expected, actual, "House ID in parking does not match"));
    }
}

package assertion;

import io.qameta.allure.Allure;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarAssertions {

    public static void assertCarId(Integer expected, Integer actual) {
        Allure.step("Verify car ID", () ->
                assertEquals(expected, actual, "Car ID does not match"));
    }

    public static void assertCarMark(String expected, String actual) {
        Allure.step("Verify car brand", () ->
                assertEquals(expected, actual, "Car brand does not match"));
    }

    public static void assertCarModel(String expected, String actual) {
        Allure.step("Verify car model", () ->
                assertEquals(expected, actual, "Car model does not match"));
    }

    public static void assertCarPrice(BigDecimal expected, BigDecimal actual) {
        Allure.step("Verify car price", () ->
                assertEquals(0, expected.compareTo(actual), "Car price does not match"));
    }

    public static void assertEngineTypeId(Integer expected, Integer actual) {
        Allure.step("Verify engine type ID", () ->
                assertEquals(expected, actual, "Engine type ID does not match"));
    }

    public static void assertOwnerId(Integer expected, Integer actual) {
        Allure.step("Verify car owner ID", () ->
                assertEquals(expected, actual, "Owner ID (person_id) does not match"));
    }
}

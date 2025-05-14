package assertion;

import io.qameta.allure.Allure;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAssertions {

    public static void assertUserId(Integer expected, Integer actual) {
        Allure.step("Verify user ID", () ->
                assertEquals(expected, actual, "User ID does not match"));
    }

    public static void assertFirstName(String expected, String actual) {
        Allure.step("Verify user's first name", () ->
                assertEquals(expected, actual, "First name does not match"));
    }

    public static void assertSecondName(String expected, String actual) {
        Allure.step("Verify user's last name", () ->
                assertEquals(expected, actual, "Last name does not match"));
    }

    public static void assertAge(Integer expected, Integer actual) {
        Allure.step("Verify user's age", () ->
                assertEquals(expected, actual, "Age does not match"));
    }

    public static void assertMoney(BigDecimal expected, BigDecimal actual) {
        Allure.step("Verify user's balance", () ->
                assertEquals(0, expected.compareTo(actual), "Balance does not match"));
    }

    public static void assertSex(String expected, Boolean actual) {
        Allure.step("Verify sex (from string)", () -> {
            boolean expectedSex = mapSex(expected);
            assertEquals(expectedSex, actual, "Sex does not match");
        });
    }

    public static void assertSex(Boolean expected, Boolean actual) {
        Allure.step("Verify sex", () ->
                assertEquals(expected, actual, "Sex does not match"));
    }

    public static void assertHouseId(Integer expected, Integer actual) {
        Allure.step("Verify user's house ID", () ->
                assertEquals(expected, actual, "House ID does not match"));
    }

    private static boolean mapSex(String sex) {
        if ("MALE".equalsIgnoreCase(sex)) return true;
        if ("FEMALE".equalsIgnoreCase(sex)) return false;
        throw new IllegalArgumentException("Unsupported sex value: " + sex);
    }
}

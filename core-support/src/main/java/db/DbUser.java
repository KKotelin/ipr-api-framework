package db;


import io.qameta.allure.Allure;
import model.db.User;
import utils.ConversionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbUser {

    public static User getUserById(Integer id) {
        return Allure.step("Retrieve user from database", () -> {
            String query = "SELECT * FROM person WHERE id = ?";
            try (PreparedStatement statement = DbHelper.getConnection().prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    User user = new User(
                            rs.getInt("id"),
                            rs.getInt("age"),
                            rs.getString("first_name"),
                            rs.getBigDecimal("money"),
                            rs.getString("second_name"),
                            rs.getBoolean("sex"),
                            rs.getInt("house_id")
                    );
                    Allure.addAttachment("User from DB (JSON)", "application/json",
                            ConversionUtils.toPrettyJson(user), ".json");

                    return user;
                } else {
                    throw new AssertionError("User with id=" + id + " not found in the database");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving user: " + e.getMessage(), e);
            }
        });
    }
}

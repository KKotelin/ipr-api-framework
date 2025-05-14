package db;

import io.qameta.allure.Allure;
import model.db.Car;
import utils.ConversionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbCar {

    public static Car getCarById(Integer id) {
        return Allure.step("Retrieve car from database (ID = " + id + ")", () -> {
            String query = "SELECT * FROM car WHERE id = ?";
            try (PreparedStatement statement = DbHelper.getConnection().prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    Car car = new Car(
                            rs.getInt("id"),
                            rs.getString("mark"),
                            rs.getString("model"),
                            rs.getBigDecimal("price"),
                            rs.getInt("engine_type_id"),
                            rs.getInt("person_id")
                    );

                    Allure.addAttachment("Car from DB (JSON)", "application/json",
                            ConversionUtils.toPrettyJson(car), ".json");

                    return car;
                } else {
                    throw new AssertionError("Car with id=" + id + " not found in the database");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving car: " + e.getMessage(), e);
            }
        });
    }
}

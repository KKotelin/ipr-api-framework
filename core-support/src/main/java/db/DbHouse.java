package db;

import io.qameta.allure.Allure;
import model.db.House;
import model.db.ParkingPlace;
import utils.ConversionUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbHouse {

    public static House getHouseById(Integer id) {
        return Allure.step("Retrieve house from database (ID = " + id + ")", () -> {
            String query = "SELECT * FROM house WHERE id = ?";
            try (PreparedStatement statement = DbHelper.getConnection().prepareStatement(query)) {
                statement.setInt(1, id);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    House house = new House(
                            rs.getInt("id"),
                            rs.getInt("floor_count"),
                            rs.getBigDecimal("price")
                    );

                    Allure.addAttachment("House from DB (JSON)", "application/json",
                            ConversionUtils.toPrettyJson(house), ".json");

                    return house;
                } else {
                    throw new AssertionError("House with id=" + id + " not found in the database");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving house: " + e.getMessage(), e);
            }
        });
    }

    public static ParkingPlace getParkingPlaceByHouseId(Integer houseId) {
        return Allure.step("Retrieve parking place by house ID (houseId = " + houseId + ")", () -> {
            String query = "SELECT * FROM parking_place WHERE house_id = ?";
            try (PreparedStatement statement = DbHelper.getConnection().prepareStatement(query)) {
                statement.setInt(1, houseId);
                ResultSet rs = statement.executeQuery();

                if (rs.next()) {
                    ParkingPlace parking = new ParkingPlace(
                            rs.getInt("id"),
                            rs.getBoolean("is_warm"),
                            rs.getBoolean("is_covered"),
                            rs.getInt("places_count"),
                            rs.getInt("house_Id")
                    );

                    Allure.addAttachment("Parking place from DB (JSON)", "application/json",
                            ConversionUtils.toPrettyJson(parking), ".json");

                    return parking;
                } else {
                    throw new AssertionError("Parking place for house with id=" + houseId + " not found in the database");
                }

            } catch (SQLException e) {
                throw new RuntimeException("Error retrieving parking place: " + e.getMessage(), e);
            }
        });
    }
}

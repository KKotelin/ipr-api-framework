package db;

import config.Config;
import config.DbConfigDto;
import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHelper {
    @Getter
    private static final Connection connection;

    static {
        try {
            DbConfigDto dbConfig = Config.getInstance().getDb();
            connection = DriverManager.getConnection(
                    dbConfig.getUrl(),
                    dbConfig.getUsername(),
                    dbConfig.getPassword()
            );
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to the database: " + e.getMessage(), e);
        }
    }
}

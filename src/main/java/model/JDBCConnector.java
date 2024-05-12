package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Util class for connecting to DB with JDBC
 */
public final class JDBCConnector {
    private static final String JDBC_URL = "jdbc:sqlite:C:/Users/Arsenii/JavaProjects/CurrencyExchange/currency_exchange";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(JDBC_URL);
        } catch (ClassNotFoundException | SQLException e) {
            //responseStatus = 500;
        }

        return connection;
    }
}

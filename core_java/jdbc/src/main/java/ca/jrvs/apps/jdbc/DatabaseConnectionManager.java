package ca.jrvs.apps.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionManager {

    public final String url;
    public final Properties properties;

    public DatabaseConnectionManager(String host, String dataBaseName,
                                     String userName, String password) {

        this.url = "jdbc:postgresql://" + host + "/" + dataBaseName;
        this.properties = new Properties();
        this.properties.setProperty("user", userName);
        this.properties.setProperty("password", password);
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(this.url, this.properties);
    }
}

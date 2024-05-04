import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
    public static Connection connect() throws SQLException {
        // JDBC Connection parameters
        String dbHost = "localhost";
        String dbPort = "5432";
        String dbName = "weather_forecast";
        String dbUser = "weather_forecast_admin";
        String dbPassword = "wfadmin";
        String dbUrl = "jdbc:postgresql://" + dbHost + ":" + dbPort + "/" + dbName;


        try {
            // Open a connection
            return DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException  e) {
            System.err.println(e.getMessage());
            return null;
        }
    }
}

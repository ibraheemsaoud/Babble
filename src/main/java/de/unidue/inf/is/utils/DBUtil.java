package de.unidue.inf.is.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.ibm.db2.jcc.DB2Driver;



public final class DBUtil {

    private DBUtil() {
    }


    static {
        com.ibm.db2.jcc.DB2Driver driver = new DB2Driver();
        try {
            DriverManager.registerDriver(driver);
        }
        catch (SQLException e) {
            throw new Error("Laden des Datenbanktreiber nicht möglich");
        }
    }


    public static Connection getConnection(String database) throws SQLException {
        final String url = "jdbc:db2:" + database;
        return DriverManager.getConnection(url);
    }


    public static Connection getExternalConnection(String database) throws SQLException {
        Properties properties = new Properties();
        properties.setProperty("securityMechanism",
                        Integer.toString(com.ibm.db2.jcc.DB2BaseDataSource.CLEAR_TEXT_PASSWORD_SECURITY));
        properties.setProperty("user", "dbp40");
        properties.setProperty("password", "aegho7te");

        final String url = "jdbc:db2://hecate.is.inf.uni-due.de:50040/" + database + ":currentSchema=dbp40;";
        System.out.println(url);
        Connection connection = DriverManager.getConnection(url, properties);
        return connection;
    }


    public static boolean checkDatabaseExistsExternal(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getExternalConnection(database)) {
            exists = true;
        }
        catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }


    public static boolean checkDatabaseExists(String database) {
        // Nur für Demozwecke!
        boolean exists = false;

        try (Connection connection = getConnection(database)) {
            exists = true;
        }
        catch (SQLException e) {
            exists = false;
            e.printStackTrace();
        }

        return exists;
    }

}

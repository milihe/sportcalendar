package org.sradar;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static spark.Spark.*;

public class Main {
    private static String dbConnectionString = getDbConnectionString();

    public static void main(String[] args) {
        get("/", (req, res) -> {
            try(var conn = getDbConnection()) {
                var dbAccess = new DbAccess(conn);
                var events = dbAccess.getEvents();
                var response = "";
                for(var e: events) {
                    response = response + "<br/>" + "id:" + e.getEventId() + ", start:" + e.getStart() + ", sport:" + e.getSport() + ", t1:" + e.getTeam1() + ", t2:" + e.getTeam2();
                }
                return response;
            }
        });
    }

    private static Connection getDbConnection() throws SQLException {
        return DriverManager.getConnection(dbConnectionString);
    }

    private static String getDbConnectionString() {
        try(var input = Main.class.getClassLoader().getResourceAsStream("config.properties")) {
            var prop = new Properties();
            prop.load(input);
            return prop.getProperty("db.connection");
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
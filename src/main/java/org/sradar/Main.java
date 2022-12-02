package org.sradar;

import spark.template.velocity.VelocityTemplateEngine;

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
                return Controller.getEvents(req, res, new DbAccess(conn));
            }
        }, new VelocityTemplateEngine());

        get("/add-event", (req, res) -> {
            try(var conn = getDbConnection()) {
                return Controller.addEvent(req, res, new DbAccess(conn));
            }
        }, new VelocityTemplateEngine());

        post("/add-event", (req, res) -> {
            try(var conn = getDbConnection()) {
                return Controller.addEvent(req, res, new DbAccess(conn));
            }
        }, new VelocityTemplateEngine());
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
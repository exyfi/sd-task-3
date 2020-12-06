package com.exyfi.onlineshop.utils;

import com.sun.org.slf4j.internal.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Class that provides void to configure DB for this app.
 */
public class DbUtils {

    private static final Logger log = Logger.getLogger(DbUtils.class.getName());

    private static final String CREATE_DATABASE_QUERY = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    public static void initDb() {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            log.info("configuring db for product app");
            Statement stmt = c.createStatement();
            stmt.executeUpdate(CREATE_DATABASE_QUERY);
            stmt.close();
            log.info("db configured successfully");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

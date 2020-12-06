package com.exyfi.onlineshop.utils;

import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Class provides init configuration DB for this app.
 */
public class DbUtils {

    private static final Logger log = Log.getLogger(DbUtils.class.getName());

    private static final String CREATE_DATABASE_QUERY = "CREATE TABLE IF NOT EXISTS PRODUCT" +
            "(ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            " NAME           TEXT    NOT NULL, " +
            " PRICE          INT     NOT NULL)";

    /**
     * Inits DB before start application.
     */
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

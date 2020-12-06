package com.exyfi.onlineshop.dao;

import com.exyfi.onlineshop.dao.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DbProductQueryService {

    private static final String ADD_PRODUCT_SQL_QUERY = "INSERT INTO PRODUCT " +
            "(NAME, PRICE) VALUES (\"%s\",%s)";

    public void addProduct(Product productItem) {
        final String sqlQuery = String.format(ADD_PRODUCT_SQL_QUERY, productItem.getName(), productItem.getPrice());
        executeQuery(sqlQuery);
    }

    private void executeQuery(final String query) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                stmt.executeUpdate(query);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

package com.exyfi.onlineshop.dao;

import com.exyfi.onlineshop.dao.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DbProductQueryService {

    private static final String ADD_PRODUCT_SQL_QUERY = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\",%s)";
    private static final String GET_PRODUCTS_SQL_QUERY = "SELECT * FROM PRODUCT";

    public void addProduct(Product productItem) {
        final String sqlQuery = String.format(ADD_PRODUCT_SQL_QUERY, productItem.getName(), productItem.getPrice());
        executeUpdateQuery(sqlQuery);
    }


    public List<Product> getProducts() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_PRODUCTS_SQL_QUERY);

        return extractProducts(rs);
    }

    private ResultSet executeSelectQuery(String query) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                stmt.close();
                return rs;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdateQuery(final String query) {
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

    private List<Product> extractProducts(ResultSet rs) throws SQLException {
        List<Product> result = new ArrayList<>();
        while (rs.next()) {
            String name = rs.getString("name");
            int price = rs.getInt("price");
            result.add(new Product(name, price));
        }
        rs.close();
        return result;
    }
}

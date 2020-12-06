package com.exyfi.onlineshop.dao;

import com.exyfi.onlineshop.dao.model.Product;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DbProductQueryService {

    private static final String ADD_PRODUCT_SQL_QUERY = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\",%s)";
    private static final String GET_PRODUCTS_SQL_QUERY = "SELECT * FROM PRODUCT";
    private static final String GET_MAX_PRICE_PRODUCT_SQL_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    private static final String GET_MIN_PRICE_PRODUCT_SQL_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    private static final String GET_SUM_PRODUCT_PRICE_SQL_QUERY = "SELECT SUM(price) as sum FROM PRODUCT";
    private static final String GET_PRODUCT_COUNT_SQL_QUERY = "SELECT COUNT(*) as cnt FROM PRODUCT";

    public void addProduct(Product productItem) {
        final String sqlQuery = String.format(ADD_PRODUCT_SQL_QUERY, productItem.getName(), productItem.getPrice());
        executeUpdateQuery(sqlQuery);
    }


    public List<Product> getProducts() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_PRODUCTS_SQL_QUERY);

        return extractProducts(rs);
    }

    public Optional<Product> getMaxPriceProduct() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_MAX_PRICE_PRODUCT_SQL_QUERY);

        return extractProducts(rs).stream().findFirst();
    }

    public Optional<Product> getMinPriceProduct() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_MIN_PRICE_PRODUCT_SQL_QUERY);

        return extractProducts(rs).stream().findFirst();
    }

    public int getProductsCount() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_PRODUCT_COUNT_SQL_QUERY);

        return rs.getInt("cnt");
    }

    public long getProductPriceSum() throws SQLException {
        ResultSet rs = executeSelectQuery(GET_SUM_PRODUCT_PRICE_SQL_QUERY);

        return rs.getLong("sum");
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

package com.exyfi.onlineshop.dao;

import com.exyfi.onlineshop.dao.model.Product;
import com.exyfi.onlineshop.exceptions.ProductShopException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DbProductQueryService {

    private static final String DB_URL = "jdbc:sqlite:test.db";

    private static final String ADD_PRODUCT_SQL_QUERY = "INSERT INTO PRODUCT (NAME, PRICE) VALUES (\"%s\",%s)";
    private static final String GET_PRODUCTS_SQL_QUERY = "SELECT * FROM PRODUCT";
    private static final String GET_MAX_PRICE_PRODUCT_SQL_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1";
    private static final String GET_MIN_PRICE_PRODUCT_SQL_QUERY = "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    private static final String GET_SUM_PRODUCT_PRICE_SQL_QUERY = "SELECT SUM(price) as sum FROM PRODUCT";
    private static final String GET_PRODUCT_COUNT_SQL_QUERY = "SELECT COUNT(*) as cnt FROM PRODUCT";
    public static final String DB_ERROR_MESSAGE = "Exception occurred during operations with DB";

    public void addProduct(Product productItem) {
        final String sqlQuery = String.format(ADD_PRODUCT_SQL_QUERY, productItem.getName(), productItem.getPrice());
        executeUpdateQuery(sqlQuery);
    }

    public List<Product> getProducts() {
        return executeSelectQuery(GET_PRODUCTS_SQL_QUERY, DbProductQueryService::extractProducts);
    }

    public Optional<Product> getMaxPriceProduct() {
        return executeSelectQuery(GET_MAX_PRICE_PRODUCT_SQL_QUERY, DbProductQueryService::extractProducts).stream()
                .findFirst();

    }

    public Optional<Product> getMinPriceProduct() {
        return executeSelectQuery(GET_MIN_PRICE_PRODUCT_SQL_QUERY, DbProductQueryService::extractProducts).stream()
                .findFirst();
    }

    public long getProductsCount() {
        return executeSelectQuery(GET_PRODUCT_COUNT_SQL_QUERY, DbProductQueryService::extractNumberValueFromRs);

    }

    public long getProductPriceSum() {
        return executeSelectQuery(GET_SUM_PRODUCT_PRICE_SQL_QUERY, DbProductQueryService::extractNumberValueFromRs);
    }

    private static long extractNumberValueFromRs(ResultSet resultSet) {
        try {
            return resultSet.getLong(1);
        } catch (SQLException e) {
            throw new ProductShopException(DB_ERROR_MESSAGE, e);
        }
    }

    private <T> T executeSelectQuery(String query, Function<ResultSet, T> resultMapper) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db");
                 Statement stmt = c.createStatement()) {

                ResultSet rs = stmt.executeQuery(query);

                return resultMapper.apply(rs);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void executeUpdateQuery(final String query) {
        try {
            try (Connection c = DriverManager.getConnection(DB_URL);
                 Statement stmt = c.createStatement()) {
                stmt.executeUpdate(query);
            }
        } catch (Exception e) {
            throw new ProductShopException(DB_ERROR_MESSAGE, e);
        }
    }

    private static List<Product> extractProducts(ResultSet rs) {
        List<Product> result = new ArrayList<>();
        try {
            while (rs.next()) {
                String name = rs.getString("name");
                int price = rs.getInt("price");
                result.add(new Product(name, price));
            }
            rs.close();
            return result;
        } catch (SQLException e) {
            throw new ProductShopException(DB_ERROR_MESSAGE, e);
        }
    }
}

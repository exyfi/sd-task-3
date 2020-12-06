package com.exyfi.onlineshop.dao.model;

/**
 * Product info.
 */
public class Product {

    /**
     * Product name.
     */
    private String name;

    /**
     * Product price.
     */
    private Long price;

    public Product(final String name, long price) {
        this.name = name;
        this.price = price;
    }

    public Long getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }
}

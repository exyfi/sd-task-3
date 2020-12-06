package com.exyfi.onlineshop.exceptions;

/**
 * Base app exception.
 */
public class ProductShopException extends RuntimeException {

    public ProductShopException(String message) {
        super(message);
    }

    public ProductShopException(String message, Throwable cause) {
        super(message, cause);
    }
}

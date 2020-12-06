package com.exyfi.onlineshop.utils;

import com.exyfi.onlineshop.dao.model.Product;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * Utility class for handling html response.
 */
public class HtmlResponseUtils {

    private static final Function<String, String> HTML_RESPONSE_PATTERN = item -> String.format("<html><body>\n%s\n</body></html>", item);

    private static final Function<List<Product>, String> PRODUCTS_TO_HTML_VIEW = productList -> {
        StringBuilder sb = new StringBuilder();
        for (Product p : productList) {
            String name = p.getName();
            long price = p.getPrice();
            sb.append(name).append("\t").append(price).append("</br>");
        }
        return sb.toString();
    };

    public static void writeSuccessResponse(HttpServletResponse response, List<Product> productList) throws IOException {
        final String responseBody = HTML_RESPONSE_PATTERN.apply(PRODUCTS_TO_HTML_VIEW.apply(productList));

        writeResponse(response, HttpServletResponse.SC_OK, responseBody);
    }

    public static void writeSuccessResponse(HttpServletResponse response, String operationName, String operationValue) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(operationName).append("\n");
        sb.append(operationValue);

        final String responseBody = HTML_RESPONSE_PATTERN.apply(sb.toString());

        writeResponse(response, HttpServletResponse.SC_OK, responseBody);
    }

    public static void writeSuccessResponse(HttpServletResponse response, String operationName, Optional<Product> product) throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append(operationName).append("\n");
        product.ifPresent(i -> sb.append(i.getName()).append("\t").append(i.getPrice()).append("</br>"));

        final String responseBody = HTML_RESPONSE_PATTERN.apply(sb.toString());

        writeResponse(response, HttpServletResponse.SC_OK, responseBody);
    }

    public static void writeSuccessResponse(HttpServletResponse response, final String responseBody) throws IOException {
        writeResponse(response, HttpServletResponse.SC_OK, responseBody);
    }

    public static void writeBadRequestResponse(HttpServletResponse response, String errorDescription) throws IOException {
        writeResponse(response, HttpServletResponse.SC_BAD_REQUEST, errorDescription);
    }

    public static void writeInternalServiceErrorResponse(HttpServletResponse response, String errorDesc) throws IOException {
        writeResponse(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, errorDesc);
    }

    private static void writeResponse(HttpServletResponse response, int statusCode, String responseBody) throws IOException {
        response.setStatus(statusCode);
        if (responseBody != null) {
            response.getWriter().println(responseBody);
        }
        setCommonSuccessResponseInfo(response);
    }

    private static void setCommonSuccessResponseInfo(HttpServletResponse response) {
        response.setContentType("text/html");
    }
}

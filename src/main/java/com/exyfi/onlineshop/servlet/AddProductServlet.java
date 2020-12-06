package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
import com.exyfi.onlineshop.utils.HtmlResponseUtils;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author akirakozov
 */
public class AddProductServlet extends AbstractProductServlet {

    public AddProductServlet(DbProductQueryService dbProductQueryService) {
        super(dbProductQueryService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String productName = request.getParameter("name");
        String productPrice = request.getParameter("price");
        long price;

        if (productName == null || productPrice == null) {
            HtmlResponseUtils.writeBadRequestResponse(response, "Product name and price required");
            return;
        }
        try {
            price = Long.parseLong(request.getParameter("price"));
        } catch (NumberFormatException e) {
            HtmlResponseUtils.writeBadRequestResponse(response, "Price must be digit");
            return;
        }

        dbProductQueryService.addProduct(new Product(productName, price));

        HtmlResponseUtils.writeResponse(response, "OK");
    }
}

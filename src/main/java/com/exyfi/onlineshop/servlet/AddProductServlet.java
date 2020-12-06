package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
import com.exyfi.onlineshop.exceptions.ProductShopException;
import com.exyfi.onlineshop.utils.HtmlResponseUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * This class provides api to add new product.
 */
public class AddProductServlet extends AbstractProductServlet {

    private static final Logger log = Log.getLogger(AddProductServlet.class.getName());

    public AddProductServlet(DbProductQueryService dbProductQueryService) {
        super(dbProductQueryService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            log.info("Accepted request to add new product");
            String productName = request.getParameter("name");
            String productPrice = request.getParameter("price");
            long price;

            if (productName == null || productPrice == null) {
                HtmlResponseUtils.writeBadRequestResponse(response, "Product name and price required");
                return;
            }
            log.debug("New product info: name={}, price={}", productName, productPrice);
            try {
                price = Long.parseLong(request.getParameter("price"));
            } catch (NumberFormatException e) {
                log.warn("Provided incorrect product price {}, request failed", productPrice);
                HtmlResponseUtils.writeBadRequestResponse(response, "Price must be digit");
                return;
            }

            dbProductQueryService.addProduct(new Product(productName, price));

            HtmlResponseUtils.writeSuccessResponse(response, "OK");
            log.debug("Request to add new product finished with success");
        } catch (IOException e) {
            HtmlResponseUtils.writeInternalServiceErrorResponse(response, "INTERNAL SERVICE ERROR OCCURRED. PLEASE TRY USE THIS APP LATER");
            throw new ProductShopException("Internal service error occurred", e);
        }
    }
}

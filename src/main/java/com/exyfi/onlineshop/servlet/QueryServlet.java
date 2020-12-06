package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
import com.exyfi.onlineshop.exceptions.ProductShopException;
import com.exyfi.onlineshop.utils.HtmlResponseUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

/**
 * @author akirakozov
 */
public class QueryServlet extends AbstractProductServlet {
    public QueryServlet(DbProductQueryService dbProductQueryService) {
        super(dbProductQueryService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {

            if ("max".equals(command)) {
                Optional<Product> maxPriceProduct = dbProductQueryService.getMaxPriceProduct();

                HtmlResponseUtils.writeResponse(response, "<h1>Product with max price: </h1>", maxPriceProduct);
            } else if ("min".equals(command)) {
                Optional<Product> minPriceProduct = dbProductQueryService.getMinPriceProduct();

                HtmlResponseUtils.writeResponse(response, "<h1>Product with min price: </h1>", minPriceProduct);
            } else if ("sum".equals(command)) {
                long sum = dbProductQueryService.getProductPriceSum();

                HtmlResponseUtils.writeResponse(response, "Summary price: ", String.valueOf(sum));
            } else if ("count".equals(command)) {
                int count = dbProductQueryService.getProductsCount();

                HtmlResponseUtils.writeResponse(response, "Number of products: ", String.valueOf(count));
            } else {
                HtmlResponseUtils.writeBadRequestResponse(response, "Unknown command: " + command);
            }
        } catch (Exception e) {
            HtmlResponseUtils.writeInternalServiceErrorResponse(response, "INTERNAL SERVICE ERROR OCCURRED. PLEASE TRY USE THIS APP LATER");
            throw new ProductShopException("Internal service error occurred", e);
        }

    }
}

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
import java.util.Optional;

/**
 * This class provides common operations with products such as get max price, get min price, get sum of products price, get products count.
 */
public class QueryServlet extends AbstractProductServlet {

    private static final Logger log = Log.getLogger(QueryServlet.class.getName());

    public QueryServlet(DbProductQueryService dbProductQueryService) {
        super(dbProductQueryService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            if ("max".equals(command)) {
                log.info("Accepted request to show max price product");
                Optional<Product> maxPriceProduct = dbProductQueryService.getMaxPriceProduct();
                HtmlResponseUtils.writeSuccessResponse(response, "<h1>Product with max price: </h1>", maxPriceProduct);
                log.debug("Request show max price product finished with success");
            } else if ("min".equals(command)) {
                log.info("Accepted request to show min price product");
                Optional<Product> minPriceProduct = dbProductQueryService.getMinPriceProduct();
                HtmlResponseUtils.writeSuccessResponse(response, "<h1>Product with min price: </h1>", minPriceProduct);
                log.debug("Request min price product finished with success");
            } else if ("sum".equals(command)) {
                log.info("Accepted request to show sum price of all products");
                long sum = dbProductQueryService.getProductPriceSum();
                HtmlResponseUtils.writeSuccessResponse(response, "Summary price: ", String.valueOf(sum));
                log.debug("Request show sum price of all products finished with success");
            } else if ("count".equals(command)) {
                log.info("Accepted request to show count of products");
                long count = dbProductQueryService.getProductsCount();
                HtmlResponseUtils.writeSuccessResponse(response, "Number of products: ", String.valueOf(count));
                log.debug("Request show count of products finished with success");
            } else {
                log.info("Accepted illegal request {}", command);
                HtmlResponseUtils.writeBadRequestResponse(response, "Unknown command: " + command);
            }
        } catch (Exception e) {
            HtmlResponseUtils.writeInternalServiceErrorResponse(response, "INTERNAL SERVICE ERROR OCCURRED. PLEASE TRY USE THIS APP LATER");
            throw new ProductShopException("Internal service error occurred", e);
        }

    }
}

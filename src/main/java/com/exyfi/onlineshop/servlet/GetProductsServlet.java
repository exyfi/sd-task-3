package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
import com.exyfi.onlineshop.utils.HtmlResponseUtils;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * This class provides api to show all products.
 */
public class GetProductsServlet extends AbstractProductServlet {

    private static final Logger log = Log.getLogger(GetProductsServlet.class.getName());

    public GetProductsServlet(DbProductQueryService dbProductQueryService) {
        super(dbProductQueryService);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        try {
            log.info("Accepted request to show all products");
            List<Product> products = dbProductQueryService.getProducts();

            HtmlResponseUtils.writeSuccessResponse(response, products);
            log.debug("Request to show all products finished with success");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

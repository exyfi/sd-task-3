package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;

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

        if ("max".equals(command)) {
            try {
                Optional<Product> maxPriceProduct = dbProductQueryService.getMaxPriceProduct();
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");

                if (maxPriceProduct.isPresent()) {
                    Product product = maxPriceProduct.get();
                    response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                }
                response.getWriter().println("</body></html>");

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("min".equals(command)) {
            try {
                Optional<Product> minPriceProduct = dbProductQueryService.getMinPriceProduct();
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                if (minPriceProduct.isPresent()) {
                    Product product = minPriceProduct.get();
                    response.getWriter().println(product.getName() + "\t" + product.getPrice() + "</br>");
                }

                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("sum".equals(command)) {
            try {
                long sum = dbProductQueryService.getProductPriceSum();
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");

                response.getWriter().println(sum);

                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else if ("count".equals(command)) {
            try {

                int count = dbProductQueryService.getProductsCount();
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");

                response.getWriter().println(count);

                response.getWriter().println("</body></html>");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getWriter().println("Unknown command: " + command);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

}

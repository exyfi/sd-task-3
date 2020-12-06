package com.exyfi.onlineshop.utils;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.servlet.AddProductServlet;
import com.exyfi.onlineshop.servlet.GetProductsServlet;
import com.exyfi.onlineshop.servlet.QueryServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;


/**
 * Class configures servlet for this application.
 */
public class ServletUtils {

    private static final Logger log = Log.getLogger(ServletUtils.class.getName());

    /**
     * Configure servlet and start them on 8081 port.
     *
     * @throws Exception when occurred Exception during configuring and starting servlet
     */
    public static void startServlet() throws Exception {
        Server server = new Server(8081);

        log.info("configuring and starting servlet");
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(new DbProductQueryService())), "/add-product");
        log.info("added AddProductServlet");
        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
        log.info("added GetProductsServlet");
        context.addServlet(new ServletHolder(new QueryServlet()), "/query");
        log.info("added QueryServlet");


        server.start();
        log.info("servlet started successfully");
        server.join();
    }
}

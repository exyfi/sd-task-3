package com.exyfi.onlineshop;

import com.exyfi.onlineshop.servlet.AddProductServlet;
import com.exyfi.onlineshop.servlet.QueryServlet;
import com.exyfi.onlineshop.utils.DbUtils;
import com.exyfi.onlineshop.utils.ServletUtils;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import com.exyfi.onlineshop.servlet.GetProductsServlet;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

/**
 * @author exyfi.
 */
public class Main {
    public static void main(String[] args) throws Exception {
        DbUtils.initDb();
        ServletUtils.startServlet();
    }
}

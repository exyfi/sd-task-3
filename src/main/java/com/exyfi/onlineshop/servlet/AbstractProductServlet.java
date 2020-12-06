package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;

import javax.servlet.http.HttpServlet;

/**
 * Abstract servlet.
 */
public class AbstractProductServlet extends HttpServlet {

    protected final DbProductQueryService dbProductQueryService;

    AbstractProductServlet(DbProductQueryService dbProductQueryService) {
        this.dbProductQueryService = dbProductQueryService;
    }
}

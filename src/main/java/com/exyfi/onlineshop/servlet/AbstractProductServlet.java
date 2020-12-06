package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;

import javax.servlet.http.HttpServlet;

public class AbstractProductServlet extends HttpServlet {

    final DbProductQueryService dbProductQueryService;

    AbstractProductServlet(DbProductQueryService dbProductQueryService) {
        this.dbProductQueryService = dbProductQueryService;
    }
}

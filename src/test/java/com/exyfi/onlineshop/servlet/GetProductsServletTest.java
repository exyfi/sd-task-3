package com.exyfi.onlineshop.servlet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class GetProductsServletTest {


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private GetProductsServlet servlet;


    @BeforeEach()
    public void init() {
        servlet = new GetProductsServlet();
    }

    @Test
    public void testGetProductsServlet() throws IOException {

    }

}
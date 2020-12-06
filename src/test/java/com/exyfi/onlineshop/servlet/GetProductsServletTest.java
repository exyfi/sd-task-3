package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
import com.sun.tools.javac.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetProductsServletTest {


    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private DbProductQueryService queryService;

    @InjectMocks
    private GetProductsServlet getProductsServlet;

    @BeforeEach
    public void init() {
        getProductsServlet = new GetProductsServlet(queryService);
    }

    @Test
    public void testGetProductsServlet() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(queryService.getProducts()).thenReturn(List.of(new Product("test", 1)));
        when(response.getWriter())
                .thenReturn(printer);

        getProductsServlet.doGet(request, response);
        printer.flush();

        final String expected = "<html><body>\n" +
                "test\t1</br>\n" +
                "</body></html>\n";

        assertEquals(expected, stringWriter.toString());
    }

}
package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
import com.exyfi.onlineshop.dao.model.Product;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private DbProductQueryService queryService;

    @InjectMocks
    private QueryServlet queryServlet;

    @BeforeEach
    public void init() {
        queryServlet = new QueryServlet(queryService);
    }

    @Test
    public void testQueryServletGetMaxPriceProduct() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("max");
        when(queryService.getMaxPriceProduct()).thenReturn(Optional.of(new Product("test", 100)));
        when(response.getWriter())
                .thenReturn(printer);

        queryServlet.doGet(request, response);
        printer.flush();

        final String expected = "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                "test\t100</br>\n" +
                "</body></html>\n";

        assertEquals(expected, stringWriter.toString());
    }

    @Test
    public void testQueryServletGetMinPriceProduct() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("min");
        when(queryService.getMinPriceProduct()).thenReturn(Optional.of(new Product("test", 1)));
        when(response.getWriter())
                .thenReturn(printer);

        queryServlet.doGet(request, response);
        printer.flush();

        final String expected = "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                "test\t1</br>\n" +
                "</body></html>\n";

        assertEquals(expected, stringWriter.toString());
    }

    @Test
    public void testQueryServletGetSumPriceProduct() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("sum");
        when(queryService.getProductPriceSum()).thenReturn(100L);
        when(response.getWriter())
                .thenReturn(printer);

        queryServlet.doGet(request, response);
        printer.flush();

        final String expected = "<html><body>\n" +
                "Summary price: \n" +
                "100\n" +
                "</body></html>\n";

        assertEquals(expected, stringWriter.toString());
    }

    @Test
    public void testQueryServletGetProductCount() throws IOException, SQLException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("count");
        when(queryService.getProductsCount()).thenReturn(100L);
        when(response.getWriter())
                .thenReturn(printer);

        queryServlet.doGet(request, response);
        printer.flush();

        final String expected = "<html><body>\n" +
                "Number of products: \n" +
                "100\n" +
                "</body></html>\n";

        assertEquals(expected, stringWriter.toString());
    }

    @Test
    public void testQueryServletIncorrectCommand() throws IOException {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(request.getParameter("command")).thenReturn("blablabla");
        when(response.getWriter())
                .thenReturn(printer);

        queryServlet.doGet(request, response);
        printer.flush();

        final String expected = "Unknown command: blablabla\n";

        assertEquals(expected, stringWriter.toString());
    }
}
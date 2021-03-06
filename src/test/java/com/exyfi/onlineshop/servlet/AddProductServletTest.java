package com.exyfi.onlineshop.servlet;

import com.exyfi.onlineshop.dao.DbProductQueryService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AddProductServletTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private AddProductServlet addProductServlet;

    @BeforeEach
    public void init() {
        addProductServlet = new AddProductServlet(new DbProductQueryService());
    }

    @Test
    public void testAddProductQuery() throws IOException {
        when(request.getParameter("name")).thenReturn("testproduct");
        when(request.getParameter("price")).thenReturn("300");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printer);

        addProductServlet.doGet(request, response);
        printer.flush();

        assertEquals(stringWriter.toString(), "OK" + System.lineSeparator());
    }

    @Test
    public void testIncorrectAddProductQueryIncorrectPrice() throws IOException {
        when(request.getParameter("name")).thenReturn("testproduct");
        when(request.getParameter("price")).thenReturn("fadsdsda");

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printer);

        addProductServlet.doGet(request, response);
        printer.flush();

        final String expected = "Price must be digit\n";

        assertEquals(stringWriter.toString(), expected);
    }


    @Test
    public void testIncorrectAddProductQueryNullInput() throws IOException {
        when(request.getParameter("name")).thenReturn(null);
        when(request.getParameter("price")).thenReturn(null);

        StringWriter stringWriter = new StringWriter();
        PrintWriter printer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(printer);

        addProductServlet.doGet(request, response);
        printer.flush();

        final String expected = "Product name and price required\n";

        assertEquals(stringWriter.toString(), expected);
    }
}
package com.radical.iqube.controller.filters;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class HomeFilterTest {
    HomeFilter filter;
    HttpServletRequest req;
    HttpServletResponse resp;
    FilterChain chain;
    RequestDispatcher dispatcher;
    @Before
    public void setUp(){
         filter = new HomeFilter();
         req = Mockito.mock(HttpServletRequest.class);
         resp = Mockito.mock(HttpServletResponse.class);
         chain = Mockito.mock(FilterChain.class);
         dispatcher = Mockito.mock(RequestDispatcher.class);
    }
    @Test
    public void redirectDueNonnullableSession() throws IOException {
        HttpSession session = Mockito.mock(HttpSession.class);
        Mockito.when(req.getSession(false)).thenReturn(session);
        filter.doFilter(req,resp,chain);
        Mockito.verify(resp,Mockito.times(1)).sendRedirect("/iqube/userPage");
    }

    @Test
    public void forwardDueSessionIsNull() throws ServletException, IOException {
        Mockito.when(req.getRequestDispatcher(Mockito.anyString())).thenReturn(dispatcher);
        Mockito.when(req.getSession(false)).thenReturn(null);
        filter.doFilter(req,resp,chain);
        Mockito.verify(dispatcher,Mockito.times(1)).forward(req,resp);
    }
}

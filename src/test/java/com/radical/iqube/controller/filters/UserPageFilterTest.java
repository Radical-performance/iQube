package com.radical.iqube.controller.filters;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.*;

public class UserPageFilterTest {

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
        UserPageFilter filter = new UserPageFilter();
    }

//    @Test
//    public void NullSessionCase() throws IOException {
//        when(req.getSession(false)).thenReturn(mock(HttpSession.class));
//        when(req.getRequestDispatcher("/userPage")).thenReturn(dispatcher);
//        filter.doFilter(req,resp,chain);
////        verify(resp, times(1)).sendRedirect("/iqube/userPage");
//    }

    @Test
    public void NonNullSessionCase() throws ServletException, IOException {
        when(req.getSession(false)).thenReturn(null);
        when(req.getRequestDispatcher("/home")).thenReturn(dispatcher);
        filter.doFilter(req,resp,chain);
        verify(req,times(1)).getRequestDispatcher("/home");
        verify(dispatcher,times(1)).forward(req,resp);

    }
}
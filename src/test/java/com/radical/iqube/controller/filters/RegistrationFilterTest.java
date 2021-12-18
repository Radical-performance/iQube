package com.radical.iqube.controller.filters;

import org.junit.Before;
import org.mockito.Mockito;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistrationFilterTest {
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


}

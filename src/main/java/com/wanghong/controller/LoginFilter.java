package com.wanghong.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wanghong.po.User;

public class LoginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		User user = (User) httpRequest.getSession().getAttribute("userInfo");
		String url = httpRequest.getRequestURI();
		if(url.contains("login") || user != null || url.contains("media") || !url.contains("downloadFile.jsp") || !url.contains("h5")){
			chain.doFilter(httpRequest, httpResponse);
		}else{
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.jsp");
		}
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

}

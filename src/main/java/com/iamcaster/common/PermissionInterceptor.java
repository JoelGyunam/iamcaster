package com.iamcaster.common;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class PermissionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(
			HttpServletRequest request
			,HttpServletResponse response
			,Object Handler
			) throws IOException {
		HttpSession session = request.getSession();
		Integer UID = (Integer) session.getAttribute("UID");
		String uri = request.getRequestURI();
		
		if(UID == null) {
			if(uri.startsWith("/main")) {
				response.sendRedirect("/greeting");
				return false;
			}
		} else
			if(uri.startsWith("/greeting")) {
				response.sendRedirect("/main/predict");
				return false;
			}
		return true;
	}
}
